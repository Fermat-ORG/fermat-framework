package com.bitdubai.android_core.app.common.version_1.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettingsManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettingsManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.List;

/**
     * Tabs adapter
     */
    public class TabsPagerAdapter extends FragmentPagerAdapter {



    private String[] titles;


        private Context context;

        private Activity activity;

        private WalletFragmentFactory walletFragmentFactory;

        private TabStrip tabStrip;


        private WalletSession walletSession;

        private Platform platform;
        private ErrorManager errorManager;

        private WalletSettingsManager walletSettingsManager;

        private WalletResourcesProviderManager walletResourcesProviderManager;

        private SubAppFragmentFactory subAppFragmentFactory;

        private SubAppSettingsManager subAppSettingsManager;

        private SubAppResourcesProviderManager subAppResourcesProviderManager;

        private SubAppsSession subAppsSession;


    public TabsPagerAdapter(FragmentManager fm,Context context,Activity activity,SubAppsSession SubAppSession,ErrorManager errorManager,SubAppFragmentFactory subAppFragmentFactory,SubAppSettingsManager subAppSettingsManager,SubAppResourcesProviderManager subAppResourcesProviderManager) {
            super(fm);
            this.context=context;


            this.subAppsSession = SubAppSession;
            //this.walletSessionManager=applicationSession.getWalletSessionManager();
            //this.subAppSessionManager=applicationSession.getSubAppSessionManager();
            //this.platform=applicationSession.getFermatPlatform();
        this.errorManager=errorManager;
            this.activity=activity;
            tabStrip=activity.getTabStrip();
            this.subAppFragmentFactory=subAppFragmentFactory;
            this.subAppSettingsManager = subAppSettingsManager;
            this.subAppResourcesProviderManager = subAppResourcesProviderManager;


            if(activity.getTabStrip() != null){
                List<Tab> titleTabs = activity.getTabStrip().getTabs();
                titles = new String[titleTabs.size()];
                for (int i = 0; i < titleTabs.size(); i++) {
                    Tab tab = titleTabs.get(i);
                    titles[i] = tab.getLabel();
                }
            }

        }

        public TabsPagerAdapter(FragmentManager fm,Context context,WalletFragmentFactory walletFragmentFactory,TabStrip tabStrip,WalletSession walletSession,WalletSettingsManager walletSettingsManager,WalletResourcesProviderManager walletResourcesProviderManager) {
            super(fm);
            this.context=context;

            this.walletSession=walletSession;
            this.errorManager=errorManager;
            this.walletFragmentFactory = walletFragmentFactory;
            this.tabStrip=tabStrip;
            this.walletSettingsManager=walletSettingsManager;
            this.walletResourcesProviderManager =walletResourcesProviderManager;

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

            return titles[position];
        }

        @Override
        public int getCount() {

            if (titles != null)
                return titles.length;
            else
                return 0;
        }

        @Override
        public Fragment getItem(int position) {



            /**
             * SubApp Session
             */
            SubAppsSession subAppSession=null;

            Fragment currentFragment = null;
            Fragments fragmentType = Fragments.CWP_SHELL_LOGIN;
            List<Tab> titleTabs =tabStrip.getTabs();
            for (int j = 0; j < titleTabs.size(); j++) {
                if (j == position)
                {
                    Tab tab = titleTabs.get(j);
                    fragmentType = tab.getFragment();
                    break;
                }
            }



//            if(activity!=null){
//                if(activity.getType()== Activities.CWP_SUB_APP_ALL_DEVELOPER){
//                     subAppSession = subAppSessionManager.openSubAppSession(SubApps.CWP_DEVELOPER_APP,
//                            (ErrorManager) platform.getCorePlatformContext().getAddon(Addons.ERROR_MANAGER),
//                             platform.getCorePlatformContext().getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE));
//                }else if (activity.getType()== Activities.CWP_WALLET_FACTORY_MAIN){
//                     subAppSession = subAppSessionManager.openSubAppSession(SubApps.CWP_WALLET_FACTORY,
//                            (ErrorManager) platform.getCorePlatformContext().getAddon(Addons.ERROR_MANAGER),
//                             platform.getCorePlatformContext().getPlugin(Plugins.BITDUBAI_WALLET_FACTORY_MODULE));
//                }
//            }





            try {
                if(walletFragmentFactory !=null){
                    currentFragment= walletFragmentFactory.getFragment(fragmentType.getKey(), walletSession,walletSettingsManager,walletResourcesProviderManager);
                }
            } catch (FragmentNotFoundException e) {
                e.printStackTrace();
            }


            try {
                if(subAppFragmentFactory !=null){
                    currentFragment= subAppFragmentFactory.getFragment(fragmentType.getKey(),subAppsSession,subAppSettingsManager,subAppResourcesProviderManager);
                }
            } catch (FragmentNotFoundException e) {
                e.printStackTrace();
            }


            /**
             *  Swith for subApps
             */
            //execute current activity fragments
            //try {
                //switch (fragmentType) {
//                    case CWP_SHELL_LOGIN:
//
//                        break;
//                    //developr aap
//                    case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS:
//                        currentFragment = DatabaseToolsFragment.newInstance(position,subAppSession);
//                        break;
//
//                    case CWP_SUB_APP_DEVELOPER_LOG_TOOLS:
//                        currentFragment = LogToolsFragment.newInstance(0,subAppSession);
//                        break;
//                    //wallet store
//                    case CWP_SHOP_MANAGER_MAIN:
//                        currentFragment = AllFragment.newInstance(position,subAppSession);
//                        break;
//                    case CWP_SHOP_MANAGER_FREE:
//                        currentFragment = FreeFragment.newInstance(position,subAppSession);
//                        break;
//                    case CWP_SHOP_MANAGER_PAID:
//                        currentFragment = PaidFragment.newInstance(position,subAppSession);
//                        break;
//                    case CWP_SHOP_MANAGER_ACCEPTED_NEARBY:
//                        currentFragment = AcceptedNearbyFragment.newInstance(position,subAppSession);
//                        break;
//                    //**
//                    case CWP_SUB_APP_DEVELOPER:
//                        currentFragment = SubAppDesktopFragment.newInstance(position);
//                        break;
//                    case CWP_WALLET_FACTORY_MANAGER:
//                        currentFragment = ManagerFragment.newInstance(position,subAppSession);
//                        break;
//                    case CWP_WALLET_FACTORY_PROJECTS:
//                        currentFragment = ProjectsFragment.newInstance(position,subAppSession);
//                        break;
//                    case CWP_WALLET_FACTORY_EDIT_MODE:
//                        currentFragment = EditableWalletFragment.newInstance(position, subAppSession,false,null);
//                        break;
//                    case CWP_WALLET_PUBLISHER_MAIN:
//                        currentFragment = com.bitdubai.sub_app.wallet_publisher.fragment.MainFragment.newInstance(position);
//                        break;


//                }
//
//            }
//            catch(Exception ex)
//            {
//                errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, ex);
//
//                Toast.makeText(context, "Error in ScreenPagerAdapter GetItem " + ex.getMessage(),
//                        Toast.LENGTH_LONG).show();
//            }


            return currentFragment;
        }


    }