package com.feathersoft.navigator.sample.menu;

import com.feathersoft.navigator.config.NavigatorMenuOption;
import com.feathersoft.navigator.config.UIConfiguration;
import com.feathersoft.navigator.sample.R;
import com.feathersoft.navigator.sample.ui.fragment.FirstOptionFragmentNavigatorContentFragment;
import com.feathersoft.navigator.ui.fragment.NavigatorContentFragment;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public class UserUIConfiguration extends UIConfiguration {

    public UserUIConfiguration() {
        super(R.string.app_name, UserMenu.values(), UserMenu.FIRST);
    }

    public enum UserMenu implements NavigatorMenuOption {

        FIRST(FirstOptionFragmentNavigatorContentFragment.class, R.string.app_name, R.drawable.ic_launcher),
        SECOND(FirstOptionFragmentNavigatorContentFragment.class, R.string.app_name, R.drawable.ic_launcher),
        THIRD(FirstOptionFragmentNavigatorContentFragment.class, R.string.app_name, R.drawable.ic_launcher),
        ;

        private Class<? extends NavigatorContentFragment> mContentClass;
        private int mTitleRes;
        private int mIconRes;

        private UserMenu(Class<? extends NavigatorContentFragment> contentClass,
                         int titleRes, int iconRes){
            this.mContentClass = contentClass;
            this.mTitleRes = titleRes;
            this.mIconRes = iconRes;
        }

        @Override
        public Class<? extends NavigatorContentFragment> getContentClass() {
            return this.mContentClass;
        }

        @Override
        public int getTitleRes() {
            return this.mTitleRes;
        }

        @Override
        public int getIconRes() {
            return this.mIconRes;
        }
    }

}
