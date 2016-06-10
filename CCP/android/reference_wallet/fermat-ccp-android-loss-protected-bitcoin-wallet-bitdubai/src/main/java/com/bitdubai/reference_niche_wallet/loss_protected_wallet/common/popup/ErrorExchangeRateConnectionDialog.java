package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;

/**
 * Created by Gian Barboza on 18/05/16.
 */
public class ErrorExchangeRateConnectionDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog d;

    /**
     *  UI components
     */

    Button cancel_btn;


    public ErrorExchangeRateConnectionDialog(Activity a) {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();
    }

    private void setUpScreenComponents(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exchange_rate_error_connection);

        cancel_btn = (Button) findViewById(R.id.cancel_btn);

        cancel_btn.setOnClickListener(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(0));

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel_btn){
            dismiss();
        }
    }
}
