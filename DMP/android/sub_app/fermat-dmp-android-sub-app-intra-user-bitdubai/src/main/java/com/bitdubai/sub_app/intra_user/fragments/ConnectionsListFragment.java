package com.bitdubai.sub_app.intra_user.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.intra_user.common.models.WalletStoreListItem;
import com.bitdubai.sub_app.intra_user.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user.util.CommonLogger;

import java.util.ArrayList;

/**
 * Created by Matias Furszyfer on 2015.08.31..
 */

public class ConnectionsListFragment extends FermatListFragment<IntraUserInformation> implements FermatListItemListeners<WalletStoreListItem> {

    IntraUserModuleManager intraUserModuleManager;
    private ErrorManager errorManager;
    private ArrayList<IntraUserInformation> catalogueItemList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // setting up  module
            intraUserModuleManager = ((IntraUserSubAppSession) subAppsSession).getIntraUserModuleManager();
            errorManager = subAppsSession.getErrorManager();
            catalogueItemList = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    /**
     * Determine if this fragment use menu
     *
     * @return true if this fragment has menu, otherwise false
     */
    @Override
    protected boolean hasMenu() {
        return false;
    }

    /**
     * Get layout resource
     *
     * @return int layout resource Ex: R.layout.fragment_view
     */
    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return 0;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return 0;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return false;
    }


    @Override
    public ArrayList<IntraUserInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<WalletStoreListItem> data;

        try {
            //WalletStoreCatalogue catalogue = moduleManager.getCatalogue();
            //List<WalletStoreCatalogueItem> catalogueItems = catalogue.getWalletCatalogue(0, 0);

//            data = new ArrayList<>();
//            for (WalletStoreCatalogueItem catalogItem : catalogueItems) {
//                WalletStoreListItem item = new WalletStoreListItem(catalogItem, getResources());
//                data.add(item);
//            }

        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

            data = WalletStoreListItem.getTestData(getResources());
        }

        //return data;
        return null;
    }

    /**
     * onItem click listener event
     *
     * @param data
     * @param position
     */
    @Override
    public void onItemClickListener(WalletStoreListItem data, int position) {

    }

    /**
     * On Long item Click Listener
     *
     * @param data
     * @param position
     */
    @Override
    public void onLongItemClickListener(WalletStoreListItem data, int position) {

    }

    /**
     * implement this function to handle the result object through dynamic array
     *
     * @param result array of native object (handle result field with result[0], result[1],... result[n]
     */
    @Override
    public void onPostExecute(Object... result) {

    }

    /**
     * Implement this function to handle errors during the execution of any fermat worker instance
     *
     * @param ex Throwable object
     */
    @Override
    public void onErrorOccurred(Exception ex) {

    }

    @Override
    public FermatAdapter getAdapter() {
        return null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }
}
