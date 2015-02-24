package com.feathersoft.navigator.sample.menu;

import com.feathersoft.navigator.config.NavigatorMenuOption;
import com.feathersoft.navigator.config.UIConfiguration;
import com.feathersoft.navigator.sample.R;
import com.feathersoft.navigator.sample.ui.fragment.FirstNavigatorContentFragment;
import com.feathersoft.navigator.sample.ui.fragment.SecondNavigatorContentFragment;
import com.feathersoft.navigator.sample.ui.fragment.ThirdNavigatorContentFragment;
import com.feathersoft.navigator.ui.fragment.NavigatorContentFragment;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public class UserUIConfiguration extends UIConfiguration {

    public UserUIConfiguration() {
        super(R.string.app_name, UserMenu.values(), UserMenu.FIRST);
    }

    public enum UserMenu implements NavigatorMenuOption {

        FIRST(FirstNavigatorContentFragment.class, R.string.menu_opton1, R.drawable.ic_launcher),
        SECOND(SecondNavigatorContentFragment.class, R.string.menu_opton2, R.drawable.ic_launcher),
        THIRD(ThirdNavigatorContentFragment.class, R.string.menu_opton3, R.drawable.ic_launcher),
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

        public int getItemId(){
            return this.ordinal();
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
