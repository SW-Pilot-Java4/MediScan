import { useState } from "react";
import { useNavigate } from "react-router-dom";
import SearchSection from './SearchSection';

function Home() {
  const navigate = useNavigate();
  const [showSearch,setShowSearch]=useState(false);
  const handleLogout = () => {
    alert("로그아웃 되었습니다.");
    navigate("/login");
  };

  return (
    <div className="mainPage">
      <h1>메인 페이지</h1>
      <p>환영합니다! 🎉</p>
      <button onClick={handleLogout}>로그아웃</button>

      <button onClick={()=>setShowSearch(!showSearch)}>
        {showSearch ? '검색 닫기':'병원 검색'}
      </button>

      {showSearch&&<SearchSection />}
    </div>
  );
}

export default Home; // ✅ 이 줄이 없으면 import Main from './Main'이 안 됨
