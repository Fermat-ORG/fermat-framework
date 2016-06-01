package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.ReceiveFragmentDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.BitmapWorkerTask;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;

import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static android.widget.Toast.makeText;

/**
 * Contact Detail Fragment.
 *
 * @author Gian Barboza
 * @version 1.0
 */
public class ContactDetailFragment extends AbstractFermatFragment implements View.OnClickListener {


    /**
     * Root fragment view reference
     */
    private View mFragmentView;
    /**
     * Fragment UI controls
     */
    private ImageView image_view_profile;
    private EditText edit_text_name;
    private TextView text_view_address;
    private LinearLayout linear_layout_extra_user_receive;

    /**
     * Typeface Font
     */
    private Typeface typeface;
    /**
     * Platform
     */
    private LossProtectedWallet lossProtectedWalletManager;
    private ErrorManager errorManager;


    /**
     * DATA
     */
    private LossProtectedWalletContact lossProtectedWalletContact;
    /**
     *  Resources
     */

    private LossProtectedWalletSession lossProtectedWalletSession;
    private FermatButton send_button;
    private FermatButton receive_button;
    private ImageView img_update;
    private boolean addressIsTouch=false;
    private static final long DELAY_TIME = 2;
    private Handler delayHandler = new Handler();
    private Runnable delay = new Runnable() {
        @Override
        public void run() {
            addressIsTouch = false;
        }
    };
  //  SettingsManager<LossProtectedWalletSettings> settingsManager;
    private LossProtectedWalletSettings lossProtectedWalletSettings;
    BlockchainNetworkType blockchainNetworkType;


    public static ContactDetailFragment newInstance() {
        ContactDetailFragment f = new ContactDetailFragment();
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            lossProtectedWalletSession = (LossProtectedWalletSession)appSession;
            setHasOptionsMenu(true);
            lossProtectedWalletContact = lossProtectedWalletSession.getLastContactSelected();
            if(lossProtectedWalletContact==null){
                onBack(null);
            }

            lossProtectedWalletManager = lossProtectedWalletSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());

