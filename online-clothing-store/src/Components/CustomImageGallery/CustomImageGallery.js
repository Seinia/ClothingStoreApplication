import React from 'react';
import ImageGallery from 'react-image-gallery';
import 'react-image-gallery/styles/css/image-gallery.css';
import './CustomImageGallery.css'

function CustomImageGallery({ imagesURL }) {
  const images = imagesURL.map((url) => ({
    original: url,
    thumbnail: url,
  }));

  const galleryStyles = {
    showFullscreenButton: false,
    showPlayButton: false,
    showNav: true,
    lazyLoad: true,
    showBullets: true,
    showThumbnails: false,
    renderItem: (item) => (
      <div className="image-gallery-image">
        <img src={item.original} alt={item.originalAlt} style={{ maxHeight: '700px', maxWidth: '800px', }} />
      </div>
    ),
    renderLeftNav: (onClick, disabled) => (
      <button
        type="button"
        className={`image-gallery-left-nav ${disabled ? 'disabled' : ''}`}
        disabled={disabled}
        onClick={onClick}
        aria-label="Previous Slide"
      >
        &lt;
      </button>
    ),
    renderRightNav: (onClick, disabled) => (
      <button
        type="button"
        className={`image-gallery-right-nav ${disabled ? 'disabled' : ''}`}
        disabled={disabled}
        onClick={onClick}
        aria-label="Next Slide"
      >
        &gt;
      </button>
    ),
  };

  return <ImageGallery items={images} {...galleryStyles} />;
}

export default CustomImageGallery;
