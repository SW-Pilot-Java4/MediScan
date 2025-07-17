import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Login";
import Register from "./Register";
import Home from "./Home"; // ✅ 메인 컴포넌트 import 추가
import BigPicture from "./BigPicture";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/main" element={<Home />} /> {/* ✅ 괄호 닫기 */}
        <Route path="/map" element={<BigPicture />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
