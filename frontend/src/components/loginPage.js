
import { useState } from 'react'
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

 function LoginPage({setUser}){
    const navigate = useNavigate();

    const[email , setemail] = useState("");
    const[pass , setpass] = useState("");
    const[msg , setmsg] = useState("");
    const[loading , setloading] = useState(false);

    const login = async (e) => {    
       e.preventDefault();

       if(email.trim() === ""){
        showMessage(`Enter The Valid Email`);
        return;
       }
       else if(pass.trim() === ""){
        showMessage(`Enter Valid password`);
        return;
       }
       setloading(true);
       showMessage("");

       // inserting the data into the users login
       try{
            const response = await fetch("http://localhost:8080/api/users/login" , {
                method: "POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body:JSON.stringify({
                    email: email,
                    password : pass
                })
            });

            console.log("Status:", response.status);

            // Retrieving the data from the users login
            if(response.ok){

                const data  = await response.json();
                
                const userData = data.user;
                localStorage.setItem("token" , data.token);
                localStorage.setItem("user" , JSON.stringify(userData));
                setUser(userData);
                if(userData.role === 'ADMIN'){
                    navigate("/admin");
                }else{
                    navigate("/")
                }
                
            }else{
                const errorText = await response.text();
                showMessage(errorText);
            }
       } catch(error){
            console.error("Full Error:" , error);
            showMessage(error.message)
       }
       finally{
        setloading(false);
       }
    };

    const showMessage = (text) => {
        setmsg(text);
        setTimeout(() => {
            setmsg("")
        } , 3000)
    }

    return (
        <div style={{backgroundColor:'#80a8c9' , height:'100vh'}} >
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
                    <form onSubmit={login} className='p-5 shadow-lg' style={{
                        display:'flex',
                        flexDirection:'column',
                        justifyContent:'center',
                        gap:'10px',
                        backgroundColor:'#c78438',
                        borderRadius:'10px',
                        fontFamily:'times-new-roman'
                    }}>
                        <h2 style={{fontWeight:'bolder' , textAlign:'center'}}>Welcome Back 👋</h2>
                        <p style={{ fontSize: '20px' }}>
                        Login to continue ordering your favorite food
                        </p>
                        <label style={{fontWeight:'bolder', fontSize:"20px"}}>Email:</label>
                        <input type="email" placeholder="Email address" style={{borderRadius:'8px'}}  className='py-2 mb-2' value={email} onChange={(e)=> setemail(e.target.value)}/>
                        <label style={{fontWeight:'bolder', fontSize:"20px"}}>Password:</label>
                        <input type="password" placeholder="Password" style={{borderRadius:'8px'}} className='py-2 mb-2' value={pass} onChange={(e) => setpass(e.target.value)}/>

                        <button className='mt-2' style={{cursor:'pointer' , borderRadius:'8px' , fontSize:'20px'}} disabled={loading}>{loading?"Logging In..":"Login"}</button>

                        {/* {msg && <p style={{fontWeight:"bolder"}}>{msg}</p>} */}

                        <p className="signup-text" style={{fontSize:'20px', display:'flex', flexDirection:'row' , gap:'10px'}}>
                            New here? <Link to="/Register" className='text-dark nav-link' style={{textDecoration:'none'}}>Create an account</Link>
                        </p>
                    </form>
            </div>
      </div>
    );
}

export default LoginPage;
