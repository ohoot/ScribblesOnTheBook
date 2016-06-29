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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.BookListSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.list_scribble.ScribbleListActivity;
import com.example.joo.scribblesonthebook.main.adapter.DefaultPagerAdapter;
import com.example.joo.scribblesonthebook.main.adapter.ScribblePagerAdapter;
import com.example.joo.scribblesonthebook.writing_scribble.WritingScribbleActivity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScribbleFragment extends Fragment {

    public static final int START_SPINNER_INDEX = 0;
    public static final String CURRENT_BOOK = "currentBook";
    public static final String START_PAGE = "startPage";

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
    Button fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scribble, container, false);
        sAdapter = new ScribblePagerAdapter(getChildFragmentManager());
        fab = (Button) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sAdapter.getCount() == 0) {
                    Toast.makeText(getActivity(), "책을 추가해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getContext(), WritingScribbleActivity.class);
                intent.putExtra(WritingScribbleActivity.CURRENT_BOOK_DATA, sAdapter.getCurrentBook(viewPager.getCurrentItem()));
                intent.putExtra(WritingScribbleActivity.OUTPUT_TYPE, WritingScribbleActivity.OUTPUT_TYPE_WRITING);
                startActivity(intent);
            }
        });

        spinner = (Spinner) view.findViewById(R.id.spinner_scribble);
        mApdater = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        mApdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mApdater);
        viewPager = (ViewPager) view.findViewById(R.id.scribble_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BookData bd = sAdapter.getCurrentBook(viewPager.getCurrentItem());
                seekBar.setProgress(bd.getCurrentPage());
                seekBar.setMax(bd.getTotalPage());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //sAdapter = new ScribblePagerAdapter(getChildFragmentManager());
        //viewPager.setAdapter(sAdapter);
        arrowView = (ImageView) view.findViewById(R.id.image_swaping_arrow);
        arrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sAdapter.getCount() == 0) {
                    Toast.makeText(getActivity(), "책을 추가해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), ScribbleListActivity.class);
                intent.putExtra(ScribbleListActivity.CURRENT_BOOK_DATA, sAdapter.getCurrentBook(viewPager.getCurrentItem()));
                startActivity(intent);
            }
        });
        totalBookView = (TextView) view.findViewById(R.id.text_total_books);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setMax(999);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {
                if (sAdapter.getCount() != 0) {
                    BookData bookData = sAdapter.getCurrentBook(viewPager.getCurrentItem());
                    int startPage = bookData.getCurrentPage();
                    PagePickerDialogFragment fragment = new PagePickerDialogFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(CURRENT_BOOK, bookData);
                    b.putInt(START_PAGE, startPage);
                    fragment.setArguments(b);
                    fragment.setPagePickerOkClickListener(new PagePickerDialogFragment.PagePickerOkClickListener() {
                        @Override
                        public void onPagePickerOkClick(int page) {
                            seekBar.setProgress(page);
                            callBookList(spinner.getSelectedItemPosition());
                        }
                    });
                    fragment.show(getActivity().getSupportFragmentManager(), "");
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (sAdapter.getCount() != 0) {
                    BookData bd = sAdapter.getCurrentBook(viewPager.getCurrentItem());
                    seekBar.setProgress(bd.getCurrentPage());
                    seekBar.setMax(bd.getTotalPage());
                }
            }
        });
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

    //public static final int

    private void callBookList(int position) {
        try {
            NetworkManager.getInstance().getBookList(getContext(), "" + position, "" + 1, new NetworkManager.OnResultListener<BookListSuccess>() {
                @Override
                public void onSuccess(Request request, final BookListSuccess result) {
                    if (result.tenseList.size() == 0) {
                        dApater = new DefaultPagerAdapter(getChildFragmentManager());
                        viewPager.setAdapter(dApater);
                    } else {
                        sAdapter = new ScribblePagerAdapter(getChildFragmentManager());
                        sAdapter.clearAll();
                        sAdapter.addAll(result.tenseList);
                        viewPager.setAdapter(sAdapter);

                        BookData bd = sAdapter.getCurrentBook(viewPager.getCurrentItem());
                        seekBar.setProgress(bd.getCurrentPage());
                        seekBar.setMax(bd.getTotalPage());
                    }
                    setScribblePage(result);
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
