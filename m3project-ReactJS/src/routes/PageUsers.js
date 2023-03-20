// import { Link } from "react-router-dom"; 

import localAPI from "../api/localAPI"; 
import TableUsers from "../components/TableUsers"; 

import { useState, useEffect } from "react"; 

function PageUsers() {

    const [UsersList, setUsersList] = useState([]); 

    const getUsers = async() => {
        try {
            const response = await localAPI.get("/users")
            setUsersList(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    useEffect(() => {
        getUsers(); 
    }, [])

    return (
        <>
            <div style={{textAlign: "center", backgroundColor: "#f4f4f4", height: "100%"}}>
                <h3>View all Users</h3>
                {UsersList && <TableUsers list={UsersList}/>}
            </div>
        </>
    )
}

export default PageUsers; 