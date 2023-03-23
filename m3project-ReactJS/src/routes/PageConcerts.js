import style from "./Page.module.css"; 

import TableConcerts from "../components/TableConcerts"; 

function PageConcerts({ ConcertsList }) {

    // const [ConcertsList, setConcertsList] = useState([]); 

    /* const getConcerts = async() => {
        try {
            const response = await localAPI.get("/concerts")
            setConcertsList(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error.message)
        }
    } */

    /* useEffect(() => {
        getConcerts(); 
        // eslint-disable-next-line
    }, []) */

    return (
        <>
            <div className={style.page}>
                <div className={style.title}>View all Concerts</div>
                {ConcertsList && <TableConcerts list={ConcertsList}/>}
            </div>
        </>
    )
}

export default PageConcerts; 