import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [email, setEmail] = useState("");
  const [address, setAddress] = useState("");
  const [phone, setPhone] = useState("");
  const [canSubmit, setCanSubmit] = useState(false);

  const navigate = useNavigate();

  const handleRegister = async () => {
    if (password !== passwordConfirm) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/join", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify({ username, password, email, address, phone }),
      });

      if (response.status === 200) {
        alert("✅ 회원가입 성공!");
        navigate("/login");
      } else if (response.status === 409) {
        alert("⚠ 이미 존재하는 아이디입니다.");
      } else {
        alert("⚠ 회원가입 실패! 입력 값을 확인해주세요.");
      }
    } catch (error) {
      console.error("회원가입 오류:", error);
      alert("🚨 서버 오류가 발생했습니다.");
    }
  };

  useEffect(() => {
    const validEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    const validPw =
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(
        password
      );
    const allFilled = !!(
      username &&
      password &&
      email &&
      address &&
      phone &&
      password === passwordConfirm
    );

    setCanSubmit(validEmail && validPw && allFilled);
  }, [username, password, passwordConfirm, email, address, phone]);

  return (
    <div className="min-h-screen flex justify-center items-center bg-base-200 px-4">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-md p-8 space-y-4">
        <h2 className="text-2xl font-bold text-center">회원가입</h2>
        <input
          type="text"
          placeholder="아이디 (username)"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />
        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />
        <input
          type="password"
          placeholder="비밀번호 확인"
          value={passwordConfirm}
          onChange={(e) => setPasswordConfirm(e.target.value)}
          className="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />
        <input
          type="text"
          placeholder="이메일"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />
        <input
          type="text"
          placeholder="주소"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
          className="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />
        <input
          type="text"
          placeholder="전화번호"
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
          className="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />
        <button
          onClick={handleRegister}
          disabled={!canSubmit}
          className={`w-full py-2 rounded font-semibold transition ${
            canSubmit
              ? "bg-blue-600 text-white hover:bg-blue-700"
              : "bg-gray-300 text-gray-500 cursor-not-allowed"
          }`}
        >
          가입하기
        </button>
      </div>
    </div>
  );
}

export default Register;
