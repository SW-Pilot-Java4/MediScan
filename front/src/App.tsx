import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import Login from "./pages/login/Login";
import Register from "./pages/join/Register";
import Home from "./pages/home/Home";
import SearchSection from "./pages/search/SearchSection";
import BigPicture from "./BigPicture";
import "./SearchSection.css";

function App() {
  return (
    <BrowserRouter>
      <Routes>
          {/* Layout을 감싸는 공통 구조 */}
          <Route element={<Layout />}>
            <Route path="/" element={<Login />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/main" element={<Home />} /> {/* ✅ 괄호 닫기 */}
            <Route path="/map" element={<BigPicture />} />
              <Route path="/search" element={<SearchSection />} />
          </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
