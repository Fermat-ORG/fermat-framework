package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class MainFragment extends FermatWalletFragment {


    private AssetIssuerWalletSupAppModuleManager manager;
    private List<AssetIssuerWalletList> assetIssuerWalletList;
    private List<DigitalAsset> bookAssets;/**
     * UI
     */
    private View rootView;
    private ListView assetsView;


    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            manager = ((AssetIssuerSession) walletSession).getManager();
        } catch (Exception ex) {
            CommonLogger.exception(this.getClass().getName(), ex.getMessage(), ex);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FermatWorker worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                if (manager == null)
                    throw new NullPointerException("AssetIssuerWalletModuleManager is null");
                assetIssuerWalletList = manager.getAssetIssuerWalletBalancesBook("public_key");
                if (assetIssuerWalletList != null && !assetIssuerWalletList.isEmpty()) {
                    bookAssets = new ArrayList<>();
                    for (AssetIssuerWalletList assetIssuerWallet : assetIssuerWalletList) {
                        bookAssets.add(new DigitalAsset(assetIssuerWallet.getName(),
                                String.valueOf(String.format("BookBalance: %d - AvailableBalance: %d",
                                        assetIssuerWallet.getBookBalance(), assetIssuerWallet.getAvailableBalance()))));
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
                        ArrayAdapter<DigitalAsset> adapter = new ArrayAdapter<DigitalAsset>(getActivity(),
                                R.layout.simple_row_asset, R.id.option, bookAssets);
                        assetsView.setAdapter(adapter);
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
        assetsView = (ListView) rootView.findViewById(R.id.assets);
        ArrayAdapter<DigitalAsset> adapter = new ArrayAdapter<DigitalAsset>(getActivity(), R.layout.simple_row_asset, R.id.option, DigitalAsset.getAssets());
        assetsView.setAdapter(adapter);
        return rootView;
    }
}
