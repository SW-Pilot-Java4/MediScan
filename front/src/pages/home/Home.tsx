import React from "react";
import { useNavigate } from "react-router-dom";

function Home() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    alert("로그아웃 되었습니다!");
    navigate("/login");
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-base-200 px-4">
      <div className="w-full max-w-md bg-white rounded-xl shadow-lg p-6">
        <div className="flex flex-col gap-4 items-center">
          <button className="btn btn-primary w-full">기본 버튼 (하늘색)</button>
          <button className="btn btn-outline btn-primary w-full">
            아웃라인 버튼
          </button>
          <button className="btn btn-primary btn-active w-full">
            활성 상태
          </button>

          <button className="btn btn-error w-full" onClick={handleLogout}>
            로그아웃
          </button>
        </div>

        <div className="bg-primary text-white p-4 mt-6 rounded text-center">
          테마 색상 확인용 박스
        </div>
      </div>
    </div>
  );
}

export default Home;