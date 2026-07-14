import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FiHome, FiPlusSquare, FiList, FiShoppingBag, FiUsers , FiUser , FiHelpCircle, FiLogOut } from "react-icons/fi";

function Sidebar() {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("user");
    navigate("/login");
  };

  const user = JSON.parse(localStorage.getItem("user"));

  return (
    <>
      {/* MOBILE TOP BAR */}
      <div
        className="mobileTopBar d-flex d-md-none align-items-center justify-content-between px-3 py-2 shadow-sm"
        style={{
          backgroundColor: "#212529",
          position: "sticky",
          top: 0,
          zIndex: 1000,
        }}
      >
        <div className="d-flex align-items-center gap-3">
          <div
            className="d-flex justify-content-center align-items-center"
            style={{
              width: "45px",
              height: "45px",
              borderRadius: "50%",
              background: "linear-gradient(135deg, #0d6efd, #2563eb)",
              fontSize: "18px",
              fontWeight: "700",
              color: "white",
              textTransform: "uppercase",
            }}
          >
            {user?.firstName?.charAt(0)}
          </div>

          <div>
            <h6 style={{ margin: 0, color: "white" }}>Admin Panel</h6>
            <small style={{ color: "#adb5bd" }}>Welcome back</small>
          </div>
        </div>

        <button
          onClick={() => setOpen(true)}
          style={{
            padding: "6px 10px",
            border: "none",
            background: "#fff",
            borderRadius: "4px",
          }}
        >
          ☰
        </button>
      </div>

      {/* OVERLAY */}
      {open && (
        <div
          onClick={() => setOpen(false)}
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            background: "rgba(0,0,0,0.5)",
            zIndex: 999,
          }}
        />
      )}

      {/* SIDEBAR */}
      <div
        className="sidebar"
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          height: "100vh",
          width: "250px",
          backgroundColor: "#212529",
          color: "white",
          padding: "20px",
          zIndex: 1000,
          transform: open ? "translateX(0)" : "translateX(-100%)",
          transition: "0.3s ease-in-out",
          display: "flex",
          flexDirection: "column",
        }}
      >
        {/* HEADER */}
        <div className="d-flex align-items-center gap-3 pb-3 border-bottom">
          <div
            className="d-flex justify-content-center align-items-center"
            style={{
              width: "40px",
              height: "40px",
              borderRadius: "50%",
              background: "linear-gradient(135deg, #0d6efd, #2563eb)",
              fontSize: "18px",
              fontWeight: "700",
              color: "white",
              textTransform: "uppercase",
            }}
          >
            {user?.firstName?.charAt(0)}
          </div>

          <h5 className="mb-0 fw-bold">Admin Panel</h5>
        </div>

        {/* MENU */}
        <div style={{ flex: 1, overflowY: "auto", marginTop: "15px" }}>
          <ul style={{ listStyle: "none", padding: 0, margin: 0 }}>

            <li className="mb-3">
              <Link to="/admin" className="text-white text-decoration-none">
                <FiHome style={{ marginRight: "10px" }} /> Dashboard
              </Link>
            </li>

            <li className="mb-3">
              <Link to="/admin/add-food" className="text-white text-decoration-none">
                <FiPlusSquare style={{ marginRight: "10px" }} /> Add Food
              </Link>
            </li>

            <li className="mb-3">
              <Link to="/admin/foods" className="text-white text-decoration-none">
                <FiList style={{ marginRight: "10px" }} /> Food List
              </Link>
            </li>

            <li className="mb-3">
              <Link to="/admin/orders" className="text-white text-decoration-none">
                <FiShoppingBag style={{ marginRight: "10px" }} /> Orders
              </Link>
            </li>

            <li className="mb-3">
              <Link to="/admin/users" className="text-white text-decoration-none">
                <FiUsers style={{ marginRight: "10px" }} /> Users
              </Link>
            </li>

            <li className="mb-3">
              <Link className="text-white text-decoration-none" to='/admin/my-account'>
                <FiUser style={{marginRight:"10px"}} />
                My Account
              </Link>
            </li>

            <li className="mb-3">
              <Link className="text-white text-decoration-none" to='/admin/help'>
                <FiHelpCircle style={{marginRight:"10px"}} />
                  Help
              </Link>
            </li>
          </ul>
        </div>

        {/* FOOTER */}
        <div className="pt-3 border-top">
          <button className="btn btn-warning w-100 mb-2" onClick={() => navigate('/')}>
            <FiUser style={{marginRight:"10px"}}/>User View
          </button>

          <button className="btn btn-danger w-100" onClick={handleLogout}>
            <FiLogOut style={{marginRight:"10px"}}/>
            Logout
          </button>
        </div>
      </div>

      {/* RESPONSIVE FIX */}
      <style>{`
        @media (min-width: 768px) {
          .sidebar {
            transform: translateX(0) !important;
          }

          .mobileTopBar {
            display: none !important;
          }
        }
      `}</style>
    </>
  );
}

export default Sidebar;