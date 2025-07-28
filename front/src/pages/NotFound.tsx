import { Link } from "react-router-dom";

const NotFound = () => {
  return (
    <div className="flex flex-col items-center justify-center h-screen text-center bg-sky2 px-4">
      <a href="/">
        <img src="/mediscan1.png" alt="Logo" className="h-30 w-auto" />
      </a>
      <p className="text-xl mb-6">
        잘못된 접근입니다. <br></br>홈 화면으로 돌아가 주세요.
      </p>
      <Link
        to="/"
        className="text-white bg-blue-500 px-4 py-2 rounded hover:bg-blue-600"
      >
        홈으로 돌아가기
      </Link>
    </div>
  );
};

export default NotFound;
