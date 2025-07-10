 // Tab switching logic
  function showTab(tabId) {
    document.querySelectorAll('.tab').forEach(tab => {
      tab.classList.remove('active');
    });
    document.getElementById(tabId).classList.add('active');
  }

  // Auto-load the list on page load
  window.onload = fetchTantargyak;