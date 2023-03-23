import style from "./Page.module.css"; 

function HomePage() {
    return (
        <>
            <div className={style.page}>
                <div className={style.title}>Home</div>
                Click on the tabs to view data
            </div>
        </>
    )
}

export default HomePage; 