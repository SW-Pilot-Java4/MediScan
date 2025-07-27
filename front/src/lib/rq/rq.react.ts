import createClient from 'openapi-fetch';
import type { paths } from '$lib/types/api/v1/schema';

class Rq {
  public apiEndPoints() {
    return createClient<paths>({
      baseUrl: import.meta.env.VITE_CORE_BACK_BASE_URL,
      credentials: 'include',
    });
  }

  // ✅ 로그인
  public async loginAndStoreToken(username: string, password: string) {
    const client = this.apiEndPoints();

    try {
      const { response, data, error } = await client.POST('/login', {
        body: { username, password },
      });

      if (error || !response.ok) {
        throw new Error('로그인 실패: ' + (error?.status || response.status));
      }

      const accessToken = response.headers.get('access');
      if (accessToken) {
        localStorage.setItem('accessToken', accessToken); // ✅ 일관된 키 사용
      } else {
        console.warn('⚠ access token이 응답 헤더에 존재하지 않습니다.');
      }

      return data?.data;
    } catch (err) {
      console.error('로그인 중 오류:', err);
      throw err;
    }
  }

  // ✅ 로그인 여부 확인
  public isLogin() {
    return !!localStorage.getItem('accessToken'); // ✅ 통일
  }

  // ✅ 인증 초기화
  public async initAuth() {
    const token = localStorage.getItem('accessToken'); // ✅ 통일
    if (!token) return;
    // 사용자 정보 동기화 필요 시 여기에 fetch 호출 추가 가능
  }

  // ✅ 로그아웃
  public logout() {
    localStorage.removeItem('accessToken'); // ✅ 통일
  }
}

// ✅ default export 추가
const rq = new Rq();
export default rq;
