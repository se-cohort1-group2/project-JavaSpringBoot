import style from "./Page.module.css"; 

// import { Link } from "react-router-dom"; 

function HomePage() {
    return (
        <>
            <div className={style.page}>
                <h3>Home</h3>
                Click on the tabs to view data
            </div>
        </>
    )
}

export default HomePage; 