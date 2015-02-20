package com.feathersoft.navigator.sample.ui.fragment;

import com.feathersoft.navigator.app.config.HomeMenuOption;
import com.feathersoft.navigator.sample.menu.UserUIConfiguration;
import com.feathersoft.navigator.ui.fragment.MenuOptionFragment;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public class FirstOptionFragment extends MenuOptionFragment {

    @Override
    protected HomeMenuOption getMenuOption() {
        return UserUIConfiguration.UserMenu.FIRST;
    }
}
