import style from "./Page.module.css"; 

import { useContext, useState } from "react"; 
import { Link, Navigate } from "react-router-dom"; 

import LoginContext from "../context/LoginContext"; 
import localAPI from "../api/localAPI"; 

import VisibilityOn from "../images/VisibilityOn.svg"; 
import VisibilityOff from "../images/VisibilityOff.svg"; 

const blankLoginForm = {
    email: "",
    password: "",
}

function LoginPage({ UsersList }) {

    const LoginCtx = useContext(LoginContext); 

    const [LoginForm, setLoginForm] = useState(blankLoginForm); 
    const [ShowPassword, setShowPassword] = useState(false); 

    const toggleVisibility = () => {
        ShowPassword ? setShowPassword(false) : setShowPassword(true)
    }

    const handleInput = (e) => {
        setLoginForm((prevState) => {
            return {
                ...prevState, 
                [e.target.name]: e.target.value, 
            }
        })
    }

    const handleSubmit = async (e) => {
        e.preventDefault(); 
        await loginUser(LoginForm); 
    }

    const loginUser = async (LoginData) => {
        const user = UsersList.find(({ email }) => email === LoginForm.email); 
        try {
            const response = await localAPI.post(`/users/login`, LoginData)
            console.log("Successfully logged in as '" + user.email + "'.", response.data)
            LoginCtx.handleLogin(true, user.id, response.data.token); 
        } catch (error) {
            console.log(error.message)
            if (typeof user !== "object") {
                console.log("User account not found, please try a different email.")
                window.alert("User account not found, please try a different email.")
            } else {
                console.log("Incorrect password for '" + user.email + "'. Please try again.")
                window.alert("Incorrect password for '" + user.email + "'. Please try again.")
            }
        }
    }

    if (!LoginCtx.isLoggedIn) {
    return (
        <>
        <div className={style.subtitle}>Sign in with an existing account</div>
        <form onSubmit={handleSubmit}>
        <table className={style.FormTable}>
            <tbody>
                <tr>
                    <th>Email:</th>
                    <td>
                        <input value={LoginForm.email} name="email" type="email" required placeholder="johndoe@example.com" onChange={(e) => handleInput(e, "email")}/>
                    </td>
                </tr>
                <tr>
                    <th>Password:</th>
                    <td>
                        <input value={LoginForm.password} name="password" type={ShowPassword ? "text" : "password"} required placeholder="********" onChange={(e) => handleInput(e, "password")}/>
                        {ShowPassword ?
                        <img onClick={toggleVisibility} className={style.VisibilityOnOff} alt="VisibilityOn" src={VisibilityOn}/>
                        :
                        <img onClick={toggleVisibility} className={style.VisibilityOnOff} alt="VisibilityOff" src={VisibilityOff}/>
                        }
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td>
                        <input value="Login" type="submit"/>
                    </td>
                </tr>
            </tbody>
        </table>
        </form>
        <p>Don't have an account yet? <Link to="/login/register">Sign up here</Link></p>
        </>
    )
    }

    if (LoginCtx.isLoggedIn) {
    return (
        <>
        <Navigate to={`/account`} replace={true}/>
        </>
    )
    }
}

export default LoginPage; 