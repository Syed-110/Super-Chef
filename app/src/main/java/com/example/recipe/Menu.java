package com.example.recipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.MenuAdapter;
import Model.MenuModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView mRecyclerView;
    List<MenuModel> list;
    Query query;
    FloatingActionButton floatingActionButton;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    ProgressDialog progressDialog;
    private Query postQuery;
    private String newestPostId;
    private String oldestPostId;
    private int startAt = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    EditText txt_Search;
    MenuAdapter adapter;
    MenuModel mData=new MenuModel();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Menu.
     */
    // TODO: Rename and change types and number of parameters
    public static Menu newInstance(String param1, String param2) {
        Menu fragment = new Menu();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_menu, container, false);
        mRecyclerView=v.findViewById(R.id.menurecycler);
        txt_Search=v.findViewById(R.id.txt_searchtext);
        swipeRefreshLayout =v.findViewById(R.id.swipeRefreshLayout);
        floatingActionButton=v.findViewById(R.id.floatingbtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),UploadRecipe.class));
            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Items...");
        list=new ArrayList<>();

        adapter=new MenuAdapter(getContext(),list);
        mRecyclerView.setAdapter(adapter);
        databaseReference= FirebaseDatabase.getInstance().getReference("Recipe");
        progressDialog.show();
//        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for(DataSnapshot itemSnapshot:snapshot.getChildren()){
//                    MenuModel menuModel=itemSnapshot.getValue(MenuModel.class);
//                    menuModel.setKey(itemSnapshot.getKey());
//                    String recipe_name= itemSnapshot.getKey();
//                    if(itemSnapshot.child("itemImage").getValue()!=null && itemSnapshot.child("itemInstruction").getValue()!=null && itemSnapshot.child("itemIngredients").getValue()!=null && itemSnapshot.child("prepTime").getValue()!=null && itemSnapshot.child("cookTime").getValue()!=null && itemSnapshot.child("totalTime").getValue()!=null){
//                        String ingredients=itemSnapshot.child("itemIngredients").getValue().toString();
//                        String instruction=itemSnapshot.child("itemInstruction").getValue().toString();
//                        String image=itemSnapshot.child("itemImage").getValue().toString();
//                        String cook_time=itemSnapshot.child("cookTime").getValue().toString();
//                        String prep_time=itemSnapshot.child("prepTime").getValue().toString();
//                        String total_time=itemSnapshot.child("totalTime").getValue().toString();
//                        MenuModel menuModel1=new MenuModel(recipe_name,cook_time,prep_time,total_time,image,ingredients,instruction);
//                        list.add(menuModel1);
//                    }
//                }
//                adapter.notifyDataSetChanged();
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                progressDialog.dismiss();
//            }
//        });
        query= FirebaseDatabase.getInstance().getReference("Recipe").orderByChild("3 ingredient choco bar recipe nutty chocobar ice cream 3 ways choco bar").limitToFirst(50);
        progressDialog.show();
        valueEventListener=query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot itemSnapshot:snapshot.getChildren()){
                    String recipe_name= itemSnapshot.getKey();
                    if(itemSnapshot.child("itemImage").getValue()!=null && itemSnapshot.child("itemInstruction").getValue()!=null && itemSnapshot.child("itemIngredients").getValue()!=null && itemSnapshot.child("prepTime").getValue()!=null && itemSnapshot.child("cookTime").getValue()!=null && itemSnapshot.child("totalTime").getValue()!=null){
                        String ingredients=itemSnapshot.child("itemIngredients").getValue().toString();
                        String instruction=itemSnapshot.child("itemInstruction").getValue().toString();
                        String image=itemSnapshot.child("itemImage").getValue().toString();
                        String cook_time=itemSnapshot.child("cookTime").getValue().toString();
                        String prep_time=itemSnapshot.child("prepTime").getValue().toString();
                        String total_time=itemSnapshot.child("totalTime").getValue().toString();
                        MenuModel menuModel1=new MenuModel(recipe_name,cook_time,prep_time,total_time,image,ingredients,instruction);
                        list.add(menuModel1);
                    }

                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
//        databaseReference=FirebaseDatabase.getInstance().getReference("Recipe");
//        databaseReference.limitToFirst(5).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
//                    oldestPostId= itemSnapshot.getKey();
//                    MenuModel menuModel=new MenuModel(oldestPostId);
//                    list.add(menuModel);
//                }
//                adapter.notifyDataSetChanged();
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                progressDialog.dismiss();
//            }
//        });
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                databaseReference=FirebaseDatabase.getInstance().getReference("Recipe");
//                databaseReference.orderByKey().startAt(oldestPostId).limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        swipeRefreshLayout.setEnabled(true);
//                        for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
//                            String recipe_name= itemSnapshot.getKey();
//                            MenuModel menuModel=new MenuModel(recipe_name);
//                            list.add(menuModel);
//                        }
//                        adapter.notifyDataSetChanged();
//                        progressDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        progressDialog.dismiss();
//                    }
//                });
//            }
//
//        });
        txt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        return v;
    }

    private void filter(String text) {
        ArrayList<MenuModel> filterList=new ArrayList<>();
        for(MenuModel item:list){
            if(item.getItemName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        adapter.filteredList(filterList);
    }
}