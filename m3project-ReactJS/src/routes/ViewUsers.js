import style from "./Page.module.css"; 

import TableUsers from "../components/TableUsers"; 

function ViewUsers({ UsersList }) {

    return (
        <>
            <div className={style.page}>
                <div className={style.title}>View all Users</div>
                {UsersList && <TableUsers list={UsersList}/>}
            </div>
        </>
    )
}

export default ViewUsers; 