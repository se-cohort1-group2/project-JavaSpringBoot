import style from "./Table.module.css"; 

import { useContext, useState } from "react"; 
import { useNavigate } from "react-router-dom"; 

import LoginContext from "../context/LoginContext"; 
import localAPI from "../api/localAPI"; 

function DisplayConcertSeats({ ConcertID, SeatsList, TicketsList, getTickets }) {

    const LoginCtx = useContext(LoginContext); 

    const navigate = useNavigate(); 

    const [SelectedSeats, setSelectedSeats] = useState([]); 

    let newObject = {}; 
    for (const x of SeatsList) {
        if (!newObject[x.seatCategory]) {
            newObject[x.seatCategory] = []; 
        }
        newObject[x.seatCategory].push(x); 
    }
    let newArray = Object.values(newObject); 

    // eslint-disable-next-line
    const concertTickets = TicketsList.filter(x => x.concertEntity.id == ConcertID)

    function checkIfAvailable(x) {
        for (let i = 0; i < concertTickets.length; i++) {
            if (x === concertTickets[i].seatEntity.seatId) {
                return <span className={style.UnavailableSeat}>{x}</span>; 
            }
        }
        return <span className={style.AvailableSeat} onClick={toggleSelection}>{x}</span>; 
    }

    const toggleSelection = (e) => {
        const foundSeat = SelectedSeats.find(({ seat_id }) => seat_id === e.target.innerHTML)
        if (typeof foundSeat !== "object") {
            const newSeat = {
                "seat_id": e.target.innerHTML, 
                "concert_id": ConcertID
            }
            const newList = [...SelectedSeats, newSeat]
            setSelectedSeats(newList)
            e.target.classList.add(`${style.selection}`); 
        } else {
            const newList = SelectedSeats.filter(({ seat_id }) => seat_id !== e.target.innerHTML)
            setSelectedSeats(newList)
            e.target.classList.remove(`${style.selection}`); 
        }
    }

    const PurchaseTickets = async (PurchaseData) => {
        try {
            const config = {
                headers: {"user_id": LoginCtx.userID}
            }
            const response = await localAPI.post(`/tickets`, PurchaseData, config)
            console.log("Purchase successful!", response.data)
            window.alert("Purchase successful!")
            navigate(`/account`); 
        } catch (error) {
            console.log(error.message)
            console.log("You must be logged in to purchase tickets")
            window.alert("You must be logged in to purchase tickets")
            navigate(`/login`); 
        }
    }

    const ConfirmPurchase = async () => {
        if (SelectedSeats.length > 0) {
            await PurchaseTickets(SelectedSeats); 
            getTickets(); 
        } else {
            window.alert("No seats selected")
        }
    }

    return (
        <>
            <div className={style.gridContainer}>

                <div className={style.gridBoxLeft}>
                    {/* <br/><br/><br/><br/>Ticket Prices<br/>
                    {newArray.map((i, index) => (
                    <div key={index} className={style.LegendTicketPricing}>
                        Category {i[0].seatCategory} - ${i[0].ticketPrice}
                    </div>
                    ))} */}
                </div>

                <div className={style.gridBoxCentre}>
                    <div className={style.stage}>STAGE</div>
                    <table className={style.SeatsTable}>
                        <tbody>
                            {newArray.map((i, index) => (
                            <tr key={index}>
                                {i.map((x) => (
                                <td key={x.seatId} className={style.tooltip}>
                                    {checkIfAvailable(x.seatId)}
                                    <br/>
                                    <span id></span>
                                    <span className={style.tooltiptext}>
                                        Category {x.seatCategory}
                                        <br/>
                                        ${x.ticketPrice}
                                    </span>
                                </td>
                                ))}
                            </tr>
                            ))}
                        </tbody>
                    </table>
                </div>

                <div className={style.gridBoxRight}>
                    <br/><br/><br/><br/><br/>Legend<br/>
                    <div className={style.LegendUnavailable}>&nbsp;</div>Seat Unavailable<br/>
                    <div className={style.LegendAvailable}>&nbsp;</div>Seat Available<br/>
                    <div className={style.LegendSelected}>&nbsp;</div>Your Selected Seat<br/>
                </div>

            </div>

            <br/>
            {SelectedSeats.length > 0 &&
            <>
            <table className={style.SelectedSeats}>
                <tbody>
                    <tr>
                        <td>You have selected the following seats: </td>
                        {SelectedSeats && SelectedSeats.map((z) => (
                        <td key={z.seat_id} className={style.SelectedSeat}>{z.seat_id}</td>
                        ))}
                    </tr>
                </tbody>
            </table>
            <br/><br/>
            <button className={style.PurchaseBtn} onClick={ConfirmPurchase}>Confirm Purchase</button>
            <br/>
            </>
            }
        </>
    )
}

export default DisplayConcertSeats; 