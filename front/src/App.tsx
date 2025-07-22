import { BrowserRouter, Route, Routes } from "react-router-dom";
import BigPicture from "./BigPicture";
import Home from "./Home"; // ✅ 메인 컴포넌트 import 추가
import Login from "./Login";
import Register from "./Register";
import SearchSection from "./SearchSection"; // ✅ 이미 있음
import "./SearchSection.css";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/main" element={<Home />} /> {/* ✅ 괄호 닫기 */}
        <Route path="/map" element={<BigPicture />} />
        <Route path="/search" element={<SearchSection />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
