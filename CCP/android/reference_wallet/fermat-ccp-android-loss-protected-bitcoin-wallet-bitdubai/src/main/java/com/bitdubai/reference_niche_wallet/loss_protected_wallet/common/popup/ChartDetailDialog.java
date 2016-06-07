package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dev Dan on 31/05/16.
 */

public class ChartDetailDialog extends Dialog implements View.OnClickListener
{

    public Activity activity;
    public Dialog d;
    private BitcoinLossProtectedWalletSpend spending;
    private int EARN_AND_LOST_MAX_DECIMAL_FORMAT = 6;
    private int EARN_AND_LOST_MIN_DECIMAL_FORMAT = 2;

    private ImageView earnOrLostImage;

    /**
     *  UI components
     */

    private Button button;
    private TextView txt_date;
    private TextView txt_amount;



    public ChartDetailDialog(Activity a,BitcoinLossProtectedWalletSpend spending)
    {
        super(a);
        this.activity = a;
        this.spending=spending;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();
    }

    private void setUpScreenComponents(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loss_detail);

        button = (Button) findViewById(R.id.accept_btn);
        txt_date = (TextView) findViewById(R.id.date);
        txt_amount =(TextView) findViewById(R.id.amount);
        earnOrLostImage = (ImageView) findViewById(R.id.earnOrLostImage);
        button.setOnClickListener(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:ss a", Locale.US);


        txt_date.setText(sdf.format(spending.getTimestamp()));

        //txt_amount.setText(WalletUtils.formatAmountString(amount));

        if (spending.getAmount() > 0){

            txt_amount.setText("USD "+
                    WalletUtils.formatBalanceStringWithDecimalEntry(
                            spending.getAmount(),
                            EARN_AND_LOST_MAX_DECIMAL_FORMAT,
                            EARN_AND_LOST_MIN_DECIMAL_FORMAT, ShowMoneyType.BITCOIN.getCode())+" earned");

            earnOrLostImage.setBackgroundResource(R.drawable.earning_icon);

        }else if (spending.getAmount()==0){

            txt_amount.setText("USD 0.00");
            earnOrLostImage.setVisibility(View.INVISIBLE);

        }else if (spending.getAmount()< 0){

            txt_amount.setText("USD "+WalletUtils.formatBalanceStringWithDecimalEntry(
                    spending.getAmount() * -1,
                    EARN_AND_LOST_MAX_DECIMAL_FORMAT,
                    EARN_AND_LOST_MIN_DECIMAL_FORMAT,ShowMoneyType.BITCOIN.getCode())+" lost");
            earnOrLostImage.setBackgroundResource(R.drawable.lost_icon);
        }

    }


    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.accept_btn){
            dismiss();
        }
    }


}
