import style from "./Page.module.css"; 

import { Outlet } from "react-router-dom"; 

function BlankPage() {
    return (
        <>
            <div className={style.page}>
                <Outlet/>
            </div>
        </>
    )
}

export default BlankPage; 