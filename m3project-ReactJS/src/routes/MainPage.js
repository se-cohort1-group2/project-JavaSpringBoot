import "./MainPage.css"; 

import { Link, Outlet } from "react-router-dom"; 

function switchTab(event) {
    const tabs = document.getElementsByClassName("link-tab"); 
    for (let i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove("selected-tab"); 
    }
    event.currentTarget.classList.add("selected-tab"); 
}

function MainPage() {
    return (
        <>
            <div className="main-container">
                <div className="nav-bar">
                    <nav className="nav-tabs">
                        <Link className="home-tab" to="/" onClick={switchTab}>Home</Link>
                        <Link className="link-tab" to="/concerts" onClick={switchTab}>Concerts</Link>
                        <Link className="link-tab" to="/tickets" onClick={switchTab}>Tickets</Link>
                        <Link className="link-tab" to="/users" onClick={switchTab}>Users</Link>
                    </nav>
                    <Outlet/>
                </div>
            </div>
        </>
    )
}

export default MainPage; 