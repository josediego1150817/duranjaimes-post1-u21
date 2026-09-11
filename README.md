# Post-contenido — Unidad 2: Patrones Creacionales

## Descripción
Repositorio del post-contenido de la Unidad 2 de Patrones de Diseño de Software — Sexto Semestre. Un único proyecto Maven (`exportador-reportes/`) resuelve la exportación de reportes académicos en PDF, Excel y HTML (Parte 1), y se extiende con configuración compleja mediante Builder y una evaluación objetiva de Singleton para el registro de fábricas (Parte 2).

**Estudiante:** José Diego Duran Jaimes  
**Código:** 1150817

## Cómo ejecutar

```bash
cd exportador-reportes
mvn compile
mvn exec:java -Dexec.mainClass="com.patrones.u2.Main"
```

## Decisiones de diseño

### Decisión 1 — Factory Method vs. Abstract Factory (Parte 1)

**Patrón elegido: Abstract Factory.**

La elección se fundamenta en las tres preguntas diagnósticas del enunciado. Primero, el sistema no crea un único producto aislado: crea una familia de dos productos relacionados, `ReportBody` y `ReportHeaderFooter`, que deben pertenecer al mismo formato. Un reporte PDF debe utilizar `PdfReportBody` junto con `PdfHeaderFooter`; mezclar uno de PDF con uno de Excel produciría una salida inconsistente.

Segundo, cuando llegue el formato CSV no basta con agregar una sola implementación: será necesario incorporar la pareja de productos correspondiente, por ejemplo `CsvReportBody` y `CsvHeaderFooter`, junto con su fábrica concreta. Por tanto, el problema es de familias de productos coherentes y no solamente de una clase que cambia de implementación.

Tercero, el riesgo principal es precisamente mezclar piezas de familias distintas. `ReportFormatFactory` encapsula la creación de ambas piezas y hace que el cliente obtenga los dos productos desde la misma fábrica concreta. Factory Method fue descartado porque modelaría de forma más natural la creación de un único producto que varía por formato. En este caso, obligaría a resolver por separado la creación del cuerpo y del encabezado/pie, dejando más espacio para combinaciones inválidas.

### Decisión 2 — Mecanismo de extensibilidad de formatos (Parte 1)

**Opción elegida: registro dinámico con `Map<String, Supplier<ReportFormatFactory>>`.**

Se descartó un `switch` o una cadena de `if/else` porque cada nuevo formato exigiría modificar el método que resuelve la fábrica. El registro concentra las asociaciones entre identificadores y proveedores y permite incorporar un nuevo formato mediante `ReportFactoryRegistry.register("csv", CsvReportFactory::new)` sin modificar la lógica de resolución. Esto favorece OCP porque las extensiones se incorporan registrando una nueva implementación.

El comportamiento ante un formato inexistente también está centralizado: `resolve()` lanza `IllegalArgumentException` con un mensaje descriptivo. La clase no se convierte en un Singleton clásico; se mantiene como una utilidad estática intencional, decisión que se evalúa explícitamente en la Parte 2.

### Decisión 3 — Builder vs. constructor telescópico vs. setters (Parte 2)

**Patrón elegido: Builder.**

`ExportConfig` tiene un parámetro obligatorio (`format`) y ocho opcionales. El constructor directo con nueve argumentos obliga al cliente a recordar un orden exacto y aumenta el riesgo de intercambiar valores del mismo tipo. Los constructores sobrecargados también se descartan porque la cantidad de combinaciones crece rápidamente y produciría una clase con numerosos constructores casi duplicados.

La alternativa de setters sueltos se descarta porque permitiría que la configuración permanezca parcialmente construida y dispersaría las reglas de validación. Con Builder, los valores opcionales se pueden proporcionar con métodos encadenables, mientras `build()` representa un único punto de validación. En este proyecto, `build()` rechaza `compress=true` sin `outputPath` y valores no válidos de `maxRowsPerPage`, garantizando que la instancia final nazca consistente e inmutable.

### Decisión 4 — ¿ReportFactoryRegistry necesita ser Singleton? (Parte 2)

**Conclusión: NO.**

La primera razón es la identidad de objeto: en el proyecto no existe una necesidad de pasar `ReportFactoryRegistry` como objeto, inyectarlo por constructor ni sustituirlo mediante polimorfismo. Se consume mediante métodos estáticos. La segunda razón es la inicialización: el registro solo crea un `HashMap` y carga tres entradas; no existen conexiones, archivos ni cálculos costosos que justifiquen una instancia singleton con inicialización perezosa.

La tercera razón es que el propio `Map` estático ya proporciona una única fuente de verdad dentro de la JVM. Añadir un Singleton clásico con `getInstance()` no aportaría una garantía funcional nueva, solo agregaría ceremonia y una capa de indirección. Finalmente, si el sistema evolucionara a un escenario multi-tenant en el que cada institución necesitara un registro independiente, la unicidad sería una restricción incorrecta. Por estas razones se conserva la clase final con constructor privado y miembros estáticos, sin aplicar Singleton clásico.

## Estructura

```text
exportador-reportes/
├── pom.xml
└── src/main/java/com/patrones/u2/
    ├── GradeRecord.java
    ├── ReportBody.java
    ├── ReportHeaderFooter.java
    ├── PdfReportBody.java
    ├── PdfHeaderFooter.java
    ├── ExcelReportBody.java
    ├── ExcelHeaderFooter.java
    ├── HtmlReportBody.java
    ├── HtmlHeaderFooter.java
    ├── ReportFormatFactory.java
    ├── PdfReportFactory.java
    ├── ExcelReportFactory.java
    ├── HtmlReportFactory.java
    ├── ReportFactoryRegistry.java
    ├── ReportExportService.java
    ├── ExportConfig.java
    └── Main.java
```

## Herramientas utilizadas

- Java 17
- Apache Maven
- VS Code
- Git
- GitHub/GitLab

## Conclusiones

La actividad demuestra que seleccionar un patrón requiere analizar primero la estructura del problema y no solo reconocer palabras clave. Abstract Factory es adecuado cuando varias piezas relacionadas deben mantenerse dentro de la misma familia, mientras que Builder reduce la complejidad de configurar objetos con muchos parámetros opcionales y permite concentrar las validaciones en `build()`. La evaluación de Singleton muestra que una clase utilitaria con un estado estático compartido no necesita convertirse automáticamente en un Singleton clásico. El resultado combina extensibilidad, consistencia entre productos y configuraciones válidas sin incorporar complejidad que no resuelva una necesidad real.
