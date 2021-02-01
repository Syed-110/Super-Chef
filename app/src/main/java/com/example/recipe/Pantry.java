package com.example.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.ListItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pantry#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pantry extends Fragment {
    private RecyclerView.Adapter radp;
    private List<ListItem> listitem;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Pantry() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pantry.
     */
    // TODO: Rename and change types and number of parameters
    public static Pantry newInstance(String param1, String param2) {
        Pantry fragment = new Pantry();
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
        Log.d("msg","hello from create view");
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_pantry, container, false);
        RecyclerView recycleview=rootview.findViewById(R.id.recycler_view);
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        listitem=new ArrayList<>();
        List<String> ingredients_section=new ArrayList<>();
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child("Ingredients");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot:snapshot.getChildren()) {
                    ingredients_section.add(childSnapshot.getKey());
                    Log.d("msg","Inside Datasnapshot is:"+ingredients_section);
                    Log.d("msg","Array length is:"+ingredients_section);
                }
                String[] dairy_btn={"Milk","Butter","Butter","Butter","Yogurt"};
                for(int i=0;i<ingredients_section.size();i++){
                    ListItem item=new ListItem(""+ingredients_section.get(i),dairy_btn);
                    Log.d("msg","Item is:"+item);
                    listitem.add(item);
                    radp=new MyAdapter(getActivity(),listitem);
                    recycleview.setAdapter(radp);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return rootview;
    }
}