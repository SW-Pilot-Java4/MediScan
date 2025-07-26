package com.ms.back.global.jwt;

import com.ms.back.member.application.dto.CustomUserDetails;
import com.ms.back.member.domain.model.MemberEntity; // MemberEntity가 UserDetails의 역할을 하는 모델이라고 가정
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 요청에서 JWT 토큰 가져오기 (여기서는 "access" 헤더 사용)
        String accessToken = request.getHeader("access");

        // 2. 토큰이 없는 경우 (인증이 필요 없거나, 아직 로그인하지 않은 상태)
        // -> 다음 필터로 넘깁니다. 스프링 시큐리티의 `authorizeHttpRequests`가 해당 경로에 대한 접근 권한을 판단합니다.
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 토큰이 존재하는 경우, 토큰 유효성 검사 시작
        String username = null;
        String role = null;
        String category = null;

        try {
            // 토큰 만료 여부 확인
            jwtUtil.isExpired(accessToken);

            // 토큰 카테고리 확인 (access 토큰인지, refresh 토큰인지)
            category = jwtUtil.getCategory(accessToken);

            // access 토큰이 아니라면 유효하지 않음 (401 에러 반환)
            if (!"access".equals(category)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter writer = response.getWriter();
                writer.print("invalid access token category"); // 더 명확한 에러 메시지
                return;
            }

            // access 토큰에서 사용자 이름과 역할을 추출
            username = jwtUtil.getUsername(accessToken);
            role = jwtUtil.getRole(accessToken);

        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우 (401 에러 반환)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");
            return;
        } catch (Exception e) {
            // 그 외 JWT 관련 예외 (잘못된 형식, 서명 오류 등) 발생 시 (401 에러 반환)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.print("invalid token");
            return;
        }

        // 4. 토큰 유효성 검사를 통과한 경우, Authentication 객체를 생성하여 SecurityContextHolder에 설정
        // 이렇게 해야 Spring Security가 이 요청이 인증된 요청임을 인식합니다.
        if (username != null && role != null) {
            MemberEntity userEntity = new MemberEntity(); // 실제로는 DB에서 사용자 정보를 불러오는 대신, JWT 페이로드 정보만으로 CustomUserDetails를 구성합니다.
            userEntity.setUsername(username);
            userEntity.setRole(role); // 역할은 GrantedAuthority로 제대로 매핑되어야 함
            CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 5. 모든 처리가 끝난 후, 반드시 다음 필터로 요청을 넘깁니다.
        // `authorizeHttpRequests`가 최종적으로 이 요청의 접근 권한을 결정하게 됩니다.
        filterChain.doFilter(request, response);
    }
}