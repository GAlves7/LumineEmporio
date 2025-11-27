// api.js

// Cria uma instância personalizada do Axios.
// Isso serve para facilitar chamadas ao backend, sem precisar
// repetir o endereço base em toda requisição.
const api = axios.create({
    // Endereço base da API do backend (Nuvem)
    baseURL: "https://lumineemporioonline-production.up.railway.app",
});

// Exporta essa instância para que outros arquivos JavaScript possam utilizá-la.
// Assim você pode chamar, por exemplo: api.get("/catalogo")
export default api;
