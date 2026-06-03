import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: { 'Content-Type': 'application/json' },
});

// Attach the JWT to every request, if we have one.
api.interceptors.request.use(config => {
  const token = localStorage.getItem('jdbank.token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Centralized response handling — log v1 deprecation, surface 401 for the UI.
api.interceptors.response.use(
  response => {
    if (response.headers.deprecation === 'true') {
      console.warn(
        `Deprecated endpoint: ${response.config.url} — successor: ${response.headers.link || 'unknown'}`
      );
    }
    return response;
  },
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('jdbank.token');
      window.dispatchEvent(new Event('jdbank.auth.expired'));
    }
    return Promise.reject(error);
  }
);
