import style from "./Page.module.css"; 

import TableSeats from "../components/TableSeats"; 

function PageSeats({ SeatsList }) {

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