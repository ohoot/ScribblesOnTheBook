package com.example.joo.scribblesonthebook.main;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagePickerDialogFragment extends DialogFragment {


    public PagePickerDialogFragment() {
        // Required empty public constructor
    }

    NumberPicker numberPicker1, numberPicker2, numberPicker3;

    BookData bookData;
    int startPage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        Bundle arg = getArguments();
        if (arg != null) {
            bookData = (BookData) arg.getSerializable(ScribbleFragment.CURRENT_BOOK);
            startPage = arg.getInt(ScribbleFragment.START_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_picker_dialog, container, false);
        int h = startPage / 100;
        int t = (startPage - h * 100) / 10;
        int o = startPage - (h * 100) - (t * 10);
        numberPicker1 = (NumberPicker) view.findViewById(R.id.numberPicker1_page_dialog);
        numberPicker1.setMaxValue(9);
        numberPicker1.setMinValue(0);
        numberPicker1.setValue(h);
        numberPicker2 = (NumberPicker) view.findViewById(R.id.numberPicker2_page_dialog);
        numberPicker2.setMaxValue(9);
        numberPicker2.setMinValue(0);
        numberPicker2.setValue(t);
        numberPicker3 = (NumberPicker) view.findViewById(R.id.numberPicker3_page_dialog);
        numberPicker3.setMaxValue(9);
        numberPicker3.setMinValue(0);
        numberPicker3.setValue(o);
        Button btn = (Button) view.findViewById(R.id.btn_page_dialog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int endPage = (numberPicker1.getValue() * 100) + (numberPicker2.getValue() * 10) + numberPicker3.getValue();
                if (pagePickerOkClickListener != null) {
                    pagePickerOkClickListener.onPagePickerOkClick(endPage);
                }
                try {
                    NetworkManager.getInstance().sendBookMark(getContext(), bookData.getIsbn(), "" + startPage, "" + endPage, new NetworkManager.OnResultListener<SimpleRequest>() {
                        @Override
                        public void onSuccess(Request request, SimpleRequest result) {
                            if (result.error == null) {
                                Toast.makeText(getContext(), result.success.message, Toast.LENGTH_SHORT).show();
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
        int width = getResources().getDimensionPixelSize(R.dimen.page_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.page_dialog_height);
        getDialog().getWindow().setLayout(width, height);
    }

    public PagePickerOkClickListener pagePickerOkClickListener;

    public interface PagePickerOkClickListener {
        public void onPagePickerOkClick(int page);
    }

    public void setPagePickerOkClickListener(PagePickerOkClickListener listener) {
        pagePickerOkClickListener = listener;
    }


}
