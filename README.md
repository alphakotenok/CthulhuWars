## Cthulhu Wars

This is an implementation of the Cthulhu Wars board game.

## How to compile using Visual Studio Code

1. Download javaFX library
2. Copy the project from the repository
3. Add .vscode folder to the local project folder
4. Create launch.json file in .vscode folder with the following text:
```
{
    "version": "0.1.0",
    "configurations": [
        {
            "name": "Java",
            "type": "java",
            "request": "launch",
            "stopOnEntry": true,
            "jdkPath": "${env:JAVA_HOME}/bin",
            "cwd": "${fileDirname}",
            "startupClass": "${fileBasenameNoExtension}",
            "classpath": [
                ".",
                "${fileDirname}"
            ]
        },
        {
            "name": "Java Console App",
            "type": "java",
            "request": "launch",
            "stopOnEntry": true,
            "jdkPath": "${env:JAVA_HOME}/bin",
            "cwd": "${fileDirname}",
            "startupClass": "${fileBasenameNoExtension}",
            "classpath": [
                ".",
                "${fileDirname}"
            ],
            "externalConsole": true
        },
        {
            "type": "java",
            "name": "Current File",
            "request": "launch",
            "mainClass": "${file}"
        },
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
7. Run the project
