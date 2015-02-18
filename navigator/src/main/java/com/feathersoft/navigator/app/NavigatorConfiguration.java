package com.feathersoft.navigator.app;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by GrzegorzFeathers on 2/17/15.
 */
public class NavigatorConfiguration {

    private static final UIConfiguration defaultUIConfiguration = null;//UIConfiguration.USER;
    //private static final BackendConfiguration defaultBackendConfiguration = new DefaultBackendConfiguration();

    //private static BackendConfiguration backendConfiguration = defaultBackendConfiguration;
    private static UIConfiguration currentUIConfiguration =
            UIConfiguration.values()[NavigatorPreferences.loadInt(
                    //NavigatorPreferences.APP_CONFIG, UIConfiguration.USER.ordinal())];
                    NavigatorPreferences.APP_CONFIG, 0)];

    public static void setUIConfiguration(UIConfiguration uiConfiguration){
        currentUIConfiguration = uiConfiguration;
        NavigatorPreferences.saveInt(NavigatorPreferences.APP_CONFIG, uiConfiguration.ordinal());
    }

    public static void restoreConfiguration(){
        currentUIConfiguration = defaultUIConfiguration;
        NavigatorPreferences.saveInt(NavigatorPreferences.APP_CONFIG, defaultUIConfiguration.ordinal());
    }

    public static HomeMenuOption getDefaultMenuOption(){
        return currentUIConfiguration.getDefaultMenuOption();
    }

    public static HomeMenuOption[] getMenuOptions(){
        return currentUIConfiguration.getMenuOptions();
    }

    public static UIConfiguration getCurrentUIConfiguration(){
        return currentUIConfiguration;
    }

    /*
    public static BackendConfiguration getDefaultBackendConfiguration(){
        return defaultBackendConfiguration;
    }

    public static void setBackendConfiguration(BackendConfiguration configuration){
        backendConfiguration = configuration;
    }

    public static BackendConfiguration getBackendConfiguration(){
        return backendConfiguration;
    }
    */

    public enum UIConfiguration {

        //USER(UserMenu.VISIBLE_NAME, UserMenu.MY_LOCATION, UserMenu.values());
        ;

        private int mVisibleName;
        private HomeMenuOption mDefaultMenuOption;
        private HomeMenuOption[] mMenuOptions;

        private UIConfiguration(int mVisibleName, HomeMenuOption mDefaultMenuOption,
                                HomeMenuOption[] mMenuOptions) {
            this.mVisibleName = mVisibleName;
            this.mDefaultMenuOption = mDefaultMenuOption;
            this.mMenuOptions = mMenuOptions;
        }

        private HomeMenuOption getDefaultMenuOption(){
            return this.mDefaultMenuOption;
        }

        private HomeMenuOption[] getMenuOptions(){
            return this.mMenuOptions;
        }

        public static CharSequence[] asCharSequences(Context ctx){
            UIConfiguration[] configs = UIConfiguration.values();
            CharSequence[] sequences = new CharSequence[configs.length];
            for(int i = 0 ; i < configs.length ; i++){
                sequences[i] = ctx.getString(configs[i].mVisibleName);
            }
            return sequences;
        }
    }

    public interface HomeMenuOption {
        public Class<? extends Fragment> getContentClass();
        public int getTitleRes();
        public int getIconRes();
        public boolean isVisible();
    }

    /*
    private static class DefaultBackendConfiguration extends BackendConfiguration {

        public DefaultBackendConfiguration() {
            super("");
        }
    }
    */

}