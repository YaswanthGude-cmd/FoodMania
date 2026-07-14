import React, { useState , useRef } from 'react'


function AddFood() {

  const [name , setName] = useState("");
  const [description , setDescription] = useState("");
  const [price , setPrice] = useState("");
  const [categoryId , setcategoryId] = useState(0);
  const [restaurantId , setrestaurantId] = useState("");
  const [available , setAvailable] = useState(false);
  const [image , setimage] = useState(null);
  const [categoryName , setCategoryName] = useState("");
  const [alertMessage , setAlertMessage] = useState("");
  const fileInputRef = useRef(null);


  const addFood = async(e) => {
    e.preventDefault();

    if(name.trim() === "" || description.trim() === "" || !price || !categoryId || !restaurantId || !image){
      setAlertMessage(`Please Fill All required fields`);
      setTimeout(() => setAlertMessage(""), 3000);
      return;
    }

    try{ 

      const formData = new FormData();

      formData.append("name" ,name);
      formData.append("details" , description);
      formData.append("price" , price);
      formData.append("restaurantId" , restaurantId);
      formData.append("categoryId" , categoryId);
      formData.append("image" , image)

      const response = await fetch("http://localhost:8080/api/fooditems/additem" ,{
        method : "POST",
        body : formData
      });
      let data ;
      try{
        data = await response.json();
      }catch{
        data = {message : "Unexpected Server Response"};
      }

      if(response.ok){
        setAlertMessage(`Successfully FoodItem Added`);
        setTimeout(() => setAlertMessage(""), 3000);

        setName("");
        setDescription("");
        setPrice("");
        setrestaurantId("");
        setcategoryId(0);
        setCategoryName("");
        setAvailable(false);
        setimage(null);
        if(fileInputRef.current){
          fileInputRef.current.value = "";
        }
      }else{
        setAlertMessage( data.message ||`Adding Food Item Failed`);
        setTimeout(() => setAlertMessage(""), 3000);
      }
    }catch(error){
      console.error(error);
      setAlertMessage(`Server Problem`);
      setTimeout(() => setAlertMessage(""), 3000);
    }
  }

  return (
    <div className='container'>
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
      <div style={{
        display:'flex',
        justifyContent:'center',
        alignItems:'center',
        height:'90vh'
      }}>
        <form style={{
            display:'flex',
            flexDirection:'column',
            justifyContent:'center',
            gap:'10px',
            backgroundColor:'#c78438',
            borderRadius:'10px',
            fontFamily:'times-new-roman'
        }} className='shadow-lg p-3' onSubmit={addFood}> 
            <h2 style={{fontWeight:'bolder' , textAlign:'center'}}>Welcome To Add Food Items 🍜</h2>
            <label style={{fontWeight:'bolder', fontSize:"20px"}}>Name</label>
            <input type='text' placeholder='Name Of an Item' style={{borderRadius:'8px'}}  className='py-2 mb-2' value={name} onChange={(e) => setName(e.target.value)}/>
            <label style={{fontWeight:'bolder', fontSize:"20px"}}>Description</label>
            <input type='text' placeholder='Description Of an Item' style={{borderRadius:'8px'}}  className='py-2 mb-2' value={description} onChange={(e) => setDescription(e.target.value)}/>
            <label style={{fontWeight:'bolder', fontSize:"20px"}}>Price</label>
            <input type='text' placeholder='Price Of an Item' style={{borderRadius:'8px'}}  className='py-2 mb-2' value={price} onChange={(e) => setPrice(e.target.value ? Number(e.target.value) : "")}/>
            <label style={{fontWeight:'bolder', fontSize:"20px"}}>Availablity</label>
            <select
              value={available.toString()}
              onChange={(e) => setAvailable(e.target.value === "true")}
              className='py-2 mb-2'
              style={{ borderRadius: '8px' }}
            >
              <option value="true">Yes</option>
              <option value="false">No</option>
            </select>
            <label style={{fontWeight:'bolder', fontSize:"20px"}}>restaurantId</label>
            <input type='number' placeholder='restaurantId' style={{borderRadius:'8px'}}  className='py-2 mb-2' value={restaurantId} onChange={(e) => setrestaurantId(Number(e.target.value))}/>
            <label style={{fontWeight:'bolder', fontSize:"20px"}}>categoryId</label>
            <div className="dropdown-center">
              <button className="btn btn-secondary dropdown-toggle text-black my-2" type="button" data-bs-toggle="dropdown" aria-expanded="false"
              style={{backgroundColor:'white' , width:'100%' , fontSize:'20px'}}>
              {categoryName || "category"}
              </button>
              <ul className="dropdown-menu mx-auto text-center" style={{width:'50%'}}>
                <li><button className="dropdown-item" type='button' onClick={() => {setcategoryId(3) ; setCategoryName("Chinese")}} style={{fontWeight:'bolder' , fontSize:'18px'}}>Chinese</button></li>
                <li><button className="dropdown-item" type='button' onClick={() => {setcategoryId(2) ; setCategoryName("Indian")}} style={{fontWeight:'bolder' , fontSize:'18px'}}>Indian</button></li>
                <li><button className="dropdown-item" type='button' onClick={() => {setcategoryId(4) ; setCategoryName("Starters")}} style={{fontWeight:'bolder' , fontSize:'18px'}}>Starters</button></li>
                <li><button className="dropdown-item" type='button' onClick={() => {setcategoryId(5) ; setCategoryName("Biryanis")}} style={{fontWeight:'bolder' , fontSize:'18px'}}>Biryanis</button></li>
                <li><button className="dropdown-item" type='button' onClick={() => {setcategoryId(6) ; setCategoryName("IceCreams")}} style={{fontWeight:'bolder' , fontSize:'18px'}}>IceCreams</button></li>
                <li><button className="dropdown-item" type='button' onClick={() => {setcategoryId(7) ; setCategoryName("Nonveg")}} style={{fontWeight:'bolder' , fontSize:'18px'}}>Non-Veg</button></li>
                <li><button className="dropdown-item" type='button' onClick={() => {setcategoryId(8) ; setCategoryName("Veg")}} style={{fontWeight:'bolder' , fontSize:'18px'}}>Veg</button></li>
              </ul>
            </div>
            <label>Food Image</label>

            <input
              ref={fileInputRef}
              type="file"
              accept="image/*"
              className="py-2 mb-2"
              style={{
                  borderRadius: "8px",
                  backgroundColor: "white",
                  border: "1px solid #ced4da",
                  padding: "8px"
              }}
              onChange={(e) => setimage(e.target.files[0])}
          />
            <button type='submit' className='mt-2 py-1' style={{cursor:'pointer' , borderRadius:'8px' , fontSize:'20px'}}>Submit</button>
        </form>
      </div>
    </div>
  )
}

export default AddFood
