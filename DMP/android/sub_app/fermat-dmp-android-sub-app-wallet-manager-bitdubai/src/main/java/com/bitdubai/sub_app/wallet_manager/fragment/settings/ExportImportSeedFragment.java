package com.bitdubai.sub_app.wallet_manager.fragment.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Matias Furszyfer on 2016.05.24..
 */
public class ExportImportSeedFragment extends AbstractFermatFragment<DesktopSession,ResourceProviderManager> {

    private View root;
    private FermatTextView txt_mnemonic;
    private Spinner spinnerKeyType;
    private Button btn_show_seed;

    //todo: este int va a ser cambiando por algun metodo que me devuelva las vaults que estan disponibles por ahora 0=bitcoin, 1=fermat
    private int vaultType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.export_import_seed_layout,container,false);
        txt_mnemonic = (FermatTextView) root.findViewById(R.id.txt_mnemonic);
        spinnerKeyType = (Spinner) root.findViewById(R.id.spinner_key_type);
        btn_show_seed = (Button) root.findViewById(R.id.btn_show_seed);
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Bitcoin");
        spinnerArray.add("Fermat");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinnerKeyType.setAdapter(spinnerArrayAdapter);
        spinnerKeyType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vaultType = position;
            }
        });
        btn_show_seed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<String> mnemonicCode = appSession.getModuleManager().getMnemonicCode();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (String s : mnemonicCode) {
                                        txt_mnemonic.append(s+" ");
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
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public static AbstractFermatFragment newInstance() {
        return new ExportImportSeedFragment();
    }
}
