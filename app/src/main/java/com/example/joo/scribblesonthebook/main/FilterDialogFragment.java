package com.example.joo.scribblesonthebook.main;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.ReferInterestResponse;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;
import com.example.joo.scribblesonthebook.signup.FilterAdapter;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterDialogFragment extends DialogFragment {

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

    public FilterDialogFragment() {
        // Required empty public constructor
    }

    GridView gridView;
    FilterAdapter fAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView_filter_dialog);
        fAdapter = new FilterAdapter();
        gridView.setAdapter(fAdapter);

        try {
            NetworkManager.getInstance().getInterests(getContext(), new NetworkManager.OnResultListener<ReferInterestResponse>() {
                @Override
                public void onSuccess(Request request, ReferInterestResponse result) {
                    if (result.success != null) {
                        for (int i = 0; i < result.success.filter.selectedItems.size(); i++) {
                            gridView.setItemChecked(result.success.filter.selectedItems.get(i) - 1, true);
                        }
                    }
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        fAdapter.addFilterText(convertIntResource(filterContents));

        Button btn = (Button) view.findViewById(R.id.btn_filter_dialog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray sbArray = gridView.getCheckedItemPositions();
                int checkedCount = 0;
                for(int i = 0; i < sbArray.size(); i++) {
                    int p = sbArray.keyAt(i);
                    if (sbArray.get(p)) {
                        checkedCount++;
                    }
                }
                String[] checkItems = new String[checkedCount];
                int checkedItemIndex = 0;
                for (int i = 0; i < sbArray.size(); i++) {
                    int p = sbArray.keyAt(i);
                    if (sbArray.get(p)) {
                        checkItems[checkedItemIndex] = "" + p;
                        checkedItemIndex++;
                    }
                }
                try {
                    NetworkManager.getInstance().changeInterests(getContext(), checkItems, new NetworkManager.OnResultListener<SimpleRequest>() {
                        @Override
                        public void onSuccess(Request request, SimpleRequest result) {
                            if (result.success.message != null) {
                                Toast.makeText(getContext(), "수정 되었습니다", Toast.LENGTH_SHORT).show();
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), result.error.message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int width = getResources().getDimensionPixelSize(R.dimen.filter_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.filter_dialog_height);
        getDialog().getWindow().setLayout(width, height);
    }


    private String[] convertIntResource(int[] filterContents) {
        String[] texts = new String[filterContents.length];
        for (int i = 0; i < filterContents.length; i++) {
            texts[i] = getResources().getString(filterContents[i]);
        }
        return texts;
    }
}
