import React, { useEffect, useState } from 'react';

function FoodCard({ item , setFoodItems }) {

    const [isEditing, setIsEditing] = useState(false);

    const [editedFood, setEditedFood] = useState({
        name: item.name || "",
        details: item.details || "",
        price: item.price || "",
        image: item.image || "",
        available: item.available || false,
        category: {
            id:item.categoryId,
            name: item.categoryName
        },
        restaurant: {
            id : item.restaurantId,
            name : item.restaurantName
        }
    });

    const [categories, setCategories] = useState([]);
    const [restaurants, setRestaurants] = useState([]);

    useEffect(() => {
        fetchCategories();
        fetchRestaurants();
    }, []);

    const fetchCategories = async () => {
        try {
            const res = await fetch("http://localhost:8080/api/category/all");
            const data = await res.json();
            setCategories(data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchRestaurants = async () => {
        try {
            const res = await fetch("http://localhost:8080/api/restaurant/all");
            const data = await res.json();
            setRestaurants(data);
        } catch (err) {
            console.error(err);
        }
    };

    const handleChange = (field, value) => {
        setEditedFood(prev => ({
            ...prev,
            [field]: value
        }));
    };

    const handleSave = async () => {
        try {

            const formData = new FormData();
            formData.append("name", editedFood.name);
            formData.append("details", editedFood.details);
            formData.append("price", editedFood.price);
            formData.append("available", editedFood.available);
            formData.append(
                "categoryId",
                editedFood.category?.id
            );
            formData.append(
                "restaurantId",
                editedFood.restaurant?.id
            );
            if (editedFood.image instanceof File) {
                formData.append(
                    "image",
                    editedFood.image
                );
            }
            const res = await fetch(
                `http://localhost:8080/api/fooditems/update/${item.id}`,
                {
                    method: "PUT",
                    body: formData
                }
            );

            if (!res.ok) {
                throw new Error("Update failed");
            }
            setIsEditing(false);
        } catch (err) {
            console.error(err);
        }
    };

    const handleDelete = async () => {
        try {
            const res = await fetch(
                `http://localhost:8080/api/fooditems/${item.id}`,
                { method: "DELETE" }
            );

            if (!res.ok) {
                const msg = await res.text();
                console.error("Delete Failed :" , msg);
                throw new Error("Delete failed");
            }

            setFoodItems(prev => prev.filter(food => food.id !== item.id));

        } catch (err) {
            console.error(err);
        }
    };

    const handleCancel = () => {
        setEditedFood({
            name: item.name,
            details: item.details,
            price: item.price,
            image: item.image,
            available: item.available,
            category: {
                id : item.categoryId,
                name : item.categoryName
            },
            restaurant: {
                id : item.restaurantId,
                name : item.restaurantName
            }
        });

        setIsEditing(false);
    };

    return (
        <div className="col-lg-6 col-md-12 my-3">
            <div className="card shadow-lg p-3" style={{ borderRadius: "12px" }}>

                <div className="d-flex align-items-start gap-3">

                    <img
                        src={
                            editedFood.image instanceof File
                                ? URL.createObjectURL(editedFood.image)
                                : editedFood.image
                        }
                        alt={editedFood.name}
                        style={{
                            width: "120px",
                            height: "120px",
                            objectFit: "cover",
                            borderRadius: "10px",
                            marginTop:"30px"
                        }}
                    />

                    <div className="flex-grow-1">

                        {isEditing ? (
                            <>
                                <input
                                    className="form-control mb-2"
                                    value={editedFood.name}
                                    onChange={(e) => handleChange("name", e.target.value)}
                                />

                                <textarea
                                    className="form-control mb-2"
                                    value={editedFood.details}
                                    onChange={(e) => handleChange("details", e.target.value)}
                                />

                                <input
                                    className="form-control mb-2"
                                    type="number"
                                    value={editedFood.price}
                                    onChange={(e) => handleChange("price", e.target.value)}
                                />

                                <input
                                    type="file"
                                    className="form-control mb-2"
                                    accept="image/*"
                                    onChange={(e) =>
                                        handleChange("image", e.target.files[0])
                                    }
                                />

                                <select
                                    className="form-select mb-2"
                                    value={editedFood.category?.id || ""}
                                    onChange={(e) => {
                                        const selected = categories.find(
                                            c => c.id === Number(e.target.value)
                                        );
                                        setEditedFood(prev => ({
                                            ...prev,
                                            category: selected
                                        }));
                                    }}
                                >
                                    <option value="">Select Category</option>
                                    {categories.map(c => (
                                        <option key={c.id} value={c.id}>
                                            {c.name}
                                        </option>
                                    ))}
                                </select>

                                <select
                                    className="form-select mb-2"
                                    value={editedFood.restaurant?.id || ""}
                                    onChange={(e) => {
                                        const selected = restaurants.find(
                                            r => r.id === Number(e.target.value)
                                        );
                                        setEditedFood(prev => ({
                                            ...prev,
                                            restaurant: selected
                                        }));
                                    }}
                                >
                                    <option value="">Select Restaurant</option>
                                    {restaurants.map(r => (
                                        <option key={r.id} value={r.id}>
                                            {r.name}
                                        </option>
                                    ))}
                                </select>

                                <div className="form-check">
                                    <input
                                        type="checkbox"
                                        className="form-check-input"
                                        checked={editedFood.available}
                                        onChange={(e) =>
                                            handleChange("available", e.target.checked)
                                        }
                                    />
                                    <label className="form-check-label">
                                        Available
                                    </label>
                                </div>
                            </>
                        ) : (
                            <>
                                <h4>{editedFood.name}</h4>
                                <p>Category: {editedFood.category?.name}</p>
                                <p>Restaurant: {editedFood.restaurant?.name}</p>
                                <p>₹{editedFood.price}</p>
                                <p>{editedFood.available ? "Available" : "Out of Stock"}</p>
                            </>
                        )}

                    </div>

                    <div className="d-flex flex-column gap-2">

                        {isEditing ? (
                            <>
                                <button className="btn btn-success btn-sm" onClick={handleSave}>
                                    Save
                                </button>
                                <button className="btn btn-secondary btn-sm" onClick={handleCancel}>
                                    Cancel
                                </button>
                            </>
                        ) : (
                            <>
                                <button className="btn btn-outline-primary btn-sm" onClick={() => setIsEditing(true)}>
                                    Edit
                                </button>
                                <button className="btn btn-danger btn-sm" onClick={handleDelete}>
                                    Delete
                                </button>
                            </>
                        )}

                    </div>

                </div>
            </div>
        </div>
    );
}

export default FoodCard;