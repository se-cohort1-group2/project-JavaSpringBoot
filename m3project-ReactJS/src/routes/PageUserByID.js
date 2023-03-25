import style from "./Page.module.css"; 

import { useContext, useState } from "react"; 
import { useParams, Link } from "react-router-dom"; 

import LoginContext from "../context/LoginContext"; 
import localAPI from "../api/localAPI"; 

const blankUpdateForm = {
    "name": "",
    "phone": "",
    "email": "",
    "password": ""
}

function PageUserByID({ UsersList, getUsers }) {

    const LoginCtx = useContext(LoginContext); 

    const [isEditing, setIsEditing] = useState(false); 
    const [UpdateForm, setUpdateForm] = useState(blankUpdateForm); 

    const {UserID} = useParams(); 

    // eslint-disable-next-line
    const user = UsersList.find(({ id }) => id == UserID); 

    /* console.log("-----", user)
    if (typeof user === "object") {
        console.log("-----", user.id)
    } */

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
        //window.location.reload(); 
        getUsers(); 
    }

    const updateUser = async (updatedData) => {
        try {
            const config = {
                headers: {"user-id": LoginCtx.userID} // user.id // LoginCtx.userID
            }
            const response = await localAPI.put(`/users/${user.id}`, updatedData, config)
            console.log("Successfully updated!", response.data)
        } catch (error) {
            console.log(error.message)
        }
    }

    return (
        <>
        <div className={style.page}>
            <div className={style.title}>View User by ID</div>
            {user && 
            <>
            <button onClick={() => handleEdit()}>Update user profile</button>
            <br/><br/>
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
                        <td>{user.password}</td>
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
                            <input value={UpdateForm.password} name="password" type="password" required onChange={(e) => handleInput(e, "password")}/>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td>
                            <input value="Save" type="submit"/>
                        </td>
                    </tr>
                </tbody>
            </table>
            </form>)}
            </>
            }
            <Link to="/users" className={style.BackLink}>
                <span className={style.BackArrow}>🡄</span>
                <span className={style.BackText}>&nbsp;Back</span>
            </Link>
        </div>
        </>
    )
}

export default PageUserByID; 