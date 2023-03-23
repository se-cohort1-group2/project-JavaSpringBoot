import style from "./Page.module.css"; 

import TableUsers from "../components/TableUsers"; 

function PageUsers({ UsersList }) {

    // const [UsersList, setUsersList] = useState([]); 

    /* const getUsers = async() => {
        try {
            const response = await localAPI.get("/users")
            setUsersList(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error.message)
        }
    } */

    /* useEffect(() => {
        getUsers(); 
        // eslint-disable-next-line
    }, []) */

    return (
        <>
            <div className={style.page}>
                <div className={style.title}>View all Users</div>
                {UsersList && <TableUsers list={UsersList}/>}
            </div>
        </>
    )
}

export default PageUsers; 