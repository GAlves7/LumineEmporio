export async function loadComponent(targetId, filePath, scriptPath = null) {
    const target = document.getElementById(targetId);

    const response = await fetch(filePath);
    const html = await response.text();
    target.innerHTML = html;

    // Se tiver script associado, carrega depois
    if (scriptPath) {
        const script = document.createElement("script");
        script.type = "module";
        script.src = scriptPath;
        document.body.appendChild(script);
    }
}
