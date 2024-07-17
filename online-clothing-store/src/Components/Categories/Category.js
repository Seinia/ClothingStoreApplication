import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import Products from '../Products/Products';
import './Categories.css'

function Category() {
  const { categoryName } = useParams();
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:8080/getItemsByCategory?category=${categoryName}`)
      .then(response => response.json())
      .then(data => setProducts(data));
  }, [categoryName]);

  return (
    <div>
      <h1>{categoryName}</h1>
      <Products products={products} />
    </div>
  );
}

export default Category;