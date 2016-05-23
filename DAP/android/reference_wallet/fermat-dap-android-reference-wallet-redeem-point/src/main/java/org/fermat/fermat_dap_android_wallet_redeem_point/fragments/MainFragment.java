package org.fermat.fermat_dap_android_wallet_redeem_point.fragments;

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

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_redeem_point.adapters.DigitalAssetAdapter;
import org.fermat.fermat_dap_android_wallet_redeem_point.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_redeem_point.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class MainFragment extends AbstractFermatFragment
        implements PopupMenu.OnMenuItemClickListener {

    private AssetRedeemPointWalletSubAppModule manager;
    private List<AssetRedeemPointWalletList> assetRedeemPointWalletList;
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
//        try {
//            manager = ((RedeemPointSession) appSession).getRedeemManager();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FermatWorker worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                if (manager == null)
                    throw new NullPointerException("AssetRedeemPointWalletModuleManager is null");
                assetRedeemPointWalletList = manager.getAssetRedeemPointWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
                if (assetRedeemPointWalletList != null && !assetRedeemPointWalletList.isEmpty()) {
                    bookAssets = new ArrayList<>();
                    for (AssetRedeemPointWalletList assetRedeemPointWallet : assetRedeemPointWalletList) {
                        DigitalAsset asset = new DigitalAsset(assetRedeemPointWallet.getDigitalAsset().getName(),
                                String.valueOf(String.format("BookBalance: %d - AvailableBalance: %d",
                                        assetRedeemPointWallet.getQuantityBookBalance(), assetRedeemPointWallet.getQuantityAvailableBalance())));
                        asset.setAssetPublicKey(assetRedeemPointWallet.getDigitalAsset().getPublicKey());
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
        rootView = inflater.inflate(R.layout.dap_wallet_asset_redeempoint_main_fragment, container, false);
        assetsView = (RecyclerView) rootView.findViewById(R.id.assets);
        assetsView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        assetsView.setLayoutManager(layoutManager);
        adapter = new DigitalAssetAdapter(getActivity());
        adapter.setPopupMenu(new org.fermat.fermat_dap_android_wallet_redeem_point.interfaces.PopupMenu() {
            @Override
            public void onMenuItemClickListener(View menuView, DigitalAsset project, int position) {
                setAsset(project);
                PopupMenu popupMenu = new PopupMenu(getActivity(), menuView);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.dap_wallet_asset_redeempoint_main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(MainFragment.this);
                popupMenu.show();
            }
        });
        assetsView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_new) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
            FermatWorker task = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    //TODO implement work to do
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
                        Toast.makeText(getActivity(), "Fermat Has detected an exception. Please retry later.",
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
