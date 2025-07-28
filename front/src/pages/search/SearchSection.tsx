import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import rq from "../../lib/rq/rq.react.ts";

function SearchSection() {
  const [name, setName] = useState("");
  const [address, setAddress] = useState("");
  const [callNumber, setCallNumber] = useState("");
  const [callNumberError, setCallNumberError] = useState("");
  const [categoryCode, setCategoryCode] = useState("");
  const [results, setResults] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const client = rq.apiEndPoints();
  const token = localStorage.getItem("accessToken");

  const navigate = useNavigate();

  const fixedCategoryOptions = [
    { code: "01", label: "종합전문병원" },
    { code: "11", label: "종합병원" },
    { code: "21", label: "병원" },
    { code: "28", label: "요양병원" },
    { code: "29", label: "정신병원" },
    { code: "31", label: "의원" },
    { code: "41", label: "치과병원" },
    { code: "51", label: "치과의원" },
    { code: "71", label: "보건소" },
    { code: "72", label: "보건지소" },
    { code: "73", label: "보건진료소" },
    { code: "92", label: "한방병원" },
    { code: "93", label: "한의원" },
  ];

  const [categoryOptions, setCategoryOptions] = useState(fixedCategoryOptions);

  const handleSearch = async (newPage = 0) => {
    try {
      const response = await client.GET("/api/hospital/search", {
        params: {
          query: {
            name: name.trim() || undefined,
            address: address.trim() || undefined,
            callNumber: callNumber.trim() || undefined,
            categoryCode: categoryCode.trim() || undefined,
            page: newPage,
            size: 10,
          },
        },
      });

      const data = response.data.data;
      setResults(data.content);
      setTotalPages(data.totalPages);
      setPage(newPage);
    } catch (error) {
      console.error("검색 중 오류:", error);
    }
  };

  return (
    <div className="min-h-screen bg-base-200 flex flex-col items-center justify-center px-4 py-12">
      {/* 검색 박스 */}
      <div className="w-full max-w-3xl bg-white rounded-xl shadow-lg p-6 space-y-4">
        <h2 className="text-2xl font-bold text-center">🔍 병원 검색</h2>

        <input
          type="text"
          placeholder="병원 이름"
          value={name}
          onChange={(e) => setName(e.target.value)}
          className="w-full border rounded px-3 py-2 outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="text"
          placeholder="병원 주소"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
          className="w-full border rounded px-3 py-2 outline-none focus:ring-2 focus:ring-blue-500"
        />
        <div>
          <input
            type="text"
            placeholder="전화번호 (숫자만 입력)"
            value={callNumber}
            onChange={(e) => {
              const value = e.target.value;
              if (/^\d*$/.test(value)) {
                setCallNumber(value);
                setCallNumberError("");
              } else {
                setCallNumber(value);
                setCallNumberError("전화번호는 숫자만 입력해야 합니다.");
              }
            }}
            className="w-full border rounded px-3 py-2 outline-none focus:ring-2 focus:ring-blue-500"
          />
          {callNumberError && (
            <p className="text-sm text-red-500 mt-1">{callNumberError}</p>
          )}
        </div>

        <select
          value={categoryCode}
          onChange={(e) => setCategoryCode(e.target.value)}
          className="w-full border rounded px-3 py-2 outline-none focus:ring-2 focus:ring-blue-500"
        >
          <option value="">- 병원 카테고리 선택 -</option>
          {categoryOptions.map((opt) => (
            <option key={opt.code} value={opt.code}>
              {opt.label}
            </option>
          ))}
        </select>

        <button
          onClick={() => handleSearch(0)}
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
        >
          검색
        </button>
      </div>

      {/* 검색 결과 */}
      <div className="w-full max-w-3xl mt-8 space-y-4">
        {results.map((hospital: any) => (
          <div
            key={hospital.hospitalCode}
            className="border rounded-lg p-4 bg-white shadow-sm"
            onClick={() => {
              const baseUrl = import.meta.env.VITE_CORE_FRONT_BASE_URL;
              window.location.href = `${baseUrl}/hospital/${hospital.hospitalCode}`;
            }}
          >
            <p className="font-semibold text-lg">{hospital.name}</p>
            <p className="text-sm text-gray-600">{hospital.address}</p>
            <p className="text-sm text-gray-600">{hospital.callNumber}</p>
          </div>
        ))}

        {/* 페이지네이션 */}
        {totalPages > 0 && (
          <div className="flex justify-center items-center gap-4 mt-4">
            <button
              onClick={() => handleSearch(page - 1)}
              disabled={page <= 0}
              className="px-4 py-1 rounded bg-gray-200 hover:bg-gray-300 disabled:opacity-50"
            >
              ◀ 이전
            </button>
            <span>
              페이지 {page + 1} / {totalPages}
            </span>
            <button
              onClick={() => handleSearch(page + 1)}
              disabled={page + 1 >= totalPages}
              className="px-4 py-1 rounded bg-gray-200 hover:bg-gray-300 disabled:opacity-50"
            >
              다음 ▶
            </button>
          </div>
        )}
      </div>
    </div>
  );
}

export default SearchSection;
