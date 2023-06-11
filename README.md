## Cthulhu Wars

This is an implementation of the Cthulhu Wars board game.

## How to compile using Visual Studio Code

1. Download javaFX SDK library from https://gluonhq.com/products/javafx/
2. Copy the project from the repository
3. Add .vscode folder to the local project folder
4. Create launch.json file in .vscode folder with the following text:
```
{
    "configurations": [
        {
            "type": "java",
            "name": "App",
            "request": "launch",
            "mainClass": "App",
            "vmArgs": "--module-path \"*your path to the following folder*/javafx-sdk-20.0.1/lib\" --add-modules javafx.controls,javafx.fxml"
        }
    ]
}
```

Be sure to put the correct path to the javafx-sdk

5. Create settings.json file in .vscode folder with the following text:
```
{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/**/*.jar",
        "*your path to the following folder*\\javafx-sdk-20.0.1\\lib\\javafx.base.jar",
        "*your path to the following folder*\\javafx-sdk-20.0.1\\lib\\javafx.controls.jar",
        "*your path to the following folder*\\javafx-sdk-20.0.1\\lib\\javafx.fxml.jar",
        "*your path to the following folder*\\javafx-sdk-20.0.1\\lib\\javafx.graphics.jar",
        "*your path to the following folder*\\javafx-sdk-20.0.1\\lib\\javafx.media.jar",
        "*your path to the following folder*\\javafx-sdk-20.0.1\\lib\\javafx.swing.jar",
        "*your path to the following folder*\\javafx-sdk-20.0.1\\lib\\javafx.web.jar",
        "*your path to the following folder*\\javafx-sdk-20.0.1\\lib\\javafx-swt.jar"
    ],
    "git.ignoreLimitWarning": true
}
```
Be sure to put the correct path to the javafx-sdk

6. Dowload the default java pack in VS code
7. Run the project in the "App" configuration
