package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

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

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.ReceiveFragmentDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.BitmapWorkerTask;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;

import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static android.widget.Toast.makeText;

/**
 * Contact Detail Fragment.
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class ContactDetailFragment extends AbstractFermatFragment<ReferenceWalletSession,ResourceProviderManager> implements View.OnClickListener {


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
    private CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    /**
     * DATA
     */
    private CryptoWalletWalletContact cryptoWalletWalletContact;
    /**
     *  Resources
     */
    WalletResourcesProviderManager walletResourcesProviderManager;
    private ReferenceWalletSession referenceWalletSession;
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
    private BlockchainNetworkType blockchainNetworkType;


    public static ContactDetailFragment newInstance() {
        ContactDetailFragment f = new ContactDetailFragment();
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            referenceWalletSession = appSession;
            setHasOptionsMenu(true);
            cryptoWalletWalletContact = referenceWalletSession.getLastContactSelected();
            if(cryptoWalletWalletContact==null){
                onBack(null);
            }
            //typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
            errorManager = appSession.getErrorManager();
            cryptoWallet = appSession.getModuleManager();

            BitcoinWalletSettings bitcoinWalletSettings = null;

            bitcoinWalletSettings = referenceWalletSession.getModuleManager().loadAndGetSettings(referenceWalletSession.getAppPublicKey());

            if(bitcoinWalletSettings != null) {

                if (bitcoinWalletSettings.getBlockchainNetworkType() == null) {
                    bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                }
                referenceWalletSession.getModuleManager().persistSettings(referenceWalletSession.getAppPublicKey(), bitcoinWalletSettings);

            }

            blockchainNetworkType = referenceWalletSession.getModuleManager().loadAndGetSettings(referenceWalletSession.getAppPublicKey()).getBlockchainNetworkType();
            System.out.println("Network Type"+blockchainNetworkType);

        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            makeText(getActivity(), "Oooops! recovering from system error",Toast.LENGTH_SHORT).show();
        }
