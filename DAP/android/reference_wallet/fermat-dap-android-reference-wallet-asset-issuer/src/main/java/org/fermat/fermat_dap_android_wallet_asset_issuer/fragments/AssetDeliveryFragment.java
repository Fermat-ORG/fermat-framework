package org.fermat.fermat_dap_android_wallet_asset_issuer.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Data;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Group;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.User;
import org.fermat.fermat_dap_android_wallet_asset_issuer.sessions.AssetIssuerSession;
import org.fermat.fermat_dap_android_wallet_asset_issuer.sessions.SessionConstantsAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by frank on 12/15/15.
 */
public class AssetDeliveryFragment extends AbstractFermatFragment {

    private Activity activity;
    private static final int MAX_ASSET_QUANTITY = 200;
    private AssetIssuerSession assetIssuerSession;
    private AssetIssuerWalletSupAppModuleManager moduleManager;
    private ErrorManager errorManager;

    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private ImageView assetDeliveryImage;
    private FermatTextView assetDeliveryNameText;
    private FermatTextView assetDeliveryRemainingText;
    private FermatTextView selectedUsersText;
    private FermatEditText assetsToDeliverEditText;
    private View selectUsersButton;
    private View deliverAssetsButton;

    private DigitalAsset digitalAsset;

    int selectedUsersCount;
    int selectedGroupsCount;
    int selectedUsersInGroupsCount;

    SettingsManager<AssetIssuerSettings> settingsManager;

    public AssetDeliveryFragment() {

    }

    public static AssetDeliveryFragment newInstance() {
        return new AssetDeliveryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        assetIssuerSession = (AssetIssuerSession) appSession;
        moduleManager = assetIssuerSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        settingsManager = appSession.getModuleManager().getSettingsManager();
        moduleManager.clearDeliverList();
        activity = getActivity();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_issuer_asset_delivery, container, false);
        res = rootView.getResources();

        setupUI();
        setupUIData();

