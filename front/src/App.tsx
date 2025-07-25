import { useEffect } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import Login from "./pages/login/Login";
import Register from "./pages/join/Register";
import Home from "./pages/home/Home";
import BigPicture from "./pages/map/BigPicture";
import SearchSection from "./pages/search/SearchSection";
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
        {/* Layout을 감싸는 공통 구조 */}
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/main" element={<Home />} />
          <Route path="/map" element={<BigPicture />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
            <Route path="/search" element={<SearchSection />} />
          <Route path="/hospital/:hospitalCode" element={<HospitalDetail />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