//        /* Load Wallet Contact */
//        walletContact = CollectionUtils.find(getWalletContactList(), new Predicate<WalletContact>() {
//            @Override
//            public boolean evaluate(WalletContact walletContact) {
//                try {
//                    return walletContact.name.equalsIgnoreCase(accountName);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                return false;
//            }
//        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{
            mFragmentView = inflater.inflate(R.layout.contact_detail_base, container, false);

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
                if(cryptoWalletWalletContact.getReceivedCryptoAddress().size() > 0) {
                    referenceWalletSession.setLastContactSelected(cryptoWalletWalletContact);
                    referenceWalletSession.setData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS, false);
                    changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY, referenceWalletSession.getAppPublicKey());
                }else{
                    Toast.makeText(getActivity(),"You don't have address to send\nplease wait to get it or touch the refresh button",Toast.LENGTH_SHORT).show();
                }
            }
            else if( id == R.id.receive_button){
                if(cryptoWalletWalletContact.getReceivedCryptoAddress().size() > 0) {
                    referenceWalletSession.setLastContactSelected(cryptoWalletWalletContact);
                    referenceWalletSession.setData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS, false);
                    changeActivity(Activities.CCP_BITCOIN_WALLET_REQUEST_FORM_ACTIVITY, referenceWalletSession.getAppPublicKey());
                }else{
                    Toast.makeText(getActivity(),"You don't have address to request\nplease wait to get it or touch the refresh button",Toast.LENGTH_SHORT).show();
                }
            }
            else if ( id == R.id.linear_layout_extra_user_receive){
                ReceiveFragmentDialog receiveFragmentDialog = new ReceiveFragmentDialog(
                        getActivity(),
                        cryptoWallet,
                        referenceWalletSession.getErrorManager(),
                        cryptoWalletWalletContact,
                        referenceWalletSession.getIntraUserModuleManager().getPublicKey(),
                        referenceWalletSession.getAppPublicKey(),
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
            if(cryptoWalletWalletContact!=null)
            if(cryptoWalletWalletContact.getActorType().equals(Actors.INTRA_USER)){
                linear_layout_extra_user_receive.setVisibility(View.GONE);
            }
            img_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(!addressIsTouch) {
                            addressIsTouch=true;
                            cryptoWallet.sendAddressExchangeRequest(
                                    cryptoWalletWalletContact.getActorName(),
                                    Actors.INTRA_USER,
                                    cryptoWalletWalletContact.getActorPublicKey(),
                                    cryptoWalletWalletContact.getProfilePicture(),
                                    Actors.INTRA_USER,
                                    referenceWalletSession.getIntraUserModuleManager().getPublicKey()
                                    , appSession.getAppPublicKey(),
                                    CryptoCurrency.BITCOIN,
                                    blockchainNetworkType
                            );

                            delayHandler.postDelayed(delay, TimeUnit.MINUTES.toMillis(DELAY_TIME));
                        }else{
                            Toast.makeText(getActivity(),"Address exchange sent, wait 2 minutes please",Toast.LENGTH_SHORT).show();
                        }
                    } catch (CantGetCryptoWalletException | CantListCryptoWalletIntraUserIdentityException e) {
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
        if (cryptoWalletWalletContact != null) {
            if(image_view_profile!=null){
                try {
                          //  Bitmap bitmapDrawable = BitmapFactory.decodeByteArray(cryptoWalletWalletContact.getProfilePicture(), 0, cryptoWalletWalletContact.getProfilePicture().length);// MemoryUtils.decodeSampledBitmapFromByteArray(cryptoWalletWalletContact.getProfilePicture(),image_view_profile.getMaxWidth(),image_view_profile.getMaxHeight());
                          //  bitmapDrawable = Bitmap.createScaledBitmap(bitmapDrawable, image_view_profile.getWidth(), 200, true);
                           // image_view_profile.setImageBitmap(bitmapDrawable);
                        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(image_view_profile,getResources(),false);
                        bitmapWorkerTask.execute(cryptoWalletWalletContact.getProfilePicture());
                }catch (Exception e){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Toast.makeText(getContext(),"Loading image error",Toast.LENGTH_SHORT).show();
                    }
                    e.printStackTrace();
                }
            }
            if (edit_text_name != null)
                edit_text_name.setText(cryptoWalletWalletContact.getActorName());
            if (text_view_address != null) {
                if (cryptoWalletWalletContact.getReceivedCryptoAddress().size() > 0) {



                       try {


                           if (cryptoWalletWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress()== null){


                           cryptoWallet.sendAddressExchangeRequest(
                                   cryptoWalletWalletContact.getActorName(),
                                   Actors.INTRA_USER,
                                   cryptoWalletWalletContact.getActorPublicKey(),
                                   cryptoWalletWalletContact.getProfilePicture(),
                                   Actors.INTRA_USER,
                                   referenceWalletSession.getIntraUserModuleManager().getPublicKey()
                                   , appSession.getAppPublicKey(),
                                   CryptoCurrency.BITCOIN,
                                   blockchainNetworkType
                           );

                           img_update.setVisibility(View.VISIBLE);
                           receive_button.setVisibility(View.GONE);
                           send_button.setVisibility(View.GONE);


                       }else{
                           String address = cryptoWalletWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress();
                           //TODO: si la address es nula hay que ver porqué es
                           text_view_address.setText((address!=null)?address:"mnK7DuBQT3REr9bmfYcufTwjiAWfjwRwMf");
                           img_update.setVisibility(View.GONE);
                           receive_button.setVisibility(View.VISIBLE);
                           send_button.setVisibility(View.VISIBLE);
                       }

                       } catch (CantGetCryptoWalletException e) {
                           e.printStackTrace();
                       } catch (CantListCryptoWalletIntraUserIdentityException e) {
                           e.printStackTrace();
                       } catch (NullPointerException e) {

                           try {
                               cryptoWallet.sendAddressExchangeRequest(
                                       cryptoWalletWalletContact.getActorName(),
                                       Actors.INTRA_USER,
                                       cryptoWalletWalletContact.getActorPublicKey(),
                                       cryptoWalletWalletContact.getProfilePicture(),
                                       Actors.INTRA_USER,
                                       referenceWalletSession.getIntraUserModuleManager().getPublicKey()
                                       , appSession.getAppPublicKey(),
                                       CryptoCurrency.BITCOIN,
                                       blockchainNetworkType
                               );
                           } catch (CantGetCryptoWalletException e1) {
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
            cryptoWalletWalletContact = cryptoWallet.findWalletContactById(UUID.fromString(code), referenceWalletSession.getIntraUserModuleManager().getPublicKey());


            if(cryptoWalletWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress() != null)
            {
                text_view_address.setText(cryptoWalletWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress());
                img_update.setVisibility(View.GONE);
                receive_button.setVisibility(View.VISIBLE);
                send_button.setVisibility(View.VISIBLE);

            }

            referenceWalletSession.setLastContactSelected(cryptoWalletWalletContact);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
