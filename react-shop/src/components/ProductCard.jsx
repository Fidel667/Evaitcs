export default function ProductCard({ product, onClick }) {
  return (
    <div className="product-card" onClick={() => onClick(product.id)}>
      <img src={product.thumbnail} alt={product.title} />
      <h3>{product.title}</h3>
      <p className="price">${product.price.toFixed(2)}</p>
    </div>
  );
}
