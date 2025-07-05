import React, { useEffect, useRef } from "react";

interface KakaoLatLng {
  getLat(): number;
  getLng(): number;
}

interface KakaoMapOptions {
  center: KakaoLatLng;
  level: number;
}

interface Kakao {
  maps: KakaoMaps;
}

interface KakaoMaps {
  LatLng: new (lat: number, lng: number) => KakaoLatLng;
  Map: new (container: HTMLElement, options: KakaoMapOptions) => object;
  load: (callback: () => void) => void;
}

declare global {
  interface Window {
    kakao: Kakao;
  }
}

const KakaoMap: React.FC = () => {
  const mapRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!window.kakao || !window.kakao.maps) return;

    window.kakao.maps.load(() => {
      if (!mapRef.current) return;

      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      new window.kakao.maps.Map(mapRef.current, options);
    });
  }, []);

  return <div ref={mapRef} style={{ width: "500px", height: "400px" }} />;
};

export default KakaoMap;
