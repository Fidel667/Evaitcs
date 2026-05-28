# React Shop — Pure React + DummyJSON

A product catalog with shopping cart and JWT auth, built with **only** what ships with React: hooks, Context, and the browser's `fetch` + `localStorage`. No axios, Redux, React Query, React Router, or UI libraries.

## Stack

- React 18 (scaffolded with Vite)
- Plain CSS (single `App.css`)
- Browser `fetch` API
- Browser `localStorage`
- [DummyJSON](https://dummyjson.com) as the backend

## Features

- Product grid with search (400ms debounced) and pagination
- Product detail view
- Shopping cart with quantity controls, persisted to `localStorage`
- User login via DummyJSON `/auth/login`, persisted across refresh
- Hand-rolled view switching (no router)

## Run

```bash
npm install
npm run dev
```

Open http://localhost:5173.

## Login

Use any DummyJSON user, e.g. `emilys` / `emilyspass`.

## Project Structure

```
src/
├── main.jsx                Vite entry
├── App.jsx                 root + view switching
├── App.css                 single stylesheet
├── components/
│   ├── ProductList.jsx     list + search + pagination (uses useFetch, useDebouncedValue)
│   ├── ProductCard.jsx
│   ├── ProductDetail.jsx   detail view + add-to-cart (uses useFetch)
│   ├── SearchBar.jsx
│   ├── Pagination.jsx
│   ├── Cart.jsx
│   ├── CartIcon.jsx
│   ├── LoginForm.jsx
│   └── Header.jsx
├── context/
│   ├── CartContext.jsx     useReducer + localStorage persistence
│   └── AuthContext.jsx     useState + localStorage persistence
└── hooks/
    ├── useFetch.jsx        fetch with loading/error/cleanup
    ├── useLocalStorage.jsx generic persisted state
    └── useDebouncedValue.jsx
```

## Notes

- The cart and auth tokens live in `localStorage` (keys `react-shop-cart-v1`, `react-shop-auth-v1`). For production, JWTs belong in an `HttpOnly` cookie set by the server — see the tutorial's "Senior insight."
- DummyJSON simulates a real API but does not persist writes.
