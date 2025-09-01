# Printscript

Git para desarrollar el proyecto de PrintScript de la Universidad Austral - Rawson, Ritondale y Decoud

# Verificación
Para ejecutar todas las verificaciones del proyecto, utilice:

```
bash
./gradlew check
```

# Overview y analisis del código

```
https://deepwiki.com/IngSisTeam3-2025/printscript/
4.1-lexical-analysis
```

# Git Hooks

Para habilitar los ganchos después de clonar el repositorio, ejecutar:

```
git config core.hooksPath .githooks
```

Esto configura Git para usar los scripts del directorio `.git/hooks`, donde `pre-commit` ejecuta `./gradlew spotlessApply` y `./gradlew test`, y `pre-push` ejecuta `./gradlew test`.


