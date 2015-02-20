package com.feathersoft.navigator.config;

import com.feathersoft.navigator.ui.fragment.NavigatorContentFragment;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public interface NavigatorMenuOption {

    public Class<? extends NavigatorContentFragment> getContentClass();
    public int getTitleRes();
    public int getIconRes();

}
