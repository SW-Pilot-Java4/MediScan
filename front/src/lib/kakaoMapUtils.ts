export function showHospitalMarkerOnMap(
  mapId: string,
  hospital: {
    name: string;
    address: string;
    latitude?: string;
    longitude?: string;
  }
) {
  window.kakao.maps.load(() => {
    (async () => {
      const container = document.getElementById(mapId);
      if (!container) {
        console.warn(`지도 엘리먼트 #${mapId}를 찾을 수 없습니다.`);
        return;
      }

      const mapOption = {
        center: new window.kakao.maps.LatLng(37.5665, 126.978),
        level: 4,
        disableDoubleClick: true,
        scrollwheel: false,
        draggable: false,
      };

      const map = new window.kakao.maps.Map(container, mapOption);

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

      let latLng: InstanceType<typeof window.kakao.maps.LatLng> | null = null;

      if (hospital.latitude && hospital.longitude) {
        const lat = parseFloat(hospital.latitude);
        const lng = parseFloat(hospital.longitude);
        if (!isNaN(lat) && !isNaN(lng)) {
          latLng = new window.kakao.maps.LatLng(lat, lng);
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

      // 마커 클릭 시 infoWindow 열기
      window.kakao.maps.event.addListener(marker, "click", () => {
        infoWindow.open(map, marker);
      });

      map.setCenter(latLng);
    })();
  });
}
