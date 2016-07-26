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
import android.widget.LinearLayout;
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
    private FermatTextView txt_mnemonic_date;
    private Spinner spinnerKeyType;
    private Button btn_show_seed;
    private LinearLayout mnemonicLinear;

    //Import
    private EditText editText_mnemonic;
    private EditText editText_mnemonic_date;
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
        editText_mnemonic_date = (EditText) view.findViewById(R.id.editText_mnemonic_date);

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

                    try {
                        String[] mNemonceWord = editText_mnemonic.getText().toString().split(" ");
                        final long date = Long.parseLong(editText_mnemonic_date.getText().toString());
                            if(mNemonceWord.length>0 || date > 0) {
                            //final long date = Long.parseLong(input[input.length - 1]);
                            final List<String> mnemonicWords = Arrays.asList(mNemonceWord);
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i(TAG,"Starting import");
                                    try {
                                        appSession.getModuleManager().importMnemonicCode(mnemonicWords, date, BlockchainNetworkType.getDefaultBlockchainNetworkType());
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

                        }else {
                            editText_mnemonic.animate();
                            editText_mnemonic_date.animate();
                            Toast.makeText(getActivity(),"Import failed, Please fill this box",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
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
        txt_mnemonic_date = (FermatTextView) view.findViewById(R.id.txt_mnemonic_date);
        spinnerKeyType = (Spinner) view.findViewById(R.id.spinner_key_type);
        btn_show_seed = (Button) view.findViewById(R.id.btn_show_seed);
        mnemonicLinear = (LinearLayout) view.findViewById(R.id.mnemonic_linear);

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
                txt_mnemonic_date.setText("");
                if (mnemonicLinear.getVisibility() == View.GONE){
                    mnemonicLinear.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            txt_mnemonic.setText(appSession.getModuleManager().getMnemonicPhrase());
                                            txt_mnemonic_date.setText(String.valueOf(appSession.getModuleManager().getCreationTimeSeconds()));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

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
