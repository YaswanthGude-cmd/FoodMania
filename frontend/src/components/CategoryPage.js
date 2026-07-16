import React, { useEffect, useState } from "react";
import { getUser } from "./utils/Auth";
import { apiFetch } from "./utils/Api";

function CategoryPage({ category }) {
  const [items, setItems] = useState([]);
  const [cartId, setCartId] = useState(null);
  const [cartItems, setCartItems] = useState([]);
  const [alertMessage, setAlertMessage] = useState("");
  // const[loading , setloading] = useState(true);

  
  // 1. Fetch food items
  
  useEffect(() => {
    async function fetchFoodItems() {
      try{
        const response = await apiFetch(`/api/fooditems/category/${category}`);

        if(!response.ok){
          throw new Error("Failed to fetch FoodItems");
        }
        const data = await response.json();
        setItems(data);
      }catch(err){
        console.error("Food fetch error :" , err);
      }
    }
    fetchFoodItems();
  }, [category]);


  // 2. Load cart (ONLY ONCE)
  
useEffect(() => {
  const user = getUser();
  if (!user) return;
  async function fetchCartData() {
    try {
      // 1. Fetch cart directly
      const cartResponse = await apiFetch(`/api/cart/user/${user.id}`);
      if (!cartResponse.ok) {
        throw new Error("Failed to fetch cart");
      }
      const cartData = await cartResponse.json();
      setCartId(cartData.id);
      // 2. Fetch cart items
      const itemsResponse = await apiFetch(`/api/cartitems/user/${user.id}`);
      if (!itemsResponse.ok) {
        throw new Error("Failed to fetch cart items");
      }
      const itemsData = await itemsResponse.json();
      setCartItems(itemsData || []);
    } catch (err) {
      console.error(err);
    }
  }
  fetchCartData();
}, []);
   
  // 3. Convert cartItems → map
  
const cartMap = React.useMemo(() => {
  const map = {};

  (cartItems || []).forEach((ci) => {
    const foodId = ci.foodItems?.id;
    if (foodId) {
      map[foodId] = ci.quantity;
    }
  });

  return map;
}, [cartItems]);

  
  // 4. Add to cart (FIXED)
const addToCart = async (foodId, foodname) => {
  try {
    const user = getUser();

    if (!user) {
      setAlertMessage("Please login first");
      setTimeout(() => setAlertMessage(""), 3000);
      return;
    }

    if (cartId == null) {
      throw new Error("Cart not loaded");
    }

    // 1. Add item to cart
    const response = await apiFetch("/api/cartitems", {
      method: "POST",
      body: JSON.stringify({
        cart: { id: cartId },
        foodItems: { id: foodId },
        quantity: 1,
      }),
    });

    if (!response.ok) throw new Error("Failed to add item");

    setAlertMessage(`${foodname} added to cart`);
    setTimeout(() => setAlertMessage(""), 3000);

    // 2. Re-fetch cart (NO delay hack)
    const cartRes = await apiFetch(`/api/cartitems/user/${user.id}`);
    if (!cartRes.ok) {
      throw new Error("Cart refresh failed");
    }
    const cartItemsData = await cartRes.json();
    setCartItems(cartItemsData || []);
}catch (error) {
    console.error("Add to cart error:", error);
  }
};
  
  return (
    <div
      className="container"
      style={{  marginTop: "80px" }}
    >
      {/* ALERT */}
      {alertMessage && (
        <div
          className="alert alert-success position-fixed start-50 translate-middle-x mb-3"
          style={{ zIndex: 9999 ,
            bottom:"20px"
          }}
        >
          {alertMessage}
        </div>
      )}

      <div className="row">
        {items.map((item) => (
          <div
            className="col-lg-4 col-md-6 col-sm-12 my-3 d-flex justify-content-center"
            key={item.id}
          >
            <div className="card h-100 shadow-lg">

              {/* IMAGE */}
              <img
                src={item.image}
                className="card-img-top"
                alt={item.name}
                style={{ height: "220px", objectFit: "cover" }}
              />

              <div className="card-body d-flex flex-column">
                <h4>{item.name}</h4>
                <p>{item.details}</p>

                <p className="fw-bold mt-auto">
                  Price: <strong>{item.price} Rs</strong>
                </p>
              </div>

              {/* BUTTON */}
              <div className="d-flex justify-content-around mb-3">
                <button
                  className="btn btn-outline-primary btn-sm"
                  onClick={() => addToCart(item.id, item.name)}
                >
                  Add To Cart
                </button>
                <div className="bg-light border rounded-pill px-3 py-2 d-inline-flex align-items-center">
                  <span className="me-2 fw-semibold">
                    In Cart QNTY
                  </span>

                  <span className="badge bg-success rounded-pill">
                    {cartMap[item.id] || 0}
                  </span>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default CategoryPage;