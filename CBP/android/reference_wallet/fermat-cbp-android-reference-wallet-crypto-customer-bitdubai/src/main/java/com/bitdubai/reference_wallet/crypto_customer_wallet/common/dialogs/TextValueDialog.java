package com.bitdubai.reference_wallet.crypto_customer_wallet.common.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;


/**
 * Created by nelson on 14/01/16.
 */
public class TextValueDialog extends FermatDialog<ReferenceAppFermatSession<CryptoCustomerWalletModuleManager>, ResourceProviderManager>
        implements View.OnClickListener {

    private FermatEditText editTextView;
    private int titleStringResource;
    private int hintStringResource;
    private String editTextValue;

    private OnClickAcceptListener acceptBtnListener;
    private boolean setTextFree;
    private NumberFormat numberFormat = DecimalFormat.getInstance();

    //TEXT COUNT
    private boolean activeTextCount = false;
    private int maxLengthTextCount = 100;
    FermatTextView textCount;


    //TEXT COUNT
    private final TextWatcher textWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textCount.setText(String.valueOf(maxLengthTextCount - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };

    public interface OnClickAcceptListener {
        void onClick(String editTextValue);
    }


    public TextValueDialog(Activity activity, ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> fermatSession, ResourceProviderManager resources) {
        super(activity, fermatSession, resources);

        hintStringResource = R.string.hint;
        titleStringResource = R.string.title;
        editTextValue = null;
    }

    public void configure(int titleRes, int hintRes) {
        titleStringResource = titleRes;
        hintStringResource = hintRes;
    }

    public void setAcceptBtnListener(OnClickAcceptListener acceptBtnListener) {
        this.acceptBtnListener = acceptBtnListener;
    }

    public void setTextFreeInputType(boolean setTextFree) {
        this.setTextFree = setTextFree;

        if (editTextView != null) {
            if (setTextFree) {
                editTextView.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_FLAG_MULTI_LINE);
            } else {
                editTextView.setInputType(TYPE_CLASS_NUMBER);
                if (giveMeDecimalSeparator().equals(".")) {
                    editTextView.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
                } else {
                    editTextView.setKeyListener(DigitsKeyListener.getInstance("0123456789,"));
                }
            }
        }
    }

    public void setEditTextValue(String editTextValue) {
        this.editTextValue = editTextValue;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ccw_text_dialog_cancel_btn) {
            dismiss();

        } else if (view.getId() == R.id.ccw_text_dialog_accept_btn) {
            if (acceptBtnListener != null)
                acceptBtnListener.onClick(editTextView.getText().toString());
            dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FermatTextView titleTextView = (FermatTextView) findViewById(R.id.ccw_text_dialog_title);
        titleTextView.setText(titleStringResource);
        FermatButton acceptBtn = (FermatButton) findViewById(R.id.ccw_text_dialog_accept_btn);
        acceptBtn.setOnClickListener(this);
        FermatButton cancelBtn = (FermatButton) findViewById(R.id.ccw_text_dialog_cancel_btn);
        cancelBtn.setOnClickListener(this);

        editTextView = (FermatEditText) findViewById(R.id.ccw_text_dialog_edit_text);
        editTextView.setHint(hintStringResource);

        //TEXT COUNT
        if (activeTextCount) {
            textCount = (FermatTextView) findViewById(R.id.ccw_text_dialog_edit_text_count);
            editTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthTextCount)});
            editTextView.addTextChangedListener(textWatcher);
            textCount.setText(String.valueOf(maxLengthTextCount));
            textCount.setVisibility(View.VISIBLE);
        }

        numberFormat.setMaximumFractionDigits(8);

        if (editTextValue != null) {
            if (editTextValue.equals("0.0") || editTextValue.equals("0,0") || editTextValue.equals("0")) {
                editTextView.setText("");
            } else {
                if (activeTextCount) {
                    editTextView.setText(editTextValue);
                } else {
                    editTextView.setText(fixFormat(editTextValue));
                }

            }
        }

        if (setTextFree) {
            editTextView.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_FLAG_MULTI_LINE);
        } else {
            editTextView.setInputType(TYPE_CLASS_NUMBER);
            if (giveMeDecimalSeparator().equals(".")) {
                editTextView.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
            } else {
                editTextView.setKeyListener(DigitsKeyListener.getInstance("0123456789,"));
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ccw_clause_text_dialog;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    //TEXT COUNT
    public void setTextCount(int maxLengthText) {
        this.maxLengthTextCount = maxLengthText;
        this.activeTextCount = true;
    }


    private String fixFormat(String value) {
        String doubleFormat;
        String commaOrDotFormat;
        try {
            if (compareLessThan1(value)) {
                numberFormat.setMaximumFractionDigits(8);
            } else {
                numberFormat.setMaximumFractionDigits(2);
            }

            final Double doubleValue = Double.valueOf(value);
            final String formattedDouble = numberFormat.format(doubleValue);
            final Number parsedDouble = numberFormat.parse(formattedDouble);
            final String parsedDoubleString = String.valueOf(parsedDouble);
            final BigDecimal bigDecimalDouble = new BigDecimal(parsedDoubleString);

            doubleFormat = String.valueOf(bigDecimalDouble);
            commaOrDotFormat = doubleFormat.replace(".", giveMeDecimalSeparator());

            return commaOrDotFormat;

        } catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }

    }

    private Boolean compareLessThan1(String value) {
        return BigDecimal.valueOf(Double.valueOf(value)).compareTo(BigDecimal.ONE) == -1;
    }

    String giveMeDecimalSeparator() {
        DecimalFormatSymbols symbols = ((DecimalFormat) numberFormat).getDecimalFormatSymbols();
        if (symbols.getDecimalSeparator() == '.') {
            return ".";
        } else {
            return ",";
        }
    }

}
