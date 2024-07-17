import React from 'react';

function Products({ products }) {
  return (
    <div className="products">
      {products.map((product) => (
        <div key={product.id} className="product">
          <img src={product.imageURL} alt={product.name} />
          <h2>{product.name}</h2>
          <p>Ціна: {product.price} грн.</p>
          <button className="details-button">Докладніше</button>
        </div>
      ))}
    </div>
  );
}

export default Products;
