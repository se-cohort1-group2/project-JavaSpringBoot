import { createContext, useState } from "react"; 

const LoginContext = createContext({
    isLoggedIn: false, 
    setIsLoggedIn: () => {}, 
    userID: "", 
    setUserID: () => {}, 
})

export function LoginContextProvider({ children }) {

    const [isLoggedIn, setIsLoggedIn] = useState(false); 
    const [userID, setUserID] = useState(""); 

    const handleLogin = (status, userID) => {
        setIsLoggedIn(status); 
        setUserID(userID); 
    }

    const context = {
        isLoggedIn: isLoggedIn, 
        userID: userID, 
        handleLogin: handleLogin, 
    }

    return (
        <LoginContext.Provider value={context}>
            {children}
        </LoginContext.Provider>
    )
}

export default LoginContext; 