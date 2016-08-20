package ${packageName};

import ${superClassFqcn};
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import cn.six.sup.rv.RvViewHolder;
import cn.six.sup.rv.OnRvItemClickListener;
import cn.six.sup.rv.one_adapter.OneAdapter;

<#if applicationPackage??>
import ${applicationPackage}.R;
</#if>

public class ${activityClass} extends ${superClass} {
	private RecyclerView rv;
	private OneAdapter<${itemType}> adapter;
	private List<${itemType}> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.${layoutName});

		rv = (RecyclerView) findViewById(R.id.rv_data);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));	

        adapter = new OneAdapter<${itemType}>(R.layout.${itemLayoutName}) {
            @Override
            protected void apply(RvViewHolder vh, ${itemType} value, int position) {

            }
        };
        adapter.data = data;
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(new OnRvItemClickListener(rv) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) { }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                
            }
        });

        // ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RvItemTouchHelperCallback(this));
        // itemTouchHelper.attachToRecyclerView(rv);        
    }
}
