import style from "./Page.module.css"; 

// import { Link } from "react-router-dom"; 

import localAPI from "../api/localAPI"; 
import TableConcerts from "../components/TableConcerts"; 

import { useState, useEffect } from "react"; 

function PageConcerts() {

    const [ConcertsList, setConcertsList] = useState([]); 

    const getConcerts = async() => {
        try {
            const response = await localAPI.get("/concerts")
            setConcertsList(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    useEffect(() => {
        getConcerts(); 
    }, [])

    return (
        <>
            <div className={style.page}>
                <h3>View all Concerts</h3>
                {ConcertsList && <TableConcerts list={ConcertsList}/>}
            </div>
        </>
    )
}

export default PageConcerts; 