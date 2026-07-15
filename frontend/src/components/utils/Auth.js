export const getUser = ()  =>{
    try{
        return JSON.parse(localStorage.getItem("user"));
    }catch{
        return null;
    }
}

export const getToken = () => {
    return localStorage.getItem("token");
}

export const isLoggedIn = () => {
    return !!getToken();
}

export const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
}

export const saveUser = (user) => {
    localStorage.setItem("user" , JSON.stringify(user));
}