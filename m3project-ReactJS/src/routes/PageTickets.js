import style from "./Page.module.css"; 

import TableTickets from "../components/TableTickets"; 

function PageTickets({ TicketsList }) {

    return (
        <>
            <div className={style.page}>
                <div className={style.title}>View all Tickets</div>
                {TicketsList && <TableTickets list={TicketsList}/>}
            </div>
        </>
    )
}

export default PageTickets; 