import React from 'react'
import { Link } from 'react-router-dom';

function MyAccount({user}) {
    
    const editPath = user?.role === "ADMIN" ? "/admin/edit-profile" : "/edit-profile";
    
  return (
    <div
        className="container d-flex justify-content-center align-items-center"
        style={{ minHeight: "100vh", marginTop: "40px" }}
        >
        <div
            className="card border-0 shadow-lg p-4"
            style={{
            maxWidth: "600px",
            width: "100%",
            borderRadius: "20px",
            background: "#ffffff",
            }}
        >
            {/* Header */}
            <div className="text-center mb-4">
            <div
                className="mx-auto d-flex justify-content-center align-items-center mb-3"
                style={{
                width: "80px",
                height: "80px",
                borderRadius: "50%",
                background: "#f1f3f5",
                fontSize: "32px",
                fontWeight: "bold",
                color: "#0d6efd",
                }}
            >
                {user?.firstName?.charAt(0)}
            </div>

            <h2 className="fw-bold mb-1">My Account</h2>

            <p className="text-muted mb-0">
                Manage your personal information
            </p>
            </div>

            {/* User Details */}
            <div className="d-flex flex-column gap-3">

            <div className="d-flex justify-content-between align-items-center border rounded-3 p-3 bg-light">
                <span className="fw-semibold text-secondary">First Name</span>
                <span className="fw-bold">{user?.firstName || "N/A"}</span>
            </div>

            <div className="d-flex justify-content-between align-items-center border rounded-3 p-3 bg-light">
                <span className="fw-semibold text-secondary">Last Name</span>
                <span className="fw-bold">{user?.lastName || "N/A"}</span>
            </div>

            <div className="d-flex justify-content-between align-items-center border rounded-3 p-3 bg-light">
                <span className="fw-semibold text-secondary">Email</span>
                <span className="fw-bold text-break">
                {user?.email || "N/A"}
                </span>
            </div>

            <div className="d-flex justify-content-between align-items-center border rounded-3 p-3 bg-light">
                <span className="fw-semibold text-secondary">Phone No</span>
                <span className="fw-bold">
                {user?.phoneNo || "N/A"}
                </span>
            </div>

            <div className="d-flex justify-content-between align-items-start border rounded-3 p-3 bg-light">
                <span className="fw-semibold text-secondary">Address</span>
                <span
                className="fw-bold text-end"
                style={{ maxWidth: "60%" }}
                >
                {user?.address || "N/A"}
                </span>
            </div>

            </div>

            {/* Button */}
            <div className="mt-4 text-center">
            <Link className="btn btn-primary px-4 py-2 rounded-pill" to={editPath}>
                Edit Profile
            </Link>
            </div>
        </div>
    </div>
  )
}

export default MyAccount
