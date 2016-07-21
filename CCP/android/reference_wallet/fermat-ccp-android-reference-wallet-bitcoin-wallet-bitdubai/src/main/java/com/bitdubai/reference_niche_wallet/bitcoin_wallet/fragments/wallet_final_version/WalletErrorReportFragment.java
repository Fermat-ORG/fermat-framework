package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by natalia on 05/04/16.
 */
public class WalletErrorReportFragment extends AbstractFermatFragment {

    // Fermat Managers

    private ErrorManager errorManager;

    private ReferenceAppFermatSession referenceWalletSession;

    private Button okBtn;
    private EditText messageEdit;
    private EditText copyEdit;
    private Button cancelBtn;
    private Toolbar toolbar;
    private String textToCopy;
    private  FermatWorker worker;

    public static WalletErrorReportFragment newInstance() { return new WalletErrorReportFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            referenceWalletSession = (ReferenceAppFermatSession) appSession;
            errorManager = appSession.getErrorManager();

            errorManager = appSession.getErrorManager();
            toolbar = getToolbar();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    private static final String TAG = "CCP log";
    private static final String processId = Integer.toString(android.os.Process
            .myPid());
    public static StringBuilder getLog() {

        int lineNumber = 0;

        StringBuilder builder = new StringBuilder();

        try {
            String[] command = new String[] { "logcat", "-v", "threadtime" };

            Process process = Runtime.getRuntime().exec(command);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processId)
                        /*&& ((line.contains("Error")
                        || line.contains("error")
                        || line.contains("ERROR"))
                            || line.contains("ERROR"))*/) {
                    builder.append(line);
                    lineNumber++;
                }
                if(lineNumber==1000){
                    break;
                }
            }
        } catch (IOException ex) {
            Log.e(TAG, "getLog failed", ex);
        }

        return builder;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.send_errors_report, container, false);
        messageEdit = (EditText) layout.findViewById(R.id.messageEdit);
        copyEdit = (EditText) layout.findViewById(R.id.copyEdit);
        okBtn = (Button) layout.findViewById(R.id.okButton);
        cancelBtn = (Button) layout.findViewById(R.id.cancelButton);
        copyEdit.setText("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY, appSession.getAppPublicKey());
            }
        });


        copyEdit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(copyEdit.getText().length() == 0) {
                    try {
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Please wait");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        worker = new FermatWorker() {
                            @Override
                            protected Object doInBackground() throws Exception {
                                return getMoreData();
                            }
                        };
                        worker.setContext(getActivity());
                        worker.setCallBack(new FermatWorkerCallBack() {
                            @SuppressWarnings("unchecked")
                            @Override
                            public void onPostExecute(Object... result) {
                                if (result != null && result.length > 0) {
                                    progressDialog.dismiss();
                                    if (getActivity() != null) {
                                        textToCopy = (String) result[0];
                                        copyEdit.setText(textToCopy);
                                    }
                                } else copyEdit.setText("No log");
                            }

                            @Override
                            public void onErrorOccurred(Exception ex) {
                                progressDialog.dismiss();
                                if (getActivity() != null)
                                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                ex.printStackTrace();
                            }
                        });
                        worker.execute();
                    } catch (Exception e) {
                        if (errorManager != null)
                            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
                    }
                }
                return true;
            }
        });


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String messageText = messageEdit.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }else {
                    try {
                        //TODO: esto no se hace así pero bueno, me hacen arreglarlo y es tarde...
                        //TODO: y otra cosa, el mail que tiene que ingresar es a donde lo quiere mandar.
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    sendErrorReport(messageText);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();

                        Toast.makeText(getActivity(), "Report Sent", Toast.LENGTH_SHORT).show();
                        messageEdit.getText().clear();
                    } catch (Exception e) {
                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY, appSession.getAppPublicKey());
                } catch (Exception e) {
                    errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
                }
            }
        });

        return layout;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // Inflate the menu items
        //inflater.inflate(R.menu.chat_menu, menu);
    }

    private synchronized String getMoreData() {
        String dataSet = "";
        try {
            dataSet= getLog().toString();
        }catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
        }
        return dataSet;
    }

}