//auto_findViewByid


//规则1： view_id最末是"_x"的，就不会声明为成员。这主要是rlay中仅是用于表示位置就要声明id的view太多了
//规则1： view_id最末是"_c"的，会有findViewById(), 才会有setOnclickListener()。默认是只有findview， 没有OnClick的
//规则3： tv_pintu_name取的成员名是: tvName， 规则是后面首字母大写，第二个_后的省略不计入命名。
//规则4： android:id要是View的第一个属性。 中间可以用各种空白隔开。

//隐藏属性： 会自动跳过TitleBar不解析。 因为auto_activity脚本里已经有它了


def xmlSource = new File('layout.xml')
def xml = xmlSource.text
def outputFile = new File('auto_findview_output.txt')
def sb = new StringBuilder()
def lineSeparator = System.getProperty("line.separator");
def viewIdList = []


def matcher = xml =~ /<(.*?)(\s*)android:id="@\+id\/(.*?)"/
println "matcher.size() = "+matcher.size()
//println "matcher[0].size() = "+matcher[0].size()
//matcher.each{amatch -> println amatch.size()+"" + amatch }

if(matcher.size() <= 0) {
	return
}

matcher.each{ amatcher ->
	def className = amatcher[1]
	def viewId = amatcher[3]  //=> tv_pintu_exp
	if(viewId == 'atitlebar'){ // auto_activity脚本中已经包含这个了
		return
	}	
	//println viewId

	// 由tv_pintu_exp变成tvExp
	if(viewId.endsWith("_x")){
		return
	}
	viewIdList<<viewId
	ary2 = viewId.tokenize('_')
	sb2 = new StringBuilder()
	ary2.eachWithIndex{ item, index ->
		if(index > 1){
			if(index == (ary2.size() -1 ) && item == 'c'){				
				return
			}
			// 可以用string.capitalize()来替换下面的做法
			if(item.size()>1){
				sb2 << item[0].toUpperCase()+item[1..item.length()-1]
			} else {
				sb2 << item[0].toUpperCase()
			}
		} else if(index == 0){
			sb2 << item
		} 
	}
	memeberName = sb2.toString() 
	println memeberName 


	ary5 = className.tokenize('.') //主要是防备android.support.v4.ViewPager这样的也能有清爽的类名（不带包名的）
	className = ary5[-1]

	println '-------------------'

	sb.insert(0, "private $className $memeberName;"+lineSeparator)
	sb<<"$memeberName = ($className) findViewById(R.id.$viewId);"<<lineSeparator
	if(viewId.endsWith("_c")){
		sb<<"${memeberName}.setOnClickListener(this);"<<lineSeparator
	}
}



sb<<lineSeparator
sb<<"\t@Override"<<lineSeparator
sb<<"\tpublic void onClick(View v) {"<<lineSeparator
sb<<"\t\tswitch (v.getId()) {"<<lineSeparator
viewIdList.each{
	if(it.endsWith("_c")){
		sb<<"\t\t\tcase R.id.${it}:"<<lineSeparator
		sb<<"\t\t\t\t"<<lineSeparator
		sb<<"\t\t\t\tbreak;"<<lineSeparator
	}	
}
sb<<"\t\t\tdefault:"<<lineSeparator
sb<<"\t\t\t\tbreak;"<<lineSeparator
sb<<"\t\t}"<<lineSeparator
sb<<"\t}"<<lineSeparator


outputFile.withWriter { Writer writer ->
	writer.append(sb)
}





// 实践 节点
// 1. XmlSlurper取出来的数据为空。 所以只能选用XmlParser
// 2. println xml.TextView // 取出一级子项的所有TextView。 结果是一个Lsit
// 3. 正则匹配“@+id”时， 匹配规则里因为+在正则里已经有意义，所以要写成\+。
