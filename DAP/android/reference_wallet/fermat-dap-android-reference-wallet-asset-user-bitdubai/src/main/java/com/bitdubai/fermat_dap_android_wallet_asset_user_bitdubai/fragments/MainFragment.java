package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters.DigitalAssetAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class MainFragment extends FermatWalletFragment
        implements PopupMenu.OnMenuItemClickListener {


    private AssetUserWalletSubAppModuleManager manager;
    private List<AssetUserWalletList> assetUserWalletList;
    private List<DigitalAsset> bookAssets;
    private DigitalAsset asset;


    /**
     * UI
     */
    private View rootView;
    private RecyclerView assetsView;
    private LinearLayoutManager layoutManager;
    private DigitalAssetAdapter adapter;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            manager = ((AssetUserSession) walletSession).getWalletManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        if (manager == null)
//            Toast.makeText(getActivity(), "AssetUserWalletSubAppModuleManager is null", Toast.LENGTH_SHORT).show();
//        else {
//            Toast.makeText(getActivity(), "AssetUserWalletSubAppModuleManager is connected", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FermatWorker worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
//                if (manager == null)
//                    throw new NullPointerException("AssetUserWalletModuleManager is null");
//                assetUserWalletList = manager.getAssetUserWalletBalancesBook("walletPublicKeyTest");
                if (assetUserWalletList != null && !assetUserWalletList.isEmpty()) {
                    bookAssets = new ArrayList<>();
                    for (AssetUserWalletList assetUserWallet : assetUserWalletList) {
                        DigitalAsset asset = new DigitalAsset(assetUserWallet.getName(),
                                String.valueOf(String.format("BookBalance: %d - AvailableBalance: %d",
                                        assetUserWallet.getQuantityBookBalance(), assetUserWallet.getQuantityAvailableBalance())));
                        asset.setAssetPublicKey(assetUserWallet.getAssetPublicKey());
                        asset.setWalletPublicKey("public_key");
                        bookAssets.add(asset);
                    }
                }
                return true;
            }
        };
        worker.setContext(getActivity());
        worker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (getActivity() != null) {
                    if (bookAssets != null && !bookAssets.isEmpty()) {
                        if (adapter != null)
                            adapter.changeDataSet(bookAssets);
                    }
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                if (getActivity() != null)
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                CommonLogger.exception(this.getClass().getName(), ex.getMessage(), ex);
                ex.printStackTrace();
            }
        });
        worker.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);
        assetsView = (RecyclerView) rootView.findViewById(R.id.assets);
        assetsView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        assetsView.setLayoutManager(layoutManager);
        adapter = new DigitalAssetAdapter(getActivity());
        adapter.setPopupMenu(new com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.interfaces.PopupMenu() {
            @Override
            public void onMenuItemClickListener(View menuView, DigitalAsset project, int position) {
                setAsset(project);
                PopupMenu popupMenu = new PopupMenu(getActivity(), menuView);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(MainFragment.this);
                popupMenu.show();
            }
        });
        assetsView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_appropriate) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
            FermatWorker task = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
//                    manager.distributionAssets(
//                            asset.getAssetPublicKey(),
//                            asset.getWalletPublicKey(),
//                            asset.getActorAssetUser()
//                    );
                    //TODO implement work to do
//                    manager.appropriateAsset();
                    return true;
                }
            };
            task.setContext(getActivity());
            task.setCallBack(new FermatWorkerCallBack() {
                @Override
                public void onPostExecute(Object... result) {
                    dialog.dismiss();
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "Everything ok...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    dialog.dismiss();
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), "Fermat Has detected an exception",
                                Toast.LENGTH_SHORT).show();
                }
            });
            task.execute();
            return true;
        }
        return false;
    }

    public void setAsset(DigitalAsset asset) {
        this.asset = asset;
    }
}
