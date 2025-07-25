<<<<<<< HEAD
import React from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/login/Login";
import Register from "./pages/join/Register";
import Home from "./pages/home/Home"; // ✅ 메인 컴포넌트 import 추가
import BigPicture from "./BigPicture";
=======
import React, { useEffect } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import Login from "./pages/login/Login";
import Register from "./pages/join/Register";
import Home from "./pages/home/Home";
import BigPicture from "./pages/map/BigPicture";
import HospitalDetail from "./pages/hospital/Hospital";
import "./index.css";
>>>>>>> origin/infra/deploy

function App() {
  useEffect(() => {
    // 테마 설정
    document.documentElement.setAttribute("data-theme", "mytheme");
    console.log("✅ mytheme 적용됨");
  }, []);
  return (
    <BrowserRouter>
      <Routes>
<<<<<<< HEAD
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/main" element={<Home />} /> {/* ✅ 괄호 닫기 */}
        <Route path="/map" element={<BigPicture />} />
=======
        {/* Layout을 감싸는 공통 구조 */}
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/main" element={<Home />} />
          <Route path="/map" element={<BigPicture />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/hospital/:hospitalCode" element={<HospitalDetail />} />
        </Route>
>>>>>>> origin/infra/deploy
      </Routes>
    </BrowserRouter>
  );
}

export default App;
