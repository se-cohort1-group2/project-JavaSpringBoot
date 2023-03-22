import { Link } from "react-router-dom"; 

function ErrorPage() {
    return (
        <>
            <div style={{textAlign: "center", margin: "50px 0 0 0"}}>
                <h1>Error</h1>
                <p> Page not found</p>
                <Link to="/" style={{textDecoration: "none"}}>
                    <span style={{fontSize: "2em"}}>🡄</span>
                    <span style={{verticalAlign: "35%"}}>&nbsp;Back</span>
                </Link>
            </div>
        </>
    )
}

export default ErrorPage; 