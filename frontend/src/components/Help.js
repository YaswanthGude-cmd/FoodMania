import React, { useState } from "react";

export default function Help() {
  const [formData, setFormData] = useState({
    type: "Order Issue",
    message: ""
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const text = `Type: ${formData.type}\nMessage: ${formData.message}`;

    try {
        const subject = encodeURIComponent(formData.type);
        const body = encodeURIComponent(text);

        window.open(
        `https://mail.google.com/mail/?view=cm&fs=1&to=officialfoodmania07@gmail.com&su=${subject}&body=${body}`,
        "_blank"
        );

        setFormData({
            type:"Order Issue",
            message:""
        });
    } catch (err) {
        navigator.clipboard.writeText(text);
        alert("Copied to clipboard. Please send mail manually.");
    }
  };

  return (
    <div className="container py-5" style={{marginTop:'80px'}}>
      <div className="row g-4">

        {/* LEFT: Contact Info */}
        <div className="col-md-4">
          <div className="card shadow-sm border-0 p-4 h-100">
            <h4 className="mb-3">Support Center</h4>

            <p className="text-muted mb-3">
              Reach out to us for any issues related to orders, payments, or app bugs.
            </p>

            <hr />

            <h6>Email</h6>
            <p>officialfoodmania07@gmail.com</p>

            <h6>Phone</h6>
            <p>+91 9876543210</p>

            <h6>Response Time</h6>
            <p>Within 24-48 hours</p>
          </div>
        </div>

        {/* RIGHT: Form */}
        <div className="col-md-8">
          <div className="card shadow-sm border-0 p-4">
            <h4 className="mb-3">Raise a Complaint</h4>

            <form onSubmit={handleSubmit}>
              
              {/* Type */}
              <div className="mb-3">
                <label className="form-label">Complaint Type</label>
                <select
                  name="type"
                  className="form-select"
                  value={formData.type}
                  onChange={handleChange}
                >
                  <option>Order Issue</option>
                  <option>Payment Issue</option>
                  <option>Delivery Delay</option>
                  <option>Refund Request</option>
                  <option>App Bug</option>
                  <option>Other</option>
                </select>
              </div>

              {/* Message */}
              <div className="mb-3">
                <label className="form-label">Message</label>
                <textarea
                  name="message"
                  className="form-control"
                  rows="5"
                  placeholder="Describe your issue..."
                  value={formData.message}
                  onChange={handleChange}
                  required
                />
              </div>

              {/* Button */}
              <button className="btn btn-primary w-100" type="submit">
                Send Complaint
              </button>
            </form>
          </div>
        </div>

      </div>
    </div>
  );
}
