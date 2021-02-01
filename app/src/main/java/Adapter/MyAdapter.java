package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;

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
        TableLayout layout = v.findViewById(R.id.tablelayout);
        layout.setOrientation(LinearLayout.VERTICAL);

        int i=0;
        ListItem lst = listitems.get(i);
        ToggleButton[][] buttonArray = new ToggleButton[1][lst.getbtn_size()];
        TableLayout table = new TableLayout(con);
        for (int row = 0; row < 1; row++) {
            TableRow currentRow = new TableRow(con);
            currentRow.setFadingEdgeLength(4);
            for (int button = 0; button < lst.getbtn_size(); button++) {
                ToggleButton currentButton = new ToggleButton(con);
                currentButton.setBackgroundDrawable(con.getDrawable(R.drawable.ic_toggle));
                currentButton.setText(lst.getBtn(button));
                currentButton.setTextOn(lst.getBtn(button));
                currentButton.setTextOff(lst.getBtn(button));
                // you could initialize them here
                // you can store them
                buttonArray[row][button] = currentButton;

                // and you have to add them to the TableRow
                currentRow.addView(currentButton);
            }
            // a new row has been constructed -> add to table
            table.addView(currentRow);
        }
// and finally takes that new table and add it to your layout.
        layout.addView(table);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem lst=listitems.get(position);
        holder.titletv.setText(lst.getTitle());
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titletv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titletv=itemView.findViewById(R.id.titletv);
        }
    }
}