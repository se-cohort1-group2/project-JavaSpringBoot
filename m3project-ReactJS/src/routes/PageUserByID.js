import style from "./Page.module.css"; 

import { useParams, Link } from "react-router-dom"; 

import DisplayUser from "../components/DisplayUser"; 

function PageUserByID({ UsersList, getUsers }) {

    const {UserID} = useParams(); 

    return (
        <>
        <div className={style.page}>
            <div className={style.title}>View User by ID</div>
            <DisplayUser UsersList={UsersList} getUsers={getUsers} displayID={UserID}/>
            <br/>
            <Link to="/users" className={style.BackLink}>
                <span className={style.BackArrow}>🡄</span>
                <span className={style.BackText}>&nbsp;Back</span>
            </Link>
        </div>
        </>
    )
}

export default PageUserByID; 