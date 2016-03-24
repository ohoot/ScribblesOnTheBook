package com.example.joo.scribblesonthebook.list_scribble;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.Scribble;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;
import com.example.joo.scribblesonthebook.writing_scribble.WritingScribbleActivity;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyDialogFragment extends DialogFragment {

    TextView textModify, textDelete;

    public ModifyDialogFragment() {
        // Required empty public constructor
    }

    Scribble scribble;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        Bundle bundle = getArguments();
        if (bundle != null) {
            scribble = (Scribble) bundle.get(ScribbleListActivity.TRIANGLE_CLICKED_SCRIBBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modify_dialog, container, false);
        textModify = (TextView) view.findViewById(R.id.text_scribble_modify);
        textModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WritingScribbleActivity.class);
                intent.putExtra(WritingScribbleActivity.MODIFY_SCRIBBLE_DATA, scribble);
                intent.putExtra(WritingScribbleActivity.OUTPUT_TYPE, WritingScribbleActivity.OUTPUT_TYPE_MODIFIYNG);
                startActivity(intent);
                dismiss();
            }
        });
        textDelete = (TextView) view.findViewById(R.id.text_scribble_delete);
        textDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    NetworkManager.getInstance().deleteScribble(getContext(), scribble.getIsbn(), scribble.getScribbleId() + "", new NetworkManager.OnResultListener<SimpleRequest>() {
                        @Override
                        public void onSuccess(Request request, SimpleRequest result) {
                            if (result.error == null) {
                                Toast.makeText(getContext(), result.success.message, Toast.LENGTH_SHORT).show();
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), result.error.message, Toast.LENGTH_SHORT).show();
                                dismiss();
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
        int width = getResources().getDimensionPixelSize(R.dimen.scribble_list_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.scribble_list_dialog_height);
        getDialog().getWindow().setLayout(width, height);
    }
}
