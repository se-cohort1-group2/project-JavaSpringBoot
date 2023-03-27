import "./App.css"; 

import { useContext, useState, useEffect } from "react"; 
import { BrowserRouter, Routes, Route } from "react-router-dom"; 

import localAPI from "./api/localAPI"; 

import ErrorPage from "./routes/ErrorPage"; 
import MainPage from "./routes/MainPage"; 
import HomePage from "./routes/HomePage"; 
import BlankPage from "./routes/BlankPage"; 

import PageConcerts from "./routes/PageConcerts"; 
import PageConcertByID from "./routes/PageConcertByID"; 
import PageSeats from "./routes/PageSeats"; 
import PageTickets from "./routes/PageTickets"; 
import PageUsers from "./routes/PageUsers"; 
import PageUserByID from "./routes/PageUserByID"; 

import { LoginContextProvider } from "./context/LoginContext"; 
import LoginContext from "./context/LoginContext"; 

import LoginPage from "./routes/LoginPage"; 
import LoginRegisterPage from "./routes/LoginRegisterPage"; 
import LoginAccountPage from "./routes/LoginAccountPage"; 

function App() {

    const LoginCtx = useContext(LoginContext); 

    const [ConcertsHistoryList, setConcertsHistoryList] = useState([]); 
    const [ConcertsList, setConcertsList] = useState([]); 
    const [SeatsList, setSeatsList] = useState([]); 
    const [TicketsList, setTicketsList] = useState([]); 
    const [UsersList, setUsersList] = useState([]); 

    const getConcertsHistory = async() => {
        try {
            const response = await localAPI.get("/concerts/history")
            setConcertsHistoryList(response.data)
            console.log("ConcertsHistoryList", response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    const getConcerts = async() => {
        try {
            const response = await localAPI.get("/concerts")
            setConcertsList(response.data)
            console.log("ConcertsList", response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    const getSeats = async() => {
        try {
            const response = await localAPI.get("/seats")
            setSeatsList(response.data)
            console.log("SeatsList", response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    const getTickets = async() => {
        try {
            const response = await localAPI.get("/tickets")
            setTicketsList(response.data)
            console.log("TicketsList", response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    const getUsers = async() => {
        try {
            const config = {
                headers: {"user-id": LoginCtx.userID}
            }
            const response = await localAPI.get("/users", config)
            setUsersList(response.data)
            console.log("UsersList", response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    useEffect(() => {
        getConcertsHistory(); 
        getConcerts(); 
        getSeats(); 
        getTickets(); 
        getUsers(); 
        // eslint-disable-next-line
    }, [])

    return (
        <>
        <LoginContextProvider>
            <BrowserRouter>
                <Routes>

                    <Route path="*" element={<ErrorPage/>}/>

                    <Route path="/" element={<MainPage/>}>
                        <Route index element={<HomePage/>}/>

                        <Route path="/concerts" element={<BlankPage/>}>
                            <Route index element={<PageConcerts ConcertsList={ConcertsList}/>}/>
                            <Route path="history" element={<PageConcerts ConcertsList={ConcertsHistoryList}/>}/>
                            <Route path=":ConcertID" element={<PageConcertByID ConcertsList={ConcertsHistoryList}/>}/>
                        </Route>

                        <Route path="/seats" element={<PageSeats SeatsList={SeatsList}/>}/>

                        <Route path="/tickets" element={<PageTickets TicketsList={TicketsList}/>}/>

                        <Route path="/users" element={<BlankPage/>}>
                            <Route index element={<PageUsers UsersList={UsersList}/>}/>
                            <Route path=":UserID" element={<PageUserByID UsersList={UsersList} getUsers={getUsers}/>}/>
                        </Route>

                        <Route path="/login" element={<BlankPage/>}>
                            <Route index element={<LoginPage UsersList={UsersList}/>}/>
                            <Route path="register" element={<LoginRegisterPage/>}/>
                        </Route>

                        <Route path="/account" element={<LoginAccountPage UsersList={UsersList} getUsers={getUsers} TicketsList={TicketsList}/>}/>
                    </Route>

                </Routes>
            </BrowserRouter>
        </LoginContextProvider>
        </>
    )
}

export default App; 