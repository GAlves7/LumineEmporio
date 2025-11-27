// include.js

// Função responsável por carregar componentes HTML externos dentro da página.
// Isso permite reutilizar partes do site (como header, footer ou menus) sem duplicar código.
export async function loadComponent(targetId, filePath, scriptPath = null) {

    // Seleciona o elemento onde o conteúdo será inserido
    const target = document.getElementById(targetId);

    // Faz a requisição para buscar o arquivo HTML desejado
    const response = await fetch(filePath);

    // Converte o conteúdo do arquivo para texto (HTML bruto)
    const html = await response.text();

    // Insere o HTML dentro do elemento alvo
    target.innerHTML = html;

    // Se o componente tiver um JavaScript próprio, ele será carregado depois do HTML
    if (scriptPath) {
        const script = document.createElement("script");

        // Define o tipo como módulo para permitir import/export dentro do script
        script.type = "module";

        // Caminho do arquivo JavaScript associado ao componente
        script.src = scriptPath;

        // Adiciona o script ao final do body para garantir que o HTML já foi carregado
        document.body.appendChild(script);
    }
}
