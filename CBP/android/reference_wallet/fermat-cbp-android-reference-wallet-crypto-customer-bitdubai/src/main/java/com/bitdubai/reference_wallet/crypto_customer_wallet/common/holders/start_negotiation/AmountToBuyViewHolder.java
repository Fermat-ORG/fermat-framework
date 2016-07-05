package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TransactionFee;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.math.BigDecimal;
import java.util.Map;


/**
 * Created by nelson on 10/01/16.
 */
public class AmountToBuyViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private TextView currencyToBuyTextValue;
    private TextView buyingText;
    private FermatButton buyingValue;
    private boolean paymentBuy;
    private TextView currency_total;
    private TextView currency_fee_miner;
    private TextView feeMiner;
    private TextView total_after_fee;
    private RadioGroup fee_speed_group;
    private String feeAmount;


    public AmountToBuyViewHolder(View itemView) {
        super(itemView);

        this.paymentBuy = Boolean.TRUE;

        currencyToBuyTextValue  = (TextView) itemView.findViewById(R.id.ccw_currency_to_buy);
        buyingText              = (TextView) itemView.findViewById(R.id.ccw_buying_text);
        buyingValue             = (FermatButton) itemView.findViewById(R.id.ccw_buying_value);



        buyingValue.setOnClickListener(this);
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation data, ClauseInformation clause, int position) {
        super.bindData(data, clause, position);
        ClauseType currencyType = ClauseType.CUSTOMER_CURRENCY;

        final Map<ClauseType, ClauseInformation> clauses = data.getClauses();
        final ClauseInformation currencyToBuy = clauses.get(currencyType);
        BigDecimal totalToPay;

        clauses.get(ClauseType.CUSTOMER_CURRENCY)

        int buyingTextValue = R.string.buying_text;

        if (!paymentBuy) {
            currencyType = ClauseType.BROKER_CURRENCY;
            buyingTextValue = R.string.paying_text;
            //Notification of fee miner when customer pay with BTC
            if(CryptoCurrency.codeExists(clauses.get(currencyType).getValue())){

                feeMiner=(TextView) itemView.findViewById(R.id.ccw_miner_fee);
                currency_fee_miner=(TextView) itemView.findViewById(R.id.ccw_currency_fee_miner);
                currency_total=(TextView) itemView.findViewById(R.id.ccw_currency_total);
                total_after_fee=(TextView) itemView.findViewById(R.id.ccw_total_after_fee);
                fee_speed_group=(RadioGroup)itemView.findViewById(R.id.ccw_fee_speed_group);

                fee_speed_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        if (checkedId == R.id.ccw_send_speed_low){
                            feeAmount= TransactionFee.getByCode("LOW"+currencyToBuy.getValue());
                        }else if (checkedId == R.id.ccw_send_speed_normal){
                            feeAmount= TransactionFee.getByCode("NORMAL"+currencyToBuy.getValue());
                        }else if (checkedId == R.id.ccw_send_speed_fast){
                            feeAmount= TransactionFee.getByCode("FAST"+currencyToBuy.getValue());
                        }

                    }

                });

                feeMiner.setText(new BigDecimal(feeAmount).toString());
                currency_fee_miner.setText(currencyToBuy.getValue());
                currency_total.setText(currencyToBuy.getValue());
                totalToPay = new BigDecimal(clause.getValue()).add(new BigDecimal(feeAmount));
                total_after_fee.setText(totalToPay.toString());

            }
        }



        currencyToBuyTextValue.setText(currencyToBuy.getValue());
        buyingText.setText(buyingTextValue);
        buyingValue.setText(clause.getValue());
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClauseCLicked(buyingValue, clause, clausePosition);
    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
    }

    @Override
    protected int getConfirmButtonRes() {
        return R.id.ccw_confirm_button;
    }

    @Override
    protected int getClauseNumberImageViewRes() {
        return R.id.ccw_clause_number;
    }

    @Override
    protected int getTitleTextViewRes() {
        return R.id.ccw_card_view_title;
    }

    public boolean setPaymentBuy(boolean paymentBuy){
        return this.paymentBuy = paymentBuy;
    }
}
