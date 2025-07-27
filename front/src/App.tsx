import React, { useEffect } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/login/Login";
import Register from "./pages/join/Register";
import Home from "./pages/home/Home"; // ✅ 메인 컴포넌트 import 추가
import BigPicture from "./pages/map/BigPicture";
import SearchSection from "./pages/search/SearchSection";
import Layout from "./components/Layout";
import HospitalDetail from "./pages/hospital/Hospital";
import "./index.css";

function App() {
  useEffect(() => {
    // 테마 설정
    document.documentElement.setAttribute("data-theme", "mytheme");
    console.log("✅ mytheme 적용됨");
  }, []);
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/hospital/:hospitalCode" element={<HospitalDetail />} />
          <Route path="/main" element={<Home />} />
          <Route path="/map" element={<BigPicture />} />
          <Route path="/search" element={<SearchSection />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
