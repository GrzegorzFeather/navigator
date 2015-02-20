package com.feathersoft.navigator.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by GrzegorzFeathers on 1/6/15.
 */
public class NavigatorBaseFragment extends Fragment {

    protected String getLogTag(){
        return this.getClass().getSimpleName();
    }

}
