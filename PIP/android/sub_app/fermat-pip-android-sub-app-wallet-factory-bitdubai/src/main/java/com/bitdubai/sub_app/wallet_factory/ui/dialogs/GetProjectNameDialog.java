package com.bitdubai.sub_app.wallet_factory.ui.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.sub_app.wallet_factory.R;

/**
 * Created by francisco on 30/09/15.
 */
public class GetProjectNameDialog extends DialogFragment {

    private View rootView;
    private FermatEditText name;
    private DialogChooseNameListener callBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_clone_wallet, container, false);
        name = (FermatEditText) rootView.findViewById(R.id.project_name);
        rootView.findViewById(R.id.action_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null)
                    callBack.onCompleteInfo(name.getText().toString().trim());
            }
        });
        return rootView;
    }

    public void setCallBack(DialogChooseNameListener callBack) {
        this.callBack = callBack;
    }


    public interface DialogChooseNameListener {
        void onCompleteInfo(String name);
    }
}
