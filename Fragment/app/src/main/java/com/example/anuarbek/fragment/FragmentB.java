package com.example.anuarbek.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FragmentB extends Fragment {
    SharedPreferences sPref;
    SimpleAdapter adapter;
    ListView lv;
    Button refresh;
    ArrayList newData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_b, container, false);
        sPref = rootView.getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        lv= (ListView) rootView.findViewById(R.id.listView);
        refresh = (Button) rootView.findViewById(R.id.refresh);
        newData = new ArrayList();
        adapter=new SimpleAdapter(getContext(),newData,R.layout.item,new String[]{"text"},new int[]{R.id.text});
        lv.setAdapter(adapter);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    newData = new ArrayList();
                    JSONArray jsonArray = new JSONArray(sPref.getString("text", "[]"));
                    for (int i=0;i<jsonArray.length();i++){
                        HashMap map = new HashMap();
                        map.put("text",jsonArray.getString(i));
                        newData.add(map);
                    }
                    adapter=new SimpleAdapter(getContext(),newData,R.layout.item,new String[]{"text"},new int[]{R.id.text});
                    lv.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
