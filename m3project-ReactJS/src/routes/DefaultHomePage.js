import style from "./Page.module.css"; 
import style2 from "../components/Table.module.css"; 

import { useContext, useState } from "react"; 
import { Link, NavLink } from "react-router-dom"; 

import LoginContext from "../context/LoginContext"; 
import localAPI from "../api/localAPI"; 

function DefaultHomePage() {

    const LoginCtx = useContext(LoginContext); 

    const [SearchInput, setSearchInput] = useState(""); 
    const [SearchResults, setSearchResults] = useState([]); 

    const handleSearch = async (e) => {
        setSearchInput(e.target.value)
        if (e.target.value.length > 0) {
            await searchConcerts(e.target.value); 
        } else {
            setSearchResults([])
        }
    }

    const searchConcerts = async(SearchData) => {
        try {
            const response = await localAPI.get(`/concerts/search?artist=${SearchData}`)
            setSearchResults(response.data)
            console.log("SearchResults", response.data)
        } catch (error) {
            //console.log(error.message)
            setSearchResults([{artist: "No results found"}])
        }
    }

    return (
        <>
        <div className={style.page}>
            <div style={{ fontSize: "1.75rem", margin: "2.2em 0 0 0", fontWeight: "bold" }}>
                Welcome to TicketChope!™
            </div>
            <br/><br/>
            {/* <span style={{ fontSize: "1.25rem" }}>
                Chope the best seats like a pro. Book your tickets with TicketChope!™
            </span>
            <br/><br/><br/> */}
            <div className={style.SearchOuterArea}>
                Search for an event:&nbsp;&nbsp;&nbsp;
                <div className={style.SearchArea}>
                    <input className={style.SearchInput} value={SearchInput} placeholder="Search by artist" onChange={handleSearch}/>
                    <div className={style.SearchResults}>
                        <table className={style.SearchResultsTable}>
                            <tbody>
                                {SearchResults.map((item) => (
                                <tr key={item.id}>
                                    <td>
                                        {typeof item.id === "number" &&
                                        <NavLink className={style.SearchedResult} to={`/concerts/${item.id}/buy`}>
                                            {item.artist}
                                        </NavLink>
                                        }
                                        {typeof item.id !== "number" &&
                                        <>
                                            {item.artist}
                                        </>
                                        }
                                    </td>
                                </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            {/* <br/><br/>
            {!LoginCtx.isLoggedIn &&
                <>
                <Link to="/login">
                    <button className={style2.PurchaseBtn}>Login</button>
                </Link>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <Link to="/login/register">
                    <button className={style2.PurchaseBtn}>Register</button>
                </Link>
                </>
            }
            {LoginCtx.isLoggedIn &&
                <>
                <Link to="/account">
                    <button className={style2.PurchaseBtn}>View your account</button>
                </Link>
                </>
            } */}
        </div>
        </>
    )
}

export default DefaultHomePage; 