import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { showHospitalMarkerOnMap } from "../../KakaoMap";
import rq from "../../lib/rq/rq.react.ts";

function HospitalDetail() {
  // const baseUrl = import.meta.env.VITE_API_BASE_URL;
  const { hospitalCode } = useParams();
  const [hospital, setHospital] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [selectedTab, setSelectedTab] = useState("general");

  useEffect(() => {
    const fetchHospital = async () => {
      setLoading(true);
      try {
        const client = rq.apiEndPoints();

        const res = await client.GET("/api/hospital/{hospitalCode}", {
          params: {
            path: { hospitalCode },
          },
        });

        if (!res.data) throw new Error("병원 데이터를 불러오지 못했습니다.");
        setHospital(res.data.data); // ApiResponse<T> 구조상 data.data
      } catch (err: any) {
        setError(err.message || "알 수 없는 에러");
      } finally {
        setLoading(false);
      }
    };

    fetchHospital();
  }, [hospitalCode]);

  useEffect(() => {
    if (hospital?.baseInfo) {
      showHospitalMarkerOnMap("detail-map", {
        name: hospital.baseInfo.name,
        address: hospital.baseInfo.address,
        latitude: hospital.baseInfo.latitude,
        longitude: hospital.baseInfo.longitude,
      });
    }
  }, [hospital]);

  if (loading) return <p>로딩 중...</p>;
  if (error) return <p className="text-red-500">에러: {error}</p>;
  if (!hospital) return <p>데이터가 없습니다.</p>;

  const { baseInfo, detailInfo, gradeInfo } = hospital;

  // 🔧 공통 박스 스타일
  const boxStyle = {
    width: "100%",
    maxWidth: "800px",
    padding: "24px",
    backgroundColor: "#ffffff",
    borderRadius: "16px",
    boxShadow: "0 4px 12px rgba(0, 0, 0, 0.1)",
  };

  return (
    <div className="flex flex-col items-center p-6 bg-white min-h-screen">
      {/* 병원 기본 정보 */}
      <div style={{ ...boxStyle, marginBottom: "24px" }}>
        <h2
          style={{
            fontSize: "1.25rem",
            fontWeight: "600",
            marginBottom: "16px",
          }}
        >
          기본 정보
        </h2>
        <div
          style={{
            fontWeight: "bold",
            fontSize: "1.125rem",
            marginBottom: "4px",
          }}
        >
          {baseInfo.name}
        </div>
        <div style={{ marginBottom: "4px" }}>
          <span>{baseInfo.address}</span>
        </div>
        <div>
          <span>
            전화 번호 : <span>{baseInfo.callNumber}</span>
          </span>
        </div>
      </div>

      {/* 병원 상세 정보 */}
      <div style={{ ...boxStyle, marginBottom: "24px" }}>
        <h2
          style={{
            fontSize: "1.25rem",
            fontWeight: "600",
            marginBottom: "16px",
          }}
        >
          병원 상세 정보
        </h2>

        {/* 진료 유형 선택 탭 */}
        <div className="flex gap-4 justify-center mb-6">
          <button
            onClick={() => setSelectedTab("general")}
            className="flex items-center justify-center"
            style={{
              padding: "0.5rem 1rem",
              border: "2px solid #38bdf8", // sky-400
              color: selectedTab === "general" ? "#fff" : "#38bdf8",
              backgroundColor:
                selectedTab === "general" ? "#38bdf8" : "transparent",
              borderRadius: "0.5rem",
              fontWeight: 600,
              cursor: "pointer",
              transition: "all 0.2s ease",
              marginBottom: "4px",
            }}
          >
            일반 진료 정보
          </button>

          <button
            onClick={() => setSelectedTab("emergency")}
            className="flex items-center justify-center"
            style={{
              padding: "0.5rem 1rem",
              border: "2px solid #f87171", // red-400
              color: selectedTab === "emergency" ? "#fff" : "#f87171",
              backgroundColor:
                selectedTab === "emergency" ? "#f87171" : "transparent",
              borderRadius: "0.5rem",
              fontWeight: 600,
              cursor: "pointer",
              transition: "all 0.2s ease",
              marginBottom: "4px",
            }}
          >
            응급 진료 정보
          </button>

          <button
            onClick={() => setSelectedTab("schedule")}
            className="flex items-center justify-center"
            style={{
              padding: "0.5rem 1rem",
              border: "2px solid #4ade80", // green-400
              color: selectedTab === "schedule" ? "#fff" : "#4ade80",
              backgroundColor:
                selectedTab === "schedule" ? "#4ade80" : "transparent",
              borderRadius: "0.5rem",
              fontWeight: 600,
              cursor: "pointer",
              transition: "all 0.2s ease",
              marginBottom: "4px",
            }}
          >
            요일별 진료 시간
          </button>
        </div>

        {/* 진료 정보 테이블 */}
        <table
          className="table text-left border-t border-gray-200"
          style={{
            maxWidth: "90%",
            margin: "0 auto",
          }}
        >
          <thead>
            <tr>
              <th
                colSpan={2}
                className="py-3 px-4 text-lg font-semibold text-center text-blue-800"
                style={{
                  borderBottom: "2px solid #ccc",
                }}
              >
                진료과 정보
              </th>
            </tr>
            <tr>
              <th
                colSpan={2}
                className="py-2 px-4 text-center text-sm text-gray-700"
              >
                {detailInfo.departmentCodes &&
                detailInfo.departmentCodes.length > 0
                  ? detailInfo.departmentCodes.join(", ")
                  : "등록된 진료과 없음"}
              </th>
            </tr>
          </thead>
          <tbody>
            {selectedTab === "general" && (
              <>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    일요일 진료 여부
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.closedSunday === "Y" ? "진료 안함" : "진료함"}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    공휴일 진료 여부
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.closedHoliday === "Y" ? "진료 안함" : "진료함"}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    평일 점심시간
                  </th>
                  <td className="py-2 px-4">{detailInfo.lunchWeekday}</td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    토요일 점심시간
                  </th>
                  <td className="py-2 px-4">{detailInfo.lunchSaturday}</td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    평일 접수시간
                  </th>
                  <td className="py-2 px-4">{detailInfo.receptionWeekday}</td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    토요일 접수시간
                  </th>
                  <td className="py-2 px-4">{detailInfo.receptionSaturday}</td>
                </tr>
              </>
            )}

            {selectedTab === "emergency" && (
              <>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    주간 응급 진료
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.emergencyDayYn === "Y" ? "가능" : "불가능"}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    주간 응급 연락처
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.emergencyDayPhone1} /{" "}
                    {detailInfo.emergencyDayPhone2}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    야간 응급 진료
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.emergencyNightYn === "Y" ? "가능" : "불가능"}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    야간 응급 연락처
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.emergencyNightPhone1} /{" "}
                    {detailInfo.emergencyNightPhone2}
                  </td>
                </tr>
              </>
            )}
            {selectedTab === "schedule" && (
              <>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    월요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.treatMonStart} ~ {detailInfo.treatMonEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    화요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.treatTueStart} ~ {detailInfo.treatTueEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    수요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.treatWedStart} ~ {detailInfo.treatWedEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    목요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.treatThuStart} ~ {detailInfo.treatThuEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    금요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.treatFriStart} ~ {detailInfo.treatFriEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    토요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.treatSatStart} ~ {detailInfo.treatSatEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    일요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo.treatSunStart} ~ {detailInfo.treatSunEnd}
                  </td>
                </tr>
              </>
            )}
          </tbody>
        </table>
      </div>

      {/* 병원 등급 정보 */}
      <div style={{ ...boxStyle, marginBottom: "24px" }}>
        <h2
          style={{
            fontSize: "1.25rem",
            fontWeight: "600",
            marginBottom: "16px",
          }}
        >
          병원 등급 정보
        </h2>

        <table
          className="table text-left border-t border-gray-200"
          style={{
            maxWidth: "90%",
            margin: "0 auto",
          }}
        >
          <thead>
            <tr>
              <th className="py-2 px-4 font-semibold text-gray-800">
                등급 항목
              </th>
              <th className="py-2 px-4 font-semibold text-gray-800">등급</th>
            </tr>
          </thead>
          <tbody>
            {gradeInfo.grades &&
              Object.entries(gradeInfo.grades)
                .filter(([_, value]) => value && value >= 1 && value <= 5)
                .map(([key, value]) => (
                  <tr key={key} className="border-b">
                    <td className="py-2 px-4">{key}</td>
                    <td className="py-2 px-4">{value}등급</td>
                  </tr>
                ))}
          </tbody>
        </table>
      </div>

      {/* 지도 영역 */}
      <div style={{ ...boxStyle }}>
        <div
          id="detail-map"
          style={{ width: "100%", height: "300px", borderRadius: "8px" }}
        />
      </div>
    </div>
  );
}

export default HospitalDetail;
