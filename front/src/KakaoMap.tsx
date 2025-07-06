import React, { useEffect, useRef, useState } from "react";
import axios from "axios";

interface Hospital {
  hospitalCode: string;
  name: string;
  code: number;
  address: string;
  callNumber: string;
  latitude: string; // 실질적 위도
  longitude: string; // 실질적 경도
}

const KakaoMap: React.FC = () => {
  const mapRef = useRef<HTMLDivElement>(null);
  const [hospitals, setHospitals] = useState<Hospital[]>([]);

  // 1. 병원 데이터 받아오기 (10개만 사용)
  useEffect(() => {
    axios
      .get<Hospital[]>("http://localhost:8080/hospitals")
      .then((response) => {
        const limited = response.data.slice(0, 10); // ⚠️ 제한
        console.log("받아온 병원 데이터:", limited);
        setHospitals(limited);
      })
      .catch((error) => {
        console.error("데이터 요청 중 에러:", error);
      });
  }, []);

  // 2. 카카오 맵 렌더링 및 마커 표시
  useEffect(() => {
    if (!window.kakao || !window.kakao.maps || hospitals.length === 0) return;

    window.kakao.maps.load(() => {
      if (!mapRef.current) return;

      const map = new window.kakao.maps.Map(mapRef.current, {
        center: new window.kakao.maps.LatLng(37.5665, 126.978), // 서울 중심
        level: 7,
      });

      const bounds = new window.kakao.maps.LatLngBounds();
      const infoWindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });
      let openMarker: kakao.maps.Marker | null = null;

      hospitals.forEach((hospital) => {
        const lat = parseFloat(hospital.longitude);
        const lng = parseFloat(hospital.latitude);

        if (isNaN(lat) || isNaN(lng)) {
          console.warn("유효하지 않은 좌표:", hospital);
          return;
        }

        const position = new window.kakao.maps.LatLng(lat, lng);

        const marker = new window.kakao.maps.Marker({
          map,
          position,
        });

        bounds.extend(position);

        window.kakao.maps.event.addListener(marker, "click", () => {
          if (openMarker === marker) {
            infoWindow.close();
            openMarker = null;
          } else {
            infoWindow.setContent(
              `<div style="padding:5px;font-size:14px;">${hospital.name}</div>`
            );
            infoWindow.open(map, marker);
            openMarker = marker;
          }
        });
      });

      map.setBounds(bounds);
    });
  }, [hospitals]);

  return <div ref={mapRef} style={{ width: "100%", height: "500px" }} />;
};

export default KakaoMap;
