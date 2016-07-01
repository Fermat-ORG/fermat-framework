package com.bitdubai.reference_niche_wallet.fermat_wallet.fragments;

import android.content.Context;
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

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletWalletContact;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.BitmapWorkerTask;
import com.bitdubai.reference_niche_wallet.fermat_wallet.session.SessionConstant;

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

public class ContactDetailFragment extends AbstractFermatFragment<ReferenceAppFermatSession<FermatWallet>,ResourceProviderManager> implements View.OnClickListener {


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
    private FermatWallet cryptoWallet;
    private ErrorManager errorManager;

    /**
     * DATA
     */
    private FermatWalletWalletContact cryptoWalletWalletContact;
    /**
     *  Resources
     */
    WalletResourcesProviderManager walletResourcesProviderManager;

    private ReferenceAppFermatSession<FermatWallet> fermatWalletSessionReferenceApp;

    private FermatButton send_button;
    private FermatButton receive_button;
    private ImageView img_update;
    private ImageView img_copy;
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
            fermatWalletSessionReferenceApp = appSession;
            setHasOptionsMenu(true);
            cryptoWalletWalletContact = (FermatWalletWalletContact) fermatWalletSessionReferenceApp.getData(SessionConstant.LAST_SELECTED_CONTACT);
            if(cryptoWalletWalletContact==null){
                onBack(null);
            }
            //typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
            errorManager = appSession.getErrorManager();
            cryptoWallet = appSession.getModuleManager();

            FermatWalletSettings fermatWalletSettings = null;

            fermatWalletSettings = fermatWalletSessionReferenceApp.getModuleManager().loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey());

            if(fermatWalletSettings != null) {

                if (fermatWalletSettings.getBlockchainNetworkType() == null) {
                    fermatWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                }
                fermatWalletSessionReferenceApp.getModuleManager().persistSettings(fermatWalletSessionReferenceApp.getAppPublicKey(), fermatWalletSettings);

            }

            blockchainNetworkType = fermatWalletSessionReferenceApp.getModuleManager().loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey()).getBlockchainNetworkType();
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
            mFragmentView = inflater.inflate(R.layout.fermat_contact_detail_base, container, false);

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
                    fermatWalletSessionReferenceApp.setData(SessionConstant.LAST_SELECTED_CONTACT, cryptoWalletWalletContact);
                    fermatWalletSessionReferenceApp.setData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS, false);
                    changeActivity(Activities.CCP_BITCOIN_FERMAT_WALLET_SEND_FORM_ACTIVITY, fermatWalletSessionReferenceApp.getAppPublicKey());
                }else{
                    Toast.makeText(getActivity(),"You don't have address to send\nplease wait to get it or touch the refresh button",Toast.LENGTH_SHORT).show();
                }
            }
            else if( id == R.id.receive_button){
                if(cryptoWalletWalletContact.getReceivedCryptoAddress().size() > 0) {
                    fermatWalletSessionReferenceApp.setData(SessionConstant.LAST_SELECTED_CONTACT, cryptoWalletWalletContact);
                    fermatWalletSessionReferenceApp.setData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS, false);
                    changeActivity(Activities.CCP_BITCOIN_FERMAT_WALLET_REQUEST_FORM_ACTIVITY, fermatWalletSessionReferenceApp.getAppPublicKey());
                }else{
                    Toast.makeText(getActivity(),"You don't have address to request\nplease wait to get it or touch the refresh button",Toast.LENGTH_SHORT).show();
                }
            }
            /*else if ( id == R.id.linear_layout_extra_user_receive){
                ReceiveFragmentDialog receiveFragmentDialog = new ReceiveFragmentDialog(
                        getActivity(),
                        cryptoWallet,
                        fermatWalletSessionReferenceApp.getErrorManager(),
                        cryptoWalletWalletContact,
                        cryptoWallet.getSelectedActorIdentity().getPublicKey(),
                        fermatWalletSessionReferenceApp.getAppPublicKey(),
                        blockchainNetworkType);
                receiveFragmentDialog.show();
            }*/

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
            img_copy = (ImageView) mFragmentView.findViewById(R.id.img_copy);
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
                                    cryptoWallet.getSelectedActorIdentity().getPublicKey()
                                    , appSession.getAppPublicKey(),
                                    CryptoCurrency.BITCOIN,
                                    blockchainNetworkType
                            );

                            delayHandler.postDelayed(delay, TimeUnit.MINUTES.toMillis(DELAY_TIME));
                        }else{
                            Toast.makeText(getActivity(),"Address exchange sent, wait 2 minutes please",Toast.LENGTH_SHORT).show();
                        }

                    } catch (CantGetSelectedActorIdentityException e) {
                        Toast.makeText(getActivity(),"CantGetSelectedActorIdentityException",Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    } catch (ActorIdentityNotSelectedException e) {
                        Toast.makeText(getActivity(),"ActorIdentityNotSelectedException",Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }

                }
            });

            img_copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        setClipboard(getActivity(), text_view_address.getText().toString());
                        Toast.makeText(getActivity(),"Address copied to clipbooard",Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
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

        //if api 17 set address font size
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
                text_view_address.setTextSize(10);

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
                                   cryptoWallet.getSelectedActorIdentity().getPublicKey()
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


                       } catch (NullPointerException e) {

                           try {
                               cryptoWallet.sendAddressExchangeRequest(
                                       cryptoWalletWalletContact.getActorName(),
                                       Actors.INTRA_USER,
                                       cryptoWalletWalletContact.getActorPublicKey(),
                                       cryptoWalletWalletContact.getProfilePicture(),
                                       Actors.INTRA_USER,
                                       cryptoWallet.getSelectedActorIdentity().getPublicKey()
                                       , appSession.getAppPublicKey(),
                                       CryptoCurrency.BITCOIN,
                                       blockchainNetworkType
                               );

                           } catch (CantGetSelectedActorIdentityException e1) {
                               e1.printStackTrace();
                           } catch (ActorIdentityNotSelectedException e1) {
                               e1.printStackTrace();
                           }

                           img_update.setVisibility(View.VISIBLE);
                           receive_button.setVisibility(View.GONE);
                           send_button.setVisibility(View.GONE);


                       } catch (CantGetSelectedActorIdentityException e) {
                           e.printStackTrace();
                       } catch (ActorIdentityNotSelectedException e) {
                           e.printStackTrace();
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
            if(!code.equals("BlockchainDownloadComplete"))
            {
                //update contact address
                cryptoWalletWalletContact = cryptoWallet.findWalletContactById(UUID.fromString(code), cryptoWallet.getSelectedActorIdentity().getPublicKey());


                if(cryptoWalletWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress() != null)
                {
                    text_view_address.setText(cryptoWalletWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress());
                    img_update.setVisibility(View.GONE);
                    receive_button.setVisibility(View.VISIBLE);
                    send_button.setVisibility(View.VISIBLE);

                }

                fermatWalletSessionReferenceApp.setData(SessionConstant.LAST_SELECTED_CONTACT, cryptoWalletWalletContact);

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    // copy text to clipboard
    private void setClipboard(Context context,String text) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData
                    .newPlainText("Address", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
