import style from "../routes/Page.module.css"; 

import { useContext, useState } from "react"; 

import LoginContext from "../context/LoginContext"; 
import localAPI from "../api/localAPI"; 

import VisibilityOn from "../images/VisibilityOn.svg"; 
import VisibilityOff from "../images/VisibilityOff.svg"; 

function formatPassword(x) {
    let maskedPassword = ""; 
    for (let i = 0; i < x.length; i++) {
        maskedPassword += "*"; 
    }
    return maskedPassword; 
}

const blankUpdateForm = {
    "name": "",
    "phone": "",
    "email": "",
    "password": ""
}

function DisplayUser({ UsersList, getUsers, displayID }) {

    const LoginCtx = useContext(LoginContext); 

    const [isEditing, setIsEditing] = useState(false); 
    const [UpdateForm, setUpdateForm] = useState(blankUpdateForm); 
    const [ShowPassword, setShowPassword] = useState(false); 

    const toggleVisibility = () => {
        ShowPassword ? setShowPassword(false) : setShowPassword(true)
    }

    // eslint-disable-next-line
    const user = UsersList.find(({ id }) => id == displayID); 

    const handleEdit = () => {
        setUpdateForm({
            "name": user.name,
            "phone": user.phone,
            "email": user.email,
            "password": user.password
        })
        setIsEditing(true); 
    }

    const handleInput = (e) => {
        setUpdateForm((prevState) => {
            return {
                ...prevState, 
                [e.target.name]: e.target.value, 
            }
        })
    }

    const handleSubmit = async (e) => {
        e.preventDefault(); 
        await updateUser(UpdateForm); 
        setIsEditing(false); 
        getUsers(); 
    }

    const updateUser = async (updatedData) => {
        try {
            const config = {
                headers: {"user-id": LoginCtx.userID}
            }
            const response = await localAPI.put(`/users/${user.id}`, updatedData, config)
            console.log("Successfully updated!", response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    return (
        <>
        <div style={{textAlign: "center"}}>
            {user && 
            <>
            {!isEditing && (
            <table className={style.MiniTable}>
                <tbody>
                    <tr>
                        <th>User ID:</th>
                        <td>{user.id}</td>
                    </tr>
                    <tr>
                        <th>Name:</th>
                        <td>{String(user.name)}</td>
                    </tr>
                    <tr>
                        <th>Phone:</th>
                        <td>{String(user.phone)}</td>
                    </tr>
                    <tr>
                        <th>Email:</th>
                        <td>{user.email}</td>
                    </tr>
                    <tr>
                        <th>Password:</th>
                        <td>{formatPassword(user.password)}</td>
                    </tr>
                </tbody>
            </table>
            )}
            {isEditing && (<form onSubmit={handleSubmit}>
            <table className={style.MiniTable}>
                <tbody>
                    <tr>
                        <th>User ID:</th>
                        <td>{user.id}</td>
                    </tr>
                    <tr>
                        <th>Name:</th>
                        <td>
                            <input value={UpdateForm.name} name="name" type="text" onChange={(e) => handleInput(e, "name")}/>
                        </td>
                    </tr>
                    <tr>
                        <th>Phone:</th>
                        <td>
                            <input value={UpdateForm.phone} name="phone" type="tel" onChange={(e) => handleInput(e, "phone")}/>
                        </td>
                    </tr>
                    <tr>
                        <th>Email:</th>
                        <td>
                            <input value={UpdateForm.email} name="email" type="email" required onChange={(e) => handleInput(e, "email")}/>
                        </td>
                    </tr>
                    <tr>
                        <th>Password:</th>
                        <td>
                            <input value={UpdateForm.password} name="password" type={ShowPassword ? "text" : "password"} required onChange={(e) => handleInput(e, "password")}/>
                            {ShowPassword ?
                            <img onClick={toggleVisibility} className={style.VisibilityOnOff} alt="VisibilityOn" src={VisibilityOn}/>
                            :
                            <img onClick={toggleVisibility} className={style.VisibilityOnOff} alt="VisibilityOff" src={VisibilityOff}/>
                            }
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td>
                            <input value="Save" type="submit"/>
                            &nbsp;&nbsp;
                            <button onClick={() => setIsEditing(false)}>Cancel</button>
                        </td>
                    </tr>
                </tbody>
            </table>
            </form>)}
            {isEditing ? <></> : <><button onClick={() => handleEdit()}>Edit user profile</button></>}
            </>
            }
        </div>
        </>
    )
}

export default DisplayUser; 