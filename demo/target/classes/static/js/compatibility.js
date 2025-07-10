async function checkKapcsolatok() {
  const results = [];
  const resultList = document.getElementById("result-list");
  resultList.innerHTML = "";

  for (let i = 0; i < 9; i++) {
    const kod1 = document.getElementById(`kod1-${i}`).value.trim();
    const kod2 = document.getElementById(`kod2-${i}`).value.trim();

    if (!kod1 || !kod2) continue; // skip empty pairs

    try {
      const response = await fetch(`http://localhost:8080/api/v1/kapcsolat/check?kod1=${kod1}&kod2=${kod2}`);
      const isCompatible = await response.json();

      const li = document.createElement("li");
      li.textContent = `${kod1} - ${kod2}: ${isCompatible ? "Kompatibilis" : "Nem kompatibilis"}`;
      resultList.appendChild(li);
    } catch (error) {
      const li = document.createElement("li");
      li.textContent = `${kod1} - ${kod2}: Hiba történt az ellenőrzés során.`;
      resultList.appendChild(li);
    }
  }

  if (resultList.innerHTML === "") {
    resultList.innerHTML = "<li>Nincs megadott kód pár.</li>";
  }
}


async function findBestMatch() {
  const pairs = [];

  for (let i = 0; i < 9; i++) {
    const kod1 = document.getElementById(`kod1-${i}`).value.trim();
    const kod2 = document.getElementById(`kod2-${i}`).value.trim();

    if (kod1 && kod2) {
      pairs.push({ kod1, kod2 });
    }
  }

  const resultList = document.getElementById('result-list');
  resultList.innerHTML = "";

  if (pairs.length === 0) {
    resultList.innerHTML = "<li>Nincs megadott kód pár.</li>";
    return;
  }

try {
  const response = await fetch('http://localhost:8080/api/v1/kerveny/match', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(pairs)
  });

  const text = await response.text();
  console.log("Raw response text:", text);

  if (!response.ok) {
    resultList.innerHTML = `<li>Hiba történt a szerveren: ${response.status} - ${text}</li>`;
    return;
  }

  const result = JSON.parse(text);

  if (result && result.id && result.neptunkod) {
    resultList.innerHTML = `<li>Legjobb egyezés kérvény ID: ${result.id}, Neptunkód: ${result.neptunkod}</li>`;
  } else {
    resultList.innerHTML = "<li>Nincs megfelelő egyezés.</li>";
  }
} catch (error) {
  console.error("Fetch hiba:", error);
  resultList.innerHTML = "<li>Hiba történt a kérés feldolgozása során.</li>";
}
}
