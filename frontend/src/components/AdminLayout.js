import React from "react";
import Sidebar from "./admin/Sidebar";
import { Outlet } from "react-router-dom";

function AdminLayout() {

  return (
    <div>

      {/* SIDEBAR */}
      <Sidebar />

      {/* PAGE CONTENT */}
      <div
        style={{
          marginLeft: "250px",
          padding: "20px"
        }}
        className="admin-content"
      >

        <Outlet />

      </div>

      {/* MOBILE RESPONSIVE */}
      <style>
        {`
          @media (max-width: 768px) {
            .admin-content {
              margin-left: 0 !important;
            }
          }
        `}
      </style>

    </div>
  );
}

export default AdminLayout;