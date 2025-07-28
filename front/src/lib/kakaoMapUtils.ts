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

    type LatLngConstructor = typeof window.kakao.maps.LatLng;
    type LatLngInstance = InstanceType<LatLngConstructor>;

    const geocodeToLatLng = async (
      address: string
    ): Promise<LatLngInstance | null> => {
      return new Promise((resolve) => {
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

    let latLng: InstanceType<typeof window.kakao.maps.LatLng> | null = null;

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
      content: `
    <div style="
      padding: 8px;
      font-size: 14px;
      max-width: 240px;
      white-space: normal;
      word-break: break-word;
      line-height: 1.4;
    ">
      <strong>${hospital.name}</strong><br/>
      ${hospital.address}
    </div>
  `,
    });
    infoWindow.open(map, marker);

    map.setCenter(latLng);
    map.setDraggable(false); // 확대/축소, 드래그 금지
    map.setZoomable(false);
  });
}
// ✅ 추가 작성 끝 부분
