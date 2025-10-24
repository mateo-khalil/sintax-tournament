# CUP y JFLEX - Guía de Instalación y Uso

## Descargas

- **JFLEX**: https://www.jflex.de/download.html
- **CUP**: http://www2.cs.tum.edu/projects/cup/

## Instalación

### Windows

1. Descargar JFlex y extraer en una carpeta (ej: `C:\jflex-1.9.1`)
2. Modificar `bin/jflex.bat` y configurar `JFLEX_HOME`:
    ```batch
    set JFLEX_HOME=C:\jflex-1.9.1
    ```
3. Agregar al PATH: `C:\jflex-1.9.1\bin`
4. Descargar `java-cup-11b.jar` y colocarlo en la carpeta del proyecto

### macOS/Linux

1. Descargar JFlex y extraer en una carpeta (ej: `~/jflex-1.9.1`)
2. Editar `~/.bash_profile` o `~/.zshrc` y agregar:
    ```bash
    export JFLEX_HOME=~/jflex-1.9.1
    export PATH=$PATH:$JFLEX_HOME/bin
    ```
3. Ejecutar: `source ~/.bash_profile` (o `~/.zshrc`)
4. Descargar `java-cup-11b.jar` y colocarlo en la carpeta del proyecto

## Uso - Comandos

### Generar el scanner (analizador léxico)
```bash
jflex scanner.jflex
```

### Generar el parser (analizador sintáctico)
```bash
java -jar java-cup-11b.jar parser.cup
```

### Compilar el proyecto
```bash
javac -cp java-cup-11b-runtime.jar *.java
```

### Ejecutar (ejemplo)
```bash
java -cp .:java-cup-11b-runtime.jar Main
```
**Nota en Windows**: usar `;` en lugar de `:` en el classpath
```bash
java -cp .;java-cup-11b-runtime.jar Main
```