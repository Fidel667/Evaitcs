import { useFetch } from '../hooks/useFetch';
import { useCart } from '../context/CartContext';

export default function ProductDetail({ productId, onBack }) {
  const { data: product, loading, error } =
    useFetch(`https://dummyjson.com/products/${productId}`);
  const { dispatch } = useCart();

  if (loading) return <p>Loading product...</p>;
  if (error)   return <p className="error">{error}</p>;
  if (!product) return null;

  return (
    <div className="product-detail">
      <button onClick={onBack} className="back-btn">← Back to list</button>
      <div className="detail-grid">
        <img src={product.thumbnail} alt={product.title} />
        <div>
          <h2>{product.title}</h2>
          <p className="category">{product.category}</p>
          <p className="price-large">${product.price.toFixed(2)}</p>
          <p>{product.description}</p>
          <p className="stock">In stock: {product.stock}</p>
          <p className="rating">Rating: {product.rating} / 5</p>
          <button
            className="add-btn"
            onClick={() => dispatch({ type: 'ADD', product })}
          >
            Add to cart
          </button>
        </div>
      </div>
    </div>
  );
}
