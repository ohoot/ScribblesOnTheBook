package com.example.joo.scribblesonthebook.signup;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.LoginActivity;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {

    GridView gridView;
    FilterAdapter fAdapter;

    public FilterFragment() {
        // Required empty public constructor
    }

    int[] filterContents = {
            R.string.filter_1reference_book,
            R.string.filter_2kid_book,
            R.string.filter_3economy_book,
            R.string.filter_4social_science_book,
            R.string.filter_5history_book,
            R.string.filter_6art_book,
            R.string.filter_7science_book,
            R.string.filter_8family_book,
            R.string.filter_9self_develop_book,
            R.string.filter_10computer_book,
            R.string.filter_11comic_book,
            R.string.filter_12humanity_book,
            R.string.filter_13novel_book,
            R.string.filter_14essay_book
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView_filter);
        fAdapter = new FilterAdapter();
        gridView.setAdapter(fAdapter);
        String[] filterTexts = convertIntResource(filterContents);
        fAdapter.addFilterText(filterTexts);

        Button btn = (Button) view.findViewById(R.id.btn_filter_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btn = (Button) view.findViewById(R.id.btn_filter_done);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NetworkManager.getInstance().changeInterests(getContext(), )
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        return view;

    }

    private String[] convertIntResource(int[] filterContents) {
        String[] texts = new String[filterContents.length];
        for (int i = 0; i < filterContents.length; i++) {
            texts[i] = getResources().getString(filterContents[i]);
        }
        return texts;
    }

}
