import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import CustomImageGallery from '../CustomImageGallery/CustomImageGallery';
import './ProductDetails.css';

function Details() {
  const { id } = useParams();
  const [item, setItem] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [size, setSize] = useState(null);

  useEffect(() => {
    localStorage.removeItem('orders');
  }, []);

  useEffect(() => {
    fetch(`http://localhost:8080/getItem?id=${id}`)
      .then(response => response.json())
      .then(data => {
        console.log('Received data:', data);
        setItem(data);
      });
  }, [id]);

  if (!item) return <div>Loading...</div>;

  const handleAddToCart = () => {
    const orderItem = {
      id: item.id,
      name: item.name,
      price: item.price,
      quantity: quantity,
      size: size,
      image: item.imagesURL[0]
    };

    const orders = JSON.parse(localStorage.getItem('orders')) || [];

    orders.push(orderItem);

    localStorage.setItem('orders', JSON.stringify(orders));

    window.dispatchEvent(new CustomEvent('cartUpdated', {
      detail: {
        orders: orders
      }
    }));

    setQuantity(1);
    setSize(null);

  };

  const sizes = ['xs', 's', 'm', 'l', 'xl'];

  return (
    <div className="details-container">
      <div className="gallery-container">
        {item.imagesURL && (
          <CustomImageGallery imagesURL={item.imagesURL} />
        )}
      </div>
      <div className="title-container">
        <h1 style={{ fontSize: '34px', fontWeight: '700', margin: '0 0 10px' }}>{item.name}</h1>
      </div>
      <div className="product-info-container">
        <ProductInformation item={item} />
        <SizeSelector sizes={sizes} size={size} setSize={setSize} />
        <QuantitySelector quantity={quantity} setQuantity={setQuantity} />
        <AddToCartButton handleAddToCart={handleAddToCart} />
      </div>
    </div>
  );
}

function ProductInformation({ item }) {
  return (
    <div style={{ margin: '20px 0' }}>
      <p className="product-info-price">Ціна: {item.price} грн.</p>
      <p className="product-info-description">{item.description}</p>
    </div>
  );
}

function SizeSelector({ sizes, size, setSize }) {
  return (
    <div className="size-selector-container" style={{ margin: '20px 0' }}>
      {sizes.map((sizeOption) => (
        <button
          className={`size-selector ${size === sizeOption ? 'active' : ''}`}
          key={sizeOption}
          onClick={() => setSize(sizeOption)}
        >
          {sizeOption.toUpperCase()}
        </button>
      ))}
    </div>
  );
}

function QuantitySelector({ quantity, setQuantity }) {
  const handleIncrement = () => setQuantity(quantity + 1);
  const handleDecrement = () => setQuantity(quantity > 1 ? quantity - 1 : 1);

  return (
    <div className="quantity-selector-container" style={{ margin: '20px 0' }}>
      <button className="quantity-selector1" onClick={handleDecrement}>-</button>
      <span style={{ fontSize: '18px', margin: '0 10px' }}>{quantity}</span>
      <button className="quantity-selector2" onClick={handleIncrement}>+</button>
    </div>
  );
}

function AddToCartButton({ handleAddToCart }) {
  return (
    <button onClick={handleAddToCart} className="add-to-cart-button">
      Додати до кошику
    </button>
  );
}

export default Details;
