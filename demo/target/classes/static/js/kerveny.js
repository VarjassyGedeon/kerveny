    async function fetchKerveny() {
        const response = await fetch('http://localhost:8080/api/v1/kerveny');
        const kerveny = await response.json();

        const list = document.getElementById('kerveny-list');
        list.innerHTML = '';

        if (kerveny.length === 0) {
            list.innerHTML = '<li>Nincs kérvény.</li>';
            return;
        }

        kerveny.forEach(k => {
            const item = document.createElement('li');
            item.textContent = `ID: ${k.id}, Neptun: ${k.neptunkod}, Elfogadva: ${k.elfogadva}, Döntés ideje: ${k.dontesIdopont ? new Date(k.dontesIdopont).toLocaleString() : "Nincs"}`;
            list.appendChild(item);
        });
    }