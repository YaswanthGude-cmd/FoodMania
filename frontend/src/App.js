import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import LoginPage from './components/loginPage';
import Navbar from './components/Navbar';
import Home from './components/Home';
import CreateAcc from './components/CreateAcc';
import CategoryWrapper from './components/CategoryWrapper';
import { Route, Routes, useLocation } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import CartItems from './components/CartItems';
import AdminHome from './components/AdminHome';
import AdminLayout from './components/AdminLayout';
import AddFood from './components/AddFood';
import FoodList from './components/FoodList';
import Orders from './components/Orders';
import Users from './components/Users';
import AdminRoute from './components/AdminRoute';
import Help from './components/Help';
import MyAccount from './components/MyAccount';
import EditProfile from './components/EditProfile';
import { useState } from 'react';


function App() {
  const location = useLocation();
  const path = location.pathname.toLowerCase();
  const shouldHideNavbar =
    path.startsWith("/admin") ||
    path === "/login" ||
    path === "/register";
  const [user , setUser] = useState(
    JSON.parse(localStorage.getItem("user"))
  );
  
  return (
    <>

      {!shouldHideNavbar && <Navbar user={user} setUser={setUser} />}

      <Routes>
        {/* Public routes */}
        <Route path='/login' element={<LoginPage setUser={setUser} />} />
        <Route path='/register' element={<CreateAcc />} />
        


        {/* Protected routes */}
        <Route path='/' element={
          <ProtectedRoute>
            <Home />
          </ProtectedRoute>
        } />

        <Route path='/cart' element={
          <ProtectedRoute>
            <CartItems />
          </ProtectedRoute>
        } />

        <Route path="/category/:type" element={
        <ProtectedRoute><CategoryWrapper /></ProtectedRoute>} />

        <Route path='/help' element={
          <ProtectedRoute><Help/></ProtectedRoute>} />

        <Route path='/my-account' element={
          <ProtectedRoute><MyAccount user={user} /></ProtectedRoute>
        } />

        <Route path='/edit-profile' element={
          <ProtectedRoute><EditProfile user={user} setUser={setUser} /></ProtectedRoute>
        } />

          <Route
            path="/admin"
            element={
              <AdminRoute>
                <AdminLayout />
              </AdminRoute>
            }
          >
            <Route index element={<AdminHome />} />
            <Route path="orders" element={<Orders />} />
            <Route path="users" element={<Users />} />
            <Route path="add-food" element={<AddFood />} />
            <Route path="foods" element={<FoodList />} />
            <Route path="my-account" element={<MyAccount user={user} />} />
            <Route path="edit-profile" element={<EditProfile user={user} setUser={setUser} />} />
            <Route path='help' element={<Help />} />
          </Route>
          <Route path="*" element={<LoginPage />} /> 
        </Routes>
    </>
  );
}

export default App;