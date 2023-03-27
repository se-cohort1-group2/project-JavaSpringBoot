import TableTickets from "./TableTickets"; 

function DisplayUserTickets({ TicketsList, displayID }) {

    // eslint-disable-next-line
    const filteredTickets = TicketsList.filter(x => x.userEntity.id == displayID)

    return (
        <>
            {filteredTickets && <TableTickets list={filteredTickets}/>}
        </>
    )
}

export default DisplayUserTickets; 