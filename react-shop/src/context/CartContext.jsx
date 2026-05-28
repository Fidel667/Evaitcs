import { createContext, useContext, useEffect, useReducer } from 'react';

const CartContext = createContext(null);

const initialState = {
  items: [],   // [{ id, title, price, thumbnail, quantity }]
};

function cartReducer(state, action) {
  switch (action.type) {
    case 'ADD': {
      const existing = state.items.find(i => i.id === action.product.id);
      if (existing) {
        return {
          ...state,
          items: state.items.map(i =>
            i.id === action.product.id ? { ...i, quantity: i.quantity + 1 } : i
          ),
        };
      }
      return {
        ...state,
        items: [...state.items, { ...action.product, quantity: 1 }],
      };
    }

    case 'REMOVE':
      return {
        ...state,
        items: state.items.filter(i => i.id !== action.id),
      };

    case 'SET_QUANTITY': {
      if (action.quantity < 1) {
        return { ...state, items: state.items.filter(i => i.id !== action.id) };
      }
      return {
        ...state,
        items: state.items.map(i =>
          i.id === action.id ? { ...i, quantity: action.quantity } : i
        ),
      };
    }

    case 'CLEAR':
      return initialState;

    case 'HYDRATE':
      return action.state ?? initialState;

    default:
      throw new Error(`Unknown cart action: ${action.type}`);
  }
}

const STORAGE_KEY = 'react-shop-cart-v1';

export function CartProvider({ children }) {
  const [state, dispatch] = useReducer(cartReducer, initialState, () => {
    // Lazy initializer — runs once. Read from localStorage on mount.
    try {
      const stored = localStorage.getItem(STORAGE_KEY);
      return stored ? JSON.parse(stored) : initialState;
    } catch {
      return initialState;
    }
  });

  // Write to localStorage whenever cart state changes
  useEffect(() => {
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
    } catch (err) {
      console.error('Failed to save cart:', err);
    }
  }, [state]);

  const totalItems = state.items.reduce((sum, i) => sum + i.quantity, 0);
  const totalPrice = state.items.reduce((sum, i) => sum + i.quantity * i.price, 0);

  const value = { state, dispatch, totalItems, totalPrice };

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
}

export function useCart() {
  const ctx = useContext(CartContext);
  if (ctx === null) {
    throw new Error('useCart must be used within a CartProvider');
  }
  return ctx;
}
