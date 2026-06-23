const API = '/api';

if (localStorage.getItem('token') && window.location.pathname.includes('login')) {
  window.location.href = '/dashboard.html';
}

function showErr(msg) {
  const el = document.getElementById('err');
  el.textContent = msg; el.style.display = 'block';
}

const loginForm = document.getElementById('loginForm');
if (loginForm) {
  loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(`${API}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: document.getElementById('email').value, password: document.getElementById('password').value })
      });
      const data = await res.json();
      if (!res.ok) throw new Error(data.message || 'Login failed');
      localStorage.setItem('token', data.token);
      localStorage.setItem('userName', data.name);
      localStorage.setItem('userRole', data.role || 'USER');
      window.location.href = '/dashboard.html';
    } catch (err) { showErr(err.message); }
  });
}

const registerForm = document.getElementById('registerForm');
if (registerForm) {
  registerForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(`${API}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: document.getElementById('name').value, email: document.getElementById('email').value, password: document.getElementById('password').value })
      });
      const data = await res.json();
      if (!res.ok) throw new Error(data.message || 'Registration failed');
      localStorage.setItem('token', data.token);
      localStorage.setItem('userName', data.name);
      localStorage.setItem('userRole', data.role || 'USER');
      window.location.href = '/dashboard.html';
    } catch (err) { showErr(err.message); }
  });
}
