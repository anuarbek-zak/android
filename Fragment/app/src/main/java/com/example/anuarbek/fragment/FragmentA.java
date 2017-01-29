package com.example.anuarbek.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FragmentA extends Fragment {
    SharedPreferences sPref;
    EditText edit;
    Set<String> set;
    JSONArray jsonArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_, container, false);
        sPref = view.getContext().getSharedPreferences("shared",Context.MODE_PRIVATE);
        Button add = (Button) view.findViewById(R.id.button);
        edit = (EditText) view.findViewById(R.id.edit);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("add",edit.getText().toString());

                try {
                   jsonArray = new JSONArray(sPref.getString("text", "[]"));
                    if(jsonArray.length()==0){
                        jsonArray = new JSONArray();
                    }
                    Log.d("jsonArr",jsonArray.toString());
                    jsonArray.put(edit.getText().toString());
                    SharedPreferences.Editor edit = sPref.edit();
                    edit.putString("text", jsonArray.toString());
                    edit.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

}
