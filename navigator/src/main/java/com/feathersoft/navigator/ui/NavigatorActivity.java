package com.feathersoft.navigator.ui;

import com.feathersoft.navigator.R;
import com.feathersoft.navigator.config.NavigatorMenuOption;
import com.feathersoft.navigator.config.UIConfiguration;
import com.feathersoft.navigator.ui.fragment.NavigatorContentFragment;
import com.feathersoft.navigator.ui.fragment.NavigatorMenuFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by GrzegorzFeathers on 2/17/15.
 */
public abstract class NavigatorActivity extends ActionBarActivity implements Toolbar.OnMenuItemClickListener {

    public static final String TAG = NavigatorActivity.class.getSimpleName();

    private static final String ARG_CURRENT_MENU_ITEM = "arg_current_menu_item";

    public static final int DEFAULT_FRAGMENT_TRANSITION = FragmentTransaction.TRANSIT_FRAGMENT_FADE;

    private UIConfiguration mUIConfiguration;
    private NavigatorMenuFragment mMenuFragment;
    private NavigatorMenuOption mCurrentSelection = null;

    public NavigatorActivity(UIConfiguration uiConfiguration){
        this.mUIConfiguration = uiConfiguration == null ?
                UIConfiguration.defaultUIConfig : uiConfiguration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);
        this.setupActivityState(savedInstanceState);
        this.setupHome();
    }

    protected abstract @Nullable RecyclerView.ViewHolder onCreateMenuHeader();

    @Override
    protected void onPostResume() {
        super.onPostResume();
        this.mMenuFragment.setMenuHeader(this.onCreateMenuHeader());
    }

    private void setupActivityState(Bundle bundle){
        if(bundle == null){
            this.mCurrentSelection = this.mUIConfiguration.getDefaultMenuOption();
            return;
        }

        this.mCurrentSelection = bundle.containsKey(ARG_CURRENT_MENU_ITEM)
                && this.mUIConfiguration.findMenuOptionById(bundle.getInt(ARG_CURRENT_MENU_ITEM)) != null
                    ? this.mUIConfiguration.findMenuOptionById(bundle.getInt(ARG_CURRENT_MENU_ITEM))
                    : this.mUIConfiguration.getDefaultMenuOption();
    }

    private void setupHome(){
        this.mMenuFragment = (NavigatorMenuFragment) this.getSupportFragmentManager()
                .findFragmentByTag(NavigatorMenuFragment.TAG);

        ActionBar ab = this.getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        this.pushToStack(this.mUIConfiguration.getDefaultMenuOption().getContentClass(), null, -1, false);
        if(!this.mCurrentSelection.equals(this.mUIConfiguration.getDefaultMenuOption())){
            this.onHomeMenuOptionSelected(this.mCurrentSelection);
        }
    }

    public void setSubtitle(int subtitleRes){
        this.getSupportActionBar().setSubtitle(subtitleRes);
    }

    public void onHomeMenuOptionSelected(NavigatorMenuOption menuOption) {
        this.mCurrentSelection = menuOption;
        if(this.mCurrentSelection.equals(this.mUIConfiguration.getDefaultMenuOption())){
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
        Log.d(TAG, "mMenuFragment: " + this.mMenuFragment);
        if(this.mMenuFragment != null && this.mMenuFragment.isDrawerOpen()){
            this.mMenuFragment.closeDrawer();
        } else if(this.getCurrentVisibleContent() != null){
            Fragment currentContent = this.getCurrentVisibleContent();
            if(!((NavigatorContentFragment) currentContent).onBackPressed()){
                super.onBackPressed();
                Class<? extends NavigatorContentFragment> currentContentClass =
                        this.getCurrentVisibleContent() != null ? null :
                        (Class<? extends NavigatorContentFragment>) this.getCurrentVisibleContent().getClass();
                this.mCurrentSelection = this.mUIConfiguration.findMenuOptionByContentClass(currentContentClass) != null
                        ? this.mUIConfiguration.findMenuOptionByContentClass(currentContentClass)
                        : this.mUIConfiguration.getDefaultMenuOption();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        this.clearStack();
        outState.putInt(ARG_CURRENT_MENU_ITEM, this.mCurrentSelection.getItemId());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}