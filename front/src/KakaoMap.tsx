import React, { useEffect, useRef, useState } from "react";
import axios from "axios";

// Google API를 사용해 위경도로 주소를 가져오는 함수
const GOOGLE_API_KEY = "AIzaSyDcIVcjPP-0zmLtZV4nXhdoZfCCHDTy_ng";

const getAddressFromCoordinates = async (lat: number, lng: number) => {
  const response = await axios.get(
    `https://maps.googleapis.com/maps/api/geocode/json`,
    {
      params: {
        latlng: `${lat},${lng}`,
        key: GOOGLE_API_KEY,
      },
    }
  );
  return response.data;
};

// 백엔드에서 받아오는 병원 객체의 타입
interface Hospital {
  hospitalCode: string;
  name: string;
  code: number;
  address: string;
  callNumber: string;
  latitude: string; // 위도
  longitude: string; // 경도
}

const KakaoMap: React.FC = () => {
  const mapRef = useRef<HTMLDivElement>(null); // 지도를 렌더링할 div 참조
  const [hospitals, setHospitals] = useState<Hospital[]>([]); // 병원 리스트 상태
  const [userLocation, setUserLocation] = useState<{
    lat: number;
    lng: number;
  } | null>(null); // 사용자 위치
  const [userAddress, setUserAddress] = useState<string>(""); // 사용자 주소
  const [geoError, setGeoError] = useState<string>(""); // 위치 에러 메시지

  // 1. 사용자 현재 위치와 주소 가져오기
  useEffect(() => {
    if (!navigator.geolocation) {
      setGeoError("브라우저에서 위치 정보를 지원하지 않습니다.");
      return;
    }

    navigator.geolocation.getCurrentPosition(
      async (pos) => {
        setGeoError("");
        const lat = pos.coords.latitude;
        const lng = pos.coords.longitude;
        console.log("📍 내 위치 위경도:", { lat, lng });

        setUserLocation({ lat, lng });

        try {
          const addressData = await getAddressFromCoordinates(lat, lng);
          const address = addressData.results[0]?.formatted_address;
          setUserAddress(address || "주소 불러오기 실패");
        } catch (e) {
          console.error("주소 변환 실패:", e);
          setUserAddress("주소 변환 실패");
        }
      },
      (error) => {
        console.error("위치 접근 실패:", error);
        setGeoError(`위치 접근 실패: ${error.message} (code: ${error.code})`);
      },
      {
        enableHighAccuracy: true,
        timeout: 10000,
        maximumAge: 0,
      }
    );
  }, []);

  // 2. 병원 데이터 가져오기 (백엔드 API 호출)
  useEffect(() => {
    axios
      .get<Hospital[]>("http://localhost:8080/hospitals")
      .then((res) => {
        console.log("🚑 가져온 병원 데이터:", res.data);
        setHospitals(res.data);
      })
      .catch((e) => console.error("병원 데이터 오류:", e));
  }, []);

  // 3. 지도 렌더링 및 병원 마커 표시, 클릭 시 InfoWindow 토글 기능 구현
  useEffect(() => {
    if (
      !window.kakao ||
      !window.kakao.maps ||
      !userLocation ||
      hospitals.length === 0
    )
      return;

    window.kakao.maps.load(() => {
      if (!mapRef.current) return;

      // 지도 생성
      const map = new window.kakao.maps.Map(mapRef.current, {
        center: new window.kakao.maps.LatLng(
          userLocation.lat,
          userLocation.lng
        ),
        level: 6, // 줌 레벨
      });

      // 내 위치를 별 아이콘 마커로 표시
      const imageSrc =
        "https://png.pngtree.com/png-vector/20220625/ourmid/pngtree-vector-icon-of-star-shape-png-image_5357019.png";
      const imageSize = new window.kakao.maps.Size(24, 35);
      const markerImage = new window.kakao.maps.MarkerImage(
        imageSrc,
        imageSize
      );

      new window.kakao.maps.Marker({
        position: new window.kakao.maps.LatLng(
          userLocation.lat,
          userLocation.lng
        ),
        image: markerImage,
        map,
        title: "내 위치 (별 표시)",
      });

      // InfoWindow 생성 (마커 클릭 시 보여줄 팝업)
      const infoWindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });
      let currentOpenedMarker: any = null; // 현재 열린 마커 추적용

      // 병원 리스트 마커 생성 및 클릭 이벤트 등록
      hospitals.forEach((hospital) => {
        const lat = parseFloat(hospital.longitude);
        const lng = parseFloat(hospital.latitude);

        console.log(`🏥 병원 ${hospital.name} 위치: (${lat}, ${lng})`);

        if (isNaN(lat) || isNaN(lng)) return;

        // 내 위치 기준 3km 이내 병원만 표시
        const distance = getDistanceFromLatLonInKm(
          userLocation.lat,
          userLocation.lng,
          lat,
          lng
        );

        if (distance <= 3) {
          const marker = new window.kakao.maps.Marker({
            map,
            position: new window.kakao.maps.LatLng(lat, lng),
            title: hospital.name,
          });

          // 클릭 시 InfoWindow 열기/닫기 토글
          window.kakao.maps.event.addListener(marker, "click", () => {
            if (currentOpenedMarker === marker) {
              infoWindow.close(); // 이미 열려있으면 닫음
              currentOpenedMarker = null;
              return;
            }
            infoWindow.setContent(
              `<div style="padding:5px;">
                <strong>${hospital.name}</strong><br/>
                주소: ${hospital.address}<br/>
                전화: ${hospital.callNumber}
              </div>`
            );
            infoWindow.open(map, marker);
            currentOpenedMarker = marker;
          });
        }
      });

      // 지도 중심과 줌 레벨 고정
      map.setCenter(
        new window.kakao.maps.LatLng(userLocation.lat, userLocation.lng)
      );
      map.setLevel(5);
    });
  }, [hospitals, userLocation]);

  return (
    <div>
      {geoError ? (
        <p style={{ color: "red" }}>⚠️ {geoError}</p>
      ) : (
        <p>📍 내 위치: {userAddress || "불러오는 중..."}</p>
      )}
      <div ref={mapRef} style={{ width: "100%", height: "500px" }} />
    </div>
  );
};

