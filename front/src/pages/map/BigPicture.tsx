import "../../assets/css/main.css";
import "../../assets/css/noscript.css";
import KakaoMap from "../../KakaoMap";

const BigPicture: React.FC = () => {
  return (
    <div
      className="w-full flex items-center justify-center"
      style={{ height: "calc(100vh - 4rem - 3rem)" }} // 헤더+푸터 제외
    >
      <div className="w-full max-w-6xl bg-white rounded-2xl shadow-xl overflow-hidden p-2">
        <KakaoMap />
      </div>
    </div>
  );
};

export default BigPicture;
