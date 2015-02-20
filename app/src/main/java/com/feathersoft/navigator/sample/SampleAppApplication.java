package com.feathersoft.navigator.sample;

import com.feathersoft.navigator.app.config.UIConfigurationManager;
import com.feathersoft.navigator.sample.menu.UserUIConfiguration;

import android.app.Application;


/**
 * Created by GrzegorzFeathers on 2/20/15.
 */
public class SampleAppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UIConfigurationManager.init(new UserUIConfiguration());
        UIConfigurationManager.getInstance().setCurrentUIConfiguration(UserUIConfiguration.TAG);
    }
}
