export default function CartIcon({ count, onClick }) {
  return (
    <button className="cart-button" onClick={onClick}>
      🛒 Cart ({count})
    </button>
  );
}
