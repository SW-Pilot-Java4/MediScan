import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/login/Login";
import Register from "./pages/join/Register";
import Home from "./pages/home/Home"; // ✅ 메인 컴포넌트 import 추가
import BigPicture from "./pages/map/BigPicture";
import SearchSection from "./pages/search/SearchSection";
import Layout from "./components/Layout";
import NotFound from "./pages/NotFound";
import "./index.css";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/main" element={<Home />} />
          <Route path="/map" element={<BigPicture />} />
          <Route path="/search" element={<SearchSection />} />
        </Route>
        <Route path="/*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
