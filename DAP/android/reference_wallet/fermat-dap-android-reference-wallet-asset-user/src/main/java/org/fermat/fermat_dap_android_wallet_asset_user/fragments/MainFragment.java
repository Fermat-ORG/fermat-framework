package org.fermat.fermat_dap_android_wallet_asset_user.fragments;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.adapters.DigitalAssetAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_user.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;

import java.io.ByteArrayInputStream;
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


    private AssetUserWalletSubAppModuleManager manager;
    private List<AssetUserWalletList> assetUserWalletList;

    private List<DigitalAsset> bookAssets;
    private DigitalAsset asset;
    private ImageView assetImageDetail;


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
//            manager = ((AssetUserSessionReferenceApp) appSession).getWalletManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FermatWorker worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                if (manager == null)
                    throw new NullPointerException("AssetUserWalletModuleManager is null");
                assetUserWalletList = manager.getAssetUserWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
                if (assetUserWalletList != null && !assetUserWalletList.isEmpty()) {
                    bookAssets = new ArrayList<>();
                    for (AssetUserWalletList assetUserWallet : assetUserWalletList) {
                        DigitalAsset asset = new DigitalAsset(assetUserWallet.getDigitalAsset().getName(),
                                String.valueOf(String.format("BookBalance: %d - AvailableBalance: %d",
                                        assetUserWallet.getQuantityBookBalance(), assetUserWallet.getQuantityAvailableBalance())));
                        asset.setAssetPublicKey(assetUserWallet.getDigitalAsset().getPublicKey());
                        asset.setWalletPublicKey("public_key");
                        asset.setDigitalAsset(assetUserWallet.getDigitalAsset());
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
        rootView = inflater.inflate(R.layout.dap_wallet_asset_user_main_fragment, container, false);
        assetsView = (RecyclerView) rootView.findViewById(R.id.assets);
        assetsView.setHasFixedSize(true);
        assetImageDetail = (ImageView) rootView.findViewById(R.id.asset_image);

        if (asset.getImage() != null) {
            assetImageDetail.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(asset.getImage())));
        } else {
            assetImageDetail.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
        }


        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        assetsView.setLayoutManager(layoutManager);
        adapter = new DigitalAssetAdapter(getActivity());
        adapter.setPopupMenu(new org.fermat.fermat_dap_android_wallet_asset_user.interfaces.PopupMenu() {
            @Override
            public void onMenuItemClickListener(View menuView, DigitalAsset project, int position) {
                setAsset(project);
                PopupMenu popupMenu = new PopupMenu(getActivity(), menuView);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.dap_wallet_asset_user_main, popupMenu.getMenu());
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
                    //TODO GET THE AMOUNT TO APPROPRIATE.
                    manager.appropriateAsset(asset.getDigitalAsset(), null);
                    return true;
                }
            };
            task.setContext(getActivity());
            task.setCallBack(new FermatWorkerCallBack() {
                @Override
                public void onPostExecute(Object... result) {
                    dialog.dismiss();
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "Appropriation of the asset has started successfully. The process will be completed in a couple of minutes.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    dialog.dismiss();
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), "Fermat Has detected an exception. Please retry again.",
                                Toast.LENGTH_SHORT).show();
                }
            });
            task.execute();
            return true;
        } else if (menuItem.getItemId() == R.id.action_redeem) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
            FermatWorker task = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    manager.redeemAssetToRedeemPoint(asset.getDigitalAsset(), null, null, 1);
                    return true;
                }
            };
            task.setContext(getActivity());
            task.setCallBack(new FermatWorkerCallBack() {
                @Override
                public void onPostExecute(Object... result) {
                    dialog.dismiss();
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "Redemption of the asset has successfully started.\n\n " +
                                "The process will take some minutes and if not accepted at the destination, it will be rollback.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    dialog.dismiss();
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), "Fermat Has detected an exception. Please retry again.",
                                Toast.LENGTH_SHORT).show();
                }
            });
            task.execute();
            return true;
        }
        return false;
    }


    private void setupUI() {
    }

    private void setupUIData() {

    }

    public void setAsset(DigitalAsset asset) {
        this.asset = asset;
    }
}