        return rootView;
    }

    private void setUpHelpAssetStatistics(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_issuer_wallet)
                    .setIconRes(R.drawable.asset_issuer)
                    .setVIewColor(R.color.dap_issuer_view_color)
                    .setTitleTextColor(R.color.dap_issuer_view_color)
                    .setSubTitle(R.string.dap_issuer_wallet_delivery_subTitle)
                    .setBody(R.string.dap_issuer_wallet_delivery_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_DELIVERY, 0, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_DELIVERY) {
                setUpHelpAssetStatistics(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_issuer_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        setupBackgroundBitmap();

        assetDeliveryImage = (ImageView) rootView.findViewById(R.id.assetDeliveryImage);
        assetDeliveryNameText = (FermatTextView) rootView.findViewById(R.id.assetDeliveryNameText);
        assetDeliveryRemainingText = (FermatTextView) rootView.findViewById(R.id.assetDeliveryRemainingText);
        assetsToDeliverEditText = (FermatEditText) rootView.findViewById(R.id.assetsToDeliverEditText);
        selectedUsersText = (FermatTextView) rootView.findViewById(R.id.selectedUsersText);
        selectUsersButton = rootView.findViewById(R.id.selectUsersButton);
        deliverAssetsButton = rootView.findViewById(R.id.deliverAssetsButton);

//        layout = rootView.findViewById(R.id.assetDetailRemainingLayout);
        deliverAssetsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (assetsToDeliverEditText.getText().length() == 0) {
                    Toast.makeText(activity, R.string.dap_issuer_wallet_validate_assets_to_deliver, Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(assetsToDeliverEditText.getText().toString()) > MAX_ASSET_QUANTITY){
                    Toast.makeText(activity, R.string.dap_issuer_wallet_validate_assets_to_deliver_greater + MAX_ASSET_QUANTITY, Toast.LENGTH_SHORT).show();
                } else if (digitalAsset.getAvailableBalanceQuantity() == 0) {
                    Toast.makeText(activity, R.string.dap_issuer_wallet_validate_no_available_assets, Toast.LENGTH_SHORT).show();
                } else if (selectedUsersCount == 0 && selectedGroupsCount == 0) {
                    Toast.makeText(activity, R.string.dap_issuer_wallet_validate_no_users_groups, Toast.LENGTH_SHORT).show();
                } else if (selectedUsersCount > digitalAsset.getAvailableBalanceQuantity() || selectedUsersInGroupsCount > digitalAsset.getAvailableBalanceQuantity()) {
                    Toast.makeText(activity, R.string.dap_issuer_wallet_validate_not_enought_assets, Toast.LENGTH_SHORT).show();
                } else {
                    if (selectedUsersCount > 0) {
                        Object x = appSession.getData("users");
                        if (x != null) {
                            final List<User> users = (List<User>) x;
                            if (users.size() > 0) {
                                new ConfirmDialog.Builder(getActivity(), appSession)
                                        .setTitle(getResources().getString(R.string.dap_issuer_wallet_confirm_title))
                                        .setMessage(getResources().getString(R.string.dap_issuer_wallet_confirm_entered_info))
                                        .setColorStyle(getResources().getColor(R.color.dap_issuer_wallet_principal))
                                        .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                            @Override
                                            public void onClick() {
                                                int assetsAmount = Integer.parseInt(assetsToDeliverEditText.getText().toString());
                                                doDistributeToUsers(digitalAsset.getAssetPublicKey(), users, assetsAmount);
                                            }
                                        }).build().show();
                            }
                        }
                    } else if (selectedGroupsCount > 0) {
                        Object x = appSession.getData("groups");
                        if (x != null) {
                            final List<Group> groups = (List<Group>) x;
                            if (groups.size() > 0) {
                                new ConfirmDialog.Builder(getActivity(), appSession)
                                        .setTitle(getResources().getString(R.string.dap_issuer_wallet_confirm_title))
                                        .setMessage(getResources().getString(R.string.dap_issuer_wallet_confirm_entered_info))
                                        .setColorStyle(getResources().getColor(R.color.dap_issuer_wallet_principal))
                                        .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                            @Override
                                            public void onClick() {
                                                int assetsAmount = Integer.parseInt(assetsToDeliverEditText.getText().toString());
                                                doDistributeToGroups(digitalAsset.getAssetPublicKey(), groups, assetsAmount);
                                            }
                                        }).build().show();
                            }
                        }
                    }
                }
            }
        });

        selectUsersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                appSession.setData("asset_data", data);
                changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY_SELECT_USERS_GROUPS, appSession.getAppPublicKey());
            }
        });

        selectedUsersCount = getUsersSelectedCount();
        setupGroupsSelectedCount();

        String message = "";
        if (selectedUsersCount == 0 && selectedGroupsCount == 0) {
            message = "Select users or groups";
        } else if (selectedUsersCount > 0) {
            message = selectedUsersCount + ((selectedUsersCount == 1) ? " user" : " users") + " selected";
        } else if (selectedGroupsCount > 0) {
            message = selectedGroupsCount  + ((selectedGroupsCount == 1) ? " group" : " groups") + " selected";
        }
        selectedUsersText.setText(message);
    }

    private void setupBackgroundBitmap() {
        AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {

            WeakReference<ViewGroup> view;

            @Override
            protected void onPreExecute() {
                view = new WeakReference(rootView) ;
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap drawable = null;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = true;
                    options.inSampleSize = 5;
                    drawable = BitmapFactory.decodeResource(
                            getResources(), R.drawable.bg_app_image,options);
                }catch (OutOfMemoryError error){
                    error.printStackTrace();
                }
                return drawable;
            }

            @Override
            protected void onPostExecute(Bitmap drawable) {
                if (drawable!= null) {
                    view.get().setBackground(new BitmapDrawable(getResources(),drawable));
                }
            }
        } ;
        asyncTask.execute();
    }

    private int getUsersSelectedCount() {
        Object x = appSession.getData("users");
        int count = 0;
        if (x != null) {
            List<User> users = (List<User>) x;
            if (users.size() > 0) {
                for (User user :
                        users) {
                    if (user.isSelected()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void setupGroupsSelectedCount() {
        Object x = appSession.getData("groups");
        int countUsers = 0;
        int countGroups = 0;
        if (x != null) {
            List<Group> groups = (List<Group>) x;
            if (groups.size() > 0) {
                for (Group group :
                        groups) {
                    if (group.isSelected()) {
                        countGroups += 1;
                        countUsers += group.getUsers().size();
                    }
                }
            }
        }
        selectedGroupsCount = countGroups;
        selectedUsersInGroupsCount = countUsers;
    }

    private void doDistributeToUsers(final String assetPublicKey, final List<User> users, final int assetsAmount) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                for (User user : users) {
                    if (user.isSelected()) {
                        moduleManager.addUserToDeliver(user.getActorAssetUser());
                    }
                }
                if (users.size() > 0) {
                    moduleManager.distributionAssets(assetPublicKey, WalletUtilities.WALLET_PUBLIC_KEY, assetsAmount);
                }
                return true;
            }
        };

        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
//                    refreshUIData();
                    Toast.makeText(activity, R.string.dap_issuer_wallet_deliver_ok, Toast.LENGTH_LONG).show();
                    changeActivity(Activities.DAP_ASSET_ISSUER_WALLET_ASSET_DETAIL, appSession.getAppPublicKey());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (activity != null)
                    Toast.makeText(activity, R.string.dap_issuer_wallet_exception,
                            Toast.LENGTH_SHORT).show();
            }
        });
        task.execute();
    }

    private void doDistributeToGroups(final String assetPublicKey, final List<Group> groups, final int assetsAmount) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_issuer_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                for (Group group : groups) {
                    if (group.isSelected()) {
                        moduleManager.addGroupToDeliver(group.getActorAssetUserGroup());
                    }
                }
                if (groups.size() > 0) {
                    moduleManager.distributionAssets(assetPublicKey, WalletUtilities.WALLET_PUBLIC_KEY, assetsAmount);
                }
                return true;
            }
        };

        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
