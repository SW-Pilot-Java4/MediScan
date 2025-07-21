import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom"; // ✅ useNavigate 추가
import "./Login.css";

function Login() {
  const [username, setUsername] = useState("");
  const [pw, setPw] = useState("");

  const [usernameValid, setUsernameValid] = useState(false);
  const [pwValid, setPwValid] = useState(false);
  const [notAllow, setNotAllow] = useState(true);

  const navigate = useNavigate(); // ✅ 네비게이션 훅 선언

  // username 핸들러
  const handleUsername = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputUsername = e.target.value;
    setUsername(inputUsername);
    setUsernameValid(inputUsername.length >= 3);
  };

  // 비밀번호 핸들러
  const handlePw = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputPw = e.target.value;
    setPw(inputPw);

    const pwRegex =
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
    setPwValid(pwRegex.test(inputPw));
  };

  // ✅ 로그인 버튼 클릭 시 메인 페이지로 이동
  const onClickConfirmButton = async () => {
    const params = new URLSearchParams();
    params.append("username", username);
    params.append("password", pw);

    try {
      const res = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        credentials: "include",
        body: params.toString(),
      });

      if (res.ok) {
        alert("로그인 성공!");
        navigate("/main"); // ✅ 메인 페이지로 이동
      } else {
        alert("로그인 실패! 아이디나 비밀번호를 확인하세요.");
      }
    } catch (error) {
      console.error("로그인 오류", error);
      alert("서버 오류가 발생했습니다.");
    }
  };

  useEffect(() => {
    if (usernameValid && pwValid) {
      setNotAllow(false);
    } else {
      setNotAllow(true);
    }
  }, [usernameValid, pwValid]);

  return (
    <div className="min-h-screen flex flex-col justify-center items-center bg-gray-100 px-4 py-8">
      <div className="flex justify-center items-center min-h-screen bg-gray-100">
        <div className="flex flex-col gap-4 bg-white p-8 rounded shadow-md w-full max-w-xl">
          <div className="text-2xl font-bold mb-4 text-center">로그인</div>

          {/* 아이디 입력 */}
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

          {/* 비밀번호 입력 */}
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

          {/* 버튼 */}
          <button
            onClick={onClickConfirmButton}
            disabled={notAllow}
            className={`w-full mt-4 py-2 rounded font-semibold ${
              notAllow
                ? "bg-gray-300 text-gray-500 cursor-not-allowed"
                : "bg-blue-500 text-white hover:bg-blue-600"
            }`}
          >
            로그인
          </button>

          {/* 가입 링크 */}
          <hr className="my-6 border-gray-300" />
          <div className="text-sm text-gray-700 text-center">
            계정이 없으신가요?{" "}
            <Link to="/register" className="text-blue-500 hover:underline">
              가입하기
            </Link>
          </div>
        </div>
      </div>
      {/* Title */}
    </div>
  );
}

export default Login;
