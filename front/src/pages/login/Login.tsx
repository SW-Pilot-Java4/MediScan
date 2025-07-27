import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext"; // ✅ 추가: AuthContext에서 useAuth 가져오기
import rq from "../../lib/rq/rq.react";
import "./Login.css";

function Login() {
  const [username, setUsername] = useState("");
  const [pw, setPw] = useState("");
  const [usernameValid, setUsernameValid] = useState(false);
  const [pwValid, setPwValid] = useState(false);
  const [notAllow, setNotAllow] = useState(true);

  const navigate = useNavigate();
  const { login } = useAuth(); // ✅ context의 login 함수 사용

  const handleUsername = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputUsername = e.target.value;
    setUsername(inputUsername);
    setUsernameValid(inputUsername.length >= 3);
  };

  const handlePw = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputPw = e.target.value;
    setPw(inputPw);

    const pwRegex =
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
    setPwValid(pwRegex.test(inputPw));
  };

  const onClickConfirmButton = async () => {
    try {
      await rq.loginAndStoreToken(username, pw); // 로그인 요청
      await rq.initAuth(); // 토큰 저장 후 필요한 동기화 처리
      login(); // ✅ Context 상태 업데이트
      alert("로그인 성공!");
      navigate("/");
    } catch (err) {
      console.error("로그인 실패:", err);
      alert("로그인 실패! 아이디나 비밀번호를 확인하세요.");
    }
  };

  useEffect(() => {
    setNotAllow(!(usernameValid && pwValid));
  }, [usernameValid, pwValid]);

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="flex flex-col gap-4 bg-white p-8 rounded shadow-md w-full max-w-xl">
        <div className="text-2xl font-bold mb-4 text-center">로그인</div>

        {/* 아이디 */}
        <div>
          <label className="text-sm font-medium mb-1 block">아이디</label>
          <input
            type="text"
            className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="아이디를 입력하세요"
            value={username}
            onChange={handleUsername}
          />
          {!usernameValid && username.length > 0 && (
            <div className="text-red-500 text-xs mt-1">
              아이디는 최소 3자 이상 입력해주세요.
            </div>
          )}
        </div>

        {/* 비밀번호 */}
        <div>
          <label className="text-sm font-medium mb-1 block">비밀번호</label>
          <input
            type="password"
            className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="영문, 숫자, 특수문자 포함 8자 이상"
            value={pw}
            onChange={handlePw}
          />
          {!pwValid && pw.length > 0 && (
            <div className="text-red-500 text-xs mt-1">
              영문, 숫자, 특수문자 포함 8자 이상 입력해주세요.
            </div>
          )}
        </div>

        {/* 로그인 버튼 */}
        <button
          onClick={onClickConfirmButton}
          disabled={notAllow}
          className={`w-full mt-4 py-2 rounded font-semibold ${
            notAllow
              ? "bg-gray-300 text-gray-500 cursor-not-allowed"
              : "bg-blue-600 text-white hover:bg-blue-700"
          }`}
        >
          로그인
        </button>

        {/* 가입 유도 */}
        <hr className="my-6 border-gray-300" />
        <div className="text-sm text-gray-700 text-center">
          계정이 없으신가요?{" "}
          <Link to="/register" className="text-blue-600 hover:underline">
            가입하기
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Login;
