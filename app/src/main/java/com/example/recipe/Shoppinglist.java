package com.example.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Shoppinglist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Shoppinglist extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView t1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Shoppinglist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Shoppinglist.
     */
    // TODO: Rename and change types and number of parameters
    public static Shoppinglist newInstance(String param1, String param2) {
        Shoppinglist fragment = new Shoppinglist();
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
        View view=inflater.inflate(R.layout.fragment_shoppinglist, container, false);
        t1=view.findViewById(R.id.textview);
        Bundle bundle=getArguments();
        String data = null;
        if (bundle != null) {
            data = bundle.getString("List");
            Toast.makeText(getContext(), "Data"+data, Toast.LENGTH_SHORT).show();
        }
        t1.setText(data);
        return view;
    }
}