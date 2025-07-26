import React from "react";
import { useNavigate } from "react-router-dom";

function Home() {
  const navigate = useNavigate();

  const handleLogout = () => {
    // 토큰 등 로컬 저장소에서 삭제 (필요하면)
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");

    alert("로그아웃 되었습니다!");

    // 로그인 페이지로 이동
    navigate("/login");
  };

  return (
    <div className="min-h-screen flex flex-col justify-center items-center bg-gray-100 p-6">
      <div className="w-full h-full bg-white rounded shadow p-4">
        <div className="flex flex-col gap-4">
          <button className="btn btn-primary">기본 버튼 (하늘색)</button>
          <button className="btn btn-outline btn-primary">아웃라인 버튼</button>
          <button className="btn btn-primary btn-active">활성 상태</button>

          {/* 로그아웃 버튼 */}
          <button className="btn btn-error" onClick={handleLogout}>
            로그아웃
          </button>
        </div>

        <div className="flex"></div>
        <div className="bg-primary text-white p-4 mt-4">
          테마 색상 확인용 박스
        </div>

        <div className="flex">
          <a>s</a>
        </div>
        <div className="flex">
          <a>s</a>
        </div>
      </div>
    </div>
  );
}

export default Home;
