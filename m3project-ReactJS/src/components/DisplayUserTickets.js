import TableTickets from "./TableTickets"; 

function DisplayUserTickets({ TicketsList, displayID }) {

    const filteredTickets = TicketsList.filter(x => x.userEntity.id == displayID)

    return (
        <>
            {filteredTickets && <TableTickets list={filteredTickets}/>}
        </>
    )
}

export default DisplayUserTickets; 