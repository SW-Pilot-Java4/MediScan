import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import rq from "../../lib/rq/rq.react.ts";
import HospitalMap from "../../HospitalMap.tsx"; // 경로는 맞게 조절

interface HospitalBaseInfo {
  hospitalCode?: string;
  name?: string;
  address?: string;
  callNumber?: string;
  latitude?: string; // 서버가 string으로 보내면 string으로 맞춤
  longitude?: string;
}

interface HospitalDetailInfo {
  treatMonStart?: string;
  treatMonEnd?: string;
  treatTueStart?: string;
  treatTueEnd?: string;
  treatWedStart?: string;
  treatWedEnd?: string;
  treatThuStart?: string;
  treatThuEnd?: string;
  treatFriStart?: string;
  treatFriEnd?: string;
  treatSatStart?: string;
  treatSatEnd?: string;
  treatSunStart?: string;
  treatSunEnd?: string;

  closedSunday?: string;
  closedHoliday?: string;
  lunchWeekday?: string;
  lunchSaturday?: string;
  receptionWeekday?: string;
  receptionSaturday?: string;

  emergencyDayYn?: string;
  emergencyDayPhone1?: string;
  emergencyDayPhone2?: string;
  emergencyNightYn?: string;
  emergencyNightPhone1?: string;
  emergencyNightPhone2?: string;

  departmentCodes?: string[];
}

interface GradeInfo {
  grades?: Record<string, number | null>;
}

interface HospitalResponse {
  baseInfo?: HospitalBaseInfo;
  detailInfo?: HospitalDetailInfo;
  gradeInfo?: GradeInfo;
}

