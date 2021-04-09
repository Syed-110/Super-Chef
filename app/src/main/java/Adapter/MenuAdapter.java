package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipe.DetailActivity;
import com.example.recipe.R;

import java.util.ArrayList;
import java.util.List;

import Model.MenuModel;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    private Context con;
    private List<MenuModel> myList;
    private int lastPosition=-1;

    public MenuAdapter(Context con, List<MenuModel> myList) {
        this.con = con;
        this.myList = myList;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(con).load(myList.get(position).getItemImage()).into(holder.imageView);
        //holder.imageView.setImageResource(myList.get(position).getItemImage());
        holder.mTitle.setText(myList.get(position).getItemName());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(con, DetailActivity.class);
                intent.putExtra("name",myList.get(position).getItemName());
                intent.putExtra("key",myList.get(position).getKey());
                intent.putExtra("Image",myList.get(holder.getAdapterPosition()).getItemImage());
                intent.putExtra("PrepTime",myList.get(holder.getAdapterPosition()).getPrepTime());
                intent.putExtra("CookTime",myList.get(holder.getAdapterPosition()).getCookTime());
                intent.putExtra("TotalTime",myList.get(holder.getAdapterPosition()).getTotalTime());
                intent.putExtra("Ingredients",myList.get(holder.getAdapterPosition()).getItemIngredients());
                intent.putExtra("Instruction",myList.get(holder.getAdapterPosition()).getItemInstruction());
                con.startActivity(intent);
            }
        });
        setAnimation(holder.itemView,position);
    }

    public void setAnimation(View viewToAnimate,int position){
        if(position>lastPosition){
            ScaleAnimation animation=new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                    Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(1000);
            viewToAnimate.startAnimation(animation);
            lastPosition=position;
        }
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void filteredList(ArrayList<MenuModel> filterList) {
        myList=filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mTitle;
        CardView mCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.tvImageView);
            mTitle=itemView.findViewById(R.id.tvTitle);
            mCardView=itemView.findViewById(R.id.myCardView);
        }
    }
}
