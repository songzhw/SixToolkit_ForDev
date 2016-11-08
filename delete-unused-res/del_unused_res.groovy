// ===================== 1. set basic argumetns =====================
projectPath = 'E:/workspace/mine/SixTools'


// ===================== 2. main entrance =====================
def commandLint = "cmd /c gradlew.bat :app:lintDebug > a.txt" //TODO mac is different
commandLint.execute(null, new File(projectPath))


// ===================== 3. run lint =====================



// ===================== 2. delete unused res =====================



