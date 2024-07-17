import React from 'react';
import { Link } from 'react-router-dom';
import './Products.css'

function Products({ products }) {
  return (
    <div className="products">
      {products.map((product) => (
        <div key={product.id} className="product">
          <img src={product.imagesURL?.[0]} alt={product.name} />
          <h2>{product.name}</h2>
          <p>Ціна: {product.price} грн.</p>
          <Link to={`/product/${product.id}`} className="details-button">Докладніше</Link>
        </div>
      ))}
    </div>
  );
}

export default Products;
