window.onload = function() {
  window.ui = SwaggerUIBundle({
    url: "http://localhost:8080/laboratorul8-1.0-SNAPSHOT/api/openapi/swagger.json",
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout"
  });
};
