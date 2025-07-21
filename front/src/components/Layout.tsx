import { Outlet } from "react-router-dom";

const Layout = () => {
  return (    
    <div className="h-screen flex flex-col" >
      {/* 상단 헤더 */}
      <header
        className="h-16 flex items-center justify-between px-0 border-b border-gray-700">
        {/* 좌측 로고 */}
        <a className="flex items-center" href="/">
          <img src="/logo.png" alt="MediScan Logo" className="h-10 w-auto" />
        </a>

        {/* 우측에 버튼이든 뭐든 추가하고 싶다면 여기에 */}
        <div className="text-white">
          {/* Placeholder */}
          <a className="btn btn-ghost mx-1" href="/login"
          style={{
            fontFamily: 'Arial, sans-serif',
            color: '#ADD8E6',
            padding: '4px 8px',
            fontSize: '14px'
          }}>로그인</a>
        </div>
      </header>


      {/* 본문 영역 */}
      <main className="flex-1 overflow-y-auto text-white p-0" 
      style={{
            backgroundColor: '#0000FF'
          }}>
    
        <div className="bg-blue-500 p-4 text-white rounded">
          <Outlet />
        </div>
      </main>

      {/* 하단 푸터 */}
      <footer className="h-16 text-black text-center ">
          <div className="flex w-full max-w-4xl justify-between px-4">
            <div className="bg-yellow-400 p-4 rounded w-1/4 text-sm">Box 1</div>
            <div className="bg-red-400 p-4 rounded w-1/4 text-sm">Box 2</div>
            <div className="bg-blue-400 p-4 rounded w-1/4 text-sm">Box 3</div>
            <div className="bg-green-400 p-4 rounded w-1/4 text-sm">Box 4</div>
          </div>
      </footer>
    </div>
    
  );
};

export default Layout;