import CartIcon from './CartIcon';
import { useAuth } from '../context/AuthContext';

export default function Header({ onLogoClick, onCartClick, onLoginClick }) {
  const { user, logout } = useAuth();

  return (
    <header className="header">
      <h1 onClick={onLogoClick}>React Shop</h1>

      <div className="header-actions">
        {user ? (
          <>
            <span>Hi, {user.firstName}</span>
            <button onClick={logout}>Log out</button>
          </>
        ) : (
          <button onClick={onLoginClick}>Log in</button>
        )}
        <CartIcon onClick={onCartClick} />
      </div>
    </header>
  );
}
