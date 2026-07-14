import React, { useEffect, useState } from "react";

function CartItems() {

  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showAlert, setShowAlert] = useState(false);

  // Fetch cart items
  useEffect(() => {
  const user = JSON.parse(localStorage.getItem("user"));

  async function fetchCartData() {
    try {

      // Fetch cart items
      const itemsResponse = await fetch(
        `http://localhost:8080/api/cartitems/user/${user.id}`
      );
      if (!itemsResponse.ok) {
        throw new Error("Failed to load cart items");
      }
      const itemsData = await itemsResponse.json();
      setCartItems(itemsData);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  }
  fetchCartData();
}, []);

  // Increase quantity
  const increaseQty = async (id) => {
    const item = cartItems.find((i) => i.id === id);
    if (!item) return;
    const newQty = item.quantity + 1;

    try {
      const response = await fetch(
        `http://localhost:8080/api/cartitems/${id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            quantity: newQty,
          }),
        }
      );
      if (!response.ok) {
        throw new Error("Failed to increase quantity");
      }

      // Functional state update
      setCartItems((prev) =>
        prev.map((i) =>
          i.id === id
            ? { ...i, quantity: newQty }
            : i
        )
      );
    } catch (err) {
      console.error(err);
    }
  };

  // Decrease quantity
  const decreaseQty = async (id) => {
    const item = cartItems.find((i) => i.id === id);
    if (!item) return;
    const newQty = item.quantity - 1;

    try {
      // Remove item if quantity becomes 0
      if (newQty <= 0) {
        const response = await fetch(
          `http://localhost:8080/api/cartitems/${id}`,
          {
            method: "DELETE",
          }
        );
        if (!response.ok) {
          throw new Error("Failed to delete item");
        }
        // Functional update
        setCartItems((prev) =>
          prev.filter((i) => i.id !== id)
        );
      } else {
        const response = await fetch(
          `http://localhost:8080/api/cartitems/${id}`,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              quantity: newQty,
            }),
          }
        );
        if (!response.ok) {
          throw new Error("Failed to decrease quantity");
        }

        // Functional update
        setCartItems((prev) =>
          prev.map((i) =>
            i.id === id
              ? { ...i, quantity: newQty }
              : i
          )
        );
      }
    } catch (err) {
      console.error(err);
    }
  };

  // Total calculation
  const getTotal = () => {
    return cartItems.reduce(
      (total, item) =>
        total +
        (item.foodItems?.price || 0) * item.quantity,
      0
    );
  };

  // Place order
  const handlePlaceOrder = async () => {
    if (cartItems.length === 0) return;
    const user = JSON.parse(localStorage.getItem("user"));
    try {
      const response = await fetch(
        `http://localhost:8080/api/orders/place-order/${user.id}`,
        {
          method: "POST",
        }
      );

      const data = await response.json().catch(() => null);
      if (!response.ok) {
        const message = data?.message || "Something went wrong while placing order";
        throw new Error(message);
      }
      setCartItems([]);
      setShowAlert(true);
      setTimeout(() => {
        setShowAlert(false);
      }, 3000);
    } catch (err) {
      console.error(err);
      alert(err.message);
    }
  };

  // Loading state
  if (loading) {
    return (
      <h5
        className="text-dark text-center mt-5"
        style={{ fontSize: "20px" }}
      >
        Loading...
      </h5>
    );
  }

  return (
    <div className="container my-5">
      <h2 className="mb-4 pt-3 fw-bold">
        Your Cart
      </h2>
      {/* Alert */}
      {showAlert && (
        <div
          className="alert alert-success position-fixed"
          style={{
            bottom: "20px",
            right: "20px",
            zIndex: 9999,
            minWidth: "300px",
          }}
        >
          🎉 Order placed successfully!
        </div>
      )}

      {/* Empty cart */}
      {cartItems.length === 0 ? (
        <div className="text-center mt-5">
          <h4 className="fw-bold">
            Cart is Empty
          </h4>
        </div>
      ) : (
        <>
          {/* Cart Items */}
          <div className="shadow-lg rounded p-3 bg-white">
            {cartItems.map((item) => (
              <div
                key={item.id}
                className="d-flex justify-content-between align-items-center border-bottom py-3"
              >
                {/* Left Side */}
                <div>
                  <h5 className="fw-bold">
                    {item.foodItems?.name}
                  </h5>
                  <p className="mb-1">
                    Price: ₹{item.foodItems?.price}
                  </p>
                  <small className="text-muted">
                    {item.foodItems?.details}
                  </small>
                  <p className="mt-2 fw-bold">
                    Subtotal: ₹
                    {(item.foodItems?.price || 0) *
                      item.quantity}
                  </p>
                </div>

                {/* Right Side */}
                <div className="d-flex align-items-center gap-3">
                  <button
                    className="btn btn-outline-danger btn-sm"
                    onClick={() => decreaseQty(item.id)}
                  >
                    -
                  </button>
                  <span className="fw-bold">
                    {item.quantity}
                  </span>
                  <button
                    className="btn btn-outline-success btn-sm"
                    onClick={() => increaseQty(item.id)}
                  >
                    +
                  </button>
                </div>
              </div>
            ))}
          </div>

          {/* Total Section */}
          <div className="d-flex justify-content-between align-items-center mt-4">
            <h4 className="fw-bold">
              Total: ₹{getTotal()}
            </h4>
            <button
              className="btn btn-warning px-4"
              onClick={handlePlaceOrder}
            >
              Place Order
            </button>
          </div>
        </>
      )}
    </div>
  );
}

export default CartItems;