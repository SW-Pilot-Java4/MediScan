import { Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <div className="min-h-screen flex flex-col">
      <header className="bg-gray-800 text-white p-4">Header</header>

      <div className="flex flex-1">
        <aside className="w-64 bg-gray-100 p-4">Sidebar</aside>
        <main className="flex-1 p-4">
          <Outlet /> {/* 여기에 각 페이지 컴포넌트가 렌더링됨 */}
        </main>
      </div>

      <footer className="bg-gray-200 text-center p-4">Footer</footer>
    </div>
  );
};

export default Layout;