import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom"; // ✅ useNavigate 추가
import './Login.css';

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
    <div className="page">
      <div className="titleWrap">
        <br />
        로그인
      </div>
      <div className="contentWrap">
        <div className="inputTitle">아이디</div>
        <div className="inputWrap">
          <input
            type="text"
            className="input"
            placeholder="아이디를 입력하세요"
            value={username}
            onChange={handleUsername}
          />
        </div>
        <div className="errorMessageWrap">
          {!usernameValid && username.length > 0 && (
            <div>아이디는 최소 3자 이상 입력해주세요.</div>
          )}
        </div>

        <div style={{ marginTop: "26px" }} className="inputTitle">
          비밀번호
        </div>
        <div className="inputWrap">
          <input
            type="password"
            className="input"
            placeholder="영문, 숫자, 특수문자 포함 8자 이상"
            value={pw}
            onChange={handlePw}
          />
        </div>

        <div className="errorMessageWrap">
          {!pwValid && pw.length > 0 && (
            <div>영문, 숫자, 특수문자 포함 8자 이상 입력해주세요.</div>
          )}
        </div>
      </div>

      <div className="buttonWrap">
        <button
          onClick={onClickConfirmButton}
          disabled={notAllow}
          className="bottomButton"
        >
          로그인
        </button>
      </div>

      <hr />

      <div className="registerWrap">
        <div className="registerTitle">
          계정이 없으신가요? <Link to="/register">가입하기</Link>
        </div>
      </div>
    </div>
  );
}

export default Login;
