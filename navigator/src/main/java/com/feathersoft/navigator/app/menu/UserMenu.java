package com.feathersoft.navigator.app.menu;

import com.feathersoft.navigator.R;
import com.feathersoft.navigator.app.NavigatorConfiguration;
import com.feathersoft.navigator.ui.fragment.MenuOptionFragment;

import android.support.v4.app.Fragment;

/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public enum UserMenu implements NavigatorConfiguration.HomeMenuOption {

    //NEWS(NewsFragment.class, R.string.home_menu_news, R.drawable.ic_news, true),
    //EVENTS(EventsFragment.class, R.string.home_menu_events, R.drawable.ic_events, true),
    //DISCOGRAPHY(DiscographyFragment.class, R.string.home_menu_discography, R.drawable.ic_discography, true),
    //GALLERY(GalleryFragment.class, R.string.home_menu_gallery, R.drawable.ic_gallery, true);
    //MY_LOCATION(MyLocationOptionFragment.class, R.string.home_menu_my_location, R.drawable.ic_launcher, true),
    //LINES_MAP(LinesMapOptionFragment.class, R.string.home_menu_lines_map, R.drawable.ic_launcher, true);
    ;

    // TODO: Rename configuration
    public static final int VISIBLE_NAME = R.string.app_name;

    private Class<? extends MenuOptionFragment> mContentClass;
    private int mTitleRes;
    private int mIconRes;
    private boolean mIsVisible;

    private UserMenu(Class<? extends MenuOptionFragment> contentClass, int titleRes,
                     int iconRes, boolean isVisible){
        this.mContentClass = contentClass;
        this.mTitleRes = titleRes;
        this.mIconRes = iconRes;
        this.mIsVisible = isVisible;
    }

    @Override
    public Class<? extends Fragment> getContentClass(){
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

    @Override
    public boolean isVisible() {
        return this.mIsVisible;
    }
}