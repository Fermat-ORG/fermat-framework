package com.bitdubai.sub_app.wallet_store.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantGetRefinedCatalogException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantGetWalletsFromCatalogueException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.DatailedInformationNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogue;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogueItem;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.wallet_store.common.adapters.WalletStoreCatalogueAdapter;
import com.bitdubai.sub_app.wallet_store.common.models.CatalogueItemDao;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment que luce como un Activity donde se muestra la lista de Wallets disponibles en el catalogo de la Wallet Store
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class MainActivityFragment extends FermatListFragment  {
    // STATIC
    private static final String ARG_POSITION = "position";

    // MANAGERS
    private WalletStoreModuleManager moduleManager;
    private ErrorManager errorManager;

    // SESSION
    private WalletStoreSubAppSession session;


    /**
     * Create a new instance of this fragment
     *
     * @param position tab position
     * @param session  WalletStoreSubAppSession instance object. This contains references to WalletStoreModuleManager and ErrorManager
     * @return InstalledFragment instance object
     */
    public static MainActivityFragment newInstance(int position, WalletStoreSubAppSession session) {
        MainActivityFragment f = new MainActivityFragment();

        f.setSession(session);

        WalletStoreModuleManager moduleManager = session.getWalletStoreModuleManager();
        f.setModuleManager(moduleManager);

        ErrorManager errorManager = session.getErrorManager();
        f.setErrorManager(errorManager);

        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        f.setArguments(args);

        return f;
    }


    /**
     * Set module moduleManager
     *
     * @param moduleManager WalletStoreModuleManager object
     */
    public void setModuleManager(WalletStoreModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }


    /**
     * Set session of this subapp
     *
     * @param session WalletStoreSubAppSession object
     */
    public void setSession(WalletStoreSubAppSession session) {
        this.session = session;
    }

    /**
     * Set the error manager to handle the errors of this subapp
     *
     * @param errorManager ErrorManager object
     */
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.wallet_store_fragment_main_activity;
    }

    @Override
    public RecyclerView getRecycler(View rootView) {
        if (recyclerView == null) {
            recyclerView = (RecyclerView) rootView.findViewById(R.id.catalog_recycler_view);
        }
        return recyclerView;
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            ArrayList<CatalogueItemDao> data = CatalogueItemDao.getTestData(getResources());

            try {
                WalletStoreCatalogue catalogue = moduleManager.getCatalogue();
                List<WalletStoreCatalogueItem> catalogueItems = catalogue.getWalletCatalogue(0, 0);
                data = CatalogueItemDao.getDataFromCatalogueItemList(catalogueItems);

            } catch (CantGetRefinedCatalogException e) {
                Log.e("NELSON", "CantGetRefinedCatalogException", e);
                errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

            } catch (NullPointerException e) {
                Log.e("NELSON", "NullPointerException", e);
                errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

            } catch (CantGetWalletsFromCatalogueException e) {
                Log.e("NELSON", "CantGetWalletsFromCatalogueException", e);
                errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            } catch (CantGetWalletIconException e) {
                Log.e("NELSON", "CantGetWalletIconException", e);
                errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

            } catch (DatailedInformationNotFoundException e) {
                Log.e("NELSON", "DatailedInformationNotFoundException", e);
                errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }

            adapter = new WalletStoreCatalogueAdapter(getActivity(), data);
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        return layoutManager;
    }

//    @Override
//    public void itemClicked(int itemPos) {
//        CatalogueItemDao item = (CatalogueItemDao) adapter.getItem(itemPos);
//        if (item != null){
//            session.setCatalogItemDao(item);
//            DetailsActivityFragment fragment = DetailsActivityFragment.newInstance(0, session);
//
//            FragmentTransaction FT = this.getFragmentManager().beginTransaction();
//            FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            FT.replace(R.id.activity_container, fragment);
//            FT.addToBackStack(null);
//            FT.commit();
//        }
//    }
}
