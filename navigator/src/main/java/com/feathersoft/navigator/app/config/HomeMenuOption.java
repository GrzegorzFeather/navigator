package com.feathersoft.navigator.app.config;

import com.feathersoft.navigator.ui.fragment.MenuOptionFragment;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public interface HomeMenuOption {

    public Class<? extends MenuOptionFragment> getContentClass();
    public int getTitleRes();
    public int getIconRes();

}
