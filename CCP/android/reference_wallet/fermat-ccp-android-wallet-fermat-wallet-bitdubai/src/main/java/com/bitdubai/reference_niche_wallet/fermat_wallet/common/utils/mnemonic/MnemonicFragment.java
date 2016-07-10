package com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.mnemonic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetMnemonicTextException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.reference_niche_wallet.fermat_wallet.session.FermatWalletSessionReferenceApp;



/**
 * Created by mati on 2016.02.20..
 */

public class MnemonicFragment extends AbstractFermatFragment<FermatWalletSessionReferenceApp,ResourceProviderManager> {


    /**
     * Platform
     */
    private FermatWallet fermatWallet;
    private ErrorManager errorManager;

    //constuctor
    public static MnemonicFragment newInstance() {
        return new MnemonicFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {

            fermatWallet = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        

        View view = inflater.inflate(R.layout.bitcoin_mnemonic_fragment_main,container,false);

        final EditText txt_mnemonic = (EditText)view.findViewById(R.id.txt_mnemonic);
//       final EditText mail = (EditText ) view.findViewById(R.id.text_mail);
//        FermatButton send_button= (FermatButton)view.findViewById(R.id.send_button);
//        final EditText encriptKey = (EditText ) view.findViewById(R.id.text_key);


        try {
            //get mnemonic text from Crypto Wallet Module
            txt_mnemonic.setText("");
            for (String s : fermatWallet.getMnemonicText()) {
                txt_mnemonic.append(s+" ");
            }


        } catch (CantGetMnemonicTextException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error Getting Mnemonic Text", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

//        send_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               //send mail
//                //encript mnemonic text to send by mail
//                final String emailTo= mail.getText().toString();
//                if (TextUtils.isEmpty(emailTo)) {
//                    return;
//                }else {
//                    try {
//
//                        Thread thread = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    sendMail(emailTo, WalletUtils.encrypt(txt_mnemonic.getText().toString(), encriptKey.getText().toString()));
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                        thread.start();
//
//                        Toast.makeText(getActivity(), "Private Key Sent", Toast.LENGTH_SHORT).show();
//                        mail.getText().clear();
//                    } catch (Exception e) {
//                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
//                    }
//                }
//            }
//
//        });
        return view;
    }
}
