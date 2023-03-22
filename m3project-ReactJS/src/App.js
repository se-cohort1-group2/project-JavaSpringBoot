import "./App.css"; 

import { BrowserRouter, Routes, Route } from "react-router-dom"; 

import ErrorPage from "./routes/ErrorPage"; 
import MainPage from "./routes/MainPage"; 
import HomePage from "./routes/HomePage"; 

import PageConcerts from "./routes/PageConcerts"; 
import PageSeats from "./routes/PageSeats"; 
import PageTickets from "./routes/PageTickets"; 
import PageUsers from "./routes/PageUsers"; 

function App() {
    return (
        <>
            <BrowserRouter>
                <Routes>

                    <Route path="*" element={<ErrorPage/>}/>

                    <Route path="/" element={<MainPage/>}>
                        <Route index element={<HomePage/>}/>
                        <Route path="/concerts" element={<PageConcerts/>}/>
                        <Route path="/seats" element={<PageSeats/>}/>
                        <Route path="/tickets" element={<PageTickets/>}/>
                        <Route path="/users" element={<PageUsers/>}/>
                    </Route>

                </Routes>
            </BrowserRouter>
        </>
    )
}

export default App; 