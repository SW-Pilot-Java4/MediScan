declare global {
  interface Window {
    kakao: Kakao;
  }

  declare var kakao: Kakao;

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
    setLevel(level: number): void;
    setDraggable(draggable: boolean): void;
    setZoomable(zoomable: boolean): void; // 추가
  }

  interface KakaoMarkerOptions {
    map: KakaoMap;
    position: KakaoLatLng;
    title?: string;
    image?: KakaoMarkerImage;
  }

  interface KakaoMarker {
    setMap(map: KakaoMap | null): void;
    getPosition(): KakaoLatLng; // kakao.maps.LatLng → KakaoLatLng
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
    services: KakaoServices;
  }

  interface Kakao {
    maps: KakaoMaps;
  }

  interface KakaoGeocoderResult {
    address_name: string;
    y: string;
    x: string;
    // 필요에 따라 추가
  }

  type KakaoStatus = "OK" | "ERROR" | "ZERO_RESULT";

  interface KakaoGeocoderResult {
    address_name: string;
    y: string;
    x: string;
  }

  interface KakaoGeocoder {
    addressSearch(
      address: string,
      callback: (result: KakaoGeocoderResult[], status: KakaoStatus) => void
    ): void;
  }

  interface KakaoPlaceResult {
    id: string;
    place_name: string;
    x: string;
    y: string;
    address_name: string;
  }

  interface KakaoPlaces {
    keywordSearch(
      keyword: string,
      callback: (result: KakaoPlaceResult[], status: KakaoStatus) => void
    ): void;
    categorySearch(
      categoryCode: string,
      callback: (result: KakaoPlaceResult[], status: KakaoStatus) => void,
      options?: {
        location?: KakaoLatLng;
        radius?: number;
        sort?: "accuracy" | "distance";
      }
    ): void;
  }

  // ✅ 여기 Status 추가
  interface KakaoServices {
    Geocoder: new () => KakaoGeocoder;
    Places: new () => KakaoPlaces;
    Status: {
      OK: "OK";
      ERROR: "ERROR";
      ZERO_RESULT: "ZERO_RESULT";
    };
  }
}

export {};
