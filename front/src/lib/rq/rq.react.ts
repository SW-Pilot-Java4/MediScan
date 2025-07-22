import createClient from 'openapi-fetch';
import type { paths} from '$lib/types/api/v1/schema';

class Rq {

    // API END POINTS
	public apiEndPoints() {
		return createClient<paths>({
			baseUrl: import.meta.env.VITE_CORE_BACK_BASE_URL,
			credentials: 'include',
			// headers: {
			// 	'Content-Type': 'application/json'
			// }
		});
	}
}

const rq = new Rq();

export default rq;