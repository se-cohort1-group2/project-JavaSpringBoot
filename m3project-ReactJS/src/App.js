import "./App.css"; 

import { BrowserRouter, Routes, Route } from "react-router-dom"; 

import HomePage from "./routes/HomePage"; 
import ErrorPage from "./routes/ErrorPage"; 

import MainPage from "./routes/MainPage"; 

import PageConcerts from "./routes/PageConcerts"; 
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
                        <Route path="/tickets" element={<PageTickets/>}/>
                        <Route path="/users" element={<PageUsers/>}/>
                    </Route>

                </Routes>
            </BrowserRouter>
        </>
    )
}

export default App; 