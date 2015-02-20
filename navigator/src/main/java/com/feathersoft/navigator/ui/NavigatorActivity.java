package com.feathersoft.navigator.ui;

import com.feathersoft.navigator.R;
import com.feathersoft.navigator.config.NavigatorMenuOption;
import com.feathersoft.navigator.config.UIConfiguration;
import com.feathersoft.navigator.ui.fragment.NavigatorContentFragment;
import com.feathersoft.navigator.ui.fragment.NavigatorMenuFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by GrzegorzFeathers on 2/17/15.
 */
public abstract class NavigatorActivity extends ActionBarActivity implements Toolbar.OnMenuItemClickListener {

    public static final String TAG = NavigatorActivity.class.getSimpleName();
    public static final int DEFAULT_FRAGMENT_TRANSITION = FragmentTransaction.TRANSIT_FRAGMENT_FADE;

    private UIConfiguration mUIConfiguration;
    private NavigatorMenuFragment mMenuFragment;

    public NavigatorActivity(UIConfiguration uiConfiguration){
        this.mUIConfiguration = uiConfiguration == null ?
                UIConfiguration.defaultUIConfig : uiConfiguration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);
        this.setupHome();
    }

    private void setupHome(){
        this.mMenuFragment = (NavigatorMenuFragment) this.getSupportFragmentManager()
                .findFragmentByTag(NavigatorMenuFragment.TAG);

        ActionBar ab = this.getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        this.pushToStack(this.mUIConfiguration.getDefaultMenuOption()
                                 .getContentClass(), null, -1, false);
    }

    public void setSubtitle(int subtitleRes){
        this.getSupportActionBar().setSubtitle(subtitleRes);
    }

    public void onHomeMenuOptionSelected(NavigatorMenuOption menuOption) {
        if(menuOption.equals(this.mUIConfiguration.getDefaultMenuOption())){
            this.clearStack();
        } else {
            this.replaceStack(menuOption.getContentClass(), null);
        }

        this.supportInvalidateOptionsMenu();
    }

    public void addOnDrawerSlideListener(NavigatorMenuFragment.OnDrawerSlideListener listener) {
        this.mMenuFragment.addOnDrawerSlideListener(listener);
    }

    public void removeOnDrawerSlideListener(NavigatorMenuFragment.OnDrawerSlideListener listener) {
        this.mMenuFragment.removeOnDrawerSlideListener(listener);
    }

    public NavigatorMenuOption[] getMenuOptions(){
        return this.mUIConfiguration.getMenuOptions();
    }

    public NavigatorMenuOption getDefaultOption(){
        return this.mUIConfiguration.getDefaultMenuOption();
    }

    public void clearStack(){
        FragmentManager fm = this.getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void replaceStack(Class<? extends Fragment> newContentClass, Bundle extras){
        this.clearStack();
        this.pushToStack(newContentClass, extras);
    }

    public void pushToStack(Fragment fragment, String tag){
        this.pushToStack(fragment, tag, DEFAULT_FRAGMENT_TRANSITION);
    }

    public void pushToStack(Fragment fragment, String tag, int transition){
        this.pushToStack(fragment, tag, transition, true);
    }

    private void pushToStack(Fragment fragment, String tag, int transition, boolean addToBackStack){
        FragmentManager fm = this.getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment, tag);

        if(transition > 0){
            transaction.setTransition(transition);
        }
        if(addToBackStack){
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    private void pushToStack(Class<? extends Fragment> fragmentClass, Bundle extras,
                             int transition, boolean addToBackStack){
        Fragment fragment = this.getFragmentInstance(fragmentClass, extras);

        if(fragment == null){
            Log.d(TAG, "Unable to create the fragment instance");
            return;
        }

        this.pushToStack(fragment, fragmentClass.getName(), transition, addToBackStack);
    }

    public void pushToStack(Class<? extends Fragment> fragmentClass, Bundle extras, int transition){
        this.pushToStack(fragmentClass, extras, transition, true);
    }

    public void pushToStack(Class<? extends Fragment> fragmentClass, Bundle extras) {
        this.pushToStack(fragmentClass, extras, DEFAULT_FRAGMENT_TRANSITION);
    }

    public void singlePop(){
        FragmentManager fm = this.getSupportFragmentManager();
        fm.popBackStack();
    }

    private Fragment getFragmentInstance(Class<? extends Fragment> fragmentClass, Bundle extras){
        Fragment fragment = null;

        try {
            fragment = fragmentClass.newInstance();
            if(extras != null){
                fragment.setArguments(extras);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fragment;
    }

    @Override
    public void onBackPressed() {
        if(this.mMenuFragment != null && this.mMenuFragment.isDrawerOpen()){
            this.mMenuFragment.closeDrawer();
        } else if(this.getCurrentVisibleContent() != null){
            Fragment currentContent = this.getCurrentVisibleContent();
            if(!((NavigatorContentFragment) currentContent).onBackPressed()){
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return this.onOptionsItemSelected(menuItem);
    }

    private Fragment getCurrentVisibleContent(){
        FragmentManager fm = this.getSupportFragmentManager();
        return fm.findFragmentById(R.id.container);
    }

}