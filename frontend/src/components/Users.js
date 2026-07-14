import React, { useEffect, useState } from 'react'

function Users() {

const [users , setUsers] = useState([]);


const fetchUsers = async() => {
  try{
    const response = await fetch("http://localhost:8080/api/users/admin");

    if(!response.ok){
      throw new Error("Failed Fetch users data");
    }

    const data = await response.json();
    setUsers(data);
  }catch(error){
    console.error(error);
  }
}

useEffect(() => { fetchUsers() } , [])

const updateStatus = async(id , status) => {
  try{
    const response = await fetch(`http://localhost:8080/api/users/${id}/status?status=${status}` , {
      method : "PUT"
    });

    if(!response.ok){
      const message = await response.text();
      throw new Error(message);
    }
    
    await fetchUsers();
  }catch(error){
    console.error(error);
    alert(`error.message`);
  }
} 
  return (
    <div className='container' style={{fontFamily:'Times-New_roman'}}>
      <div className='row'>
        {users.map((item) => (
            <div className='col-lg-6 col-md-12 my-3' style={{borderRadius:'12px'}} key={item.id}>
                <div className='card shadow-lg p-3'>
                    <div className='card-body bg-white'>
                        <div className='d-flex justify-content-between'>
                            <h5 className='text-bold'>Name : {[item.firstName , item.lastName].filter(Boolean).join(" ")}</h5>
                            <span
                            className={`badge 
                                ${item.status === "Active" ? "bg-success" : ""}
                                ${item.status === "New" ? "bg-primary " : ""}
                                ${item.status === "Blocked" ? "bg-danger" : ""}
                                ${item.status === "Inactive" ? "bg-secondary" : ""}
                            ` }
                            style={{alignItems:'center', padding:'10px'}}>
                            {item.status}
                            </span>
                        </div>
                        <h5 className='text-bold'>Email : {item.email}</h5>
                        <h5 className='text-bold'>Phone No : {item.phoneNo}</h5>
                        <h5 className='text-bold'>Address : {item.address}</h5>
                        <div className='d-flex justify-content-between'>
                            <h5 className='text-bold'>Orders : {item.totalOrders}</h5>
                            <h5 className='text-bold'>Joined : {item.joined.split("T")[0]}</h5>
                        </div>
                        <div className='d-flex justify-content-end' style={{marginTop:'10px'}}>
                            {item.status === "Blocked" ? (
                              <button className='btn btn-success' onClick={() => updateStatus(item.id , "Active")}>Unblock</button>
                            ):(
                              <button className='btn btn-danger' onClick={() => updateStatus(item.id , "Blocked")}>Block</button>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        ))}
      </div>
    </div>
  )
}

export default Users
