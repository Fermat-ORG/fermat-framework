package com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by Yordin Alayn on 03.08.16.
 */
public class IdentityDialog extends FermatDialog<ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, ResourceProviderManager>
        implements View.OnClickListener {

    private FermatEditText editTextView;
    private int hintStringResource;
    private int titleString;
    private int descriptionString;
    private int description2String;
    private String editTextValue;

//    private ImageView dialogTitleIcon;
//    private ImageView dialogBanner;

    //TEXT COUNT
    private int maxLenghtTextCount = 50;
    FermatTextView textCount;

    private OnClickAcceptListener acceptBtnListener;

    //TEXT COUNT
    private final TextWatcher textWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textCount.setText(String.valueOf(maxLenghtTextCount - s.length()));
        }

        public void afterTextChanged(Editable s) { }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    };

    public interface OnClickAcceptListener {
        void onClick(String newValue);
    }


    public IdentityDialog(Activity activity, ReferenceAppFermatSession<CryptoBrokerWalletModuleManager> fermatSession, ResourceProviderManager resources) {
        super(activity, fermatSession, resources);

        titleString         = R.string.cbw_identity_dialog_title_btc;
        descriptionString   = R.string.cbw_identity_dialog_description_btc;
        description2String  = R.string.cbw_identity_dialog_description2_btc;
        hintStringResource  = R.string.cbw_identity_dialog_hint;
        editTextValue = null;

    }

    public void configure(int titleRes, int descriptionRes, int description2Res) {
        titleString         = titleRes;
        descriptionString   = descriptionRes;
        description2String  = description2Res;
    }

    public void setAcceptBtnListener(OnClickAcceptListener acceptBtnListener) {
        this.acceptBtnListener = acceptBtnListener;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.cbw_text_dialog_done_btn) {
            if (acceptBtnListener != null)
                acceptBtnListener.onClick(editTextView.getText().toString());
            dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FermatTextView dialogTitle = (FermatTextView) findViewById(R.id.cbw_text_dialog_title);
        dialogTitle.setText(titleString);

        FermatTextView dialogDescription = (FermatTextView) findViewById(R.id.cbw_text_dialog_description);
        dialogDescription.setText(descriptionString);

        FermatTextView dialogDescription2 = (FermatTextView) findViewById(R.id.cbw_text_dialog_description2);
        dialogDescription2.setText(description2String);

        FermatButton acceptBtn = (FermatButton) findViewById(R.id.cbw_text_dialog_done_btn);
        acceptBtn.setOnClickListener(this);

        editTextView = (FermatEditText) findViewById(R.id.cbw_text_dialog_edit_text);
        editTextView.setHint(hintStringResource);
        if (editTextValue != null)
            editTextView.setText(editTextValue);

        //TEXT COUNT
        textCount = (FermatTextView) findViewById(R.id.cbw_text_dialog_edit_text_count);
        editTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenghtTextCount)});
        editTextView.addTextChangedListener(textWatcher);
        textCount.setText(String.valueOf(maxLenghtTextCount));
        textCount.setVisibility(View.VISIBLE);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.cbw_identity_dialog;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }
}
