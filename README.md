# Printscript

Git para desarrollar el proyecto de PrintScript de la Universidad Austral - Rawson, Ritondale y Decoud

# Verificación
Para ejecutar todas las verificaciones del proyecto, utilice:

```
bash
./gradlew check
```

# Git Hooks

Para habilitar los ganchos después de clonar el repositorio, ejecutar:

```
git config core.hooksPath .githooks
```

Esto configura Git para usar los scripts del directorio `.githooks`, donde `pre-commit` ejecuta `./gradlew spotlessApply` y `./gradlew test`, y `pre-push` ejecuta `./gradlew test`.