            if(lossProtectedWalletSettings != null) {

                if (lossProtectedWalletSettings.getBlockchainNetworkType() == null) {
                    lossProtectedWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                }
                lossProtectedWalletManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(), lossProtectedWalletSettings);

            }

            blockchainNetworkType = lossProtectedWalletSettings.getBlockchainNetworkType();


          } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error",Toast.LENGTH_SHORT).show();
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{
            mFragmentView = inflater.inflate(R.layout.loss_contact_detail, container, false);

            setUp();
            setUpContact();
            return mFragmentView;
        }catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE,e);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return container;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        try{
            if ( id == R.id.send_button) {
                if(lossProtectedWalletContact.getReceivedCryptoAddress().size() > 0) {
                    lossProtectedWalletSession.setLastContactSelected(lossProtectedWalletContact);
                    lossProtectedWalletSession.setData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS, false);
                    changeActivity(Activities.CCP_BITCOIN_LOSS_PROTECTED_WALLET_SEND_FORM_ACTIVITY, lossProtectedWalletSession.getAppPublicKey());
                }else{
                    Toast.makeText(getActivity(),"You don't have address to send\nplease wait to get it or touch the refresh button",Toast.LENGTH_SHORT).show();
                }
            }
            else if( id == R.id.receive_button){
                if(lossProtectedWalletContact.getReceivedCryptoAddress().size() > 0) {
                    lossProtectedWalletSession.setLastContactSelected(lossProtectedWalletContact);
                    lossProtectedWalletSession.setData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS, false);
                    changeActivity(Activities.CCP_BITCOIN_LOSS_PROTECTED_WALLET_REQUEST_FORM_ACTIVITY, lossProtectedWalletSession.getAppPublicKey());
                }else{
                    Toast.makeText(getActivity(),"You don't have address to request\nplease wait to get it or touch the refresh button",Toast.LENGTH_SHORT).show();
                }
            }
            else if ( id == R.id.linear_layout_extra_user_receive){
                ReceiveFragmentDialog receiveFragmentDialog = new ReceiveFragmentDialog(
                        getActivity(),
                        lossProtectedWalletManager,
                        lossProtectedWalletSession.getErrorManager(),
                        lossProtectedWalletContact,
                        lossProtectedWalletSession.getIntraUserModuleManager().getPublicKey(),
                        lossProtectedWalletSession.getAppPublicKey(),
                        blockchainNetworkType);
                receiveFragmentDialog.show();
            }

        }catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE,e);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Setup UI references
     */
    private void setUp() {
        if (mFragmentView != null) {
            image_view_profile = (ImageView) mFragmentView.findViewById(R.id.image_view_profile);
            edit_text_name = (EditText) mFragmentView.findViewById(R.id.edit_text_name);
            text_view_address = (TextView) mFragmentView.findViewById(R.id.text_view_address);
            receive_button = (FermatButton) mFragmentView.findViewById(R.id.receive_button);
            send_button = (FermatButton) mFragmentView.findViewById(R.id.send_button);
            linear_layout_extra_user_receive = (LinearLayout) mFragmentView.findViewById(R.id.linear_layout_extra_user_receive);
            img_update = (ImageView) mFragmentView.findViewById(R.id.img_update);
            send_button.setOnClickListener(this);
            receive_button.setOnClickListener(this);
            linear_layout_extra_user_receive.setOnClickListener(this);
            if (typeface != null) {
                edit_text_name.setTypeface(typeface);
                text_view_address.setTypeface(typeface);
                receive_button.setTypeface(typeface);
                send_button.setTypeface(typeface);
            }
            if(lossProtectedWalletContact!=null)
            if(lossProtectedWalletContact.getActorType().equals(Actors.INTRA_USER)){
                linear_layout_extra_user_receive.setVisibility(View.GONE);
            }
            img_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(!addressIsTouch) {
                            addressIsTouch=true;
                            lossProtectedWalletManager.sendAddressExchangeRequest(
                                    lossProtectedWalletContact.getActorName(),
                                    Actors.INTRA_USER,
                                    lossProtectedWalletContact.getActorPublicKey(),
                                    lossProtectedWalletContact.getProfilePicture(),
                                    Actors.INTRA_USER,
                                    lossProtectedWalletSession.getIntraUserModuleManager().getPublicKey()
                                    , appSession.getAppPublicKey(),
                                    CryptoCurrency.BITCOIN,
                                    blockchainNetworkType
                            );

                            delayHandler.postDelayed(delay, TimeUnit.MINUTES.toMillis(DELAY_TIME));
                        }else{
                            Toast.makeText(getActivity(),"Address exchange sent, wait 2 minutes please",Toast.LENGTH_SHORT).show();
                        }
                    } catch (CantGetCryptoLossProtectedWalletException | CantListCryptoWalletIntraUserIdentityException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }


    /**
     * Setting up wallet contact value
     */
    private void setUpContact() {
        image_view_profile = (ImageView) mFragmentView.findViewById(R.id.image_view_profile);
        if (lossProtectedWalletContact != null) {
            if(image_view_profile!=null){
                try {
                        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(image_view_profile,getResources(),false);
                        bitmapWorkerTask.execute(lossProtectedWalletContact.getProfilePicture());
                }catch (Exception e){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Toast.makeText(getContext(),"Loading image error",Toast.LENGTH_SHORT).show();
                    }
                    e.printStackTrace();
                }
            }
            if (edit_text_name != null)
                edit_text_name.setText(lossProtectedWalletContact.getActorName());
            if (text_view_address != null) {
                if (lossProtectedWalletContact.getReceivedCryptoAddress().size() > 0) {

                       try {


                           if (lossProtectedWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress()== null){


                               lossProtectedWalletManager.sendAddressExchangeRequest(
                                       lossProtectedWalletContact.getActorName(),
                                       Actors.INTRA_USER,
                                       lossProtectedWalletContact.getActorPublicKey(),
                                       lossProtectedWalletContact.getProfilePicture(),
                                       Actors.INTRA_USER,
                                       lossProtectedWalletSession.getIntraUserModuleManager().getPublicKey()
                                       , appSession.getAppPublicKey(),
                                       CryptoCurrency.BITCOIN,
                                       blockchainNetworkType
                               );

                           img_update.setVisibility(View.VISIBLE);
                           receive_button.setVisibility(View.GONE);
                           send_button.setVisibility(View.GONE);


                       }else{
                           String address = lossProtectedWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress();
                          text_view_address.setText((address!=null)?address:"mnK7DuBQT3REr9bmfYcufTwjiAWfjwRwMf");
                           img_update.setVisibility(View.GONE);
                           receive_button.setVisibility(View.VISIBLE);
                           send_button.setVisibility(View.VISIBLE);
                       }

                       } catch (CantGetCryptoLossProtectedWalletException e) {
                           e.printStackTrace();
                       } catch (CantListCryptoWalletIntraUserIdentityException e) {
                           e.printStackTrace();
                       } catch (NullPointerException e) {

                           try {
                               lossProtectedWalletManager.sendAddressExchangeRequest(
                                       lossProtectedWalletContact.getActorName(),
                                       Actors.INTRA_USER,
                                       lossProtectedWalletContact.getActorPublicKey(),
                                       lossProtectedWalletContact.getProfilePicture(),
                                       Actors.INTRA_USER,
                                       lossProtectedWalletSession.getIntraUserModuleManager().getPublicKey()
                                       , appSession.getAppPublicKey(),
                                       CryptoCurrency.BITCOIN,
                                       blockchainNetworkType
                               );
                           } catch (CantGetCryptoLossProtectedWalletException e1) {
                               e1.printStackTrace();
                           } catch (CantListCryptoWalletIntraUserIdentityException e1) {
                               e1.printStackTrace();
                           }

                           img_update.setVisibility(View.VISIBLE);
                           receive_button.setVisibility(View.GONE);
                           send_button.setVisibility(View.GONE);

                       }
            }else{
                    img_update.setVisibility(View.VISIBLE);
                    receive_button.setVisibility(View.GONE);
                    send_button.setVisibility(View.GONE);
                }
            }else{
                text_view_address.setText("Waiting...");
                img_update.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Bitmap to byte[]
     *
     * @param bitmap Bitmap
     * @return byte array
     */
    private byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public void onUpdateViewOnUIThread(String code){
      try
        {
            //update contact address
            lossProtectedWalletManager = lossProtectedWalletSession.getModuleManager();

            lossProtectedWalletContact = lossProtectedWalletManager.findWalletContactById(UUID.fromString(code), lossProtectedWalletSession.getIntraUserModuleManager().getPublicKey());


            if(lossProtectedWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress() != null)
            {
                text_view_address.setText(lossProtectedWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress());
                img_update.setVisibility(View.GONE);
                receive_button.setVisibility(View.VISIBLE);
                send_button.setVisibility(View.VISIBLE);

            }

            lossProtectedWalletSession.setLastContactSelected(lossProtectedWalletContact);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
