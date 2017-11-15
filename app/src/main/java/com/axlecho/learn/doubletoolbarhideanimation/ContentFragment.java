package com.axlecho.learn.doubletoolbarhideanimation;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {
    private static final int ANIMATOR_SPEED = 500;
    private RecyclerView listView;
    private RecyclerViewAdapter adapter;
    private View secondNavigationBar;
    private int secondNavigationBarHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        this.init(view);
        return view;
    }

    private void init(View view) {
        listView = (RecyclerView) view.findViewById(R.id.DataListView);
        listView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        adapter = new RecyclerViewAdapter(this.getActivity(), createMockData());
        listView.setAdapter(adapter);

        listView.addOnScrollListener(new HidingScrollListener(this.getActivity()) {

            @Override
            public void onHide() {
                hideSeconNavigationBar();
            }

            @Override
            public void onShow() {
                showSeconNavigationBar();
            }
        });

        secondNavigationBar = view.findViewById(R.id.second_navigation_bar);
        secondNavigationBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                secondNavigationBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                secondNavigationBarHeight = secondNavigationBar.getHeight();
            }
        });
    }

    private void hideSeconNavigationBar() {
        if (secondNavigationBarHeight == 0) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(secondNavigationBar, View.TRANSLATION_Y, 0, -secondNavigationBarHeight);
        animator.setDuration(ANIMATOR_SPEED);
        animator.start();
    }

    private void showSeconNavigationBar() {
        if (secondNavigationBarHeight == 0) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(secondNavigationBar, View.TRANSLATION_Y, -secondNavigationBarHeight, 0);
        animator.setDuration(ANIMATOR_SPEED);
        animator.start();
    }

    private List<String> createMockData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(String.valueOf(i));
        }
        return datas;
    }


}
