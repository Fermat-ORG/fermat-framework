package com.bitdubai.android_core.app.common.version_1.navigation_drawer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;

import java.util.ArrayList;
import java.util.List;


// TODO; RAUL;  Esta clase fue tomada de algun lado para implementar el navigation drawer. Me imagino que no hay mucho que tocar, quizas ponerla mas prolija, ver el tema de las excepciones, etc.

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements AdapterView.OnItemClickListener {


    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    private List<String> menuOption;
    private  List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> menuItem = new ArrayList<>();
    private Activity context;

    private NavigationDrawerArrayAdapter adapter;

    public static NavigationDrawerFragment newInstance(Activity context) {
        NavigationDrawerFragment navigationDrawerFragment = new NavigationDrawerFragment();
        navigationDrawerFragment.setContext(context);
        return navigationDrawerFragment;
    }

    public NavigationDrawerFragment() {
    }



 //   public NavigationDrawerArrayAdapter getmAdapter() {
      //  return mAdapter;
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            // Read in the flag indicating whether or not the user has demonstrated awareness of the
            // drawer. See PREF_USER_LEARNED_DRAWER for details.
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
            mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

            if (savedInstanceState != null) {
                mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
                mFromSavedInstanceState = true;
            }


            // Select either the default item (0) or the last selected item.
            //selectItem(mCurrentSelectedPosition);
        }
        catch (Exception e){
            throw e;
        }
    }





    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {


            if(mDrawerListView==null) {

                mDrawerListView = (ListView) inflater.inflate(
                        R.layout.wallet_framework_fragment_navigation_drawer, container, false);

                mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectItem(position);
                    }
                });
            }


            menuOption = new ArrayList<String>();

            if(mDrawerListView!=null) {

                adapter = new NavigationDrawerArrayAdapter(
                        getActivity(),
                        menuOption,
                        null);

                mDrawerListView.setAdapter(adapter);



                adapter.notifyDataSetChanged();

                mDrawerListView.invalidate();


            }

          //  mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

        }
        catch (Exception e) {
            throw e;
        }

        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        try{
            if(mDrawerLayout != null) {
                if (mDrawerLayout.isDrawerOpen(mFragmentContainerView)) {
                    return true;
                }
            }
        }catch (Exception e){

        }
        return  false;
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout,SideMenu sideMenu) {

        //create menu option based activity submenu definition
        try {

            menuOption = new ArrayList<String>();

            if (sideMenu != null) {
                menuItem = sideMenu.getMenuItems();
                for (int i = 0; i < menuItem.size(); i++) {

                    com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem menu = menuItem.get(i);
                    menuOption.add(menu.getLabel());
                }

            }

            if(mDrawerListView!=null)

            mDrawerListView.setAdapter(new NavigationDrawerArrayAdapter(
                    getActivity(),
                    menuOption,null));

            mFragmentContainerView = getActivity().findViewById(fragmentId);
            mDrawerLayout = drawerLayout;

            // set a custom shadow that overlays the main content when the drawer opens
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
            // set up the drawer's list view with items and click listener

            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            // ActionBarDrawerToggle ties together the the proper interactions
            // between the navigation drawer and the action bar app icon.
            mDrawerToggle = new ActionBarDrawerToggle(
                    getActivity(),                    /* host Activity */
                    mDrawerLayout,                    /* DrawerLayout object */
                    R.drawable.ic_actionbar_menu_,             /* nav drawer image to replace 'Up' caret */
                    R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                    R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
            ) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    if (!isAdded()) {
                        return;
                    }

                    getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    if (!isAdded()) {
                        return;
                    }

                    if (!mUserLearnedDrawer) {
                        // The user manually opened the drawer; store this flag to prevent auto-showing
                        // the navigation drawer automatically in the future.
                        mUserLearnedDrawer = true;
                        SharedPreferences sp = PreferenceManager
                                .getDefaultSharedPreferences(getActivity());
                        sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                    }

                    getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                }
            };

            // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
            // per the navigation drawer design guidelines.
            if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
                mDrawerLayout.openDrawer(mFragmentContainerView);
            }

            // Defer code dependent on restoration of previous instance state.
            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });

            mDrawerLayout.setDrawerListener(mDrawerToggle);
        }
        catch (Exception e) {
            throw e;
        }
    }

    private void selectItem(int position) {

        try {


            mCurrentSelectedPosition = position;
            if (mDrawerListView != null) {
                //mDrawerListView.setItemChecked(position, true);
            }
            if (mDrawerLayout != null) {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
            if (mCallbacks != null) {

                if(menuOption!=null) {
                    com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem menuItem = this.menuItem.get(position);
                    if (menuItem != null)
                        try {
                            mCallbacks.onNavigationDrawerItemSelected(position, menuItem.getLinkToActivity().getCode());
                        } catch (Exception e){
                            e.printStackTrace();
                        }
               }

            }
        }
        catch (Exception e) {
            throw e;
        }

    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw e;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
        mFragmentContainerView = null;
        onPause();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }






    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the wallet_framework_activity_framework_drawer_open_menu app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        try {

            if (mDrawerLayout != null && isDrawerOpen()) {
                inflater.inflate(R.menu.wallet_framework_activity_framework_drawer_open_menu, menu);
                showGlobalContextActionBar();
            }
            super.onCreateOptionsMenu(menu, inflater);



        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try
        {
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }



            return super.onOptionsItemSelected(item);

        }
        catch (Exception e)
        {
            throw e;
        }

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (mDrawerLayout != null && isDrawerOpen()) {
            menu.clear();
        }
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the wallet_framework_activity_framework_drawer_open_menu app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        //actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position,String activityCode);
    }

    public void changeNavigationDrawerAdapter(ListAdapter adapter){
        mDrawerListView.setAdapter( adapter);
        mDrawerListView.deferNotifyDataSetChanged();
        mDrawerListView.invalidate();
        //
    };

//    public boolean equals(navigationDrawerFragment navigationDrawer){
//
//    }
}
