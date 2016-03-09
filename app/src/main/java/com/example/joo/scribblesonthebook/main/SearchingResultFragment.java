package com.example.joo.scribblesonthebook.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.SearchingBookSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.main.adapter.SearchingResultAdapter;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchingResultFragment extends Fragment {

    public SearchingResultFragment() {
        // Required empty public constructor
    }

    ListView listView;
    SearchingResultAdapter sAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searching_result, container, false);
        listView = (ListView) view.findViewById(R.id.searching_result_list);
        sAdapter = new SearchingResultAdapter();

        try {
            NetworkManager.getInstance().getSearchingResult(getContext(), "앨리스", "" + 1, "" + 30, new NetworkManager.OnResultListener<SearchingBookSuccess>() {
                @Override
                public void onSuccess(Request request, SearchingBookSuccess result) {

                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return view;
    }

}