export default KakaoMap;

// 두 좌표 간 거리 계산 함수 (Haversine 공식)
function getDistanceFromLatLonInKm(
  lat1: number,
  lon1: number,
  lat2: number,
  lon2: number
) {
  const R = 6371; // 지구 반지름 (km)
  const dLat = deg2rad(lat2 - lat1);
  const dLon = deg2rad(lon2 - lon1);
  const a =
    Math.sin(dLat / 2) ** 2 +
    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) ** 2;
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return R * c;
}

// 각도 → 라디안 변환
function deg2rad(deg: number) {
  return deg * (Math.PI / 180);
}

export function showHospitalMarkerOnMap(
  mapId: string,
  hospital: {
    name: string;
    address: string;
    latitude?: string;
    longitude?: string;
  }
) {
  window.kakao.maps.load(async () => {
    const container = document.getElementById(mapId);
    if (!container) return;

    const mapOption = {
      center: new window.kakao.maps.LatLng(37.5665, 126.978), // 기본 좌표 (서울 시청)
      level: 4,
      disableDoubleClickZoom: true,
      scrollwheel: false,
      draggable: false, // 드래그 비활성화
    };

    const map = new window.kakao.maps.Map(container, mapOption);

    // 좌표 변환 함수
    const geocodeToLatLng = async (address: string) => {
      return new Promise<kakao.maps.LatLng | null>((resolve) => {
        const geocoder = new window.kakao.maps.services.Geocoder();
        geocoder.addressSearch(address, (result, status) => {
          if (status === window.kakao.maps.services.Status.OK) {
            const loc = result[0];
            resolve(new window.kakao.maps.LatLng(Number(loc.y), Number(loc.x)));
          } else {
            resolve(null);
          }
        });
      });
    };

    let latLng: kakao.maps.LatLng | null = null;

    if (hospital.latitude && hospital.longitude) {
      const lat = parseFloat(hospital.latitude);
      const lng = parseFloat(hospital.longitude);
      if (!isNaN(lat) && !isNaN(lng)) {
        latLng = new window.kakao.maps.LatLng(lat, lng);
      }
    }

    // 위경도가 없거나 파싱 실패한 경우 주소로 좌표 변환
    if (!latLng) {
      latLng = await geocodeToLatLng(hospital.address);
      if (!latLng) {
        console.warn("🛑 주소로 위치를 찾을 수 없습니다.");
        return;
      }
    }

    // 마커 생성
    const marker = new window.kakao.maps.Marker({
      map,
      position: latLng,
      title: hospital.name,
    });

    const infoWindow = new window.kakao.maps.InfoWindow({
      content: `<div style="padding:5px;">${hospital.name}<br/>${hospital.address}</div>`,
    });
    infoWindow.open(map, marker);

    map.setCenter(latLng);
    map.setDraggable(false); // 확대/축소, 드래그 금지
    map.setZoomable(false);
  });
}
// ✅ 추가 작성 끝 부분
