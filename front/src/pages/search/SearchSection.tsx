import { useEffect, useState } from "react";
import rq from "../../lib/rq/rq.react.ts";
import "./SearchSection.css";
function SearchSection() {
  //병원 검색 상태
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
const [categoryOptions, setCategoryOptions] = useState<CategoryOptionType[]>([]);

useEffect(() => {
  const fetchCategoryCodes = async () => {
    try {
      const res = await client.GET("/api/hospital/category-codes");
      
      // 올바른 타입 단정 및 저장
      const data = res?.data?.data as CategoryOptionType[]; 

      setCategoryOptions(data);
    } catch (err) {
      console.error("카테고리 불러오기 실패:", err);
      setCategoryOptions([]);
    }
  };

  fetchCategoryCodes();
}, []);

console.log("📦 categoryOptions 상태", categoryOptions);
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
      const client = rq.apiEndPoints();
      const response = await client.GET("/api/hospital/search", {
    params: {
      query: {
        name: name.trim() === "" ? undefined : name,
        address: address.trim() === "" ? undefined : address,
        callNumber: callNumber.trim() === "" ? undefined : callNumber,
        categoryCode: categoryCode.trim() === "" ? undefined : categoryCode,
        page: newPage,
        size: 10,
      }
    }
  });


    console.log("📥 서버 응답 전체:", response);
    console.log("📥 응답 data.data:", response.data?.data);

    const data = response.data.data;
    setResults(data.content);
    setTotalPages(data.totalPages);

    // 이 부분 수정: newPage를 그대로 사용
    setPage(newPage);
  } catch (error) {
    console.error("검색 중 오류 발생:", error);
  }
};

  return (
    //검색 ui
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
              // setCallNumber(e.target.value)
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
              {option.label} {/* ✅ 꼭 문자열로 */}
            </option>
          ))}
        </select>

        <button onClick={() => handleSearch(0)}>검색</button>
      </div>
      {/*검색 결과*/}
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
