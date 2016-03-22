package com.example.joo.scribblesonthebook.book_detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.BookDetailResponse;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailInfoFragment extends Fragment {

    public static final String CLICKED_BOOK_DATA = "bookData";

    BookData bookData;
    public DetailInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            bookData = (BookData) bundle.getSerializable(CLICKED_BOOK_DATA);
        }
    }


    TextView titleView, authorView, publisherView, categoryView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_info, container, false);
        titleView = (TextView) view.findViewById(R.id.text_book_detail_title);
        authorView = (TextView) view.findViewById(R.id.text_book_detail_author);
        publisherView = (TextView) view.findViewById(R.id.text_book_detail_publisher);
        categoryView = (TextView) view.findViewById(R.id.text_book_detail_category);

        try {
            NetworkManager.getInstance().getBookDetail(getContext(), bookData.getIsbn(), new NetworkManager.OnResultListener<BookDetailResponse>() {
                @Override
                public void onSuccess(Request request, BookDetailResponse result) {
                    if (result.success != null) {
                        bookData = result.success.bookDetail;
                        setDetailText(bookData);
                    }
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

    private void setDetailText(BookData bd) {
        titleView.setText(bd.getTitle());
        authorView.setText(bd.getAuthor());
        publisherView.setText(bd.getPublisher());
        categoryView.setText(bd.getCategory());
    }

}
