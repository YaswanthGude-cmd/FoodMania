import React, { useEffect, useState } from 'react'
import FoodCard from './FoodCard';

function FoodList() {

const [foodItems , setFoodItems] = useState([]);
const [searchTerm , setSearchTerm] = useState("");

useEffect(() => {
  const fetchFoodItems = async() =>{
      try{
        const data = await fetch("http://localhost:8080/api/fooditems/all");

        if(!data.ok) {
          throw new Error("Failed to fetch FoodItems");
        }

        const foodItemsData = await data.json();
        setFoodItems(foodItemsData);

    }catch(error){
      console.error(error);
    }
  }
  fetchFoodItems();
},[]);

const filteredFoods = foodItems.filter((item) => 
  item.name?.toLowerCase().includes(searchTerm.toLowerCase())||
  item.categoryName?.toLowerCase().includes(searchTerm.toLowerCase())
);

  return (
    <div className='container mt-4'>

      <div className="mb-4">
        <input
          type="text"
          className="form-control"
          placeholder="Search food..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>
      <div className='row'>
        {filteredFoods.map((item) => (
          <FoodCard  key={item.id} item={item} setFoodItems={setFoodItems} />
        ))}
      </div>
    </div>
  )
}

export default FoodList
