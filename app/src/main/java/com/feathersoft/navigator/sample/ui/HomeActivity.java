package com.feathersoft.navigator.sample.ui;

import com.feathersoft.navigator.sample.R;
import com.feathersoft.navigator.sample.menu.UserUIConfiguration;
import com.feathersoft.navigator.ui.NavigatorActivity;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public class HomeActivity extends NavigatorActivity {

    public HomeActivity() {
        super(new UserUIConfiguration());
    }

    @Nullable
    @Override
    protected RecyclerView.ViewHolder onCreateMenuHeader() {
        View header = LayoutInflater.from(this).inflate(R.layout.layout_header, null, false);
        return new RecyclerView.ViewHolder(header){};
    }
}
