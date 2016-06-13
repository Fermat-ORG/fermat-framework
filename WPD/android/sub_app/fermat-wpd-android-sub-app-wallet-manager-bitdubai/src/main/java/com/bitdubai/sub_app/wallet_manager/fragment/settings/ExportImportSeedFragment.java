package com.bitdubai.sub_app.wallet_manager.fragment.settings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.desktop.InstalledDesktop;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_wpd.wallet_manager.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Matias Furszyfer on 2016.05.24..
 */
public class ExportImportSeedFragment extends AbstractFermatFragment<AbstractReferenceAppFermatSession<InstalledDesktop,WalletManager,ResourceProviderManager>,ResourceProviderManager> {

    private static final String TAG = "ImportFragment";
    private View root;

    //Export
    private FermatTextView txt_mnemonic;
    private Spinner spinnerKeyType;
    private Button btn_show_seed;

    //Import
    private EditText editText_mnemonic;
    private Button btn_import;

    //todo: este int va a ser cambiando por algun metodo que me devuelva las vaults que estan disponibles por ahora 0=bitcoin, 1=fermat
    private int vaultType;
    private int type;

    //Progress
    ProgressDialog dialog = null;

    public static ExportImportSeedFragment newInstance(int type) {
        ExportImportSeedFragment abstractFermatFragment =  new ExportImportSeedFragment();
        abstractFermatFragment.setType(type);
        return abstractFermatFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if(type==0){
            view = initExportView(inflater,container);
        }else if(type==1){
            view = initImportView(inflater,container);
        }

        root = view;
        return view;
    }

    private View initImportView(final LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.import_seed_layour,container,false);
        editText_mnemonic = (EditText) view.findViewById(R.id.editText_mnemonic);
//        Animation fadeIn = new AlphaAnimation(0, 1);
//        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
//        fadeIn.setDuration(1000);
//
//        Animation fadeOut = new AlphaAnimation(1, 0);
//        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
//        fadeOut.setStartOffset(1000);
//        fadeOut.setDuration(1000);
//
//        AnimationSet animation = new AnimationSet(false); //change to false
//        animation.addAnimation(fadeIn);
//        animation.addAnimation(fadeOut);
//        editText_mnemonic.setAnimation(animation);

        btn_import = (Button) view.findViewById(R.id.btn_import);
        btn_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] input = editText_mnemonic.getText().toString().split(" ");
                if(input.length>0) {
                    try {
                        final long date = Long.parseLong(input[input.length - 1]);
                        final List<String> mnemonic = Arrays.asList(Arrays.copyOfRange(input, 0, input.length-1));
                        ;//input[0-input.length-1]);
//                        mnemonic.remove(mnemonic.size() - 1);
                        final List<String> temp = mnemonic;
//                        FermatWorker fermatWorker = new FermatWorker() {
//                            @Override
//                            protected Object doInBackground() throws Exception {
//                                try {
//                                    Log.i(TAG,"Starting import");
//                                    appSession.getModuleManager().importMnemonicCode(temp, date, BlockchainNetworkType.getDefaultBlockchainNetworkType());
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                return null;
//                            }
//                        };
//                        fermatWorker.setCallBack(new FermatWorkerCallBack() {
//                            @Override
//                            public void onPostExecute(Object... result) {
//                                if(dialog!=null)dialog.dismiss();
//                                Toast.makeText(getActivity(), "Import completed, the money will be confirmed in a few minutes :)", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onErrorOccurred(Exception ex) {
//                                ex.printStackTrace();
//                            }
//                        });
//                        fermatWorker.execute();
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG,"Starting import");
                                try {
                                    appSession.getModuleManager().importMnemonicCode(temp, date, BlockchainNetworkType.getDefaultBlockchainNetworkType());
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (dialog != null) dialog.dismiss();
                                            Toast.makeText(getActivity(), "Import completed, the money will be confirmed in a few minutes :)", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } catch (Exception e) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (dialog != null) dialog.dismiss();
                                            Toast.makeText(getActivity(), "Import completed, the money will be confirmed in a few minutes :)", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                        dialog = ProgressDialog.show(getActivity(), "",
                                "Importing. Please wait...", true);
                    }catch (Exception e){
                        e.printStackTrace();
                        editText_mnemonic.animate();
                        Toast.makeText(getActivity(),"Import failed, Please fill this box",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    editText_mnemonic.animate();
                    Toast.makeText(getActivity(),"Import failed, Please fill this box",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private View initExportView(LayoutInflater inflater,ViewGroup container){
        View view = inflater.inflate(R.layout.export_import_seed_layout,container,false);
        txt_mnemonic = (FermatTextView) view.findViewById(R.id.txt_mnemonic);
        spinnerKeyType = (Spinner) view.findViewById(R.id.spinner_key_type);
        btn_show_seed = (Button) view.findViewById(R.id.btn_show_seed);
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Bitcoin");
        spinnerArray.add("Fermat");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinnerKeyType.setAdapter(spinnerArrayAdapter);
        spinnerKeyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vaultType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_show_seed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_mnemonic.setText("");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<String> mnemonicCode = appSession.getModuleManager().getMnemonicCode();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (String s : mnemonicCode) {
                                        txt_mnemonic.append(s + " ");
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setType(int type) {
        this.type = type;
    }
}
