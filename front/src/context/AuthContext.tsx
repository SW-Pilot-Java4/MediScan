import React, { createContext, useContext, useState, useEffect } from "react";
import rq from "../lib/rq/rq.react";

type AuthContextType = {
  isLoggedIn: boolean;
  login: () => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(rq.isLogin());

  const login = () => {
    setIsLoggedIn(true);
  };

  const logout = () => {
    rq.logout();
    setIsLoggedIn(false);
  };

  useEffect(() => {
    rq.initAuth(); // 초기화
    setIsLoggedIn(rq.isLogin());
  }, []);

  return (
    <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("AuthProvider로 감싸야 합니다.");
  return ctx;
};
