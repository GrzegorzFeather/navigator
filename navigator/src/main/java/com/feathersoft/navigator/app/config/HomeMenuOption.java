package com.feathersoft.navigator.app.config;

import android.support.v4.app.Fragment;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public interface HomeMenuOption {

    public Class<? extends Fragment> getContentClass();
    public int getTitleRes();
    public int getIconRes();

}
