package com.bitdubai.reference_wallet.fan_wallet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.layer.wallet_module.FanWalletPreferenceSettings;
import com.bitdubai.reference_wallet.fan_wallet.R;
import com.bitdubai.reference_wallet.fan_wallet.session.FanWalletSession;


/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class FanWalletMainActivity extends AbstractFermatFragment  {

    //FermatManager
    private FanWalletSession fanwalletSession;
    private FanWalletPreferenceSettings  fanWalletSettings;
    private ErrorManager errorManager;
    private SongFragment songFragment;
    private FollowingFragment followingFragment;

    View view;
    private FragmentTabHost mTabHost;
    Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            fanwalletSession = ((FanWalletSession) appSession);
            errorManager = appSession.getErrorManager();
            System.out.println("HERE START FAN WALLET");

            try {
                    fanWalletSettings =  fanwalletSession.getModuleManager().getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                fanWalletSettings = null;
            }

            if (fanWalletSettings == null) {
                fanWalletSettings = new FanWalletPreferenceSettings();
                fanWalletSettings.setIsPresentationHelpEnabled(true);
                try {
                    fanwalletSession.getModuleManager().getSettingsManager().persistSettings(appSession.getAppPublicKey(), fanWalletSettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }


        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }



    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (toolbar.getMenu() != null) toolbar.getMenu();
    }

    public static FanWalletMainActivity newInstance(){return new FanWalletMainActivity();}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        configureToolbar();
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.fanwallet_background_viewpager);

        /*view= inflater.inflate(R.layout.tky_fan_wallet_activity,container,false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.fanwallet_background_viewpager);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        // Setup the viewPager
        LockableViewPager viewPager = (LockableViewPager) view.findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setSwipeable(false);

        // Setup the Tabs
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        // By using this method the tabs will be populated according to viewPager's count and
        // with the name from the pagerAdapter getPageTitle()
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        // This method ensures that tab selection events update the ViewPager and page changes update the selected tab.
        tabLayout.setupWithViewPager(viewPager);*/

        return view;

    }



    @Override
    public void onUpdateViewUIThred(FermatBundle bundle) {

        System.out.println("TKY_BROAD_MAIN_BUNDLE");

    }

    @Override
    public void onUpdateViewOnUIThread(String code) {

        System.out.println("TKY_BROAD_MAIN_CODE");
    }



 /*   private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: songFragment= SongFragment.newInstance();
                        return songFragment;
                case 1: followingFragment= FollowingFragment.newInstance();
                        return followingFragment;
                default:songFragment= SongFragment.newInstance();
                        return songFragment;
            }


        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0){

                return "Songs";

            }else{

                return "Following";
            }


        }*/
    }







