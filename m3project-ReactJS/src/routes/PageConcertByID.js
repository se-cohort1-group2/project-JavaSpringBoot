import style from "./Page.module.css"; 

import { useParams, Link } from "react-router-dom"; 

function formatDate(x) {
    return new Date(x).toLocaleString("en-SG", {
        weekday: "short", 
        day: "numeric", 
        month: "short", 
        year: "numeric", 
        hour: "numeric", 
        minute: "2-digit", 
    })
}

function PageConcertByID({ ConcertsList }) {

    const {ConcertID} = useParams(); 

    // eslint-disable-next-line
    const concert = ConcertsList.find(({ id }) => id == ConcertID); 

    /* console.log("-----", concert)
    if (typeof concert === "object") {
        console.log("-----", concert.id)
    } */

    return (
        <>
            <div className={style.page}>
                <div className={style.title}>View Concert by ID</div>
                {concert && 
                    <>
                    <table className={style.MiniTable}>
                        <tbody>
                            <tr>
                                <th>Concert ID:</th>
                                <td>{concert.id}</td>
                            </tr>
                            <tr>
                                <th>Artist:</th>
                                <td>{concert.artist}</td>
                            </tr>
                            <tr>
                                <th>Concert Date & Time:</th>
                                <td>{formatDate(concert.concertDate)}</td>
                            </tr>
                            <tr>
                                <th>Tickets Available:</th>
                                <td>{concert.ticketsAvailable}</td>
                            </tr>
                            <tr>
                                <th>Ticket Price:</th>
                                <td>{concert.ticketPrice}</td>
                            </tr>
                            
                        </tbody>
                    </table>
                    </>
                }
                <Link to="/concerts" className={style.BackLink}>
                    <span className={style.BackArrow}>🡄</span>
                    <span className={style.BackText}>&nbsp;Back</span>
                </Link>
            </div>
        </>
    )
}

export default PageConcertByID; 