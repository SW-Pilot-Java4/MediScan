import { useEffect, useRef } from "react";

interface HospitalMapProps {
  hospital: {
    name: string;
    address: string;
    latitude?: string;
    longitude?: string;
  };
  mapId?: string;
  width?: string | number;
  height?: string | number;
}

const HospitalMap: React.FC<HospitalMapProps> = ({
  hospital,
  mapId = "hospital-map",
  width = "100%",
  height = 400, // 숫자는 px로 처리됨
}) => {
  const mapContainerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!hospital) return;
    if (!window.kakao || !window.kakao.maps) {
      console.warn("카카오맵 API가 로드되지 않았습니다.");
      return;
    }

    const initMap = () => {
      if (!mapContainerRef.current) {
        console.warn("지도 컨테이너가 없습니다.");
        return;
      }

      const mapOption = {
        center: new window.kakao.maps.LatLng(37.5665, 126.978),
        level: 4,
        disableDoubleClick: true,
        scrollwheel: false,
        draggable: false,
      };

      const map = new window.kakao.maps.Map(mapContainerRef.current, mapOption);

      // 좌표 변환 함수
      const geocodeToLatLng = (address: string) =>
        new Promise<InstanceType<typeof window.kakao.maps.LatLng> | null>(
          (resolve) => {
            const geocoder = new window.kakao.maps.services.Geocoder();
            geocoder.addressSearch(address, (result, status) => {
              if (status === window.kakao.maps.services.Status.OK) {
                const loc = result[0];
                resolve(
                  new window.kakao.maps.LatLng(Number(loc.y), Number(loc.x))
                );
              } else {
                resolve(null);
              }
            });
          }
        );

      (async () => {
        let latLng: InstanceType<typeof window.kakao.maps.LatLng> | null = null;

        if (hospital.latitude && hospital.longitude) {
          const lat = parseFloat(hospital.latitude);
          const lng = parseFloat(hospital.longitude);
          if (!isNaN(lat) && !isNaN(lng)) {
            latLng = new window.kakao.maps.LatLng(lng, lat);
          }
        }

        if (!latLng) {
          latLng = await geocodeToLatLng(hospital.address);
          if (!latLng) {
            console.warn("🛑 주소로 위치를 찾을 수 없습니다.");
            return;
          }
        }

        console.log("마커 위치 좌표:", latLng);

        const marker = new window.kakao.maps.Marker({
          map,
          position: latLng,
          title: hospital.name,
        });

        const infoWindow = new window.kakao.maps.InfoWindow({
          content: `
            <div style="
              padding: 8px;
              font-size: 14px;
              width: 300px;
              white-space: normal;
              word-break: break-word;
              line-height: 1.4;
            ">
              <strong>${hospital.name}</strong><br/>
              ${hospital.address}
            </div>
          `,
        });

        window.kakao.maps.event.addListener(marker, "click", () => {
          infoWindow.open(map, marker);
        });

        map.setCenter(latLng);
      })();

      // 클린업 함수: 마커 및 infoWindow 제거하려면 상태 관리 필요하므로 생략
      // 필요하면 useRef로 marker/infoWindow 저장 후 제거 가능
    };

    if (window.kakao.maps.load) {
      window.kakao.maps.load(() => {
        initMap();
      });
    } else {
      initMap();
    }
  }, [hospital]);

  return (
    <div
      id={mapId}
      ref={mapContainerRef}
      style={{ width, height, borderRadius: "8px", backgroundColor: "#eee" }}
    />
  );
};

export default HospitalMap;
