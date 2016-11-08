// ===================== 1. set basic argumetns =====================
projectPath = 'E:/workspace/mine/SixTools'


// ===================== 2. main entrance =====================
def commandLint = "gradlew :app:lintDebug > a.txt" //TODO mac is different
// commandLint.execute(null, new File(projectPath))

def process = "cmd /c dir".execute(null, new File(projectPath))
println "szw ${process.text}"

// ===================== 3. run lint =====================



// ===================== 2. delete unused res =====================



