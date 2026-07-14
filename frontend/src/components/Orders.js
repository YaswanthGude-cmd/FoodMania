import React, { useEffect, useState } from 'react'
import './Admin.css';

function Orders() {

const [orders , setOrders] = useState([]);
const [statusFilter , setStatusFilter] = useState("P");
const [showDropdown , setShowDropdown] = useState(false);

// to fetch ordres by using the status 
useEffect(() => {
  const fetchOrders = async() =>{

    try{
      const response = await fetch(`http://localhost:8080/api/orders/admin-orders/${statusFilter}`);

      if(!response.ok){
        throw new Error("Failed to fetch Orders");
      }

      const ordersData = await response.json();

      setOrders(ordersData);
      
    }catch(error){
      console.error(error);
    }

  };
  fetchOrders();
}, [statusFilter]);

// to update the status of the order by using the buttons 
const updateStatus = async(orderId , status) => {
  try{
    const response = await fetch(`http://localhost:8080/api/orders/${orderId}/status/${status}`, {
      method : "PUT"
    });

    if(!response.ok){
      throw new Error("Failed to update Status");
    }

    setOrders( prev => prev.filter(order => order.orderId !== orderId));
  }
  catch(error){
    console.error(error);
  }
};

// to show the pills based on the status
const getStatusText = (status) =>{
  switch(status) {
    case "D" : return "Delivered";
    case "P" : return "Pending";
    case "C" : return "Cancelled"
    case "O" : return "Out for Delivery";
    case "A" : return "Accepted";

    default : return status; 
  }
}
  return (
    <div className='container'style={{fontFamily:"Times-New-Roman"}}>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h3 className="fw-bold mb-0">
          Orders
        </h3>
        <div className="d-flex justify-content-between align-items-center mb-4">
          <div className="custom-dropdown mt-0">
            <button
              className="dropdown-btn"
              onClick={() => setShowDropdown(!showDropdown)}
            >
              {getStatusText(statusFilter)}
              <span className={`arrow ${showDropdown ? "rotate" : ""}`}>
                ▼
              </span>
            </button>
            {showDropdown && (
              <div className="dropdown-menu-custom">
                <div
                  className="dropdown-item-custom"
                  onClick={() => {
                    setStatusFilter("P");
                    setShowDropdown(false);
                  }}
                >
                  🟡 Pending
                </div>
                <div
                  className="dropdown-item-custom"
                  onClick={() => {
                    setStatusFilter("A");
                    setShowDropdown(false);
                  }}
                >
                  🔵 Accepted
                </div>
                <div
                  className="dropdown-item-custom"
                  onClick={() => {
                    setStatusFilter("O");
                    setShowDropdown(false);
                  }}
                >
                  🚚 Out For Delivery
                </div>
                <div
                  className="dropdown-item-custom"
                  onClick={() => {
                    setStatusFilter("D");
                    setShowDropdown(false);
                  }}
                >
                  ✅ Delivered
                </div>
                <div
                  className="dropdown-item-custom"
                  onClick={() => {
                    setStatusFilter("C");
                    setShowDropdown(false);
                  }}
                >
                  ❌ Cancelled
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
      <div className='row'>
        {orders.map((item) => (
            <div className='col-lg-6 col-md-12 my-3' style={{borderRadius:'12px'}} key={item.orderId}>
                <div className='card shadow-lg p-3'>
                    <div className='card-header bg-white d-flex justify-content-between'>
                        <h5 className='text-bold'>Order ID: {item.orderId}</h5>
                        <span
                          className={`badge 
                              ${item.status === "D" ? "bg-success" : ""}
                              ${item.status === "P" ? "bg-warning text-dark" : ""}
                              ${item.status === "C" ? "bg-danger" : ""}
                              ${item.status === "O" ? "bg-success" : ""}
                              ${item.status === "A" ? "bg-success" : ""}
                          ` }
                          style={{alignItems:'center', padding:'10px'}}>
                          {getStatusText(item.status)}
                        </span>
                    </div>
                    <div className='card-body bg-white '>
                        <div>
                            <h5 className='text-bold '>Name : {item.customerName}</h5>
                            <h5 className='text-bold '>Phone NO : {item.phoneNo}</h5>
                            <h5 className='text-bold '>Address : {item.address}</h5>
                        </div>
                        <hr/>
                        <div >
                            {item.items?.map((food, index) => (
                                <h5 key={index}>
                                    {food.foodName} x {food.quantity}
                                </h5>
                            ))}
                        </div>
                        <hr/>
                        <div>
                            <h5 className='text-bold'>Total Price : {item.totalPrice}</h5>
                            <div className='d-flex justify-content-between' style={{marginTop:'15px'}}>
                                <button className='btn btn-primary' onClick={() => updateStatus(item.orderId , "A")}>Accept </button>
                                <button className='btn btn-success' onClick={() => updateStatus(item.orderId , "D")}>Delivered </button>
                                <button className='btn btn-danger' onClick={() => updateStatus(item.orderId , "C")}>Cancel </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        ))}
      </div>
    </div>
  )
}

export default Orders
