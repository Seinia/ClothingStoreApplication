import React, { useState, useEffect } from 'react';
import './App.css';
import Header from './Header';
import Categories from './Categories';
import Products from './Products';

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
    <div className="App">
      <Header />
      <Categories onCategoryChange={handleCategoryChange} />
      <Products products={products} />
    </div>
  );
}

export default App;
