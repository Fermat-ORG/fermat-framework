package org.fermat.fermat_dap_android_wallet_redeem_point.v3.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_redeem_point.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.adapters.RedeemDetailAdapter;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

import static android.widget.Toast.makeText;

/**
 * Created by Jinmy Bohorquez on 25/04/16.
 */
public class RedeemPointDetailFragment extends AbstractFermatFragment<ReferenceAppFermatSession<AssetRedeemPointWalletSubAppModule>, ResourceProviderManager> {

    private AssetRedeemPointWalletSubAppModule moduleManager;
    private ErrorManager errorManager;
    private Activity activity;
    private View rootView;
    private Resources res;
    private ImageView cardDetailConfirmedImage;
    private ImageView cardDetailStatusImage;
    private ImageButton cardDetailDeliverButton;
    private ImageButton cardDetailAcceptButton;
    private ImageButton cardDetailRejectButton;
    private View redeemDetailPendingV3Asset;
    private View actionButtonsDetail;
    private View confirmedDetailV3Asset;
    private FermatTextView assetDetailDescription;
    private FermatTextView assetDetailAssetValue;
    private FermatTextView assetDetailDate;
    private ImageView detailIssuerImage;
    private FermatTextView assetDetailIssuerName;
    private FermatTextView assetIssuerAddress;
    private DigitalAsset digitalAsset;
    private ViewPager viewPager;
    private RedeemDetailAdapter adapter;

    public RedeemPointDetailFragment() {

    }

    public static RedeemPointDetailFragment newInstance() {
        return new RedeemPointDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        activity = getActivity();

        digitalAsset = (DigitalAsset) appSession.getData("asset_data");
        adapter = new RedeemDetailAdapter(this, getActivity(), digitalAsset);

        configureToolbar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_v3_wallet_asset_redeem_point_detail, container,
                false);
        res = rootView.getResources();

        cardDetailConfirmedImage = (ImageView) rootView.findViewById(R.id.cardDetailConfirmedImage);
        cardDetailStatusImage = (ImageView) rootView.findViewById(R.id.cardDetailStatusImage);
        cardDetailDeliverButton = (ImageButton) rootView.findViewById(R.id.cardDetailDeliverButton);
        cardDetailAcceptButton = (ImageButton) rootView.findViewById(R.id.cardDetailAcceptButton);
        cardDetailRejectButton = (ImageButton) rootView.findViewById(R.id.cardDetailRejectButton);
        redeemDetailPendingV3Asset = rootView.findViewById(R.id.redeemDetailPendingV3Asset);
        confirmedDetailV3Asset = rootView.findViewById(R.id.confirmedDetailV3Asset);
        actionButtonsDetail = rootView.findViewById(R.id.actionButtonsDetail);

        assetDetailDescription = (FermatTextView) rootView.findViewById(R.id.assetDetailDescription);
        assetDetailAssetValue = (FermatTextView) rootView.findViewById(R.id.assetDetailAssetValue);
        assetDetailDate = (FermatTextView) rootView.findViewById(R.id.assetDetailDate);
        detailIssuerImage = (ImageView) rootView.findViewById(R.id.detailIssuerImage);
        assetDetailIssuerName = (FermatTextView) rootView.findViewById(R.id.assetDetailIssuerName);
        assetIssuerAddress = (FermatTextView) rootView.findViewById(R.id.assetIssuerAddress);

        viewPager = (ViewPager) rootView.findViewById(R.id.redeemDetailViewPager);


        setViewData();

        return rootView;
    }

    private void setViewData() {

        viewPager.setAdapter(adapter);
        assetDetailDescription.setText(digitalAsset.getAssetDescription());
        assetDetailAssetValue.setText(digitalAsset.getFormattedAvailableBalanceBitcoin() + " BTC");
        assetDetailDate.setText(digitalAsset.getFormattedExpDate());

        Bitmap bitmap;
        if (digitalAsset.getImageActorIssuerFrom() != null && digitalAsset.getImageActorIssuerFrom().length > 0) {
            bitmap = BitmapFactory.decodeByteArray(digitalAsset.getImageActorIssuerFrom(), 0,
                    digitalAsset.getImageActorIssuerFrom().length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 42, 42, true);
            detailIssuerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));
        } else {
            detailIssuerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(activity.getResources(), R.drawable.img_asset_without_image));
        }
        assetDetailIssuerName.setText(digitalAsset.getActorIssuerNameFrom());
        assetIssuerAddress.setText(digitalAsset.getActorIssuerAddress());

        /*Listeners*/
        if (digitalAsset.getStatus() == DigitalAsset.Status.PENDING) {

            actionButtonsDetail.setVisibility(View.GONE);
            cardDetailStatusImage.setImageResource(R.drawable.wait);


        } else if (digitalAsset.getStatus() == DigitalAsset.Status.CONFIRMED) {

            actionButtonsDetail.setVisibility(View.GONE);
            cardDetailStatusImage.setImageResource(R.drawable.received);

            cardDetailAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appSession.setData("asset_data", digitalAsset);
                    /*
                    metodo para aceptar asset aqui
                    doAcceptAsset();
                     */
                }
            });
            cardDetailRejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appSession.setData("asset_data", digitalAsset);
                    /*
                    metodo para rechazar asset
                    doRejectAsset();
                     */
                }
            });

        }

    }


    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setTitle(digitalAsset.getName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.redeem_card_titlebar));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.redeem_card_titlebar));
            }
        }
    }

    @Override
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            switch (id) {
                case 1://IC_ACTION_REDEEM_HELP_DETAIL
                    setUpHelpAssetRedeemDetail(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
            }

//            if (id == SessionConstantsRedeemPoint.IC_ACTION_REDEEM_HELP_DETAIL) {
//                setUpHelpAssetRedeemDetail(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_redeem_point_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpHelpAssetRedeemDetail(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_redeem_point_wallet)
                    .setIconRes(R.drawable.redeem_point)
                    .setVIewColor(R.color.dap_redeem_point_view_color)
                    .setTitleTextColor(R.color.dap_redeem_point_view_color)
                    .setSubTitle(R.string.dap_redeem_wallet_detail_subTitle)
                    .setBody(R.string.dap_redeem_wallet_detail_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
