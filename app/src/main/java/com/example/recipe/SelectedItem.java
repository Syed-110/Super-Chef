package com.example.recipe;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.List;

import Adapter.DatabaseHandler;
import Model.Ingredient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedItem extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView t1;
    View v1;
    DatabaseHandler dbh;
    LinearLayout layout;
    ImageButton delete_btn,cart_btn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectedItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectedItem.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectedItem newInstance(String param1, String param2) {
        SelectedItem fragment = new SelectedItem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_selected_item, container, false);
        layout=view.findViewById(R.id.l1layout);
        dbh=new DatabaseHandler(getContext());
        List<Ingredient> ing_list=dbh.getAllingredients();
            for(Ingredient ing:ing_list){
                String all_ing=ing.getIngredient_name();
                for(int i=1;i<=dbh.get_count_ingredients();i++) {
                    t1=new TextView(getContext());
                    t1.setTextSize(20);
                    t1.setPadding(50,0,0,0);
                    t1.append("\n" + all_ing.toLowerCase());

                    delete_btn=new ImageButton(getContext());
                    delete_btn.setId(i);
                    delete_btn.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_delete));
                    delete_btn.setBackgroundColor(Color.TRANSPARENT);
                    delete_btn.setPadding(0,0,40,0);
                    delete_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(int i=1;i<=dbh.get_count_ingredients();i++){
                                if (i == v.getId()) {
                                    Toast.makeText(getContext(), "Delete button clicked of text box" + i, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                    cart_btn=new ImageButton(getContext());
                    cart_btn.setId(i);
                    cart_btn.setImageDrawable(getContext().getDrawable(R.drawable.ic_shopping_cart));
                    cart_btn.setBackgroundColor(Color.TRANSPARENT);
                    cart_btn.setPadding(0,0,30,20);
                    Log.d("Delete Button Id","id="+i);

                    v1 = new View(getContext());
                    v1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,2));
                    v1.setBackgroundColor(Color.GRAY);
                    v1.setPadding(200,0,20,0);
                    Log.d("all_ing", "All Ing: " + all_ing);

//                    Drawable delete = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_delete);
//                    Drawable cart = ContextCompat.getDrawable(getContext(), R.drawable.ic_shopping_cart);
//                    LayerDrawable finalDrawable = new LayerDrawable(new Drawable[] {cart, delete});
//                    finalDrawable.setLayerInset(0, 0, 0,cart.getIntrinsicHeight(),0);
//                    finalDrawable.setLayerGravity(1, Gravity.RIGHT | Gravity.CENTER_HORIZONTAL);
//                    delete_btn.setImageDrawable(finalDrawable);
//                    delete_btn.setBackgroundColor(Color.TRANSPARENT);
//                    delete_btn.setPadding(810,0,0,0);
//                    delete_btn.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_delete));
//                    delete_btn.setPadding(810,0,0,0);
//                    delete_btn.setBackgroundColor(Color.TRANSPARENT);
//                    delete_btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dbh.deleteingredient(new Ingredient(String.valueOf(t1)));
//                            getFragmentManager().beginTransaction().replace(R.id.l1layout, new SelectedItem()).addToBackStack(null).commit();
//                            Toast.makeText(getContext(), "Delete Button Clicked", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    t1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_delete,0);
//                    t1.setOnTouchListener(new View.OnTouchListener() {
//                        @SuppressLint("ClickableViewAccessibility")
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            final int DRAWABLE_LEFT = 0;
//                            final int DRAWABLE_TOP = 1;
//                            final int DRAWABLE_RIGHT = 2;
//                            final int DRAWABLE_BOTTOM = 3;
//                            if(event.getAction() == MotionEvent.ACTION_UP) {
//                            if(event.getRawX() >= (t1.getRight() - t1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                                DatabaseHandler dbh=new DatabaseHandler(getContext());
//                                dbh.deleteingredient(dbh.getIngredient(t1.getText().toString()));
//                                Toast.makeText(getContext(), "On Delete Click", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                            return false;
//                        }
//                    });
                }
                LinearLayout a = new LinearLayout(getContext());
                a.setOrientation(LinearLayout.HORIZONTAL);
                a.addView(t1);

                LinearLayout b = new LinearLayout(getContext());
                b.setOrientation(LinearLayout.HORIZONTAL);
                b.setGravity(Gravity.END);

                LinearLayout c = new LinearLayout(getContext());
                c.setOrientation(LinearLayout.HORIZONTAL);
                c.addView(cart_btn);
                c.addView(delete_btn);

                layout.addView(a);
                layout.addView(b);
                b.addView(c);
                layout.addView(v1);
            }
        return view;
    }
}