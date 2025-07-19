import React from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/login/Login";
import Register from "./pages/join/Register";
import Home from "./pages/home/Home"; // ✅ 메인 컴포넌트 import 추가
import BigPicture from "./BigPicture";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/main" element={<Home />} /> {/* ✅ 괄호 닫기 */}
        <Route path="/map" element={<BigPicture />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
