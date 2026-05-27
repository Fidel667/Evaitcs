import { useState } from 'react';
import { useFetch } from '../hooks/useFetch';
import { useDebouncedValue } from '../hooks/useDebouncedValue';
import ProductCard from './ProductCard';
import SearchBar from './SearchBar';
import Pagination from './Pagination';

const PAGE_SIZE = 12;

export default function ProductList({ onProductClick }) {
  const [searchQuery, setSearchQuery] = useState('');
  const [page, setPage]               = useState(0);

  const debouncedQuery = useDebouncedValue(searchQuery, 400);

  const skip = page * PAGE_SIZE;
  const url = debouncedQuery.trim()
    ? `https://dummyjson.com/products/search?q=${encodeURIComponent(debouncedQuery)}&limit=${PAGE_SIZE}&skip=${skip}`
    : `https://dummyjson.com/products?limit=${PAGE_SIZE}&skip=${skip}`;

  const { data, loading, error } = useFetch(url);

  const products   = data?.products ?? [];
  const total      = data?.total ?? 0;
  const totalPages = Math.ceil(total / PAGE_SIZE);

  function handleSearchChange(value) {
    setSearchQuery(value);
    setPage(0);
  }

  return (
    <>
      <SearchBar value={searchQuery} onChange={handleSearchChange} />

      {error && <p className="error">Error: {error}</p>}
      {loading && <p>Loading...</p>}

      {!loading && !error && (
        <>
          <div className="product-grid">
            {products.map(p => (
              <ProductCard key={p.id} product={p} onClick={onProductClick} />
            ))}
          </div>

          {products.length === 0 && <p>No products found.</p>}

          <Pagination
            page={page}
            totalPages={totalPages}
            onPrev={() => setPage(p => p - 1)}
            onNext={() => setPage(p => p + 1)}
          />
        </>
      )}
    </>
  );
}
