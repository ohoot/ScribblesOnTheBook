package com.example.joo.scribblesonthebook.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.AddBookActivity;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.book_detail.DetailBookInfoActivity;
import com.example.joo.scribblesonthebook.data.SearchingBookResponse;
import com.example.joo.scribblesonthebook.data.SearchingBookSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.main.adapter.SearchingResultAdapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchingResultFragment extends Fragment {

    public SearchingResultFragment() {
        // Required empty public constructor
    }

    String searchingKeyword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            searchingKeyword = b.getString(SearchRecommFragment.SEARCHING_KEYWORD);
        }
    }

    ListView listView;
    SearchingResultAdapter sAdapter;

    public static final String NORESULT_BACKSTACK = "noResult";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searching_result, container, false);
        listView = (ListView) view.findViewById(R.id.searching_result_list);
        sAdapter = new SearchingResultAdapter();
        sAdapter.setOnResultArrowClickListener(new SearchingResultAdapter.OnResultArrowClickListener() {
            @Override
            public void onResultArrowClick(BookData bookData) {
                Intent intent = new Intent(getActivity(), AddBookActivity.class);
                intent.putExtra(AddBookActivity.SEARCHED_BOOK_TAG, bookData);
                startActivity(intent);
            }
        });

        try {
            NetworkManager.getInstance().getSearchingResult(getContext(), searchingKeyword, "" + 1, new NetworkManager.OnResultListener<SearchingBookResponse>() {
                @Override
                public void onSuccess(Request request, SearchingBookResponse result) {
                    listView.setAdapter(sAdapter);
                    if (result.success.searchList.size() == 0) {
                        ResultNothingFragment f = new ResultNothingFragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.searching_recomm_container, f);
                        ft.addToBackStack(NORESULT_BACKSTACK);
                        ft.commit();
                    }

                    sAdapter.addAll(result.success.searchList);
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
