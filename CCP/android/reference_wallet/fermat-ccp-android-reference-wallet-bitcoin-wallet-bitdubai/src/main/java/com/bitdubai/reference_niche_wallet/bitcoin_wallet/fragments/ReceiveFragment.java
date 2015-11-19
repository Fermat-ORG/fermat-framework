//package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;
//
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.os.Looper;
//import android.support.annotation.NonNull;
//import android.app.Fragment;
//import android.text.Editable;
//import android.text.TextWatcher;
//
//import android.view.LayoutInflater;
//
//import android.view.View;
//import android.view.ViewGroup;
//
//import android.widget.AdapterView;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
//import com.bitdubai.fermat_api.FermatException;
//import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
//import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
//import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
//import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
//import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
//import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
//import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
//
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
//import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
//import CantGetAllWalletContactsException;
//import CantGetCryptoWalletException;
//import CantRequestCryptoAddressException;
//import CryptoWallet;
//import CryptoWalletManager;
//import CryptoWalletWalletContact;
//import ErrorManager;
//import UnexpectedUIExceptionSeverity;
//import UnexpectedWalletExceptionSeverity;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
//import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
//import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
//import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
//
//import java.util.ArrayList;
//import java.util.EnumMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;
//
///**
// * Created by Natalia on 02/06/2015.
// */
//public class ReceiveFragment extends Fragment {
//
//
//    /**
//     * Wallet session
//     */
//    WalletSession walletSession;
//
//    private static final String ARG_POSITION = "position";
//
//    /**
//     * Screen members
//     */
//    private View rootView;
//
//    private AutoCompleteTextView autocompleteContacts;
//
//    /**
//     * Wallet contact Adapter
//     */
//    private WalletContactListAdapter adapter;
//
//
//    /**
//     * font Style
//     */
//    Typeface tf;
//
//
//    private String user_address_wallet = "";
//    final int colorQR = Color.BLACK;
//    final int colorBackQR = Color.WHITE;
//    final int width = 400;
//    final int height = 400;
//
//    /**
//     * Hardcoded walletPublicKey and user_id
//     */
//    String walletPublicKey = "reference_wallet";
//    String user_id = UUID.fromString("afd0647a-87de-4c56-9bc9-be736e0c5059").toString();
//
//    /**
//     * DealsWithWalletModuleCryptoWallet Interface member variables.
//     */
//
//    private CryptoWalletManager cryptoWalletManager;
//    private CryptoWallet cryptoWallet;
//
//    /**
//     * Error Manager
//     */
//
//    private ErrorManager errorManager;
//
//
//    private WalletContact walletContact;
//
//    public boolean fromContacts = false;
//
//    /**
//     * Resources
//     */
//    private WalletResourcesProviderManager walletResourcesProviderManager;
//
//
//    public static ReceiveFragment newInstance(int position, ReferenceWalletSession walletSession, WalletResourcesProviderManager walletResourcesProviderManager) {
//        ReceiveFragment f = new ReceiveFragment();
//        f.setWalletSession(walletSession);
//        Bundle b = new Bundle();
//        b.putInt(ARG_POSITION, position);
//        f.setArguments(b);
//        f.setWalletResourcesProviderManager(walletResourcesProviderManager);
//        return f;
//    }
//    public static ReceiveFragment newInstance(int position,ReferenceWalletSession walletSession) {
//        ReceiveFragment f = new ReceiveFragment();
//        f.setWalletSession(walletSession);
//        f.setWalletContact(walletSession.getLastContactSelected());
//        Bundle b = new Bundle();
//        b.putInt(ARG_POSITION, position);
//        f.setArguments(b);
//        return f;
//    }
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
//        errorManager = walletSession.getErrorManager();
//        try {
//
//            cryptoWalletManager = walletSession.getCryptoWalletManager();
//            cryptoWallet = cryptoWalletManager.getCryptoWallet();
//
//        } catch (CantGetCryptoWalletException e) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
//            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception ex) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
//            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_receive, container, false);
//
//        // request_address button definition
//        final Button requestAdressBtn = (Button) rootView.findViewById(R.id.request_btn);
//        requestAdressBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                requestAddress(null);
//            }
//        });
//        requestAdressBtn.setTypeface(tf);
//
//
//        // share_address button definition
//        final Button shareAddressBtn = (Button) rootView.findViewById(R.id.share_btn);
//        shareAddressBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                shareAddress();
//            }
//        });
//        shareAddressBtn.setTypeface(tf);
//
//
//        // share_address button definition
//        final TextView stringAddressTextView = (TextView) rootView.findViewById(R.id.string_address);
//        stringAddressTextView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                copyToClipboard();
//            }
//        });
//        stringAddressTextView.setTypeface(tf);
//
//        autocompleteContacts = (AutoCompleteTextView) rootView.findViewById(R.id.autocomplete_contacts);
//
//        adapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, getWalletContactList());
//        autocompleteContacts.setAdapter(adapter);
//        autocompleteContacts.setTypeface(tf);
//
//        autocompleteContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                requestAddress((WalletContact) arg0.getItemAtPosition(position));
//            }
//        });
//
//        autocompleteContacts.addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable s) {
//                resetForm();
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//        });
//
//
//        return rootView;
//    }
//
//    private List<WalletContact> getWalletContactList() {
//        List<WalletContact> contacts = new ArrayList<>();
//
//        try {
//            List<CryptoWalletWalletContact> walletContactRecords = cryptoWallet.listWalletContacts(walletPublicKey);
//            for (CryptoWalletWalletContact wcr : walletContactRecords) {
//                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), wcr.getReceivedCryptoAddress().get(0).getAddress()));
//            }
//        } catch (CantGetAllWalletContactsException e) {
//            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//            showMessage(getActivity(),"CantGetAllWalletContactsException- " + e.getMessage());
//        }
//        return contacts;
//    }
//
//    public void resetForm() {
//        ImageView imageQR = (ImageView) rootView.findViewById(R.id.qr_code);
//        imageQR.setVisibility(View.GONE);
//        TextView string_address = (TextView) rootView.findViewById(R.id.string_address);
//        string_address.setVisibility(View.GONE);
//        Button share_btn = (Button) rootView.findViewById(R.id.share_btn);
//        share_btn.setEnabled(false);
//        share_btn.setFocusable(false);
//        share_btn.setTextColor(Color.parseColor("#b46a54"));
//    }
//
//    public void requestAddress(WalletContact walletcontact) {
//        if (autocompleteContacts != null && autocompleteContacts.getText().toString() != null && !autocompleteContacts.getText().toString().equals("")) {
//            if (walletcontact == null) {
//                getWalletAddress(autocompleteContacts.getText().toString());
//            } else {
//                getWalletAddress(walletcontact);
//            }
//            showQRCodeAndAddress();
//        } else {
//            Toast.makeText(getActivity().getApplicationContext(), "Enter a name to share your address", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void shareAddress() {
//        Intent intent2 = new Intent();
//        intent2.setAction(Intent.ACTION_SEND);
//        intent2.setType("text/plain");
//        intent2.putExtra(Intent.EXTRA_TEXT, user_address_wallet);
//        startActivity(Intent.createChooser(intent2, "Share via"));
//    }
//
//    private void showQRCodeAndAddress() {
//        try {
//            // set qr image
//            ImageView imageQR = (ImageView) rootView.findViewById(R.id.qr_code);
//            // get qr image
//
//
//            Bitmap bitmapQR = generateBitmap(user_address_wallet,width, height, MARGIN_AUTOMATIC, colorQR, colorBackQR);
//
//            imageQR.setImageBitmap(bitmapQR);
//            // show qr image
//            imageQR.setVisibility(View.VISIBLE);
//
//            // set string_address
//            TextView string_address = (TextView) rootView.findViewById(R.id.string_address);
//            string_address.setText(user_address_wallet);
//            // show string_address
//            string_address.setVisibility(View.VISIBLE);
//
//            // show share_btn
//            Button share_btn = (Button) rootView.findViewById(R.id.share_btn);
//            share_btn.setEnabled(true);
//            share_btn.setFocusable(true);
//            share_btn.setTextColor(Color.parseColor("#72af9c"));
//        } catch (WriterException writerException) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(writerException));
//            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    private void getWalletAddress(String contact_name) {
//        try {
//            //TODO parameters deliveredByActorId deliveredByActorType harcoded..
//            CryptoAddress cryptoAddress = cryptoWallet.requestAddressToNewExtraUser(
//                    user_id,
//                    Actors.INTRA_USER,
//                    contact_name,
//                    Platforms.CRYPTO_CURRENCY_PLATFORM,
//                    VaultType.CRYPTO_CURRENCY_VAULT,
//                    "BITV",
//                    walletPublicKey,
//                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET
//            ); user_address_wallet = cryptoAddress.getAddress();
//        } catch (CantRequestCryptoAddressException e) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
//            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//    private void getWalletAddress(WalletContact walletContact) {
//        try {
//            //TODO parameters deliveredByActorId deliveredByActorType harcoded..
//            CryptoAddress cryptoAddress = cryptoWallet.requestAddressToKnownUser(
//                    user_id,
//                    Actors.INTRA_USER,
//                    walletContact.actorPublicKey,
//                    Actors.EXTRA_USER,
//                    Platforms.CRYPTO_CURRENCY_PLATFORM,
//                    VaultType.CRYPTO_CURRENCY_VAULT,
//                    "BITV",
//                    walletPublicKey,
//                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET
//            );
//            user_address_wallet = cryptoAddress.getAddress();
//        } catch (CantRequestCryptoAddressException e) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
//            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//    /**
//     * copy wallet address to clipboard
//     */
//
//    public void copyToClipboard() {
//
//        ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//        ClipData myClip = ClipData.newPlainText("text", this.user_address_wallet);
//        myClipboard.setPrimaryClip(myClip);
//        Toast.makeText(getActivity().getApplicationContext(), "Text Copied",
//                Toast.LENGTH_SHORT).show();
//    }
//
//    /**
//     * Allow the zxing engine use the default argument for the margin variable
//     */
//    static public int MARGIN_AUTOMATIC = -1;
//
//    /**
//     * Set no margin to be added to the QR code by the zxing engine
//     */
//    static public int MARGIN_NONE = 0;
//
//    /**
//     * Encode a string into a QR Code and return a bitmap image of the QR code
//     *
//     * @param contentsToEncode String to be encoded, this will often be a URL, but could be any string
//     * @param imageWidth       number of pixels in width for the resultant image
//     * @param imageHeight      number of pixels in height for the resultant image
//     * @param marginSize       the EncodeHintType.MARGIN parameter into zxing engine
//     * @param color            data color for QR code
//     * @param colorBack        background color for QR code
//     * @return bitmap containing QR code image
//     * @throws com.google.zxing.WriterException zxing engine is unable to create QR code data
//     * @throws IllegalStateException            when executed on the UI thread
//     */
//    static public Bitmap generateBitmap(@NonNull String contentsToEncode,
//                                        int imageWidth, int imageHeight,
//                                        int marginSize, int color, int colorBack)
//            throws WriterException, IllegalStateException {
//
//        if (Looper.myLooper() == Looper.getMainLooper()) {
//            // throw new IllegalStateException("Should not be invoked from the UI thread");
//        }
//
//        Map<EncodeHintType, Object> hints = null;
//        if (marginSize != MARGIN_AUTOMATIC) {
//            hints = new EnumMap<>(EncodeHintType.class);
//            // We want to generate with a custom margin size
//            hints.put(EncodeHintType.MARGIN, marginSize);
//        }
//
//        MultiFormatWriter writer = new MultiFormatWriter();
//        BitMatrix result = writer.encode(contentsToEncode, BarcodeFormat.QR_CODE, imageWidth, imageHeight, hints);
//
//        final int width = result.getWidth();
//        final int height = result.getHeight();
//        int[] pixels = new int[width * height];
//        for (int y = 0; y < height; y++) {
//            int offset = y * width;
//            for (int x = 0; x < width; x++) {
//                pixels[offset + x] = result.get(x, y) ? color : colorBack;
//            }
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
//    }
//
//
//    public void setWalletSession(WalletSession walletSession) {
//        this.walletSession = walletSession;
//    }
//
//    public void setWalletContact(WalletContact walletContact) {
//        this.walletContact = walletContact;
//    }
//
//    public void setWalletResourcesProviderManager(WalletResourcesProviderManager walletResourcesProviderManager) {
//        this.walletResourcesProviderManager = walletResourcesProviderManager;
//    }
//}