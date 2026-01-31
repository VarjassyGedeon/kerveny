let editModal = null;

document.addEventListener("DOMContentLoaded", () => {
  editModal = new bootstrap.Modal(document.getElementById("editModal"));
});

function openEditModal(kod, nev, kredit, desc) {
  document.getElementById("editKod").value = kod;
  document.getElementById("editNev").value = nev;
  document.getElementById("editKredit").value = kredit;
  document.getElementById("editDesc").value = desc || "";
  editModal.show();
}

async function saveEdit() {
  const kod = document.getElementById("editKod").value;
  const nev = document.getElementById("editNev").value;
  const kredit = document.getElementById("editKredit").value;
  const description = document.getElementById("editDesc").value;

await fetch(`http://localhost:8080/api/v1/tantargy/${kod}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
        nev,
        kredit: parseInt(kredit),
        description
    })
});

  editModal.hide();
  fetchTantargyak();
}

async function deleteTantargy(kod) {
  await fetch(`http://localhost:8080/api/v1/tantargy/${kod}`, {
    method: "DELETE"
  });

  fetchTantargyak();
}


async function fetchTantargyak() {
  const response = await fetch('http://localhost:8080/api/v1/tantargy');
  const tantargyak = await response.json();

  const list = document.getElementById('tantargy-list');
  list.innerHTML = '';

  tantargyak.forEach(t => {
    const li = document.createElement('li');
    li.className = "list-group-item d-flex justify-content-between align-items-center";

    li.innerHTML = `
      <div>
        <strong>${t.kod}</strong> – ${t.nev} (${t.kredit} kredit)
      </div>

      <div>
        <button class="btn btn-sm btn-warning me-2"
            onclick="openEditModal('${t.kod}', '${t.nev}', ${t.kredit}, \`${t.description || ''}\`)">
          Szerkesztés
        </button>

        <button class="btn btn-sm btn-danger"
            onclick="deleteTantargy('${t.kod}')">
          Törlés
        </button>
      </div>
    `;

    list.appendChild(li);
  });
}

async function addTantargy() {
  const kod = document.getElementById("kod").value;
  const nev = document.getElementById("nev").value;
  const kredit = document.getElementById("kredit").value;
  const description = "";

  const response = await fetch("http://localhost:8080/api/v1/tantargy", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      kod: kod,
      nev: nev,
      kredit: parseInt(kredit),
      description: description
    })
  });

  const result = document.getElementById("add-result");

  if (response.ok) {
    result.textContent = "Tantárgy hozzáadva!";
    fetchTantargyak();
  } else {
    result.textContent = "Hiba történt a hozzáadás során";
  }
}
