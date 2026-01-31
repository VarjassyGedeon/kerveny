async function loadSzakok() {
  const select = document.getElementById("szakSelect");
  const response = await fetch("http://localhost:8080/api/v1/szak");
  const szakok = await response.json();

  select.innerHTML = "";
  szakok.forEach(szak => {
    const option = document.createElement("option");
    option.value = szak.id;
    option.textContent = szak.nev;
    select.appendChild(option);
  });
}

// --- Upload and extract codes filtered by szak ---
async function uploadPdf() {
  const fileInput = document.getElementById("pdfFile");
  const szakId = document.getElementById("szakSelect").value;
  const resultDiv = document.getElementById("pdf-result");
  const tableBody = document.querySelector("#pdf-table tbody");

if (!fileInput.files.length || !szakId) {
    resultDiv.textContent = "Kérlek válassz egy szakot és egy PDF fájlt!";
    return;
  }

  const formData = new FormData();
  formData.append("file", fileInput.files[0]);

  resultDiv.textContent = "Feldolgozás folyamatban...";
  tableBody.innerHTML = "";

  try {
    const response = await fetch(`http://localhost:8080/api/v1/pdf/extract-links/${szakId}`, {
      method: "POST",
      body: formData
    });

    if (!response.ok) throw new Error(`Hiba: ${response.status}`);

    const rows = await response.json();

    let html = "";
    rows.forEach(row => {
      const linked = row.linkedPdfCodes.length > 0
        ? row.linkedPdfCodes.join(", ")
        : "Nincs kapcsolat";
      html += `<tr><td>${row.tantargyKod}</td><td>${linked}</td></tr>`;
    });

    tableBody.innerHTML = html;
    resultDiv.textContent = "Eredmények betöltve!";
  } catch (err) {
    resultDiv.textContent = "Hiba történt: " + err.message;
  }
}

// --- Load szakok when page is ready ---
document.addEventListener("DOMContentLoaded", loadSzakok);