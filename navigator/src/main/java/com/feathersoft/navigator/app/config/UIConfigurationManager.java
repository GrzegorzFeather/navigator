package com.feathersoft.navigator.app.config;

import com.feathersoft.navigator.ui.NavigatorActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public final class UIConfigurationManager {

    private static UIConfigurationManager mConfigurationManager = null;

    private Map<String, UIConfiguration> mConfigurations = new HashMap<>();
    private UIConfiguration mCurrentUIConfiguration = null;

    public static void init(UIConfiguration... configurations){
        if(mConfigurationManager == null){
            mConfigurationManager = new UIConfigurationManager();
        }

        for(UIConfiguration configuration : configurations){
            mConfigurationManager.addUIConfiguration(configuration, false);
        }
    }

    public static UIConfigurationManager getInstance(){
        if(mConfigurationManager == null){
            throw new IllegalStateException("UIConfigurationManager hasn't been initialized"
                + ", please call UIConfigurationManager.init(UIConfiguration... configurations)");
        }
        return mConfigurationManager;
    }

    private UIConfigurationManager(){}

    private UIConfiguration getCurrentUIConfiguration(){
        if(this.mCurrentUIConfiguration == null){
            throw new IllegalStateException("The UIConfiguration hasn't been selected"
                + ", please call UIConfigurationManager.setCurrentUIConfiguration(String uiConfigurationTag)");
        }

        return this.mCurrentUIConfiguration;
    }

    public void addUIConfiguration(UIConfiguration uiConfiguration, boolean setAsCurrent){
        this.mConfigurations.put(uiConfiguration.getUITag(), uiConfiguration);
        if(setAsCurrent){
            this.setCurrentUIConfiguration(uiConfiguration.getUITag());
        }
    }

    public void setCurrentUIConfiguration(String uiConfigurationTag){
        if(!this.mConfigurations.containsKey(uiConfigurationTag)){
            throw new IllegalStateException("The selected UIConfiguration does not exist, please add it with "
                + "UIConfigurationManager.addUIConfiguration(UIConfiguration uiConfiguration, boolean setAsCurrent)");
        }

        this.mCurrentUIConfiguration = this.mConfigurations.get(uiConfigurationTag);
    }

    public void launchConfiguration(Context context, Intent intent, UIConfiguration uiConfiguration){
        if(!this.mConfigurations.containsKey(uiConfiguration.getUITag())){
            this.addUIConfiguration(uiConfiguration, true);
        }
        context.startActivity(intent);
    }

    public void launchConfiguration(Context context, UIConfiguration uiConfiguration,
                                    Class<? extends NavigatorActivity> targetNavigator){
        Intent intent = new Intent(context, targetNavigator);
        if(!(context instanceof Activity)){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        this.launchConfiguration(context, intent, uiConfiguration);
    }

    public void launchConfiguration(Context context, String uiConfigurationTag,
                                    Class<? extends NavigatorActivity> targetNavigator){
        if(!this.mConfigurations.containsKey(uiConfigurationTag)){
            throw new IllegalStateException("The selected UIConfiguration does not exist, please add it with "
                + "UIConfigurationManager.addUIConfiguration(UIConfiguration uiConfiguration, boolean setAsCurrent)");
        }
        this.launchConfiguration(context, this.mConfigurations.get(uiConfigurationTag), targetNavigator);
    }

    public void launchConfiguration(Context context, Intent intent, String uiConfigurationTag){
        if(!this.mConfigurations.containsKey(uiConfigurationTag)){
            throw new IllegalStateException("The selected UIConfiguration does not exist, please add it with "
                + "UIConfigurationManager.addUIConfiguration(UIConfiguration uiConfiguration, boolean setAsCurrent)");
        }
        this.launchConfiguration(context, intent, this.mConfigurations.get(uiConfigurationTag));
    }

    public HomeMenuOption getDefaultMenuOption(){
        return this.getCurrentUIConfiguration().getDefaultMenuOption();
    }

    public HomeMenuOption[] getMenuOptions(){
        return this.getCurrentUIConfiguration().getMenuOptions();
    }

    public static abstract class UIConfiguration {

        private static final UIConfiguration defaultUIConfig
                = new DefaultUIConfiguration(0, new HomeMenuOption[]{}, null);

        private int mVisibleNameRes;
        private HomeMenuOption mDefaultMenuOption;
        private HomeMenuOption[] mMenuOptions;

        private UIConfiguration(int visibleNameRes, HomeMenuOption[] menuOptions,
                                HomeMenuOption defaultMenuOption) {
            this.mVisibleNameRes = visibleNameRes;
            this.mMenuOptions = menuOptions;
            this.mDefaultMenuOption = defaultMenuOption;
        }

        protected abstract String getUITag();

        private HomeMenuOption getDefaultMenuOption(){
            return this.mDefaultMenuOption;
        }

        private HomeMenuOption[] getMenuOptions(){
            return this.mMenuOptions;
        }

        private static class DefaultUIConfiguration extends UIConfiguration {
            private DefaultUIConfiguration(int visibleNameRes, HomeMenuOption[] menuOptions,
                                           HomeMenuOption defaultMenuOption) {
                super(visibleNameRes, menuOptions, defaultMenuOption);
            }

            @Override
            protected String getUITag() {
                return DefaultUIConfiguration.class.getName();
            }
        }

    }

}
