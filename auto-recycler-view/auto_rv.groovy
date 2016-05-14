package tool

import java.text.DateFormat

/**
 * Created by songzhw on 2016-05-13.
 */

packageName = 'ca.six.todo'
basicName = 'Second'

adapterName = "${basicName}Adapter"
adapterFileName = "${adapterName}.java"
lineSeparator = System.getProperty('line.separator')

sb = new StringBuilder()
generateAdapterContent()
write2File(adapterFileName, sb)

def write2File(fileFullName, content){
    def file = new File(fileFullName)
    file.withWriter{ Writer writer ->
        writer.append(content)
    }
}


def generateAdapterContent() {
    sb<<"package ${packageName};"<<lineSeparator
    sb<<lineSeparator

    sb<<"import android.support.v7.widget.RecyclerView;"<<lineSeparator
    sb<<"import android.view.LayoutInflater;"<<lineSeparator
    sb<<"import android.view.View;"<<lineSeparator
    sb<<"import android.view.ViewGroup;"<<lineSeparator
    sb<<"import java.util.List;"<<lineSeparator
    sb<<lineSeparator


    sb<<"// Created by songzhw on ${new Date().format( 'yyyy/MM/dd')}"<<lineSeparator
    sb<<""<<lineSeparator
    sb<<""<<lineSeparator
    sb<<""<<lineSeparator
    sb<<""<<lineSeparator
    sb<<""<<lineSeparator



    sb<<""<<lineSeparator
    sb<<""<<lineSeparator
    sb<<""<<lineSeparator
}