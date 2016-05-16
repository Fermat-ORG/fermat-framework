package com.bitdubai.sub_app.wallet_manager.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.DesktopHolderClickCallback;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractDesktopFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatActivityManager;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.desktop.Item;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_module.AppManagerSettings;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.interface_objects.FermatFolder;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletsListFailedToLoadException;
import com.bitdubai.sub_app.wallet_manager.adapter.DesktopAdapter;
import com.bitdubai.sub_app.wallet_manager.commons.EmptyItem;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.OnStartDragListener;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.SimpleItemTouchHelperCallback;
import com.bitdubai.sub_app.wallet_manager.popup.FolderDialog;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;
import com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by Matias Furszyfer on 15/09/15.
 */


public class DesktopFragment extends AbstractDesktopFragment<DesktopSession,ResourceProviderManager> implements SearchView.OnCloseListener,
        SearchView.OnQueryTextListener,
        SwipeRefreshLayout.OnRefreshListener,
        OnStartDragListener,
        DesktopHolderClickCallback<Item> {

    private ItemTouchHelper mItemTouchHelper;

    /**
     * MANAGERS
     */
    private  static ErrorManager errorManager;

    private SearchView mSearchView;

    private DesktopAdapter adapter;
    private boolean isStartList;

    // recycler
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private View rootView;

    private String searchName;
    private List<InstalledWallet> lstInstalledWallet;


    ArrayList<Item> lstItems;

    private boolean started=false;
    private List<Item> lstItemsWithIcon;
    SettingsManager<AppManagerSettings> settingsSettingsManager;
    AppManagerSettings appManagerSettings;
    private Handler handler;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static DesktopFragment newInstance() {
        return new DesktopFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstItems = new ArrayList<>();




    }

    /**
     * Fragment Class implementation.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
//            handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                public void run() {
//                    if(appManagerSettings.isHelpEnabled()){
//                        startWizard(WizardTypes.DESKTOP_WELCOME_WIZARD.getKey());
//                    }
//                }
//            }, 500);



            rootView = inflater.inflate(R.layout.desktop_main, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity(), 4, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new DesktopAdapter(getActivity(), lstItems,this,DesktopAdapter.DEKSTOP);
            recyclerView.setAdapter(adapter);
            rootView.setBackgroundColor(Color.TRANSPARENT);

            ((ImageView)rootView.findViewById(R.id.container_title)).setImageResource(R.drawable.wallets_title);

            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(recyclerView);

            //adapter.setFermatListEventListener(this);

        } catch(Exception ex) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
   //         Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();

        }catch (OutOfMemoryError outOfMemoryError){
            outOfMemoryError.printStackTrace();
        }





        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRefresh();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPaintActivtyFeactures().addDesktopCallBack(this);
    }

    private void setUpData() {
        if (appSession.getModuleManager() != null)
            try {
                lstInstalledWallet = appSession.getModuleManager().getInstalledWallets();

            } catch (WalletsListFailedToLoadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        if(lstInstalledWallet!=null)
            for(InstalledWallet installedWallet: lstInstalledWallet){
                if(installedWallet.getWalletPublicKey().equals("reference_wallet")) {
                    Item item = new Item(installedWallet);
                    item.setIconResource(R.drawable.bitcoin_wallet);
                    lstItems.add(item);
                }

            }

        InstalledSubApp installedSubApp = new InstalledSubApp(SubApps.CWP_INTRA_USER_IDENTITY,null,null,"intra_user_identity_sub_app","Intra user Identity","public_key_ccp_intra_user_identity","intra_user_identity_sub_app",new Version(1,0,0), Platforms.CRYPTO_CURRENCY_PLATFORM, AppsStatus.DEV);
        Item item = new Item(installedSubApp);
        item.setIconResource(R.drawable.intra_user_identity);
        lstItems.add(item);

        installedSubApp = new InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY,null,null,"intra_user_community_sub_app","Intra user Community","public_key_intra_user_commmunity","intra_user_community_sub_app",new Version(1,0,0),Platforms.CRYPTO_CURRENCY_PLATFORM, AppsStatus.DEV);
        Item item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.intra_user_community);
        lstItems.add(item1);
    }


    @Override
    public void onRefresh() {
        if(!started) {
            FermatWorker worker = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    return getMoreData();
                }
            };
            worker.setContext(getActivity());
            worker.setCallBack(new FermatWorkerCallBack() {
                @SuppressWarnings("unchecked")
                @Override
                public void onPostExecute(Object... result) {
                    if (result != null &&
                            result.length > 0) {
                        if (getActivity() != null && adapter != null) {
                            lstItems = (ArrayList<Item>) result[0];
                            adapter.changeDataSet(lstItems);
                        }
                    }
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            });
            worker.execute();
            started = true;
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

//        inflater.inflate(R.menu.intra_user_menu, menu);
//
//        //MenuItem menuItem = new SearchView(getActivity());
//        try {
//            MenuItem searchItem = menu.findItem(R.id.action_search);
//            searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
//            //MenuItemCompat.setShowAsAction(searchItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
//            //mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//            mSearchView = (SearchView) searchItem.getActionView();
//            mSearchView.setOnQueryTextListener(this);
//            mSearchView.setSubmitButtonEnabled(true);
//            mSearchView.setOnCloseListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            CharSequence itemTitle = item.getTitle();

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onQueryTextSubmit(String name) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //Toast.makeText(getActivity(), "Probando busqueda completa", Toast.LENGTH_SHORT).show();
        return s.length() == 0 && isStartList;
    }

    @Override
    public boolean onClose() {
        if(!mSearchView.isActivated()){
            //adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));
        }

        return true;
    }



    private synchronized List<Item> getMoreData() {
        List<Item> dataSet = new ArrayList<>();

        try {
            lstItems = new ArrayList<>();

            lstInstalledWallet = appSession.getModuleManager().getInstalledWallets();
            lstItemsWithIcon = new ArrayList<>();
            Item[] arrItemsWithoutIcon = new Item[12];


            for(InstalledWallet installedWallet: lstInstalledWallet) {
                    if(installedWallet.getWalletPublicKey().equals(WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode())) {
                        Item item = new Item(installedWallet);
                        item.setIconResource(R.drawable.bitcoin_wallet);
                        item.setPosition(0);
                        installedWallet.setAppStatus(AppsStatus.ALPHA);
                        lstItemsWithIcon.add(item);

                    }

                if(installedWallet.getWalletPublicKey().equals(WalletsPublicKeys.CWP_LOSS_PROTECTED_WALLET.getCode())) {
                    Item item = new Item(installedWallet);
                    item.setIconResource(R.drawable.icon_loss_protected);
                    item.setPosition(8);
                    installedWallet.setAppStatus(AppsStatus.DEV);
                    lstItemsWithIcon.add(item);
                }
            }

            InstalledWallet installedWallet= new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledWallet(
                    WalletCategory.REFERENCE_WALLET,
                    WalletType.REFERENCE,
                    new ArrayList<InstalledSkin>(),
                    new ArrayList<InstalledLanguage>(),
                    "crypto_broker",
                    "Crypto Broker",
                    WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode(),
                    "wallet_crypto_broker_platform_identifier",
                    new Version(1,0,0),
                    AppsStatus.ALPHA);
            lstInstalledWallet.add(installedWallet);
            Item item = new Item(installedWallet);
            item.setIconResource(R.drawable.crypto_broker);
            item.setPosition(1);
            lstItemsWithIcon.add(item);

            installedWallet= new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledWallet(
                    WalletCategory.REFERENCE_WALLET,
                    WalletType.REFERENCE,
                    new ArrayList<InstalledSkin>(),
                    new ArrayList<InstalledLanguage>(),
                    "crypto_customer",
                    "Crypto Customer",
                    WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode(),
                    "wallet_crypto_customer_platform_identifier",
                    new Version(1,0,0),
                    AppsStatus.ALPHA);
            lstInstalledWallet.add(installedWallet);
            item = new Item(installedWallet);
            item.setIconResource(R.drawable.crypto_customer);
            item.setPosition(2);
            lstItemsWithIcon.add(item);

            // Harcoded para testear el circuito más arriba
            installedWallet= new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledWallet(WalletCategory.REFERENCE_WALLET,
                    WalletType.REFERENCE,
                    new ArrayList<InstalledSkin>(),
                    new ArrayList<InstalledLanguage>(),
                    "asset_issuer",
                    "Asset Issuer",
                    WalletsPublicKeys.DAP_ISSUER_WALLET.getCode(),
                    "wallet_platform_identifier",
                    new Version(1,0,0),
                    AppsStatus.ALPHA);
            lstInstalledWallet.add(installedWallet);
            item = new Item(installedWallet);
            item.setIconResource(R.drawable.asset_issuer);
            item.setPosition(3);
            lstItemsWithIcon.add(item);

            installedWallet= new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledWallet(WalletCategory.REFERENCE_WALLET,
                    WalletType.REFERENCE,
                    new ArrayList<InstalledSkin>(),
                    new ArrayList<InstalledLanguage>(),
                    "asset_user",
                    "Asset User",
                    WalletsPublicKeys.DAP_USER_WALLET.getCode(),
                    "wallet_platform_identifier",
                    new Version(1,0,0),
                    AppsStatus.ALPHA);
            lstInstalledWallet.add(installedWallet);
            item = new Item(installedWallet);
            item.setIconResource(R.drawable.asset_user_wallet);
            item.setPosition(4);
            lstItemsWithIcon.add(item);

            installedWallet= new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledWallet(WalletCategory.REFERENCE_WALLET,
                    WalletType.REFERENCE,
                    new ArrayList<InstalledSkin>(),
                    new ArrayList<InstalledLanguage>(),
                    "redeem_point",
                    "Redeem Point",
                    WalletsPublicKeys.DAP_REDEEM_WALLET.getCode(),
                    "wallet_platform_identifier",
                    new Version(1,0,0),
                    AppsStatus.ALPHA);
            lstInstalledWallet.add(installedWallet);
            item = new Item(installedWallet);
            item.setIconResource(R.drawable.redeem_point);
            item.setPosition(5);
            lstItemsWithIcon.add(item);

            //Banking Wallet
            installedWallet= new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledWallet(WalletCategory.REFERENCE_WALLET,
                    WalletType.REFERENCE,
                    new ArrayList<InstalledSkin>(),
                    new ArrayList<InstalledLanguage>(),
                    "banking_wallet",
                    "Banking Wallet",
                    WalletsPublicKeys.BNK_BANKING_WALLET.getCode(),
                    "wallet_banking_platform_identifier",
                    new Version(1,0,0),
                    AppsStatus.ALPHA);
            lstInstalledWallet.add(installedWallet);
            item = new Item(installedWallet);
            item.setIconResource(R.drawable.bank_wallet_xxhdpi);
            item.setPosition(6);
            lstItemsWithIcon.add(item);

            //Cash Wallet
            installedWallet= new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledWallet(WalletCategory.REFERENCE_WALLET,
                    WalletType.REFERENCE,
                    new ArrayList<InstalledSkin>(),
                    new ArrayList<InstalledLanguage>(),
                    "cash_wallet",
                    "Cash Wallet",
                    WalletsPublicKeys.CSH_MONEY_WALLET.getCode(),
                    "wallet_cash_platform_identifier",
                    new Version(1,0,0),
                    AppsStatus.ALPHA);
            lstInstalledWallet.add(installedWallet);
            item = new Item(installedWallet);
            item.setIconResource(R.drawable.cash_wallet_xxhdpi);
            item.setPosition(7);
            lstItemsWithIcon.add(item);


            //TKY Fan Wallet
            installedWallet= new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledWallet(WalletCategory.REFERENCE_WALLET,
                    WalletType.REFERENCE,
                    new ArrayList<InstalledSkin>(),
                    new ArrayList<InstalledLanguage>(),
                    "fan_wallet",
                    "Fan Wallet",
                    WalletsPublicKeys.TKY_FAN_WALLET.getCode(),
                    "wallet_fan_platform_identifier",
                    new Version(1,0,0),
                    AppsStatus.DEV);
            lstInstalledWallet.add(installedWallet);
            item = new Item(installedWallet);
            item.setIconResource(R.drawable.subapp_fan_wallet_icon);
            item.setPosition(9);
            lstItemsWithIcon.add(item);





            //subApps
//            InstalledSubApp installedSubApp = new InstalledSubApp(SubApps.CWP_INTRA_USER_IDENTITY,null,null,"intra_user_identity_sub_app","Identity","public_key_ccp_intra_user_identity","intra_user_identity_sub_app",new Version(1,0,0));
//            Item item2 = new Item(installedSubApp);
//            item2.setIconResource(R.drawable.intra_user_image);
//            item2.setPosition(1);
//            //lstItemsWithIcon.add(item2);
//            installedSubApp = new InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY,null,null,"intra_user_community_sub_app","Community","public_key_intra_user_commmunity","intra_user_community_sub_app",new Version(1,0,0));
//            Item item1 = new Item(installedSubApp);
//            item1.setIconResource(R.drawable.intra_user_2);
//            item1.setPosition(0);
//            //lstItemsWithIcon.add(item1);
//            List<Item> lstFolderItems = new ArrayList<>();
//            lstFolderItems.add(item1);
//            lstFolderItems.add(item2);
//            FermatFolder folder = new FermatFolder("things",lstFolderItems,11);
//            Item itemFolder = new Item(folder);
//            itemFolder.setIconResource(R.drawable.bg_launcher_folder);
//            itemFolder.setPosition(4);
//            lstItemsWithIcon.add(itemFolder);


            for(int i=0;i<12;i++){
                Item emptyItem = new Item(new EmptyItem(0,i));
                emptyItem.setIconResource(-1);
                arrItemsWithoutIcon[i] = emptyItem;
            }

            int pos = 0;
            for(int i = 0;i<lstItemsWithIcon.size();i++){
                Item itemIcon = lstItemsWithIcon.get(i);
                if(itemIcon.getInterfaceObject() instanceof InstalledWallet){
                    if(((InstalledWallet) itemIcon.getInterfaceObject()).getAppStatus() == getFermatActivityManager().getAppStatus()){
                        arrItemsWithoutIcon[pos]= itemIcon;
                    pos++;
                    }
                }

            }
            dataSet.addAll(Arrays.asList(arrItemsWithoutIcon));

//            dataSet.addAll(lstItemsWithIcon);

        } catch (Exception e){
            e.printStackTrace();
        }

        return dataSet;
    }



    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onHolderItemClickListener(Item data, int position) {
        try {
            switch (data.getType()) {
                case SUB_APP:
                    if(((InstalledSubApp)data.getInterfaceObject()).getSubAppType().equals(SubApps.Scanner)){
                        Toast.makeText(getActivity(),"Coming soon",Toast.LENGTH_SHORT).show();
                    } else{
                        selectSubApp((InstalledSubApp) data.getInterfaceObject());
                    }
                    break;
                case WALLET:
                    selectWallet((InstalledWallet) data.getInterfaceObject());
                    break;
                case EMPTY:
                    break;
                case FOLDER:
                    if(data.getInterfaceObject().getName().equals("Communities")){
                        changeActivity(Activities.DESKTOP_COMMUNITY_ACTIVITY);
                    }else {
                        FolderDialog folderDialog = new FolderDialog(getActivity(), R.style.AppThemeDialog, appSession, null, data.getName(), ((FermatFolder) data.getInterfaceObject()).getLstFolderItems(), this,((FermatActivityManager)getActivity()).getAppStatus());
                        folderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        folderDialog.show();
                    }
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void select(AppsStatus appsStatus){
        List<Item> list = new ArrayList<>();
        for (Item installedWallet : lstItemsWithIcon) {
            if(appsStatus.isAppStatusAvailable(((InstalledWallet) installedWallet.getInterfaceObject()).getAppStatus())){
                list.add(installedWallet);
            }
        }
        Item[] arrItemsWithoutIcon = new Item[12];
        for(int i=0;i<12;i++){
            Item emptyItem = new Item(new EmptyItem(0,i));
            emptyItem.setIconResource(-1);
            arrItemsWithoutIcon[i] = emptyItem;
        }

        int j =0;
        for(Item itemIcon: list){
            arrItemsWithoutIcon[j]= itemIcon;
            j++;
        }

        if(recyclerView.getAdapter()!=null) {
            ((DesktopAdapter)recyclerView.getAdapter()).changeDataSet(Arrays.asList(arrItemsWithoutIcon));
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {

        adapter = null;
        mItemTouchHelper = null;
        super.onDestroy();
    }

    @Override
    public void onUpdateViewOnUIThread(String code) {
        AppsStatus appsStatus = AppsStatus.getByCode(code);
        switch (appsStatus){
            case RELEASE:
                break;
            case BETA:
                break;
            case ALPHA:
                break;
            case DEV:
                break;
        }

        select(appsStatus);
        super.onUpdateViewOnUIThread(code);
    }
}




