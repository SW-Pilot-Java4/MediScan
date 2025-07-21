import React from "react";
import { useNavigate } from "react-router-dom";

function Home() {
  const navigate = useNavigate();

  const handleLogout = () => {
    alert("로그아웃 되었습니다.");
    navigate("/login");
  };

  return (
    <div className="mainPage">
      <h1>메인 페이지</h1>
      <p>환영합니다! 🎉</p>
      <button onClick={handleLogout}>로그아웃</button>
    </div>
  );
}

export default Home;
