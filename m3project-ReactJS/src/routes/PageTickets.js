import style from "./Page.module.css"; 

import TableTickets from "../components/TableTickets"; 

function PageTickets({ TicketsList }) {

    // const [TicketsList, setTicketsList] = useState([]); 

    /* const getTickets = async() => {
        try {
            const response = await localAPI.get("/tickets")
            setTicketsList(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error.message)
        }
    } */

    /* useEffect(() => {
        getTickets(); 
        // eslint-disable-next-line
    }, []) */

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