import style from "./Page.module.css"; 

import { useContext, useState } from "react"; 
import { Navigate } from "react-router-dom"; 

import LoginContext from "../context/LoginContext"; 

const blankLoginForm = {
    email: "",
    password: "",
}

function LoginPage({ UsersList }) {

    const LoginCtx = useContext(LoginContext); 

    const [LoginForm, setLoginForm] = useState(blankLoginForm); 

    const handleInput = (e) => {
        setLoginForm((prevState) => {
            return {
                ...prevState, 
                [e.target.name]: e.target.value, 
            }
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault(); 
        console.log("-----", LoginForm)

        const user = UsersList.find(({ email }) => email === LoginForm.email); 
        if (typeof user === "object") {
            if (LoginForm.password === user.password) {
                LoginCtx.handleLogin(true, user.id); 
                console.log("Successfully logged in as '" + user.email + "'.")
            } else {
                console.log("Wrong password for '" + user.email + "'. Please try again.")
            }
        } else {
            console.log("User not found, please try a different email.")
        }
    }

    if (!LoginCtx.isLoggedIn) {
    return (
        <>
        <form onSubmit={handleSubmit}>
        <table className={style.FormTable}>
            <tbody>
                <tr>
                    <th>Email:</th>
                    <td>
                        <input value={LoginForm.email} name="email" type="email" required onChange={(e) => handleInput(e, "email")}/>
                    </td>
                </tr>
                <tr>
                    <th>Password:</th>
                    <td>
                        <input value={LoginForm.password} name="password" type="password" required onChange={(e) => handleInput(e, "password")}/>
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
        </>
    )
    }

    if (LoginCtx.isLoggedIn) {
    return (
        <>
        <Navigate to={`/users/${LoginCtx.userID}`} replace={true}/>
        </>
    )
    }
}

export default LoginPage; 