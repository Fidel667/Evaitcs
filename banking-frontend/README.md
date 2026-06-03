# Banking Front-End — JD Bank

A production-stack React front-end for the Banking & Transfer Spring Boot API. Login, view accounts, open accounts, deposit / withdraw / transfer (with idempotency keys on v2 endpoints), and view transaction history.

## Stack

- React 18 + Vite
- Material UI (MUI) v6 — `Grid size={{}}` API, `slotProps` pattern
- TanStack Query v5 (`@tanstack/react-query`) for server state
- React Router v7 for navigation
- Axios for HTTP (with request/response interceptors)
- Formik + Yup for forms and validation
- Plain Context for auth state

> Note on versions: the source tutorial referenced MUI "v9 (April 2026)". This project uses the latest *publicly installable* MUI (v6), which already provides the exact APIs the tutorial teaches — the `Grid size={{ xs, sm, md }}` prop (no more `item`) and the standardized slot pattern. Everything else matches the tutorial's named versions.

## Prerequisites

The Spring Boot Banking API must be running on `http://localhost:8080` with:
- The `/api/v1` (reads) and `/api/v2` (writes, idempotency keys) endpoint split
- CORS configured to allow `http://localhost:5173` and expose the `Deprecation`, `Sunset`, `Link` headers (see the tutorial's CORS section)

## Run

```bash
npm install
npm run dev
```

Open http://localhost:5173. You'll be redirected to `/login`. Log in with a user you registered against the API.

## Architecture

- `api/` — all HTTP lives here. `client.js` is the axios instance with interceptors (JWT attach, 401 handling, v1 deprecation logging); `endpoints.js` has one function per backend operation. Components never call axios directly.
- `auth/` — the only manually-managed client state. `AuthContext` persists the token/user to localStorage and listens for the `jdbank.auth.expired` event the 401 interceptor fires.
- `routes/` — page-level components mapped to URLs, plus `ProtectedRoute` (a layout route that bounces logged-out users to `/login` and remembers where they were headed).
- `components/` — reusable parts: header, account card, transaction table, and the three money dialogs.

## The idempotency pattern

Each money dialog generates one `Idempotency-Key` (`crypto.randomUUID()`) when it **opens**, not on submit. If a request times out and the user retries, the same key goes out — so the server treats it as a retry and the money moves once. Reopening the dialog generates a fresh key. This is the Stripe/Square pattern.

## Reads vs writes

- Reads (`listAccounts`, `getAccount`, `getTransactionHistory`) hit `/api/v1`.
- Writes (`openAccount`, `deposit`, `withdraw`, `transfer`) hit `/api/v2` and carry an `Idempotency-Key` header.
