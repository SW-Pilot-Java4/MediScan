interface KakaoLatLng {
  getLat(): number;
  getLng(): number;
}

interface KakaoMapOptions {
  center: KakaoLatLng;
  level: number;
}

interface KakaoMap {
  setCenter(latlng: KakaoLatLng): void;
  setBounds(bounds: KakaoLatLngBounds): void;
}

interface KakaoMarkerOptions {
  map: KakaoMap;
  position: KakaoLatLng;
  title?: string;
  image?: KakaoMarkerImage;
}

interface KakaoMarker {
  setMap(map: KakaoMap | null): void;
  getPosition(): kakao.maps.LatLng;
}

interface KakaoMaps {
  LatLng: new (lat: number, lng: number) => KakaoLatLng;
  LatLngBounds: new () => KakaoLatLngBounds;
  Map: new (container: HTMLElement, options: KakaoMapOptions) => KakaoMap;
  Marker: new (options: KakaoMarkerOptions) => KakaoMarker;
  InfoWindow: new (options: KakaoInfoWindowOptions) => KakaoInfoWindow;
  Size: new (width: number, height: number) => KakaoSize;
  MarkerImage: new (src: string, size: KakaoSize) => KakaoMarkerImage;
  event: KakaoEvent;
  load: (callback: () => void) => void;
}

interface Kakao {
  maps: KakaoMaps;
}

declare global {
  interface Window {
    kakao: Kakao;
  }
}

export {}; // 모듈화 방지용 빈 export
