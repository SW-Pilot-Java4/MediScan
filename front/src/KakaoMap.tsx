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
  address: string;
  callNumber: string;
  code: number;
  name: string;
  latitude: string;
  longitude: string;
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

  // 사용자 현재 위치 가져오기 + 병원 데이터 받기
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

        // 사용자 위치 백엔드로 전송하고 병원 데이터 받기
        await sendUserLocationToBackend(lat, lng);

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

  const sendUserLocationToBackend = async (lat: number, lng: number) => {
    console.log(lat, lng);
    try {
      const res = await axios.get(`http://localhost:8080/api/hospital/nearby`, {
        params: {
          latitude: lat,
          longitude: lng,
        },
        withCredentials: true, // 인증 정보 필요 시 유지
      });

      console.log("✅ 병원 데이터 수신 완료:", res.data);

      if (Array.isArray(res.data.data)) {
        setHospitals(res.data.data);
      } else {
        console.error("응답 데이터 형식 오류:", res.data.data);
        setHospitals([]);
      }
    } catch (error) {
      console.error("❌ 병원 데이터 요청 실패:", error);
    }
  };
  // 지도 렌더링 + 병원 마커 표시
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

      // 내 위치 마커 (별 아이콘)
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

      const infoWindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });
      let currentOpenedMarker: any = null;

      hospitals.forEach((hospital) => {
        const lat = parseFloat(hospital.longitude);
        const lng = parseFloat(hospital.latitude);

        if (isNaN(lat) || isNaN(lng)) return;

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
            `<div style="padding:5px;">
              <strong>${hospital.name}</strong><br/>
              주소: ${hospital.address}<br/>
              전화: ${hospital.callNumber}
            </div>`
          );
          infoWindow.open(map, marker);
          currentOpenedMarker = marker;
        });
      });

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
