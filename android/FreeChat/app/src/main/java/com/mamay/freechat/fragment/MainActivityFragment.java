package com.mamay.freechat.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamay.freechat.R;
import com.mamay.freechat.model.ChangedText;
import com.mamay.freechat.view.FreeChatMainView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FreeChatMainView.TextChangedListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        FreeChatMainView f = (FreeChatMainView) view.findViewById(R.id.freeview);
        f.setTextChangedListener(this);


        return view;
    }

    @Override
    public void onTextChanged(FreeChatMainView.ChangedText changedText) {
        ChangedText ct = ChangedText.fromViewText(changedText);
        Log.d("text change", ct.toString());
    }
}
