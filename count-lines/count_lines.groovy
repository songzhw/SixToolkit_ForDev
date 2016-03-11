import groovy.io.FileType

def blackDir = ['build', 'gradle','result']

def whiteFile = ['java','kt','xml','groovy','rb','py','bat','sh','md','gradle']


def root = new File('.')
def dirs = []
def files = []

// 1. remove the dir in black list, and get the left directories
root.listFiles().each{ File file -> 
	if(file.isDirectory()){
		if(blackDir.every{ black -> file.name != black}){
			dirs.add(file)
		}
	}
}


// 2. starts to record files in the root directory
root.listFiles().each{
	if(!it.isDirectory()){
		files.add(it)
	}
}

// 3. starts to record files in the while list files
dirs.each{ File aDir ->
	aDir.eachFileRecurse(FileType.FILES) { File file ->
		files.add(file)
	}
}


int lines = 0
files.each{println it.name}