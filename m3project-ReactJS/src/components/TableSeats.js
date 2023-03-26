import style from "./Table.module.css"; 

function TableSeats({ list }) {
    return (
        <>
            <table className={style.table}>
                <thead>
                    <tr>
                        <th>Seat No.</th>
                        <th>Category</th>
                        <th>Venue</th>
                        <th>Ticket Price</th>
                        <th>Concert Type</th>
                    </tr>
                </thead>
                <tbody>
                    {list && list.map((item) => (
                    <tr key={item.seatId}>
                        <td>{item.seatId}</td>
                        <td>{item.seatCategory}</td>
                        <td>{item.venueHall}</td>
                        <td>S${item.ticketPrice}</td>
                        <td>{item.concertType}</td>
                    </tr>
                    ))}
                </tbody>
            </table>
            <br/><br/>
        </>
    )
}

export default TableSeats; 