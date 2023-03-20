// import { Link } from "react-router-dom"; 

import localAPI from "../api/localAPI"; 
import TableTickets from "../components/TableTickets"; 

import { useState, useEffect } from "react"; 

function PageTickets() {

    const [TicketsList, setTicketsList] = useState([]); 

    const getTickets = async() => {
        try {
            const response = await localAPI.get("/tickets")
            setTicketsList(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    useEffect(() => {
        getTickets(); 
    }, [])

    return (
        <>
            <div style={{textAlign: "center", backgroundColor: "#f4f4f4", height: "100%"}}>
                <h3>View all Tickets</h3>
                {TicketsList && <TableTickets list={TicketsList}/>}
            </div>
        </>
    )
}

export default PageTickets; 