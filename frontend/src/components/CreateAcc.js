import React , { useState } from 'react'
import { apiFetch } from './utils/Api';
import { useNavigate } from 'react-router-dom';

function CreateAcc() {

    const[fname , setfname] = useState("");
    const[lname , setlname] = useState("");
    const[mail , setmail] = useState("");
    const[pass , setpass] = useState("");
    const[pno , setpno] = useState("");
    const[msg , setmsg] = useState("");
    const[cpass , setcpass] = useState("");
    const[address , setaddress] = useState("");
    const[loading , setloading] = useState(false);
    const navigate = useNavigate();

    // Function to show the alert box
    const showMessage = (text) => {
        setmsg(text);
        setTimeout(() => {
            setmsg("")
        } , 3000)
    }

        const register = async (e) => {
        e.preventDefault();

        if (!fname.trim()) {
            showMessage("Enter First Name");
            return;
        }

        if (!lname.trim()) {
            showMessage("Enter Last Name");
            return;
        }

        if (!pno.trim() || pno.length < 10) {
            showMessage("Enter valid phone number");
            return;
        }

        if (!mail.trim().includes("@")) {
            showMessage("Enter valid email");
            return;
        }

        if (!pass.trim()) {
            showMessage("Enter password");
            return;
        }

        if (pass !== cpass) {
            showMessage("Passwords do not match");
            return;
        }

        setloading(true);
        showMessage("");

        try {
            const response = await apiFetch("/api/users/register", {
                method: "POST",
                body: JSON.stringify({
                    firstName: fname,
                    lastName: lname,
                    email: mail,
                    password: pass,
                    phoneNo: pno,
                    address: address,
                    role: "USER"   //  FIX: force backend-safe role
                })
            });

            let data;
            try {
                data = await response.json();
            } catch {
                data = { message: "Unexpected server response" };
            }

            if (response.ok) {
                showMessage("Successfully Registered! Now Login");

                setfname("");
                setlname("");
                setmail("");
                setcpass("");
                setpass("");
                setpno("");
                setaddress("");
                setTimeout(() => {
                    navigate("/login" , {
                        state : {
                            email : mail
                        }
                    });
                } , 1500)
                
            } else {
                showMessage(data.message || "Registration Failed");
            }

        } catch (error) {
            console.error(error);
            showMessage("Server problem");
        } finally {
            setloading(false);
        }

    };
  return (
    <div style={{backgroundColor:'#80a8c9' , height:'100vh'}}>
        {msg && (
                <div
                    className="alert alert-warning position-fixed bottom-0 start-50 translate-middle-x mb-3"
                    style={{ zIndex: 9999 , fontFamily:'Times-New-Roman'}}
                >
                    <strong>{msg}</strong>
                </div>
            )}
      <div className='login-container' style={{
        display:'flex',
        justifyContent:'center', 
        alignItems:'center', 
        height:'100vh'
        }}>
            <form onSubmit={register} className='p-5 gap-1 shadow-lg' style={{
                display:'flex',
                flexDirection:'column',
                justifyContent:'center',
                // gap:'8px',
                backgroundColor:'#c78438',
                borderRadius:'10px',
                fontFamily:'times-new-roman'
            }}>
                <h2 style={{fontWeight:'bolder'}}>Welcome 🙇‍♂️</h2>
                <p style={{fontSize:'20px' }}>Create Account to order your favorite food</p>
                <label style={{fontWeight:'bolder' , fontSize:'20px'}}>FirstName:</label>
                <input type='text' placeholder='FirstName' style={{borderRadius:'8px'}}  className='py-2 mb-2' value={fname} onChange={(e) => setfname(e.target.value)}/>
                <label style={{fontWeight:'bolder' , fontSize:'20px'}}>LastName:</label>
                <input type='text' placeholder='LastName' style={{borderRadius:'8px'}}  className='py-2 mb-2' value={lname} onChange={(e) => setlname(e.target.value)}/>
                <label style={{fontWeight:'bolder' , fontSize:'20px'}}>Phone No:</label>
                <input type='text' placeholder='Phone No' style={{borderRadius:'8px'}}  className='py-2 mb-2' value={pno} onChange={(e)=>setpno(e.target.value)}/>
                <label style={{fontWeight:'bolder' , fontSize:'20px'}}>Email:</label>
                <input type='email' placeholder='Email' style={{borderRadius:'8px'}}  className='py-2 mb-2' value={mail} onChange={(e)=>setmail(e.target.value)}/>
                <label style={{fontWeight:'bolder' , fontSize:'20px'}}>Password:</label>
                <input type='password' placeholder='Password' style={{borderRadius:'8px'}}  className='py-2 mb-2'value={pass} onChange={(e) =>setpass(e.target.value)}/>
                <label style={{fontWeight:'bolder' , fontSize:'20px'}}>Confirm Password:</label>
                <input type='password' placeholder='Confirm Password' style={{borderRadius:'8px'}}  className='py-2 mb-2'value={cpass} onChange={(e) =>setcpass(e.target.value)}/>
                <label style={{fontWeight:'bolder' , fontSize:'20px'}}>Address:</label>
                <input type='text' placeholder='Address' style={{borderRadius:'8px'}}  className='py-2 mb-2'value={address} onChange={(e) =>setaddress(e.target.value)}/>
                <button type='submit' className='mt-2 px-0' style={{cursor:'pointer' , borderRadius:'8px' , fontSize:'20px'}} disabled={loading}>
                    {loading ? "Registering..." : "Register"}</button>

                {/* {msg && <p className='mt-2' style={{fontWeight:"bolder"}}>{msg}</p>} */}
            </form>
      </div>
    </div>
  )
}

export default CreateAcc
