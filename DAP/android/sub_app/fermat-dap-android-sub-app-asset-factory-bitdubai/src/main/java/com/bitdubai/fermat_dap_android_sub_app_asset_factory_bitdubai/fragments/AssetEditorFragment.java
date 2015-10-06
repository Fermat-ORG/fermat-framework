package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.sessions.AssetFactorySession;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Asset Editor Fragment
 *
 * @author Francisco Vasquez
 */
public class AssetEditorFragment extends FermatFragment implements View.OnClickListener {

    private final String TAG = "AssetEditor";

    private AssetFactoryModuleManager manager;
    private ErrorManager errorManager;
    private AssetFactory asset;
    private boolean isEdit;

    private View rootView;
    private FermatEditText nameView;
    private FermatEditText descriptionView;
    private FermatEditText quantityView;
    private FermatEditText bitcoinsView;
    private FermatEditText expirationView;
    private FermatCheckBox isRedeemableView;


    public static AssetEditorFragment newInstance(AssetFactory asset) {
        AssetEditorFragment fragment = new AssetEditorFragment();
        fragment.setAsset(asset);
        fragment.setIsEdit(asset != null);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            manager = ((AssetFactorySession) subAppsSession).getManager();
            errorManager = subAppsSession.getErrorManager();
            if (!isEdit) {
                asset = manager.newAssetFactoryEmpty();
                List<InstalledWallet> installedWallets = manager.getInstallWallets();
                if (installedWallets != null && installedWallets.size() > 0) {
                    asset.setPublicKey(installedWallets.get(0).getWalletPublicKey());
                }
            }
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.asset_editor_fragment, container, false);
        rootView.findViewById(R.id.action_delete).setOnClickListener(this);
        rootView.findViewById(R.id.action_create).setOnClickListener(this);

        ((FermatButton) rootView.findViewById(R.id.action_create)).setText(isEdit ? "Edit" : "Create");

        nameView = (FermatEditText) rootView.findViewById(R.id.name);
        descriptionView = (FermatEditText) rootView.findViewById(R.id.description);
        quantityView = (FermatEditText) rootView.findViewById(R.id.quantity);
        bitcoinsView = (FermatEditText) rootView.findViewById(R.id.bitcoins);
        expirationView = (FermatEditText) rootView.findViewById(R.id.expiration_date);
        isRedeemableView = (FermatCheckBox) rootView.findViewById(R.id.isRedeemable);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setAsset(AssetFactory asset) {
        this.asset = asset;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.action_create) {
            saveAsset();
        } else if (i == R.id.action_delete) {
            deleteAsset();
        }
    }

    private void deleteAsset() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Deleting asset");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                manager.removeAssetFactory(asset.getPublicKey());
                return true;
            }
        };
        worker.setContext(getActivity());
        worker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "Asset deleted successfully", Toast.LENGTH_SHORT).show();
                    changeActivity(Activities.DAP_MAIN.getCode());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (getActivity() != null) {
                    CommonLogger.exception(TAG, ex.getMessage(), ex);
                    Toast.makeText(getActivity(), "There was an error deleting this asset", Toast.LENGTH_SHORT).show();
                }
            }
        });
        worker.execute();
    }

    private void saveAsset() {
        //asset.setPublicKey("asset-factory-public-key");//// TODO: 02/10/15 set public key
        asset.setName(nameView.getText().toString().trim());
        asset.setDescription(descriptionView.getText().toString().trim());
        asset.setQuantity(Integer.parseInt(quantityView.getText().toString().trim().isEmpty() ? "0" : quantityView.getText().toString().trim()));
        asset.setAmount(Long.parseLong(bitcoinsView.getText().toString().trim().isEmpty() ? "0" : bitcoinsView.getText().toString().trim()));
        asset.setIsRedeemable(isRedeemableView.isChecked());
        asset.setState(State.DRAFT);
        //// TODO: 02/10/15 Asset behaviour is given from the final user through dropdown control list
        asset.setAssetBehavior(AssetBehavior.REGENERATION_ASSET);
        //// TODO: 02/10/15 Get at least one resource with one image byte[] (Choose from gallery or take a picture)
        asset.setResources(null);
        if (!expirationView.getText().toString().trim().isEmpty()) {
            try {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("mm/yyy/dd h:m:s");
                asset.setExpirationDate(new java.sql.Timestamp(format.parse(expirationView.getText().toString().trim()).getTime()));
                long now = new Date().getTime();
                asset.setCreationTimestamp(new java.sql.Timestamp(now));
            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
            }
        }
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Saving asset");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                manager.saveAssetFactory(asset);
                return true;
            }
        };
        worker.setContext(getActivity());
        worker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), String.format("Asset %s has been created", asset.getName()), Toast.LENGTH_SHORT).show();
                    changeActivity(Activities.DAP_MAIN.getCode());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (getActivity() != null) {
                    CommonLogger.exception(TAG, ex.getMessage(), ex);
                    Toast.makeText(getActivity(), "There was an error deleting this asset", Toast.LENGTH_SHORT).show();
                }
            }
        });
        worker.execute();
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }
}
