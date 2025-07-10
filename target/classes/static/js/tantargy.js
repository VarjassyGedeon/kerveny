  async function fetchTantargyak() {
    const response = await fetch('http://localhost:8080/api/v1/tantargy');
    const tantargyak = await response.json();
    const list = document.getElementById('tantargy-list');
    list.innerHTML = '';
    tantargyak.forEach(t => {
      const item = document.createElement('li');
      item.textContent = `Kód: ${t.kod}, Név: ${t.nev}, Kredit: ${t.kredit}`;
      list.appendChild(item);
    });
  }

  async function addTantargy() {
    const kod = document.getElementById("kod").value;
    const nev = document.getElementById("nev").value;
    const kredit = document.getElementById("kredit").value;

    const response = await fetch("http://localhost:8080/api/v1/tantargy", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        kod: kod,
        nev: nev,
        kredit: parseInt(kredit)
      })
    });
      const result = document.getElementById("add-result");
      if (response.ok) {
        result.textContent = "Tantárgy sikeresen hozzáadva!";
      } else {
        result.textContent = "Hiba történt a hozzáadás során.";
      }
    }
