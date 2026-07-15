import { getToken } from "./Auth";

const BASE_URL = "http://localhost:8080";

export async function apiFetch(endpoints , options = {}) {
    const token = getToken();
    console.log("Token :" ,token);
    

    const headers = {
        "Content-Type": "application/json" ,
        ...options.headers,
    };
    
    if(token){
        headers.Authorization = `Bearer ${token}`;
    }
    return fetch(`${BASE_URL}${endpoints}` , {
        ...options,
        headers,
    });
}