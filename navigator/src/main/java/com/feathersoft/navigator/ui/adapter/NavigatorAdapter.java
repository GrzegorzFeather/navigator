package com.feathersoft.navigator.ui.adapter;

import com.feathersoft.navigator.R;
import com.feathersoft.navigator.config.NavigatorMenuOption;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class NavigatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_MENU_ITEM = 1;

    private List<NavigatorMenuOption> mDataSet;
    private View.OnClickListener mOnClickListener;
    private RecyclerView.ViewHolder mHeaderHolder = null;

    public NavigatorAdapter(NavigatorMenuOption[] menuOptions,
                            View.OnClickListener clickListener){
        this.updateContent(menuOptions);
        this.mOnClickListener = clickListener;
    }

    private void updateContent(NavigatorMenuOption[] menuOptions){
        this.mDataSet = Arrays.asList(menuOptions);
    }

    public void setHeader(RecyclerView.ViewHolder headerHolder){
        this.mHeaderHolder = headerHolder;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            return this.mHeaderHolder;
        } else {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_navigator_option, parent, false);
            rootView.setOnClickListener(this.mOnClickListener);
            return new MenuItemViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(this.getItemViewType(position) == TYPE_HEADER){

        } else {
            MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
            NavigatorMenuOption option = this.getItem(position);
            menuItemHolder.mImgIcon.get().setImageResource(option.getIconRes());
            menuItemHolder.mLblTitle.get().setText(option.getTitleRes());
        }
    }

    @Override
    public int getItemCount() {
        return this.mHeaderHolder == null ?
                this.mDataSet.size() : this.mDataSet.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(this.mHeaderHolder == null){
            return TYPE_MENU_ITEM;
        } else {
            return position == 0 ? TYPE_HEADER : TYPE_MENU_ITEM;
        }
    }

    public int getRealItemPosition(int position){
        if(this.mHeaderHolder == null){
            return position;
        } else {
            return position - 1;
        }
    }

    public NavigatorMenuOption getItem(int position){
        if(this.mHeaderHolder == null){
            return this.mDataSet.get(position);
        } else {
            return this.mDataSet.get(position - 1);
        }
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private WeakReference<ImageView> mImgIcon;
        private WeakReference<TextView> mLblTitle;

        public MenuItemViewHolder(View rootView) {
            super(rootView);
            this.mImgIcon = new WeakReference<ImageView>(
                    (ImageView) rootView.findViewById(R.id.img_icon));
            this.mLblTitle = new WeakReference<TextView>(
                    (TextView) rootView.findViewById(R.id.lbl_title));
        }
    }

}

