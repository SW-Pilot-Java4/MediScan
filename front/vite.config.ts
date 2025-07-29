import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

export default defineConfig({
  plugins: [
    react(),
    // tailwindcss()
  ],
  server: {
    host: "localhost",
    port: 5173, // (선택) 기본 포트 명시
    proxy: {
      "/api": {
        target: "https://api-sw-pilot.mediscan.site",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
