package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import Model.ListItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context con;
    private List<ListItem> listitems;

    public MyAdapter(Context con, List<ListItem> listitems) {
        this.con = con;
        this.listitems = listitems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ingredientsactivity,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem lst=listitems.get(position);
        holder.titletv.setText(lst.getTitle());
        FlexboxLayout layout = holder.flex;

        for (int j = 0; j < lst.getbtn_size(); j++) {
            ToggleButton btnTag = new ToggleButton(con);
            btnTag.setText(lst.getBtn(j));
            btnTag.setTextOn(lst.getBtn(j));
            btnTag.setTextOff(lst.getBtn(j));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 10, 10);
            btnTag.setLayoutParams(lp);
            String idname=lst.getTitle()+j;
            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<lst.getbtn_size();i++) {
                        String a="Dairy"+i;
                        Log.d("msg","Button Name "+a);
                        switch (v.getId()) {
                        }
                    }
                }
            });
            Log.d("msg","Idname "+idname);
            Log.d("msgid", String.valueOf(btnTag.getId()));
            Log.d("msgadapter","msgadapter"+lst.getBtn(j)+"Value of j is:"+j);
            btnTag.setBackgroundDrawable(con.getDrawable(R.drawable.ic_toggle));
            layout.addView(btnTag);
        }
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titletv;
        FlexboxLayout flex;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flex=itemView.findViewById(R.id.flexbox_layout);
            titletv=itemView.findViewById(R.id.titletv);
        }
    }
}