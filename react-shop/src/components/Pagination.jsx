export default function Pagination({ page, totalPages, onPrevious, onNext }) {
  if (totalPages <= 1) return null;

  return (
    <div className="pagination">
      <button onClick={onPrevious} disabled={page === 0}>Previous</button>
      <span>Page {page + 1} of {totalPages}</span>
      <button onClick={onNext} disabled={page >= totalPages - 1}>Next</button>
    </div>
  );
}
