package com.bitdubai.android_core.app.shell.version_1.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.bitdubai.android_core.app.common.version_1.fragment.NavigationDrawerFragment;
import com.bitdubai.android_core.app.subapp.shop.version_1.activity.ShopActivity;

import com.bitdubai.smartwallet.R;
import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;

import com.bitdubai.wallet_manager.wallet.manager.fragment.WalletDesktopFragment;
//import com.bitdubai.android_core.app.subapp.shop_manager.version_1.fragment.ShopDesktopFragment;
//import com.bitdubai.android_core.app.subapp.wallet_store.version_1.activity.StoreFrontActivity;

import android.support.v4.app.FragmentActivity;

import java.util.List;
import java.util.Vector;

public class DesktopActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setActivityId("DesktopActivity");
        setContentView(R.layout.shell_activity_wallet_desktop);
        getActionBar().hide();
       /* if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new WalletDesktopFragment())
                    .commit();
        }*/
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), null);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        ((MyApplication) this.getApplication()).setDefaultTypeface(tf);

        this.initialisePaging();
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        try
        {
            List<Fragment> fragments = new Vector<Fragment>();
            fragments.add(Fragment.instantiate(this, WalletDesktopFragment.class.getName()));
           // fragments.add(Fragment.instantiate(this, ShopDesktopFragment.class.getName()));

            this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
            //
            ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
            pager.setAdapter(this.mPagerAdapter);

            pager.setBackgroundResource(R.drawable.background_tiled_diagonal_light);


        }catch (Exception ex) {
            String strError = ex.getMessage();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
       // if (pageIndex == 0)
      //      getMenuInflater().inflate(R.menu.wallet_manager_desktop_activity_menu, menu);
       // else
       //     getMenuInflater().inflate(R.menu.shell_shop_desktop_fragment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_wallet_store) {

           // Intent intent;
            //intent = new Intent(this, StoreFrontActivity.class);
            //startActivity(intent);

            return true;
        }

       /* if (id == R.id.action_shop) {

            Intent intent;
            intent = new Intent(this, ShopActivity.class);
            startActivity(intent);

            return true;
        }*/

        if (id == R.id.action_file) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void onWalletClicked(View v) {
        String tagId="3";

        tagId = v.getTag().toString();

        if (Integer.parseInt(tagId) > 4)
        {
            Toast.makeText(getApplicationContext(), "This part of the prototype is not ready yet",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intent;

          //  intent = new Intent(this, FrameworkActivity.class);

            // Intent i=getIntent();
            // String walletId =i.getStringExtra( "Wallet Id");


            ((MyApplication) this.getApplication()).setWalletId(Integer.parseInt(tagId));

            // intent.putExtra("Wallet Id", tag );
           // startActivity(intent);
        }
    }


    public void onShopClicked(View v) {
        String tagId="";

        tagId = v.getTag().toString();
        Intent intent;
        intent = new Intent(this, ShopActivity.class);

        // ((MyApplication) this.getApplication()).setWalletId(Integer.parseInt(tagId));

        // intent.putExtra("Wallet Id", tag );
        startActivity(intent);



    }

    //***
    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}
