import React, { useEffect, useState } from "react";
import StatCards from "./admin/StatCards";
import RecentOrder from "./admin/RecentOrder";
import { apiFetch } from "./utils/Api";

function AdminHome() {

  const [stats , setStats] = useState([]);

  useEffect(() => {
    async function fetchStats() {

      try{
        const response = await apiFetch("/api/users/admin/stats");
        if(!response.ok){
          throw new Error("failed to load Admin Stats");
        }

        const data = await response.json();
        setStats(data);

      }catch(err){
        console.error(err);
      }
    }
    fetchStats();
  },[])

  const [orders , setOrders] = useState([]);

  useEffect(() => {
    async function fetchRecentOrders() {

      try{
        const response = await apiFetch("/api/orders/admin/recent-orders");
        if(!response.ok){
          throw new Error("Failed to load Recent Orders");
        }

        const data = await response.json();
        setOrders(data);
      }catch(err){
        console.error(err);
      }
    }
    fetchRecentOrders();
  }, [])

  return (
    <div>

        <StatCards stats={stats}/>
        <RecentOrder orders={orders}/>

    </div>
  );
}

export default AdminHome;
