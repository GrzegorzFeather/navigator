package com.feathersoft.navigator.sample.ui.fragment;

import com.feathersoft.navigator.config.NavigatorMenuOption;
import com.feathersoft.navigator.sample.menu.UserUIConfiguration;
import com.feathersoft.navigator.ui.fragment.NavigatorContentFragment;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public class FirstOptionFragmentNavigatorContentFragment extends NavigatorContentFragment {

    @Override
    protected NavigatorMenuOption getMenuOption() {
        return UserUIConfiguration.UserMenu.FIRST;
    }
}