//                    refreshUIData();
                    Toast.makeText(activity, R.string.dap_issuer_wallet_deliver_ok, Toast.LENGTH_LONG).show();
                    changeActivity(Activities.DAP_ASSET_ISSUER_WALLET_ASSET_DETAIL, appSession.getAppPublicKey());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (activity != null) {
                    refreshUIData();
                    Toast.makeText(activity, R.string.dap_issuer_wallet_exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        task.execute();
    }

    private void refreshUIData() {
        String digitalAssetPublicKey = ((DigitalAsset) appSession.getData("asset_data")).getAssetPublicKey();
        try {
            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }

        assetDeliveryNameText.setText(digitalAsset.getName());
        //assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetsToDeliverEditText.setText(((selectedUsersCount > 0) ? selectedUsersCount : selectedUsersInGroupsCount) + "");
        assetDeliveryRemainingText.setText(digitalAsset.getAvailableBalanceQuantity() + " " + getResources().getString(R.string.dap_issuer_wallet_remaining_assets));

        if (digitalAsset.getAvailableBalanceQuantity() == 0) {
            selectUsersButton.setOnClickListener(null);
        }
    }

    private void setupUIData() {
//        digitalAsset = (DigitalAsset) appSession.getData("asset_data");
        String digitalAssetPublicKey = ((DigitalAsset) appSession.getData("asset_data")).getAssetPublicKey();
        try {
            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }

//        toolbar.setTitle(digitalAsset.getName());

//        if (digitalAsset.getImage() != null) {
//            assetDeliveryImage.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
//        } else {
//            assetDeliveryImage.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
//        }

        byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(assetDeliveryImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img); //todo commenting to compile, please review

        assetDeliveryNameText.setText(digitalAsset.getName());
        //assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetsToDeliverEditText.setText(((selectedUsersCount > 0) ? selectedUsersCount : selectedUsersInGroupsCount) + "");
        long quantity = digitalAsset.getAvailableBalanceQuantity();
        assetDeliveryRemainingText.setText(quantity + ((quantity == 1) ? " Asset" : " Assets") + " Remaining");
    }

    private void configureToolbar() {
        toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBackgroundColor(Color.TRANSPARENT);
            toolbar.setBottom(Color.WHITE);
            Drawable drawable = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_issuer_action_bar_gradient_colors, null);
            else
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_issuer_action_bar_gradient_colors);
            toolbar.setBackground(drawable);
        }
    }
}
