import React, { useEffect, useState } from "react";
import StatCards from "./admin/StatCards";
import RecentOrder from "./admin/RecentOrder";

function AdminHome() {

  const [stats , setStats] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/users/admin/stats")
    .then(res => res.json())
    .then(data => setStats(data));
  },[])

  const [orders , setOrders] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/orders/admin/recent-orders")
    .then(res => res.json())
    .then(data => setOrders(data));
  }, [])

  return (
    <div>

        <StatCards stats={stats}/>
        <RecentOrder orders={orders}/>

    </div>
  );
}

export default AdminHome;
