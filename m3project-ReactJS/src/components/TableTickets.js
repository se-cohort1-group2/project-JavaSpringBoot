import style from "./Table.module.css"; 

function TableTickets({ list }) {
    return (
        <>
            <table className={style.table}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Concert</th>
                        <th>Seat No.</th>
                        <th>User</th>
                        <th>Submitted?</th>
                        <th>Created Timestamp</th>
                    </tr>
                </thead>
                <tbody>
                    {list && list.map((item) => (
                    <tr key={item.id}>
                        <td>{item.ticketId}</td>
                        <td>{item.concertEntity.artist}</td>
                        <td>{item.seatId}</td>
                        <td>{item.userId}</td>
                        <td>{item.submissionStatus}</td>
                        <td>{item.createdAt}</td>
                    </tr>
                    ))}
                </tbody>
            </table>
        </>
    )
}

export default TableTickets; 