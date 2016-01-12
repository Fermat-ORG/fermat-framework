package com.bitdubai.android_core.app.common.version_1.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.List;

/**
     * Tabs adapter
     */
    public class TabsPagerAdapter extends FragmentStatePagerAdapter {


    private String onlyFragment;
    private String[] titles;

        private Context context;
        private FermatFragmentFactory fragmentFactory;
        private TabStrip tabStrip;
        private FermatSession fermatSession;
        private ResourceProviderManager resourcesProviderManager;




    public TabsPagerAdapter(FragmentManager fm,Context context,Activity activity,FermatSession subAppSession,ErrorManager errorManager,FermatFragmentFactory subAppFragmentFactory,SubAppResourcesProviderManager subAppResourcesProviderManager) {
            super(fm);
            this.context=context;
            this.fermatSession = subAppSession;
            tabStrip=activity.getTabStrip();
            this.fragmentFactory =subAppFragmentFactory;
            this.resourcesProviderManager = subAppResourcesProviderManager;
            if(activity.getTabStrip() != null){
                List<Tab> titleTabs = activity.getTabStrip().getTabs();
                titles = new String[titleTabs.size()];
                for (int i = 0; i < titleTabs.size(); i++) {
                    Tab tab = titleTabs.get(i);
                    titles[i] = tab.getLabel();
                }
            }

        }

    public TabsPagerAdapter(FragmentManager fragmentManager, Context applicationContext, FermatFragmentFactory subAppFragmentFactory, String fragment, FermatSession subAppsSession, SubAppResourcesProviderManager subAppResourcesProviderManager) {
        super(fragmentManager);
        this.context=applicationContext;
        this.fragmentFactory =subAppFragmentFactory;
        this.fermatSession = subAppsSession;
        this.onlyFragment = fragment;
        this.resourcesProviderManager = subAppResourcesProviderManager;


    }

        public TabsPagerAdapter(FragmentManager fm,Context context,FermatFragmentFactory walletFragmentFactory,TabStrip tabStrip,FermatSession walletSession,WalletResourcesProviderManager walletResourcesProviderManager) {
            super(fm);
            this.context=context;
            this.fermatSession=walletSession;
            this.fragmentFactory = walletFragmentFactory;
            this.tabStrip=tabStrip;
            this.resourcesProviderManager =walletResourcesProviderManager;
            if(tabStrip != null){
                List<Tab> titleTabs = tabStrip.getTabs();
                titles = new String[titleTabs.size()];
                for (int i = 0; i < titleTabs.size(); i++) {
                    Tab tab = titleTabs.get(i);
                    titles[i] = tab.getLabel();
                }
            }

        }

    public TabsPagerAdapter(FragmentManager fm,Context context,FermatFragmentFactory walletFragmentFactory,String fragment ,FermatSession walletSession,WalletResourcesProviderManager walletResourcesProviderManager) {
        super(fm);
        this.context=context;
        this.fermatSession=walletSession;
        this.fragmentFactory = walletFragmentFactory;
        this.tabStrip=null;
        this.onlyFragment = fragment;
        this.resourcesProviderManager =walletResourcesProviderManager;

    }

    public TabsPagerAdapter(FragmentManager fragmentManager,
                            Context applicationContext,
                            FermatFragmentFactory fermatFragmentFactory,
                            TabStrip tabStrip,
                            FermatSession fermatSession,
                            ResourceProviderManager resourceProviderManager) {
        super(fragmentManager);
        this.context=applicationContext;
        this.fermatSession=fermatSession;
        this.fragmentFactory = fermatFragmentFactory;
        this.tabStrip=tabStrip;
        this.resourcesProviderManager =resourceProviderManager;

        if(tabStrip != null){
            List<Tab> titleTabs = tabStrip.getTabs();
            titles = new String[titleTabs.size()];
            for (int i = 0; i < titleTabs.size(); i++) {
                Tab tab = titleTabs.get(i);
                titles[i] = tab.getLabel();
            }
        }
    }


    public void destroyItem(android.view.ViewGroup container, int position, Object object) {
            FragmentManager manager = ((Fragment) object).getFragmentManager();
            if(manager != null) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.detach((Fragment) object);
                trans.remove((Fragment) object);
                trans.commit();


            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            if(titles.length>0) {
                title = titles[position];
            }
            return title;

        }

        @Override
        public int getCount() {

            if (titles != null)
                return titles.length;
            else if (onlyFragment!=null){
                return 1;
            } else
                return 0;
        }

        @Override
        public Fragment getItem(int position) {
            String fragmentCodeType = null;
            Fragment currentFragment = null;
            if(tabStrip!=null) {
                List<Tab> titleTabs = tabStrip.getTabs();
                for (int j = 0; j < titleTabs.size(); j++) {
                    if (j == position) {
                        Tab tab = titleTabs.get(j);
                        fragmentCodeType = tab.getFragment().getKey();
                        break;
                    }
                }
            }else{
                fragmentCodeType = onlyFragment;
            }
            try {
                if(fragmentFactory !=null){
                    currentFragment= fragmentFactory.getFragment(fragmentCodeType, fermatSession, resourcesProviderManager);
                }
            } catch (FragmentNotFoundException e) {
                e.printStackTrace();
            }
            return currentFragment;
        }


    @Override
    public Parcelable saveState() {
        return null;
    }

}