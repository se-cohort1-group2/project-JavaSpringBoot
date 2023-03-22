import style from "./Table.module.css"; 

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

function TableConcerts({ list }) {
    return (
        <>
            <table className={style.table}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Artist</th>
                        <th>Concert Date</th>
                        <th>Tickets Available</th>
                        <th>Ticket Price</th>
                        <th>Updated Timestamp</th>
                        <th>Created Timestamp</th>
                    </tr>
                </thead>
                <tbody>
                    {list && list.map((item) => (
                    <tr key={item.id}>
                        <td>{item.id}</td>
                        <td>{item.artist}</td>
                        <td>{formatDate(item.concertDate)}</td>
                        <td>{item.ticketsAvailable}</td>
                        <td>{item.ticketPrice}</td>
                        <td>{item.updatedAt}</td>
                        <td>{item.createdAt}</td>
                    </tr>
                    ))}
                </tbody>
            </table>
        </>
    )
}

export default TableConcerts; 