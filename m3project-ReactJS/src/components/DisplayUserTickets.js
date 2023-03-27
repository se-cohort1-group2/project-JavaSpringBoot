import TableTickets from "./TableTickets"; 

function DisplayUserTickets({ TicketsList, displayID }) {

    // eslint-disable-next-line
    const filteredTickets = TicketsList.filter(x => x.userEntity.id == displayID)

    return (
        <>
            {filteredTickets.length > 0 && <TableTickets list={filteredTickets}/>}
            {filteredTickets.length < 1 && <>You have not purchased any tickets.</>}
        </>
    )
}

export default DisplayUserTickets; 