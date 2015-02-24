package com.feathersoft.navigator.ui.fragment;

import com.feathersoft.navigator.R;
import com.feathersoft.navigator.ui.NavigatorActivity;
import com.feathersoft.navigator.ui.adapter.NavigatorAdapter;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class NavigatorMenuFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = NavigatorMenuFragment.class.getSimpleName();

    private View mRootView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerMenuOptionsView;
    private NavigatorAdapter mRecyclerMenuOptionsAdapter;
    private RecyclerView.LayoutManager mRecyclerMenuOptionsManager;
    private ActionBarDrawerToggle mDrawerToogle;

    private List<OnDrawerSlideListener> mDrawerListeners;

    public NavigatorMenuFragment(){
        this.mDrawerListeners = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.fragment_home_menu, container, false);

        this.mRecyclerMenuOptionsView = (RecyclerView)
                this.mRootView.findViewById(R.id.recycler_menu_options);
        this.mRecyclerMenuOptionsView.setHasFixedSize(true);

        this.mRecyclerMenuOptionsManager = new LinearLayoutManager(this.getMenuHostActivity());
        this.mRecyclerMenuOptionsAdapter = new NavigatorAdapter(this.getMenuHostActivity().getMenuOptions(), this);

        this.mRecyclerMenuOptionsView.setLayoutManager(this.mRecyclerMenuOptionsManager);
        this.mRecyclerMenuOptionsView.setAdapter(this.mRecyclerMenuOptionsAdapter);

        return this.mRootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mDrawerLayout = (DrawerLayout)
                this.getMenuHostActivity().findViewById(R.id.drawer_home);
        //this.mDrawerLayout.setScrimColor(Color.parseColor("#30000000"));
        this.mDrawerLayout.setScrimColor(this.getResources().getColor(android.R.color.transparent));
        this.mDrawerToogle = new ActionBarDrawerToggle(
                this.getMenuHostActivity(), this.mDrawerLayout,
                R.string.app_name, R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                getMenuHostActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getMenuHostActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                for(OnDrawerSlideListener l : mDrawerListeners){
                    l.onDrawerSlide(slideOffset);
                }
            }
        };
        this.mDrawerLayout.setDrawerListener(this.mDrawerToogle);

        this.mDrawerToogle.syncState();
    }

    public void setMenuHeader(RecyclerView.ViewHolder headerHolder){
        if(this.mRecyclerMenuOptionsAdapter != null){
            this.mRecyclerMenuOptionsAdapter.setHeader(headerHolder);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDrawerToogle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(this.mDrawerToogle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private NavigatorActivity getMenuHostActivity(){
        NavigatorActivity menuHostActivity = null;
        try {
            menuHostActivity = (NavigatorActivity) this.getActivity();
        } catch (ClassCastException e) {
            throw e;
        } finally {
            return menuHostActivity;
        }
    }

    @Override
    public void onClick(View v) {
        this.mDrawerLayout.closeDrawer(Gravity.LEFT);
        int position = this.mRecyclerMenuOptionsView.getChildPosition(v);
        this.getMenuHostActivity().onHomeMenuOptionSelected(
                this.getMenuHostActivity().getMenuOptions()[
                        this.mRecyclerMenuOptionsAdapter.getRealItemPosition(position)]);
    }

    public boolean isDrawerOpen(){
        return this.mDrawerLayout != null && this.mDrawerLayout.isDrawerOpen(R.id.fragment_home_menu);
    }

    public void closeDrawer(){
        if(this.isDrawerOpen()){
            this.mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    public void addOnDrawerSlideListener(OnDrawerSlideListener listener){
        this.mDrawerListeners.add(listener);
    }

    public void removeOnDrawerSlideListener(OnDrawerSlideListener listener){
        this.mDrawerListeners.remove(listener);
    }

    public static interface OnDrawerSlideListener {
        public void onDrawerSlide(float slideOffset);
    }
}