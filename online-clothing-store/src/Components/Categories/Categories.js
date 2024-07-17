import React from 'react';
import { Link } from 'react-router-dom';
import './Categories.css'

const categories = [
  { id: 1, name: 'Головні убори' },
  { id: 2, name: 'Рюкзаки' },
  { id: 3, name: 'Штани' },
  { id: 4, name: 'Худі' },
  { id: 5, name: 'Сорочки' },
  { id: 6, name: 'Шкарпетки' },
  { id: 7, name: 'Аксесуари' },
];

function Categories() {
  return (
    <div className="categories">
      {categories.map((category) => (
        <Link key={category.id} to={`/category/${category.name}`} className="category">
          {category.name}
        </Link>
      ))}
    </div>
  );
}

export default Categories;
