async function addAppUser() {
  const neptun = document.getElementById("neptun").value;
  const name = document.getElementById("name").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  const role = document.getElementById("role").value;

  const result = document.getElementById("user-add-result");

  const response = await fetch("http://localhost:8080/api/v1/appuser", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      neptun,
      name,
      email,
      password,
      role
    })
  });

  if (response.ok) {
    result.textContent = "Felhasználó hozzáadva";
  } else {
    result.textContent = "Hiba történt a hozzáadás során";
  }
}
    function logout() {
  window.location.href = "/logout";

}