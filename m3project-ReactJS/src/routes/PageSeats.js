import style from "./Page.module.css"; 

import TableSeats from "../components/TableSeats"; 

function PageSeats({ SeatsList }) {

    // const [SeatsList, setSeatsList] = useState([]); 

    /* const getSeats = async() => {
        try {
            const response = await localAPI.get("/seats")
            setSeatsList(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error.message)
        }
    } */

    /* useEffect(() => {
        getSeats(); 
        // eslint-disable-next-line
    }, []) */

    return (
        <>
            <div className={style.page}>
                <div className={style.title}>View all Seats</div>
                {SeatsList && <TableSeats list={SeatsList}/>}
            </div>
        </>
    )
}

export default PageSeats; 