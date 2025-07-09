import React, { useEffect, useRef, useState } from "react";
import axios from "axios";

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
  const mapRef = useRef<HTMLDivElement>(null);
  const [hospitals, setHospitals] = useState<Hospital[]>([]);
  const [userLocation, setUserLocation] = useState<{
    lat: number;
    lng: number;
  } | null>(null);
  const [userAddress, setUserAddress] = useState<string>("");
  const [geoError, setGeoError] = useState<string>("");

  // 1. 현재 위치 + 주소 받아오기
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

  // 2. 병원 데이터 가져오기
  useEffect(() => {
    axios
      .get<Hospital[]>("http://localhost:8080/hospitals")
      .then((res) => setHospitals(res.data))
      .catch((e) => console.error("병원 데이터 오류:", e));
  }, []);

  // 3. 지도 표시 및 마커 클릭 토글 기능 구현
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

      const map = new window.kakao.maps.Map(mapRef.current, {
        center: new window.kakao.maps.LatLng(
          userLocation.lat,
          userLocation.lng
        ),
        level: 6,
      });

      const bounds = new window.kakao.maps.LatLngBounds();

      // 내 위치 별 아이콘 마커 만들기 (InfoWindow 없이)
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

      bounds.extend(
        new window.kakao.maps.LatLng(userLocation.lat, userLocation.lng)
      );

      // 병원 마커용 infoWindow 하나 생성 (공유)
      const infoWindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      let currentOpenedMarker: any = null;

      hospitals.forEach((hospital) => {
        const lat = parseFloat(hospital.longitude);
        const lng = parseFloat(hospital.latitude);

        if (isNaN(lat) || isNaN(lng)) return;

        const distance = getDistanceFromLatLonInKm(
          userLocation.lat,
          userLocation.lng,
          lat,
          lng
        );

        if (distance <= 1) {
          const marker = new window.kakao.maps.Marker({
            map,
            position: new window.kakao.maps.LatLng(lat, lng),
            title: hospital.name,
          });

          window.kakao.maps.event.addListener(marker, "click", () => {
            if (currentOpenedMarker === marker) {
              infoWindow.close();
              currentOpenedMarker = null;
              return;
            }
            infoWindow.setContent(
              `<div style="padding:5px;">${hospital.name}<br/>${hospital.address}</div>`
            );
            infoWindow.open(map, marker);
            currentOpenedMarker = marker;
          });

          bounds.extend(marker.getPosition());
        }
      });

      map.setBounds(bounds);
    });
  }, [hospitals, userLocation, userAddress]);

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

// 거리 계산 함수
function getDistanceFromLatLonInKm(
  lat1: number,
  lon1: number,
  lat2: number,
  lon2: number
) {
  const R = 6371;
  const dLat = deg2rad(lat2 - lat1);
  const dLon = deg2rad(lon2 - lon1);
  const a =
    Math.sin(dLat / 2) ** 2 +
    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) ** 2;
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return R * c;
}

function deg2rad(deg: number) {
  return deg * (Math.PI / 180);
}
