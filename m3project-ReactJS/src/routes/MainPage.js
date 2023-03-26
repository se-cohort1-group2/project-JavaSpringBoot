import style from "./MainPage.module.css"; 

import { Link, Outlet } from "react-router-dom"; 
import { useContext } from "react"; 

import LoginContext from "../context/LoginContext"; 

import HomeIcon from "../images/OutlinedHomeIcon.svg"; 
import UserIcon from "../images/OutlinedUserIcon.svg"; 

function MainPage() {

    const LoginCtx = useContext(LoginContext); 

    return (
        <>
            <div className={style.MainContainer}>

                <nav className={style.NavBar}>
                    <Link className={`${style.NavBtn} ${style.HomeBtn}`} to="/">
                        <img className={style.HomeIcon} alt="HomeIcon" src={HomeIcon}/>
                    </Link>

                    <div className={style.Dropdown}>
                    <Link className={`${style.NavBtn} ${style.LinkBtn}`} to="/concerts">Concerts</Link>
                        <div className={style.DropdownMenu}>
                        <Link className={style.DropdownLink} to="/concerts">Upcoming Concerts</Link>
                        <Link className={style.DropdownLink} to="/concerts/history">Past Concerts</Link>
                        </div>
                    </div>

                    <Link className={`${style.NavBtn} ${style.LinkBtn}`} to="/seats">Seats</Link>
                    <Link className={`${style.NavBtn} ${style.LinkBtn}`} to="/tickets">Tickets</Link>
                    <Link className={`${style.NavBtn} ${style.LinkBtn}`} to="/users">Users</Link>

                    <Link className={`${style.LoginArea}`} to="/login">
                        <img className={style.UserIcon} alt="UserIcon" src={UserIcon}/>
                        <span className={style.LoginText}>
                            {!LoginCtx.isLoggedIn && <>Login</>}
                            {LoginCtx.isLoggedIn && <>Account</>}
                        </span>
                    </Link>
                </nav>

                <div className={style.OutletContainer}>
                    <Outlet/>
                </div>

            </div>
        </>
    )
}

export default MainPage; 