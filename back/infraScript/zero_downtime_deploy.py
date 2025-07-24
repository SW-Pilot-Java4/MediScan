#!/usr/bin/env python3

import os
import requests  # HTTP 요청을 위한 모듈 (서비스 준비 상태 확인용)
import subprocess
import time
from typing import Dict, Optional

class ServiceManager:
    """
    8080 포트로 들어온 요청을 socat을 통해 내부의 Docker 컨테이너 (8081 or 8082)로 전달.
    컨테이너를 번갈아 실행하며 새로운 버전을 띄우고, 준비가 완료되면 트래픽을 새 컨테이너로 전환함.
    """

    def __init__(self, socat_port: int = 8080, sleep_duration: int = 3) -> None:
        # socat이 바인딩할 외부 포트
        self.socat_port: int = socat_port
        # 서비스 준비 상태 재확인 대기 시간 (초)
        self.sleep_duration: int = sleep_duration
        # 컨테이너 이름과 포트 매핑 (두 개의 컨테이너를 번갈아 사용)
        self.services: Dict[str, int] = {
            'mediscan_1': 8081,
            'mediscan_2': 8082
        }
        self.current_name: Optional[str] = None
        self.current_port: Optional[int] = None
        self.next_name: Optional[str] = None
        self.next_port: Optional[int] = None
        # ❗ 나중에 ghcr 이미지 주소를 본인의 GitHub 사용자명으로 수정해야 함
        self.image_url: str = "ghcr.io/sw-pilot-java4/mediscan:latest"

    def _find_current_service(self) -> None:
        """
        socat이 현재 어떤 포트에 바인딩되어 있는지 확인하여,
        그 포트를 사용하는 컨테이너를 '현재 서비스'로 판단함.
        """
        cmd = f"ps aux | grep 'socat -t0 TCP-LISTEN:{self.socat_port}' | grep -v grep | awk '{{print $NF}}'"
        current_service = subprocess.getoutput(cmd)
        if not current_service:
            # socat이 실행 중이 아니면 기본적으로 mediscan_2가 현재 서비스라고 간주
            self.current_name, self.current_port = 'mediscan_2', self.services['mediscan_2']
        else:
            self.current_port = int(current_service.split(':')[-1])
            self.current_name = next((name for name, port in self.services.items() if port == self.current_port), None)

    def _find_next_service(self) -> None:
        """
        현재 실행 중인 컨테이너가 아닌 쪽을 '다음 서비스'로 설정함.
        """
        self.next_name, self.next_port = next(
            ((name, port) for name, port in self.services.items() if name != self.current_name),
            (None, None)
        )

    def _remove_container(self, name: str) -> None:
        """
        해당 이름의 Docker 컨테이너를 중지하고 제거함 (존재하지 않아도 무시)
        """
        os.system(f"docker stop {name} 2> /dev/null")
        os.system(f"docker rm -f {name} 2> /dev/null")

    def _run_container(self, name: str, port: int) -> None:
        """
        새로운 컨테이너를 실행함.
        주의: 볼륨 마운트, 로그 설정, 환경 변수는 필요에 따라 수정해야 함.
        """
        os.system(
            f"docker run --name={name} "
            f"-p {port}:8080 "
            f"--restart unless-stopped "
            f"-e TZ=Asia/Seoul "
            f"--pull always "
            f"--network mediscan-net "
            f"-d {self.image_url}"
        )

    def _switch_port(self) -> None:
        """
        socat 프로세스를 종료하고, 새 포트를 향해 트래픽을 전환함
        (ex: 8080 → localhost:8082)
        """
        cmd = f"ps aux | grep 'socat -t0 TCP-LISTEN:{self.socat_port}' | grep -v grep | awk '{{print $2}}'"
        pid = subprocess.getoutput(cmd)

        if pid:
            os.system(f"kill -9 {pid} 2>/dev/null")  # 기존 socat 프로세스 종료

        # 새 컨테이너로 트래픽 전달 설정
        os.system(
            f"nohup socat -t0 TCP-LISTEN:{self.socat_port},fork,reuseaddr TCP:localhost:{self.next_port} &>/dev/null &"
        )

    def _is_service_ready(self, port: int) -> bool:
        """
        새로운 컨테이너의 /ready 엔드포인트를 호출하여 서비스 준비 상태 확인
        """
        ready_url = f"http://127.0.0.1:{port}/ready"
        for _ in range(12):  # 총 1분간 (5초 간격으로 12회 시도)
            try:
                response = requests.get(ready_url, timeout=5)
                print(f"Checking {ready_url} - Status code: {response.status_code}")
                if response.status_code == 200:
                    return True
            except requests.RequestException as e:
                print(f"Error checking service readiness on port {port}: {e}")
                time.sleep(5)
        return False

    def update_service(self) -> None:
        """
        전체 zero-downtime 배포 절차 수행
        """
        self._find_current_service()
        self._find_next_service()

        self._remove_container(self.next_name)
        self._run_container(self.next_name, self.next_port)

        while not self._is_service_ready(self.next_port):
            print(f"Waiting for {self.next_name} to be 'ready' on port {self.next_port}...")
            time.sleep(self.sleep_duration)

        print(f"{self.next_name} is now ready.")
        self._switch_port()

        if self.current_name is not None:
            self._remove_container(self.current_name)

        print("Switched service successfully!")

if __name__ == "__main__":
    manager = ServiceManager()
    manager.update_service()
