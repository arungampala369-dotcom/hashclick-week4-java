const API = '/api';
const token = localStorage.getItem('token');
if (!token) window.location.href = '/login.html';

const userRole = localStorage.getItem('userRole') || 'USER';
const isAdmin = userRole === 'ADMIN';

document.getElementById('welcomeUser').textContent = 'Hi, ' + (localStorage.getItem('userName') || '');

const roleBadge = document.getElementById('roleBadge');
roleBadge.textContent = isAdmin ? 'Admin' : 'User';
roleBadge.style.display = 'inline-block';
roleBadge.style.background = isAdmin ? '#1F4E79' : '#2E75B6';

document.getElementById('taskListTitle').textContent = isAdmin ? 'All Tasks (Admin View)' : 'My Tasks';

let allTasks = [];

function headers() {
  return { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token };
}

function logout() { localStorage.clear(); window.location.href = '/login.html'; }

function showNotifications(tasks) {
  const today = new Date(); today.setHours(0,0,0,0);
  const overdue = [];
  const dueSoon = [];
  tasks.forEach(t => {
    if (!t.dueDate || t.status === 'DONE') return;
    const due = new Date(t.dueDate);
    const diff = Math.ceil((due - today) / 86400000);
    if (diff < 0) overdue.push(t.title);
    else if (diff <= 2) dueSoon.push(`${t.title} (in ${diff === 0 ? 'today' : diff + 'd'})`);
  });
  const banner = document.getElementById('notifBanner');
  const msgs = [];
  if (overdue.length) msgs.push(`⚠ Overdue: ${overdue.join(', ')}`);
  if (dueSoon.length) msgs.push(`🔔 Due soon: ${dueSoon.join(', ')}`);
  if (msgs.length) { banner.innerHTML = msgs.join('&nbsp;&nbsp;|&nbsp;&nbsp;'); banner.style.display = 'block'; }
  else banner.style.display = 'none';
}

async function loadTasks() {
  try {
    const res = await fetch(`${API}/tasks`, { headers: headers() });
    if (res.status === 401 || res.status === 403) { logout(); return; }
    allTasks = await res.json();
    renderTasks(allTasks);
    updateStats(allTasks);
    showNotifications(allTasks);
  } catch (e) { console.error(e); }
}

function updateStats(tasks) {
  document.getElementById('count-total').textContent = tasks.length;
  document.getElementById('count-todo').textContent = tasks.filter(t => t.status === 'TODO').length;
  document.getElementById('count-inprogress').textContent = tasks.filter(t => t.status === 'IN_PROGRESS').length;
  document.getElementById('count-done').textContent = tasks.filter(t => t.status === 'DONE').length;
}

function filterTasks() {
  const f = document.getElementById('filter-status').value;
  const filtered = f === 'ALL' ? allTasks : allTasks.filter(t => t.status === f);
  renderTasks(filtered);
  updateStats(filtered);
}

function badge(val, map) {
  const [cls, label] = map[val] || ['', val];
  return `<span class="badge ${cls}">${label}</span>`;
}

function renderTasks(tasks) {
  const list = document.getElementById('task-list');
  if (!tasks.length) { list.innerHTML = '<div class="no-tasks">No tasks yet.</div>'; return; }
  const statusMap = { TODO: ['badge-todo','To Do'], IN_PROGRESS: ['badge-inprogress','In Progress'], DONE: ['badge-done','Done'] };
  const prioMap = { LOW: ['badge-low','Low'], MEDIUM: ['badge-medium','Medium'], HIGH: ['badge-high','High'] };
  list.innerHTML = tasks.map(t => {
    const ownerInfo = isAdmin && t.createdBy ? `<span style="font-size:.78rem;color:#888">Owner: ${t.createdBy.name}</span>` : '';
    return `
    <div class="task-card">
      <div class="task-info">
        <h3>${t.title}</h3>
        <p>${t.description || ''}</p>
        <div class="task-meta">${badge(t.status, statusMap)} ${badge(t.priority, prioMap)} ${t.dueDate ? `<span style="font-size:.8rem;color:#888">Due: ${t.dueDate}</span>` : ''} ${ownerInfo}</div>
      </div>
      <div class="task-actions">
        <button class="btn-edit" onclick="openEdit(${t.id})">Edit</button>
        <button class="btn-delete" onclick="deleteTask(${t.id})">Delete</button>
      </div>
    </div>`;
  }).join('');
}

document.getElementById('taskForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const errEl = document.getElementById('task-error');
  errEl.style.display = 'none';
  const title = document.getElementById('task-title').value.trim();
  if (title.length < 3) { errEl.textContent = 'Title must be at least 3 characters.'; errEl.style.display = 'block'; return; }
  const task = {
    title,
    description: document.getElementById('task-desc').value.trim(),
    priority: document.getElementById('task-priority').value,
    dueDate: document.getElementById('task-due').value || null
  };
  try {
    const res = await fetch(`${API}/tasks`, { method: 'POST', headers: headers(), body: JSON.stringify(task) });
    const data = await res.json();
    if (!res.ok) throw new Error(data.message || 'Failed to create task');
    document.getElementById('taskForm').reset();
    loadTasks();
  } catch (err) { errEl.textContent = err.message; errEl.style.display = 'block'; }
});

async function deleteTask(id) {
  if (!confirm('Delete this task?')) return;
  await fetch(`${API}/tasks/${id}`, { method: 'DELETE', headers: headers() });
  loadTasks();
}

function openEdit(id) {
  const t = allTasks.find(t => t.id === id);
  if (!t) return;
  document.getElementById('edit-id').value = t.id;
  document.getElementById('edit-title').value = t.title;
  document.getElementById('edit-desc').value = t.description || '';
  document.getElementById('edit-status').value = t.status;
  document.getElementById('edit-priority').value = t.priority;
  document.getElementById('edit-due').value = t.dueDate || '';
  document.getElementById('editModal').style.display = 'flex';
}

function closeModal() { document.getElementById('editModal').style.display = 'none'; }

async function saveEdit() {
  const id = document.getElementById('edit-id').value;
  const title = document.getElementById('edit-title').value.trim();
  if (title.length < 3) { alert('Title must be at least 3 characters.'); return; }
  const task = {
    title,
    description: document.getElementById('edit-desc').value.trim(),
    status: document.getElementById('edit-status').value,
    priority: document.getElementById('edit-priority').value,
    dueDate: document.getElementById('edit-due').value || null
  };
  await fetch(`${API}/tasks/${id}`, { method: 'PUT', headers: headers(), body: JSON.stringify(task) });
  closeModal(); loadTasks();
}

document.getElementById('editModal').addEventListener('click', (e) => {
  if (e.target === document.getElementById('editModal')) closeModal();
});

loadTasks();
