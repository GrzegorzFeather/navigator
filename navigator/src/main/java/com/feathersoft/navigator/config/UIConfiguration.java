package com.feathersoft.navigator.config;

import com.feathersoft.navigator.ui.fragment.NavigatorContentFragment;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public abstract class UIConfiguration {

    public static final UIConfiguration defaultUIConfig
            = new DefaultUIConfiguration(0, new NavigatorMenuOption[]{}, null);

    private int mVisibleNameRes;
    private NavigatorMenuOption mDefaultMenuOption;
    private NavigatorMenuOption[] mMenuOptions;

    protected UIConfiguration(int visibleNameRes, NavigatorMenuOption[] menuOptions,
                                 NavigatorMenuOption defaultMenuOption) {
        this.mVisibleNameRes = visibleNameRes;
        this.mMenuOptions = menuOptions;
        this.mDefaultMenuOption = defaultMenuOption;
    }

    public NavigatorMenuOption findMenuOptionById(int id){
        for(NavigatorMenuOption option : this.mMenuOptions){
            if(option.getItemId() == id){
                return option;
            }
        }

        return null;
    }

    public NavigatorMenuOption findMenuOptionByContentClass(Class<? extends NavigatorContentFragment> clazz){
        if(clazz == null){
            return null;
        }

        for(NavigatorMenuOption option : this.mMenuOptions){
            if(option.getContentClass().equals(clazz)){
                return option;
            }
        }

        return null;
    }

    public NavigatorMenuOption getDefaultMenuOption(){
        return this.mDefaultMenuOption;
    }

    public NavigatorMenuOption[] getMenuOptions(){
        return this.mMenuOptions;
    }

    private static class DefaultUIConfiguration extends UIConfiguration {
        private DefaultUIConfiguration(int visibleNameRes, NavigatorMenuOption[] menuOptions,
                                          NavigatorMenuOption defaultMenuOption) {
            super(visibleNameRes, menuOptions, defaultMenuOption);
        }
    }

}
