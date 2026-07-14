import React from 'react'
import { useParams } from "react-router-dom";
import CategoryPage from './CategoryPage';

function CategoryWrapper() {

    const { type } = useParams();

  return <CategoryPage category={type} />;
}

export default CategoryWrapper
