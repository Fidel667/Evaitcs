import { useCart } from '../context/CartContext';

export default function CartIcon({ onClick }) {
  const { totalItems } = useCart();

  return (
    <button className="cart-button" onClick={onClick}>
      🛒 Cart ({totalItems})
    </button>
  );
}
