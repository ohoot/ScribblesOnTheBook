package com.example.joo.scribblesonthebook.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.BookListSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.list_scribble.ScribbleListActivity;
import com.example.joo.scribblesonthebook.main.adapter.DefaultPagerAdapter;
import com.example.joo.scribblesonthebook.main.adapter.ScribblePagerAdapter;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScribbleFragment extends Fragment {

    public static final int START_SPINNER_INDEX = 0;

    public ScribbleFragment() {
        // Required empty public constructor
    }
    Spinner spinner;
    ViewPager viewPager;
    ArrayAdapter<String> mApdater;
    ImageView arrowView;
    ScribblePagerAdapter sAdapter;
    DefaultPagerAdapter dApater;
    TextView totalBookView;
    SeekBar seekBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scribble, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner_scribble);
        mApdater = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        mApdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mApdater);
        viewPager = (ViewPager) view.findViewById(R.id.scribble_pager);
        //sAdapter = new ScribblePagerAdapter(getChildFragmentManager());
        //viewPager.setAdapter(sAdapter);
        arrowView = (ImageView) view.findViewById(R.id.image_swaping_arrow);
        arrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScribbleListActivity.class));
            }
        });
        totalBookView = (TextView) view.findViewById(R.id.text_total_books);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                callBookList(spinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        callBookList(START_SPINNER_INDEX);
        initSpinner();

        return view;
    }

    private void callBookList(int position) {
        try {
            NetworkManager.getInstance().getBookList(getContext(), "" + position, "" + 1, new NetworkManager.OnResultListener<BookListSuccess>() {
                @Override
                public void onSuccess(Request request, final BookListSuccess result) {
                    Toast.makeText(getContext(), result.message.toString(), Toast.LENGTH_SHORT).show();
                    /*if (result.tenseList.size() == 0) {
                        dApater = new DefaultPagerAdapter(getChildFragmentManager());
                        viewPager.setAdapter(dApater);
                    } else {
                        sAdapter = new ScribblePagerAdapter(getChildFragmentManager());
                        sAdapter.clearAll();
                        sAdapter.addAll(result.tenseList);

                    }
                    setScribblePage(result);*/
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {
                    Toast.makeText(getContext(), "낙서 Failure", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void setScribblePage(final BookListSuccess result) {
        totalBookView.setText(result.tenseList.size() + "");

    }

    private void initSpinner() {
        mApdater.add(getResources().getString(R.string.book_reading));
        mApdater.add(getResources().getString(R.string.book_done));
        mApdater.add(getResources().getString(R.string.book_will));
    }

}
