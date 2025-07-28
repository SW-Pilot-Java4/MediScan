import { Outlet, useNavigate } from "react-router-dom";

import { useAuth } from "../context/AuthContext";

const Layout = () => {
  const { isLoggedIn, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    window.location.href = "/";
  };

  return (
    <div className="h-screen flex flex-col">
      <header className="h-16 flex items-center justify-between px-4 border-b bg-white shadow fixed top-0 left-0 right-0 z-10">
        <a href="/">
          <img src="/logo.png" alt="Logo" className="h-12 w-auto" />
        </a>
        <div>
          {isLoggedIn ? (
            <button
              className="btn btn-ghost mx-1 text-red-500 font-semibold"
              onClick={handleLogout}
            >
              로그아웃
            </button>
          ) : (
            <a className="btn btn-ghost mx-1 text-blue-600" href="/login">
              로그인
            </a>
          )}
        </div>
      </header>

      {/* 본문 */}
      <main className="flex-1 overflow-y-auto pt-16">
        <Outlet />

        <div className="bg-base-200 text-base-content rounded px-6 py-10 mb-5 text-center">
          <nav className="mb-4 grid grid-flow-col justify-center gap-4">
            <a className="link link-hover">About us</a>
            <a className="link link-hover">Contact</a>
            <a className="link link-hover">Jobs</a>
            <a className="link link-hover">Press kit</a>
          </nav>
          <nav className="mb-4 grid grid-flow-col justify-center gap-4">
            <div className="grid grid-flow-col gap-4">
              <a>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                  className="fill-current"
                >
                  <path d="M24 4.557c-.883.392-1.832.656-2.828.775 1.017-.609 1.798-1.574 2.165-2.724-.951.564-2.005.974-3.127 1.195-.897-.957-2.178-1.555-3.594-1.555-3.179 0-5.515 2.966-4.797 6.045-4.091-.205-7.719-2.165-10.148-5.144-1.29 2.213-.669 5.108 1.523 6.574-.806-.026-1.566-.247-2.229-.616-.054 2.281 1.581 4.415 3.949 4.89-.693.188-1.452.232-2.224.084.626 1.956 2.444 3.379 4.6 3.419-2.07 1.623-4.678 2.348-7.29 2.04 2.179 1.397 4.768 2.212 7.548 2.212 9.142 0 14.307-7.721 13.995-14.646.962-.695 1.797-1.562 2.457-2.549z"></path>
                </svg>
              </a>
              <a>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                  className="fill-current"
                >
                  <path d="M19.615 3.184c-3.604-.246-11.631-.245-15.23 0-3.897.266-4.356 2.62-4.385 8.816.029 6.185.484 8.549 4.385 8.816 3.6.245 11.626.246 15.23 0 3.897-.266 4.356-2.62 4.385-8.816-.029-6.185-.484-8.549-4.385-8.816zm-10.615 12.816v-8l8 3.993-8 4.007z"></path>
                </svg>
              </a>
              <a>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                  className="fill-current"
                >
                  <path d="M9 8h-3v4h3v12h5v-12h3.642l.358-4h-4v-1.667c0-.955.192-1.333 1.115-1.333h2.885v-5h-3.808c-3.596 0-5.192 1.583-5.192 4.615v3.385z"></path>
                </svg>
              </a>
            </div>
          </nav>
          <p className="text-sm">
            Copyright © 2025 SW-Pilot Education Program — Java Team 4. All
            rights reserved.
          </p>
        </div>
      </main>

      {/* 푸터 */}
      <footer className="h-12 fixed bottom-0 left-0 right-0 bg-white border-t border-gray-700 flex items-center justify-center z-10">
        <div className="flex items-center justify-center gap-10 text-sm">
          <div
            className=" px-4 py-2 rounded text-center"
            onClick={() => navigate("/search")}
          >
            Search
          </div>
          <div
            className=" px-4 py-2 rounded text-center"
            onClick={() => navigate("/")}
          >
            Home
          </div>
          <div
            className=" px-4 py-2 rounded text-center"
            onClick={() => navigate("/map")}
          >
            Map
          </div>
        </div>
      </footer>
    </div>
  );
};

export default Layout;
