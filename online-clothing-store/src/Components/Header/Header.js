import React, { useState, useEffect } from 'react';
import { FaUser, FaShoppingCart } from 'react-icons/fa';
import './Header.css';

function Header() {
  const [ordersOpen, setOrdersOpen] = useState(false);
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    loadOrdersFromStorage();

    window.addEventListener('cartUpdated', handleCartUpdated);

    return () => {
      window.removeEventListener('cartUpdated', handleCartUpdated);
    };
  }, []);

  const handleCartUpdated = (event) => {
    const { orders } = event.detail;
    setOrders(orders);
  };

  const loadOrdersFromStorage = () => {
    const storedOrders = JSON.parse(localStorage.getItem('orders')) || [];
    setOrders(storedOrders);
  };

  const toggleOrders = () => {
    setOrdersOpen(!ordersOpen);
  };

  return (
    <header className="header">
      <a href="http://localhost:3000/" className="logo-link">
        Li'ma Store
      </a>
      <div className="icons">
        <FaUser className="icon" />
        <FaShoppingCart className="icon" onClick={toggleOrders} />
      </div>
      <div className={`orders-list ${ordersOpen ? 'open' : ''}`}>
        {orders.length > 0 ? (
          orders.map((order, index) => (
            <div className="order-item" key={index}>
              <img
                src={order.image}
                alt="Product"
                className="product-image"
              />
              <div className="product-details">
                <h2>{order.name}</h2>
                <p>Ціна: {order.price} грн.</p>
                <p>Кількість: {order.quantity}</p>
                <p>Розмір: {order.size}</p>
              </div>
            </div>
          ))
        ) : (
          <p className="empty-orders">Немає замовлень</p>
        )}
      </div>
    </header>
  );
}

export default Header;
