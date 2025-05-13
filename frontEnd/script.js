document.getElementById("loginForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const formData = new FormData(this);
    const email = formData.get("email");
    const senha = formData.get("senha");

    try {
        const response = await fetch("http://localhost:8080/index.html", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({ email: email, senha: senha })
        });

        if (response.ok) {
            const data = await response.json();
            alert("Login bem-sucedido!");
            window.location.href = "home.html";
        } else {
            const error = await response.text();
            alert("Erro: " + error);
        }
    } catch (error) {
        console.error(error);
        alert("Erro na conexão com o servidor.");
    }
});