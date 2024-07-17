import React, { useState, useEffect } from 'react';
import './App.css';
import Header from './Components/Header/Header';
import Categories from './Components/Categories/Categories';
import Products from './Components/Products/Products';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Details from './Components/ProductDetails/ProductDetails';
import Category from './Components/Categories/Category';

function App() {
  const [category, setCategory] = useState('headwear');
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:8080/getItemsByCategory?category=${category}`)
      .then(response => response.json())
      .then(data => setProducts(data));
  }, [category]);

  const handleCategoryChange = (newCategory) => {
    setCategory(newCategory);
  };

  return (
    <Router>
      <div className="App">
        <Header />
        <Categories onCategoryChange={handleCategoryChange} />
        <Routes>
          <Route path="/" element={<Products products={products} />} />
          <Route path="/product/:id" element={<Details />} />
          <Route path="/category/:categoryName" element={<Category />} /> // Add this route
        </Routes>
      </div>
    </Router>
  );
}

export default App;
