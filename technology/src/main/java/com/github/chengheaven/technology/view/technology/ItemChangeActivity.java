package com.github.chengheaven.technology.view.technology;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.chengheaven.componentservice.customer.DragSortRecycler;
import com.github.chengheaven.componentservice.utils.SharedPreferenceUtil;
import com.github.chengheaven.componentservice.view.BaseActivity;
import com.github.chengheaven.technology.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class ItemChangeActivity extends BaseActivity {

    Toolbar mToolbar;
    RecyclerView mItemChangeRecycler;
    private ItemChangeAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.technology_change_item));
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mItemChangeRecycler = findViewById(R.id.item_change_recycler);

        mItemChangeRecycler.setLayoutManager(new LinearLayoutManager(this));
        mItemChangeRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        String strItem = SharedPreferenceUtil.getInstance(this).getItemPosition();
        List<String> list = new ArrayList<>();
        Collections.addAll(list, strItem.split(" "));
        mAdapter = new ItemChangeAdapter(list);
        mItemChangeRecycler.setAdapter(mAdapter);

        DragSortRecycler dragSortRecycler = new DragSortRecycler();
        dragSortRecycler.setViewHandleId(R.id.item_change_text);
        dragSortRecycler.setOnItemMovedListener((from, to) -> {
            final String str = mAdapter.getItem(from);
            mAdapter.removeItem(from);
            mAdapter.addItem(to, str);
            mAdapter.notifyDataSetChanged();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mAdapter.mList.size(); i++) {
                if (i == mAdapter.mList.size() - 1) {
                    sb.append(mAdapter.mList.get(i));
                    continue;
                }
                sb.append(mAdapter.mList.get(i)).append(" ");
            }
            SharedPreferenceUtil.getInstance(this).setItemPosition(sb.toString());
        });

        mItemChangeRecycler.addItemDecoration(dragSortRecycler);
        mItemChangeRecycler.addOnItemTouchListener(dragSortRecycler);
        mItemChangeRecycler.addOnScrollListener(dragSortRecycler.getScrollListener());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.technology_item_change_activity;
    }

    class ItemChangeAdapter extends RecyclerView.Adapter<ItemChangeAdapter.ViewHolder> {

        private List<String> mList = new ArrayList<>();

        ItemChangeAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.technology_item_change_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mItem.setText(mList.get(position));
        }

        String getItem(int i) {
            return mList.get(i);
        }

        void addItem(int i, String str) {
            mList.add(i, str);
        }

        void removeItem(int i) {
            mList.remove(i);
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView mItem;

            ViewHolder(View itemView) {
                super(itemView);
                mItem = itemView.findViewById(R.id.item_change_text);
            }
        }
    }
}
