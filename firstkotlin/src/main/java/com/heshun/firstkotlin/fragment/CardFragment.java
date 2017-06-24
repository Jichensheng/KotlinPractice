package com.heshun.firstkotlin.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.heshun.firstkotlin.R;

/**
 * author：Jics
 * 2017/6/24 16:12
 */
public class CardFragment extends Fragment {
    private int index;
    private int[] card = new int[]{R.drawable.xy_walkthroughs_card1, R.drawable.xy_walkthroughs_card2, R.drawable.xy_walkthroughs_card3};
    private int[] slogan = new int[]{R.drawable.xy_walkthroughs_slogan1, R.drawable.xy_walkthroughs_slogan2, R.drawable.xy_walkthroughs_slogan3};

    public CardFragment() {
    }

    @SuppressLint("ValidFragment")
    public CardFragment(int index) {
        this.index = index;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_red_card, container, false);
        ImageView iv_card = (ImageView) view.findViewById(R.id.iv_card);
        ImageView iv_slogan = (ImageView) view.findViewById(R.id.iv_slogan);
//		if (index > card.length) index = 1;
        index = index > card.length ? 1 : index <= 0 ? 1 : index;

        iv_card.setImageResource(card[index - 1]);
        iv_slogan.setImageResource(slogan[index - 1]);
        return view;
    }
}
