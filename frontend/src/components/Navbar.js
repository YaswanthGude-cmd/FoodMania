import { Link, NavLink, useNavigate } from "react-router-dom";
import { useState } from "react";
import './Navbar.css';
import { BsCart3 } from "react-icons/bs";
import { FiLogOut, FiHelpCircle, FiUser , FiHome } from "react-icons/fi";
import { BiCategory } from "react-icons/bi";

function Navbar({user, setUser}) {

  const navigate = useNavigate();

  
  // Extract first letter safely
  const firstLetter = user?.email
    ? user.email.charAt(0).toUpperCase()
    : "";

  // Logout function
  const handleLogout = () => {
    localStorage.removeItem("user");
    setUser(null);
    navigate("/login");
  };

  const categories = [
  "Chinese",
  "Indian",
  "Starters",
  "Biryanis",
  "Icecreams",
  "Nonveg",
  "Veg"
];

  const [open, setOpen] = useState(false);

  return (
    <div className='container'>
  <nav className="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">

    <div className="container-fluid gap-3">

      <Link className="navbar-brand" to="/">
        FoodMania
      </Link>

      {/* TOGGLER BUTTON */}
      <button
        className="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarContent"
      >
        <span className="navbar-toggler-icon"></span>
      </button>

      {/* COLLAPSIBLE CONTENT */}
      <div className="collapse navbar-collapse" id="navbarContent">

        {/* LEFT SIDE */}
        <ul className="navbar-nav me-auto gap-lg-4">

          <li className="nav-item home-animation">
            <Link className="nav-link" to="/">
            <FiHome className="me-2" />
              Home
            </Link>
          </li>

          <li className="nav-item dropdown category-dropdown">
            <span
              className="nav-link dropdown-toggle"
              role="button"
            >
              <BiCategory className="me-2"/>
              Categories
            </span>

            <ul className="dropdown-menu">
              {categories.map((cat, index) => (
                <li key={index}>
                  <Link
                    className="dropdown-item"
                    to={`/category/${cat}`}
                  >
                    {cat}
                  </Link>
                </li>
              ))}
            </ul>
          </li>

          {user && (
            <li className="nav-item">
              <Link className="nav-link" to="/cart">
                <BsCart3 className="me-2" />
                Cart
              </Link>
            </li>
          )}

          {user?.role === "ADMIN" && (
            <li className="nav-item">
              <Link className="nav-link" to="/admin">
                <FiUser className="me-2" />
                Admin
              </Link>
            </li>
          )}

        </ul>

        {/* RIGHT SIDE */}
        <ul className="navbar-nav ms-auto">

          {user ? (
            <li className="nav-item">

              <div className="profile-dropdown">

                <div
                  className="profile-icon"
                  onClick={() => setOpen(!open)}
                >
                  {firstLetter}
                </div>

                {open && (
                  <div className="profile-menu">

                    <Link
                      className="profile-info"
                      to="/my-account"
                      style={{ textDecoration: "none" }}
                    >
                      <FiUser />
                      <span>My Account</span>
                    </Link>

                    <Link
                      className="menu-item"
                      to="/help"
                      style={{ textDecoration: "none" }}
                    >
                      <FiHelpCircle />
                      <span>Help</span>
                    </Link>

                    <div
                      className="menu-item logout"
                      onClick={handleLogout}
                    >
                      <FiLogOut />
                      <span>Logout</span>
                    </div>

                  </div>
                )}
              </div>

            </li>
          ) : (
            <li className="nav-item">
              <NavLink className="btn btn-primary" to="/login">
                Login
              </NavLink>
            </li>
          )}

        </ul>

      </div>
    </div>
  </nav>
</div>
  );
}

export default Navbar;