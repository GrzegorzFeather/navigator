package com.feathersoft.navigator.sample.menu;

import com.feathersoft.navigator.app.config.HomeMenuOption;
import com.feathersoft.navigator.app.config.UIConfigurationManager;
import com.feathersoft.navigator.sample.R;
import com.feathersoft.navigator.sample.ui.fragment.FirstOptionFragment;
import com.feathersoft.navigator.ui.fragment.MenuOptionFragment;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public class UserUIConfiguration extends UIConfigurationManager.UIConfigurationTBR {

    public static final String TAG = UserUIConfiguration.class.getName();

    public UserUIConfiguration() {
        super(R.string.app_name, UserMenu.values(), UserMenu.FIRST);
    }

    @Override
    protected String getUITag() {
        return TAG;
    }

    public enum UserMenu implements HomeMenuOption {

        FIRST(FirstOptionFragment.class, R.string.app_name, R.drawable.ic_launcher)
        ;

        private Class<? extends MenuOptionFragment> mContentClass;
        private int mTitleRes;
        private int mIconRes;

        private UserMenu(Class<? extends MenuOptionFragment> contentClass,
                         int titleRes, int iconRes){
            this.mContentClass = contentClass;
            this.mTitleRes = titleRes;
            this.mIconRes = iconRes;
        }

        @Override
        public Class<? extends MenuOptionFragment> getContentClass() {
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
