import style from "./Page.module.css"; 

import DisplayAllConcerts from "../components/DisplayAllConcerts"; 

function PageConcerts({ ConcertsList, titleText }) {

    return (
        <>
            <div className={style.subtitle}>{titleText}</div>
            <br/>
            {ConcertsList && <DisplayAllConcerts ConcertsList={ConcertsList}/>}
        </>
    )
}

export default PageConcerts; 