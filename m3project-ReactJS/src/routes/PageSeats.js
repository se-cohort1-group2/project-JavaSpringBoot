import style from "./Page.module.css"; 

// import { Link } from "react-router-dom"; 

import localAPI from "../api/localAPI"; 
import TableSeats from "../components/TableSeats"; 

import { useState, useEffect } from "react"; 

function PageSeats() {

    const [SeatsList, setSeatsList] = useState([]); 

    const getSeats = async() => {
        try {
            const response = await localAPI.get("/seats")
            setSeatsList(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    useEffect(() => {
        getSeats(); 
    }, [])

    return (
        <>
            <div className={style.page}>
                <h3>View all Seats</h3>
                {SeatsList && <TableSeats list={SeatsList}/>}
            </div>
        </>
    )
}

export default PageSeats; 