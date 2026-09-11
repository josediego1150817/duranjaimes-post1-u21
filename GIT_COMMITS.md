# Comandos Git para cumplir la rúbrica

El enunciado exige mínimo 3 commits descriptivos por parte (6 en total). Desde la raíz del repositorio `duranjaimes-post1-u2`:

## Parte 1

```bash
git add exportador-reportes/src/main/java/com/patrones/u2/GradeRecord.java \
        exportador-reportes/src/main/java/com/patrones/u2/ReportBody.java \
        exportador-reportes/src/main/java/com/patrones/u2/ReportHeaderFooter.java

git commit -m "feat(creacional): definir modelo de datos y contratos de productos"

git add exportador-reportes/src/main/java/com/patrones/u2/Pdf*.java \
        exportador-reportes/src/main/java/com/patrones/u2/Excel*.java \
        exportador-reportes/src/main/java/com/patrones/u2/Html*.java \
        exportador-reportes/src/main/java/com/patrones/u2/ReportFormatFactory.java

git commit -m "feat(abstract-factory): implementar familias PDF Excel y HTML"

git add exportador-reportes/src/main/java/com/patrones/u2/ReportFactoryRegistry.java \
        exportador-reportes/src/main/java/com/patrones/u2/ReportExportService.java \
        exportador-reportes/src/main/java/com/patrones/u2/Main.java \
        README.md

git commit -m "feat(ocp): agregar registro dinamico y documentar decisiones 1 y 2"
```

## Parte 2

```bash
git add exportador-reportes/src/main/java/com/patrones/u2/ExportConfig.java

git commit -m "feat(builder): implementar ExportConfig con validacion en build"

git add exportador-reportes/src/main/java/com/patrones/u2/ReportExportService.java \
        exportador-reportes/src/main/java/com/patrones/u2/Main.java

git commit -m "feat: integrar ExportConfig y demostrar configuraciones en Main"
git add README.md

git commit -m "docs: completar decisiones 3 y 4 y conclusiones de la unidad"
```

## Subida al remoto

```bash
git branch -M main
git remote add origin https://github.com/TU_USUARIO/duranjaimes-post1-u2.git
git push -u origin main
```

Para GitLab, reemplaza únicamente la URL del remoto por la URL HTTPS de tu proyecto GitLab.
