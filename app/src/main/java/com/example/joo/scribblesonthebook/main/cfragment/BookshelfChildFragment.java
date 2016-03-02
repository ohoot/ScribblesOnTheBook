package com.example.joo.scribblesonthebook.main.cfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.joo.scribblesonthebook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookshelfChildFragment extends Fragment {

    public static final String IMAGE_RESOURCE_BOOKSHELF = "resBookshelf";

    public static BookshelfChildFragment newInstance(int res) {
        BookshelfChildFragment f = new BookshelfChildFragment();
        Bundle b = new Bundle();
        b.putInt(IMAGE_RESOURCE_BOOKSHELF, res);
        f.setArguments(b);
        return f;
    }

    public BookshelfChildFragment() {
        // Required empty public constructor
    }

    int res;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null) {
            res = arg.getInt(IMAGE_RESOURCE_BOOKSHELF);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookshelf_child, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_bookshelf_child);
        imageView.setImageResource(res);
        return view;
    }

}
