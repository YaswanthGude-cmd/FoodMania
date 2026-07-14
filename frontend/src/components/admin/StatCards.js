import React from 'react'

function StatCards({stats}) {

  if(!stats) return <div>Loading.....</div>

    const cards = [
        { title: "Users", value: stats.totalUsers, color: "#0d6efd" },
        { title: "Orders", value: stats.totalOrders, color: "#198754" },
        { title: "Revenue", value: stats.revenue, color: "#ff7b2f " },
        { title: "Foods", value: stats.totalFoods, color: "#dc3545" }
    ]
  return (
    <div className='container mb-3 gap-3' style={{fontFamily:'Times-New-Roman'}}>
        <h3 className='mb-3'> Overview</h3>
      <div className='row'>
        {cards.map((item , index) => (
            <div className='col-12 col-md-6 col-lg-6 gap-3 mb-4' key={index}>
                <div style={{backgroundColor:item.color , padding:'20px' , borderRadius:'10px'}} className='text-white shadow-lg'>
                    <h5>{item.title}</h5>
                    <h3>{item.value}</h3>
                </div>
            </div>
        ))}
      </div>
    </div>
  )
}

export default StatCards
