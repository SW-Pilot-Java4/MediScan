import { useEffect, useState } from "react";
import rq from "../../lib/rq/rq.react.ts";
import "./SearchSection.css";

function SearchSection() {
  // 병원 검색 상태
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

  type CategoryOptionType = {
    code: string;
    label: string;
  };

  const fixedCategoryOptions: CategoryOptionType[] = [
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

  const [categoryOptions, setCategoryOptions] =
    useState<CategoryOptionType[]>(fixedCategoryOptions);

  const handleSearch = async (newPage = 0) => {
    console.log("📤 검색 요청:", {
      name,
      address,
      callNumber,
      categoryCode,
      page: newPage,
      size: 10,
    });

    try {
      const response = await client.GET("/api/hospital/search", {
        params: {
          query: {
            name: name.trim() === "" ? undefined : name,
            address: address.trim() === "" ? undefined : address,
            callNumber: callNumber.trim() === "" ? undefined : callNumber,
            categoryCode: categoryCode.trim() === "" ? undefined : categoryCode,
            page: newPage,
            size: 10,
          },
        },
      });

      console.log("📥 서버 응답 전체:", response);
      console.log("📥 응답 data.data:", response.data?.data);

      const data = response.data.data;
      setResults(data.content);
      setTotalPages(data.totalPages);
      setPage(newPage);
    } catch (error) {
      console.error("검색 중 오류 발생:", error);
    }
  };

  return (
    <div>
      <div className="search-container">
        <h2>병원 검색</h2>
        <input
          type="text"
          placeholder="병원 이름"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          type="text"
          placeholder="병원 주소"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
        />
        <div className="input-wrapper">
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
          />
          {callNumberError && <p className="error-text">{callNumberError}</p>}
        </div>
        <select
          value={categoryCode}
          onChange={(e) => setCategoryCode(e.target.value)}
        >
          <option value="">-병원 카테고리 선택-</option>
          {categoryOptions.map((option) => (
            <option key={option.code} value={option.code}>
              {option.label}
            </option>
          ))}
        </select>

        <button onClick={() => handleSearch(0)}>검색</button>
      </div>

      {/* 검색 결과 */}
      <div className="search-results">
        {results.map((hospital: any) => (
          <div key={hospital.hospitalCode} className="result-item">
            <p>
              <strong>{hospital.name}</strong>
            </p>
            <p>{hospital.address}</p>
            <p>{hospital.callNumber}</p>
          </div>
        ))}
      </div>

      {/* 페이지네이션 */}
      {totalPages > 0 && (
        <div className="pagination">
          <button onClick={() => handleSearch(page - 1)} disabled={page <= 0}>
            ◀이전
          </button>
          <span style={{ margin: "0 10px" }}>
            페이지 {page + 1}/{totalPages}
          </span>
          <button
            onClick={() => handleSearch(page + 1)}
            disabled={page + 1 >= totalPages}
          >
            다음▶
          </button>
        </div>
      )}
    </div>
  );
}

export default SearchSection;
