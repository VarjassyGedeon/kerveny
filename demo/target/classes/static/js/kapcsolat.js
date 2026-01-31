async function fetchKapcsolatok() {
  try {
    const response = await fetch('http://localhost:8080/api/v1/kapcsolat');
    if (!response.ok) throw new Error(`HTTP hiba: ${response.status}`);

    const kapcsolatok = await response.json();
    const list = document.getElementById('kapcsolat-list');
    list.innerHTML = '';

    if (kapcsolatok.length === 0) {
      list.innerHTML = '<li>Nincs kapcsolat.</li>';
      return;
    }

    kapcsolatok.forEach(kapcsolat => {
      const li = document.createElement('li');
      li.textContent = `${kapcsolat.targy1Id} ↔ ${kapcsolat.targy2Id}`;
      list.appendChild(li);
    });
  } catch (error) {
    document.getElementById('kapcsolat-list').innerHTML = `<li>Nem sikerült betölteni a kapcsolatokat: ${error.message}</li>`;
  }
}

  async function addKapcsolat() {
    const targy1 = document.getElementById('kapcsolatTargy1').value.trim();
    const targy2 = document.getElementById('kapcsolatTargy2').value.trim();
    const resultElem = document.getElementById('kapcsolat-add-result');

    if (!targy1 || !targy2) {
      resultElem.textContent = 'Mindkét tantárgy kód megadása kötelező';
      return;
    }

    const payload = {
      targy1Id: targy1,
      targy2Id: targy2
    };

    const response = await fetch('http://localhost:8080/api/v1/kapcsolat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (response.ok) {
      resultElem.textContent = 'Kapcsolat sikeresen hozzáadva!';
      fetchKapcsolatok();
    } else {
      const text = await response.text();
      resultElem.textContent = 'Hiba' + text;
    }
  }

    // Auto-load
    window.addEventListener('load', () => {
      fetchKapcsolatok();
    });
