import React from 'react';

const categories = [
  { id: 1, name: 'Головні убори' },
  { id: 2, name: 'Рюкзаки' },
  { id: 3, name: 'Штани' },
  { id: 4, name: 'Худі' },
  { id: 5, name: 'Сорочки' },
  { id: 6, name: 'Взуття' },
  { id: 7, name: 'Сувеніри' },
];

function Categories({ onCategoryChange }) {
  return (
    <div className="categories">
      {categories.map((category) => (
        <div
          key={category.id}
          className="category"
          onClick={() => onCategoryChange(category.name)}
        >
          {category.name}
        </div>
      ))}
    </div>
  );
}

export default Categories;
