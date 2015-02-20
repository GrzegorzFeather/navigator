package com.feathersoft.navigator.ui.fragment;

import com.feathersoft.navigator.config.NavigatorMenuOption;
import com.feathersoft.navigator.ui.NavigatorActivity;

import android.support.v7.app.ActionBar;

/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public abstract class NavigatorContentFragment extends NavigatorBaseFragment {

    private NavigatorMenuOption mMenuOption;

    public NavigatorContentFragment(){
        this.mMenuOption = this.getMenuOption();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getMenuHostActivity().setSubtitle(this.mMenuOption.getTitleRes());
    }

    protected NavigatorActivity getMenuHostActivity(){
        NavigatorActivity menuHostActivity = null;

        try {
            menuHostActivity = (NavigatorActivity) this.getActivity();
        } catch (ClassCastException e) {
            throw e;
        }

        return menuHostActivity;
    }

    protected ActionBar getActionbar(){
        return this.getMenuHostActivity().getSupportActionBar();
    }

    protected abstract NavigatorMenuOption getMenuOption();

    public boolean onBackPressed(){
        return false;
    }

}
