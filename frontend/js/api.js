// api.js

// Cria uma instância do Axios com a URL base do backend
const api = axios.create({
    baseURL: "http://localhost:8080", // 🟡 Altere para o endereço do seu backend Spring Boot
});

// Exporta para ser usada em outros scripts
export default api;
