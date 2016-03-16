package com.example.joo.scribblesonthebook.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.main.adapter.SearchingRecommAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRecommFragment extends Fragment {

    public static final String RECOMM_LIST_BACKSTACK = "recommList";
    public static final String SEARCHING_RESULT_BACKSTACK = "searchingResult";
    public static final String SEARCHING_KEYWORD = "searchingKey";

    public SearchRecommFragment() {
        // Required empty public constructor
    }

    EditText keywordView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_recomm, container, false);
        Fragment fragment = new RecommListFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.searching_recomm_container, fragment);
        ft.addToBackStack(RECOMM_LIST_BACKSTACK);
        ft.commit();

        keywordView = (EditText) view.findViewById(R.id.edit_searching_keyword);
        keywordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    Fragment f = new SearchingResultFragment();
                    Bundle b = new Bundle();
                    b.putString(SEARCHING_KEYWORD, keywordView.getText().toString());
                    f.setArguments(b);

                    FragmentTransaction fts = getActivity().getSupportFragmentManager().beginTransaction();
                    fts.replace(R.id.searching_recomm_container, f);
                    fts.addToBackStack(SEARCHING_RESULT_BACKSTACK);
                    fts.commit();
                }
                return true;
            }
        });
        return view;
    }

}
