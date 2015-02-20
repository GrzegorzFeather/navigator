package com.feathersoft.navigator.sample.ui.fragment;

import com.feathersoft.navigator.config.NavigatorMenuOption;
import com.feathersoft.navigator.sample.R;
import com.feathersoft.navigator.sample.menu.UserUIConfiguration;
import com.feathersoft.navigator.ui.fragment.NavigatorContentFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public class SecondNavigatorContentFragment extends NavigatorContentFragment {

    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.fragment_content, container, false);
        ((TextView) this.mRootView.findViewById(R.id.lbl_content_name)).setText(R.string.menu_opton2);
        return this.mRootView;
    }

    @Override
    protected NavigatorMenuOption getMenuOption() {
        return UserUIConfiguration.UserMenu.SECOND;
    }
}
