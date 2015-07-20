package com.bitdubai.android_core.app.common.version_1.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Activity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Tab;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.BalanceFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactsFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ReceiveFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.SendFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.TransactionsFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsFragment;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragment;
import com.bitdubai.sub_app.manager.fragment.SubAppDesktopFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.MainFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.WalletDesktopFragment;
import com.bitdubai.sub_app.wallet_store.fragment.AcceptedNearbyFragment;
import com.bitdubai.sub_app.wallet_store.fragment.AllFragment;
import com.bitdubai.sub_app.wallet_store.fragment.FreeFragment;
import com.bitdubai.sub_app.wallet_store.fragment.PaidFragment;

import java.util.List;

/**
     * Tabs adapter
     */
    public class TabsPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;


        private Context context;

        private Activity activity;


        public TabsPagerAdapter(FragmentManager fm,Context context,int activityType) {
            super(fm);
            this.context=context;

            if(activityType== FermatActivity.ACTIVITY_TYPE_SUB_APP){
                activity= ApplicationSession.getAppRuntimeMiddleware().getLasActivity();
            }else if(activityType== FermatActivity.ACTIVITY_TYPE_WALLET){
                activity= ApplicationSession.getWalletRuntimeManager().getLasActivity();
            }
            if(activity.getTabStrip() != null){
                List<Tab> titleTabs = activity.getTabStrip().getTabs();
                titles = new String[titleTabs.size()];
                for (int i = 0; i < titleTabs.size(); i++) {
                    Tab tab = titleTabs.get(i);
                    titles[i] = tab.getLabel();
                }
            }

        }


        public void destroyItem(android.view.ViewGroup container, int position, Object object) {

            FragmentManager manager = ((android.support.v4.app.Fragment) object).getFragmentManager();
            if(manager != null) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.detach((android.support.v4.app.Fragment) object);
                trans.remove((android.support.v4.app.Fragment) object);
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
        public android.support.v4.app.Fragment getItem(int position) {

            com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform bitcoinPlatform = null;
            com.bitdubai.sub_app.developer.fragment.Platform developerPlatform = null;

            android.support.v4.app.Fragment currentFragment = null;
            Fragments fragmentType = Fragments.CWP_SHELL_LOGIN;
            List<Tab> titleTabs =activity.getTabStrip().getTabs();
            for (int j = 0; j < titleTabs.size(); j++) {
                if (j == position)
                {
                    Tab tab = titleTabs.get(j);
                    fragmentType = tab.getFragment();
                    break;
                }
            }
            //execute current activity fragments
            try {
                switch (fragmentType) {
                    case CWP_SHELL_LOGIN:

                        break;
                    case CWP_WALLET_MANAGER_MAIN:
                        currentFragment =  WalletDesktopFragment.newInstance(position);
                        break;


                    /**
                     * Executing fragments for BITCOIN WALLET.
                     */

                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
                        bitcoinPlatform.setErrorManager((ErrorManager)ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        currentFragment =  BalanceFragment.newInstance(0);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
                        bitcoinPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        currentFragment = ReceiveFragment.newInstance(0);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
                        bitcoinPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        currentFragment =  SendFragment.newInstance(0);
                        break;

                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
                        bitcoinPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        currentFragment =  TransactionsFragment.newInstance(0);

                        break;
                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
                        bitcoinPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        currentFragment =  ContactsFragment.newInstance(0);
                        break;
                    //developr aap
                    case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS:
                        developerPlatform = new com.bitdubai.sub_app.developer.fragment.Platform();
                        developerPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        developerPlatform.setToolManager((ToolManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER));
                        currentFragment = DatabaseToolsFragment.newInstance(position);
                        break;

                    case CWP_SUB_APP_DEVELOPER_LOG_TOOLS:
                        developerPlatform = new com.bitdubai.sub_app.developer.fragment.Platform();
                        developerPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        developerPlatform.setToolManager((ToolManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER));
                        currentFragment = LogToolsFragment.newInstance(0);
                        break;
                    //wallet store
                    case CWP_SHOP_MANAGER_MAIN:
                        currentFragment = AllFragment.newInstance(position);
                        break;
                    case CWP_SHOP_MANAGER_FREE:
                        currentFragment = FreeFragment.newInstance(position);
                        break;
                    case CWP_SHOP_MANAGER_PAID:
                        currentFragment = PaidFragment.newInstance(position);
                        break;
                    case CWP_SHOP_MANAGER_ACCEPTED_NEARBY:
                        currentFragment = AcceptedNearbyFragment.newInstance(position);
                        break;
                    //**
                    case CWP_SUB_APP_DEVELOPER:
                        currentFragment = SubAppDesktopFragment.newInstance(position);
                        break;

                    case CWP_WALLET_FACTORY_MAIN:
                        currentFragment = MainFragment.newInstance(position);
                        break;
                    case CWP_WALLET_PUBLISHER_MAIN:
                        currentFragment = com.bitdubai.sub_app.wallet_publisher.fragment.MainFragment.newInstance(position);
                        break;


                }

            }
            catch(Exception ex)
            {
                ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, ex);

                Toast.makeText(context, "Error in PagerAdapter GetItem " + ex.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

            return currentFragment;
        }

    }