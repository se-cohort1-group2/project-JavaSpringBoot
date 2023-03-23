import style from "./Page.module.css"; 

import { useParams, Link } from "react-router-dom"; 

function PageUserByID({ UsersList }) {

    const {UserID} = useParams(); 

    // eslint-disable-next-line
    const user = UsersList.find(({ id }) => id == UserID); 

    /* console.log("-----", user)
    if (typeof user === "object") {
        console.log("-----", user.id)
    } */

    return (
        <>
            <div className={style.page}>
                <div className={style.title}>View User by ID</div>
                {user && 
                    <>
                    <table className={style.MiniTable}>
                        <tbody>
                            <tr>
                                <th>User ID:</th>
                                <td>{user.id}</td>
                            </tr>
                            <tr>
                                <th>Name:</th>
                                <td>{String(user.name)}</td>
                            </tr>
                            <tr>
                                <th>Phone:</th>
                                <td>{String(user.phone)}</td>
                            </tr>
                            <tr>
                                <th>Email:</th>
                                <td>{user.email}</td>
                            </tr>
                        </tbody>
                    </table>
                    </>
                }
                <Link to="/users" className={style.BackLink}>
                    <span className={style.BackArrow}>🡄</span>
                    <span className={style.BackText}>&nbsp;Back</span>
                </Link>
            </div>
        </>
    )
}

export default PageUserByID; 