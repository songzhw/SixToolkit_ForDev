package tool

import java.text.DateFormat

/**
 * Created by songzhw on 2016-05-13.
 */

// [input from users]
packageName = 'ca.six.todo'
basicName = 'Second' // ***Adapter
itemType = 'String' // the class type of data
layoutName = 'item_home'  // inflate R.layout.***

// ============================================================
// [code begins]
adapterName = "${basicName}Adapter"
resultFileName = "${adapterName}.java"
lineSeparator = System.getProperty('line.separator')

sb = new StringBuilder()
generateAdapterContent()
write2File(resultFileName, sb)

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
    sb<<"public class ${adapterName} extends RecyclerView.Adapter<${adapterName}.ViewHolder> {"<<lineSeparator
    sb<<"\tpublic List<$itemType> data;"<<lineSeparator
    sb<<lineSeparator

    sb<<"\t@Override"<<lineSeparator
    sb<<"\tpublic ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {"<<lineSeparator
    sb<<"\t\tView itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.${layoutName}, null);"<<lineSeparator
    sb<<"\t\tViewHolder holder = new ViewHolder(itemView);"<<lineSeparator
    sb<<"\t\treturn holder;"<<lineSeparator
    sb<<"\t}"<<lineSeparator
    sb<<lineSeparator


    sb<<"\t@Override"<<lineSeparator
    sb<<"\tpublic void onBindViewHolder(ViewHolder holder, int position) {"<<lineSeparator
    sb<<"\t\tif(data != null && data.size() > position){"<<lineSeparator
    sb<<"\t\t\t${itemType} item = data.get(position);"<<lineSeparator
    sb<<"\t\t\t// holder.tv.setText(item.text);"<<lineSeparator
    sb<<"\t\t\t// holder.tv.setText(item.text);"<<lineSeparator
    sb<<"\t\t}"<<lineSeparator
    sb<<"\t}"<<lineSeparator
    sb<<lineSeparator

    sb<<"\t@Override"<<lineSeparator
    sb<<"\tpublic int getItemCount() {"<<lineSeparator
    sb<<"\t\treturn data == null ? 0 : data.size();"<<lineSeparator
    sb<<"\t}"<<lineSeparator
    sb<<lineSeparator


    sb<<lineSeparator
    sb<<"\tpublic class ViewHolder extends RecyclerView.ViewHolder {"<<lineSeparator
    sb<<"\t\t // public TextView tv;"<<lineSeparator
    sb<<lineSeparator
    sb<<"\t\tpublic ViewHoler(View itemView) {"<<lineSeparator
    sb<<"\t\t\tsuper(itemView);"<<lineSeparator
    sb<<"\t\t\t// tv = (TextView)itemView.findViewById(R.id.tvTask);"<<lineSeparator
    sb<<"\t\t}"<<lineSeparator
    sb<<"\t}"<<lineSeparator
    sb<<lineSeparator
    sb<<lineSeparator

    sb<<"}"<<lineSeparator
}