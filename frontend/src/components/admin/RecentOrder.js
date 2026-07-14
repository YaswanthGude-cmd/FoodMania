import React from 'react'
// import { Navigate } from 'react-router-dom';

function RecentOrder({orders}) {
    if(!orders) return <div>Loading....</div>

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
    <div className='container' style={{fontFamily:'Times-New-Roman'}}>
        <h3 className='mb-3'>Recent Order Details</h3>
      <table className='table shadow-lg rounded-4' style={{width:'100%' , borderRadius:"10px" , overflow:"hidden" , borderCollapse:"separate"}}>
        <thead>
            <tr>
                <th scope='col'>Order</th>
                <th scope='col'>Customer</th>
                <th scope='col'>Amount</th>
                <th scope='col' style={{width:"80px"}}>Status</th>
            </tr>
        </thead>
        <tbody>

            {orders.map((item, index) => (
                <tr key={index} >

                <td>{item.id}</td>
                <td>{[item.user?.firstName , item.user?.lastName].filter(Boolean).join(" ")}</td>
                <td>{item?.totalPrice}</td>

                <td style={{width:"80px"}}>
                    <span
                    className={`badge 
                        ${item.status === "D" ? "bg-success" : ""}
                        ${item.status === "P" ? "bg-warning text-dark" : ""}
                        ${item.status === "C" ? "bg-danger" : ""}
                        ${item.status === "A" ? "bg-primary" : ""}
                    `}
                    >
                    {getStatusText(item.status)}
                    </span>
                </td>

                </tr>
            ))}

            </tbody>
      </table>
    </div>
  )
}

export default RecentOrder
