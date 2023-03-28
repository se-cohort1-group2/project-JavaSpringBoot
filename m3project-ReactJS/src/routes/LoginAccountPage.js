import style from "./Page.module.css"; 

import { useContext } from "react"; 
import { Link } from "react-router-dom"; 
//import { Navigate } from "react-router-dom"; 

import LoginContext from "../context/LoginContext"; 

import DisplayUser from "../components/DisplayUser"; 
import DisplayUserTickets from "../components/DisplayUserTickets"; 

function LoginAccountPage({ UsersList, getUsers, TicketsList }) {

    const LoginCtx = useContext(LoginContext); 

    if (LoginCtx.isLoggedIn) {
    return (
        <>
        <div className={style.page}>
            <div className={style.subtitle}>Your Profile</div>
            <br/>
            <DisplayUser UsersList={UsersList} getUsers={getUsers} displayID={LoginCtx.userID}/>
            <br/><br/>
            <hr/>
            <div className={style.subtitle}>Your Tickets</div>
            <br/>
            <DisplayUserTickets TicketsList={TicketsList} displayID={LoginCtx.userID}/>
        </div>
        </>
    )
    }

    if (!LoginCtx.isLoggedIn) {
    return (
        <>
        <div className={style.page}>
            <div className={style.subtitle}>You are not logged in, please login <Link to="/login">here</Link></div>
        </div>
        {/* <Navigate to={`/login`} replace={true}/> */}
        </>
    )
    }
}

export default LoginAccountPage; 