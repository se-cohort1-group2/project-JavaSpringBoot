import style from "./Page.module.css"; 

import TableConcerts from "../components/TableConcerts"; 

function ViewConcerts({ ConcertsList }) {

    return (
        <>
            <div className={style.page}>
                <div className={style.title}>View all Concerts</div>
                {ConcertsList && <TableConcerts list={ConcertsList}/>}
            </div>
        </>
    )
}

export default ViewConcerts; 