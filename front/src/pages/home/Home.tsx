import React from "react";

function Home() {
  return (
    <div className="min-h-screen flex flex-col justify-center items-center bg-gray-100 p-6">
      <div className="w-full h-full bg-white rounded shadow p-4">
        <div className="flex flex-col gap-4">
          <button className="btn btn-primary">기본 버튼 (하늘색)</button>
          <button className="btn btn-outline btn-primary">아웃라인 버튼</button>
          <button className="btn btn-primary btn-active">활성 상태</button>
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

{
  /* <h1>메인 페이지</h1>
      <p>환영합니다! 🎉</p> */
}
