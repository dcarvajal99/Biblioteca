# Biblioteca DUOC UC

Este es un sistema de gestión de biblioteca desarrollado en Java. Permite gestionar libros y usuarios, realizar préstamos y guardar la información en archivos CSV.

## Estructura del proyecto

- **src/main/java/com/grupo5/biblioteca/App.java**: Clase principal con menú interactivo.
- **models/**: Contiene las clases de dominio (`Libro`, `Usuario`, `Biblioteca`).
- **services/**: Servicios para leer y escribir archivos CSV de libros y usuarios.
- **exceptions/**: Excepciones personalizadas para la lógica de negocio.
- **data/**: Archivos CSV con los datos de libros y usuarios.

## Funcionalidades principales

- Mostrar libros y usuarios.
- Buscar libros por título.
- Prestar libros a usuarios registrados.
- Registrar nuevos usuarios.
- Guardar los datos en archivos CSV.

## Ejecución

1. Compila el proyecto con Maven o tu IDE favorito.
2. Ejecuta la clase `App.java`.
3. Sigue las instrucciones del menú en consola.

## Notas

- Los archivos `libros.csv` y `usuarios.csv` se encuentran en `src/main/java/com/grupo5/biblioteca/data/`.
- Si los archivos no existen, el sistema los crea automáticamente con la cabecera correspondiente.
- No uses el carácter `|` en los campos de entrada.

## Autores

- Grupo 5 - Duoc UC

