import React, { useEffect, useState } from "react";
import {useNavigate} from "react-router-dom"
import { apiFetch } from "./utils/Api";
import { saveUser } from "./utils/Auth";

function EditProfile({user , setUser}) {
  const [alertMessage , setAlertMessage] = useState("");
  const [formData, setFormData] = useState({
    id: null,
    firstName: "",
    lastName: "",
    email: "",
    phoneNo: "",
    address: "",
  });

  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const accountPath = 
    user?.role === "ADMIN"?"/admin/my-account":"/my-account";

  // Load user once safely
  useEffect(() => {
    if(user){
      setFormData({
        id: user.id || null,
        firstName: user.firstName || "",
        lastName: user.lastName || "",
        email: user.email || "",
        phoneNo: user.phoneNo || "",
        address: user.address || "",
      });
    }
  }, [user]);

  // Input handler
  function handleChange(e) {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  }

  // Reset to original user data
  function handleReset() {
    if(!user) return; 

    setFormData({
      id: user.id || null,
      firstName: user.firstName || "",
      lastName: user.lastName || "",
      email: user.email || "",
      phoneNo: user.phoneNo || "",
      address: user.address || "",
    });

    setAlertMessage("Changes Reset");
    setTimeout(() => {
      setAlertMessage("");
      navigate(accountPath);
    }, 1000);
  }

  // Submit update
  async function handleSubmit(e) {
    e.preventDefault();

    setLoading(true);

    try {
      const response = await apiFetch(
        `/api/users/update/${formData.id}`,
        {
          method: "PUT",
          body: JSON.stringify(formData),
        }
      );

      if (!response.ok) {
        const error = await response.text();
        setAlertMessage(error || "Update failed");
        setTimeout(() => setAlertMessage(""), 3000);
        return;
      }

      const data = await response.json();
      setUser(data);

      // update localStorage so UI stays synced
      saveUser(data);

      setAlertMessage("Profile updated successfully");
      setTimeout(() => {
        setAlertMessage("");
        navigate(accountPath);
      }, 1000)
      
    } catch (err) {
      console.log(err);
      setAlertMessage("Something went wrong");
      setTimeout(() => setAlertMessage(""), 3000);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="container">
        {alertMessage && (
                <div
                    className="alert alert-success position-fixed start-50 translate-middle-x mb-3"
                    style={{ zIndex: 9999 ,
                        bottom:"30px"
                    }}
                >
                    {alertMessage}
                </div>
        )}
        <div
        className="d-flex justify-content-center align-items-center"
        style={{ minHeight: "100vh", marginTop: "40px" }}
        >
        <div
            className="card shadow-lg border-0 p-4"
            style={{ maxWidth: "600px", width: "100%", borderRadius: "16px" }}
        >
            <h3 className="text-center fw-bold mb-4">Edit Profile</h3>

            <form onSubmit={handleSubmit}>
            {/* First Name */}
            <div className="mb-3">
                <label className="form-label fw-semibold">First Name</label>
                <input
                type="text"
                className="form-control"
                name="firstName"
                value={formData.firstName}
                onChange={handleChange}
                />
            </div>

            {/* Last Name */}
            <div className="mb-3">
                <label className="form-label fw-semibold">Last Name</label>
                <input
                type="text"
                className="form-control"
                name="lastName"
                value={formData.lastName}
                onChange={handleChange}
                />
            </div>

            {/* Email */}
            <div className="mb-3">
                <label className="form-label fw-semibold">Email</label>
                <input
                type="email"
                className="form-control"
                name="email"
                value={formData.email}
                onChange={handleChange}
                />
            </div>

            {/* Phone */}
            <div className="mb-3">
                <label className="form-label fw-semibold">Phone</label>
                <input
                type="text"
                className="form-control"
                name="phoneNo"
                value={formData.phoneNo}
                onChange={handleChange}
                />
            </div>

            {/* Address */}
            <div className="mb-4">
                <label className="form-label fw-semibold">Address</label>
                <input
                type="text"
                className="form-control"
                name="address"
                value={formData.address}
                onChange={handleChange}
                />
            </div>

            {/* Buttons */}
            <div className="d-flex gap-2">
                <button
                type="submit"
                className="btn btn-primary w-100"
                disabled={loading}
                >
                {loading ? "Updating..." : "Update"}
                </button>

                <button
                type="button"
                className="btn btn-outline-secondary w-100"
                onClick={handleReset}
                >
                Reset
                </button>
            </div>
            </form>
        </div>
        </div>
    </div>
  );
}

export default EditProfile;