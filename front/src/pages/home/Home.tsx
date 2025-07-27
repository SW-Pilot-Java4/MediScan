import React from "react";
import { useNavigate } from "react-router-dom";

function HospitalEntryPage() {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-base-200 px-4 py-10 flex flex-col items-center">
      <div className="w-full max-w-2xl flex flex-col gap-8">
        <h1 className="text-2xl font-bold text-center text-gray-800">
          원하는 병원을 쉽게 찾아보세요
        </h1>
        <p className="text-center text-gray-600 -mt-4">
          병원 이름으로 검색하거나, 지도로 주변 병원을 확인할 수 있어요.
        </p>
        {/* 병원 검색으로 찾아보기 */}
        <div
          onClick={() => navigate("/search")}
          className="bg-white bg-blue1 shadow-md rounded-xl p-6 cursor-pointer transition hover:shadow-lg hover:bg-gray-50"
        >
          <div className="flex items-center justify-center min-h-[80px]">
            <h2 className="text-xl font-semibold text-gray-800 text-center">
              🔍 병원 검색으로 찾아보기
            </h2>
          </div>
          {/* // 주소 기입 */}
        </div>

        {/* 지도로 병원 찾아보기 */}
        <div
          onClick={() => navigate("/map")}
          className="bg-white bg-blue1 shadow-md rounded-xl p-6 cursor-pointer transition hover:shadow-lg hover:bg-gray-50"
        >
          <div className="flex items-center justify-center min-h-[80px]">
            <h2 className="text-xl font-semibold text-gray-800 text-center">
              🗺️ 지도로 병원 찾아보기
            </h2>
          </div>
          {/* // 주소 기입 */}
        </div>
      </div>
      <footer>
        <div className="text-sm text-gray-500 text-center mt-12">
          MediScan은 건강 보험 심사평가원에서 <br></br>
          제공하는 정보를 바탕으로 제공합니다.
        </div>
      </footer>
    </div>
  );
}

export default HospitalEntryPage;
