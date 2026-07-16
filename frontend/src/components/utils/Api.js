import { getToken } from "./Auth";

const BASE_URL = "http://localhost:8080";

export async function apiFetch(endpoints , options = {}) {
    const token = getToken();
    

    const headers = {
        ...options.headers,
    };

    if(!(options.body instanceof FormData)){
        headers["Content-Type"] = "application/json"
    }
    
    if(token){
        headers.Authorization = `Bearer ${token}`;
    }
    return fetch(`${BASE_URL}${endpoints}` , {
        ...options,
        headers,
    });
}