function HospitalDetail() {
  // const baseUrl = import.meta.env.VITE_API_BASE_URL;
  const hospitalCode = useParams().hospitalCode!;
  const [hospital, setHospital] = useState<HospitalResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

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
      } catch (err: unknown) {
        if (err instanceof Error) {
          setError(err.message);
        } else {
          setError("알 수 없는 에러");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchHospital();
  }, [hospitalCode]);

  if (loading) return <p>로딩 중...</p>;
  if (error) return <p className="text-red-500">에러: {error}</p>;
  if (!hospital) return <p>데이터가 없습니다.</p>;

  const { baseInfo, detailInfo, gradeInfo } = hospital;

  // baseInfo가 undefined일 때 기본값 지정
  const safeBaseInfo = baseInfo ?? {
    name: "이름 없음",
    address: "주소 없음",
    callNumber: "전화번호 없음",
  };

  // 🔧 공통 박스 스타일
  const boxStyle = {
    width: "100%",
    maxWidth: "800px",
    padding: "24px",
    backgroundColor: "#ffffff",
    borderRadius: "16px",
    boxShadow: "0 4px 12px rgba(0, 0, 0, 0.1)",
  };

  const departmentCodeMap: Record<string, string> = {
    "00": "일반의",
    "01": "내과",
    "02": "신경과",
    "03": "정신건강의학과",
    "04": "외과",
    "05": "정형외과",
    "06": "신경외과",
    "07": "심장혈관흉부외과",
    "08": "성형외과",
    "09": "마취통증의학과",
    "10": "산부인과",
    "11": "소아청소년과",
    "12": "안과",
    "13": "이비인후과",
    "14": "피부과",
    "15": "비뇨의학과",
    "16": "영상의학과",
    "17": "방사선종양학과",
    "18": "병리과",
    "19": "진단검사의학과",
    "20": "결핵과",
    "21": "재활의학과",
    "22": "핵의학과",
    "23": "가정의학과",
    "24": "응급의학과",
    "25": "직업환경의학과",
    "26": "예방의학과",
    "27": "기타1(치과)",
    "28": "기타4(한방)",
    "31": "기타2",
    "40": "기타2(2)",
    "41": "보건",
    "42": "기타3",
    "43": "보건기관치과",
    "44": "보건기관한방",
    "49": "치과",
    "50": "구강악안면외과",
    "51": "치과보철과",
    "52": "치과교정과",
    "53": "소아치과",
    "54": "치주과",
    "55": "치과보존과",
    "56": "구강내과",
    "57": "영상치의학과",
    "58": "구강병리과",
    "59": "예방치과",
    "60": "치과소계",
    "61": "통합치의학과",
    "80": "한방내과",
    "81": "한방부인과",
    "82": "한방소아과",
    "83": "한방안·이비인후·피부과",
    "84": "한방신경정신과",
    "85": "침구과",
    "86": "한방재활의학과",
    "87": "사상체질과",
    "88": "한방응급",
    "89": "한방응급",
    "90": "한방소계",
  };

  const asmGrdMap: Record<string, string> = {
    asmGrd01: "급성질환 - 급성기뇌졸증",
    asmGrd02: "급성심근경색 평가",
    asmGrd03: "만성질환 - 혈액투석",
    asmGrd04: "정신 건강 - 의료급여정신과",
    asmGrd05: "약제 - 수술적 예방적 항생제",
    asmGrd06: "급성질환 - 급성신근경색",
    asmGrd07: "약제 - 급성상기도 감염 항생제 처방률",
    asmGrd08: "약제 - 주사제 처방률",
    asmGrd09: "약제 - 약품목수",
    asmGrd10: "요양병원 - 요양병원",
    asmGrd11: "정신건강외래영역 평가",
    asmGrd12: "암질환 - 대장암",
    asmGrd13: "암질환 - 위암",
    asmGrd14: "암질환 - 유방암",
    asmGrd15: "암질환 - 폐암",
    asmGrd16: "만성질환 - 천식",
    asmGrd17: "만성질환 - 만성폐쇄성폐질환",
    asmGrd18: "급성질환 - 폐렴",
    asmGrd19: "중환자실	- 성인 중환자실",
    asmGrd20: "중환자실 - 신생아 중환자실",
    asmGrd21: "마취",
    asmGrd22: "정신 건장 - 정신건강 입원 영역",
    asmGrd23: "만성질환 - 천식",
    asmGrd24: "만성질환 - 고혈압 당뇨병",
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
          병원 기본 정보
        </h2>
        <div
          style={{
            fontWeight: "bold",
            fontSize: "1.125rem",
            marginBottom: "4px",
          }}
        >
          {safeBaseInfo.name}
        </div>
        <div style={{ marginBottom: "4px" }}>
          <span>{safeBaseInfo.address}</span>
        </div>
        <div>
          <span>
            전화 번호 : <span>{safeBaseInfo.callNumber}</span>
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
                className="py-2 px-4 text-sm text-gray-700"
                style={{
                  padding: "8px",
                }}
              >
                <div
                  style={{
                    whiteSpace: "normal",
                    wordBreak: "break-word", // ✅ 긴 단어 줄바꿈
                    overflowWrap: "break-word",
                    maxWidth: "100%",
                    textAlign: "center",
                  }}
                >
                  {detailInfo?.departmentCodes &&
                  detailInfo.departmentCodes.length > 0
                    ? detailInfo.departmentCodes
                        .map((code) => departmentCodeMap[code] || code)
                        .join(", ")
                    : "등록된 진료과 없음"}
                </div>
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
                    {detailInfo?.closedSunday === "Y" ? "진료 안함" : "진료함"}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    공휴일 진료 여부
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo?.closedHoliday === "Y" ? "진료 안함" : "진료함"}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    평일 점심시간
                  </th>
                  <td className="py-2 px-4">{detailInfo?.lunchWeekday}</td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    토요일 점심시간
                  </th>
                  <td className="py-2 px-4">{detailInfo?.lunchSaturday}</td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    평일 접수시간
                  </th>
                  <td className="py-2 px-4">{detailInfo?.receptionWeekday}</td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    토요일 접수시간
                  </th>
                  <td className="py-2 px-4">{detailInfo?.receptionSaturday}</td>
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
                    {detailInfo?.emergencyDayYn === "Y" ? "가능" : "불가능"}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    주간 응급 연락처
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo?.emergencyDayPhone1} /{" "}
                    {detailInfo?.emergencyDayPhone2}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    야간 응급 진료
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo?.emergencyNightYn === "Y" ? "가능" : "불가능"}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    야간 응급 연락처
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo?.emergencyNightPhone1} /{" "}
                    {detailInfo?.emergencyNightPhone2}
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
                    {detailInfo?.treatMonStart} ~ {detailInfo?.treatMonEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    화요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo?.treatTueStart} ~ {detailInfo?.treatTueEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    수요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo?.treatWedStart} ~ {detailInfo?.treatWedEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    목요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo?.treatThuStart} ~ {detailInfo?.treatThuEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    금요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo?.treatFriStart} ~ {detailInfo?.treatFriEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    토요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo?.treatSatStart} ~ {detailInfo?.treatSatEnd}
                  </td>
                </tr>
                <tr className="border-b">
                  <th className="py-2 px-4 font-medium text-gray-600">
                    일요일
                  </th>
                  <td className="py-2 px-4">
                    {detailInfo?.treatSunStart} ~ {detailInfo?.treatSunEnd}
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
            {gradeInfo?.grades && Object.keys(gradeInfo.grades).length > 0 ? (
              Object.entries(gradeInfo.grades)
                .filter(
                  // eslint-disable-next-line @typescript-eslint/no-unused-vars
                  ([_key, value]) =>
                    typeof value === "number" && value >= 1 && value <= 5
                )
                .map(([key, value]) => (
                  <tr key={key} className="border-b">
                    <td className="py-2 px-4">{asmGrdMap[key] ?? key}</td>
                    <td className="py-2 px-4">{value}등급</td>
                  </tr>
                ))
            ) : (
              <tr>
                <td colSpan={2} className="py-4 px-4 text-center text-gray-400">
                  등급 정보가 없습니다.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <div style={{ ...boxStyle }}>
        {hospital?.baseInfo ? (
          <HospitalMap
            hospital={{
              ...hospital.baseInfo,
              name: hospital.baseInfo.name ?? "이름 없음",
              address: hospital.baseInfo.address ?? "주소 없음",
            }}
            height={300}
          />
        ) : (
          <p>지도 정보를 표시할 병원 정보가 없습니다.</p>
        )}
      </div>
    </div>
  );
}

export default HospitalDetail;
