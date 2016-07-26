package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.open_negotiation_details;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.common.exceptions.CantSendNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CouldNotCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.classes.CryptoCustomerWalletModuleClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotUpdateNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.OpenNegotiationDetailsAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.dialogs.ClauseDateTimeDialog;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.dialogs.SingleTextDialog;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.dialogs.TextValueDialog;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus.ACCEPTED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus.CHANGED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus.DRAFT;


/**
 * A simple {@link Fragment} subclass.
 * Modified by Yordin Alayn 22.01.16
 */
public class OpenNegotiationDetailsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerWalletModuleManager>, ResourceProviderManager>
        implements FooterViewHolder.OnFooterButtonsClickListener, ClauseViewHolder.Listener {

    private static final String TAG = "OpenNegotiationFrag";

    private ImageView brokerImage;
    private FermatTextView sellingDetails;
    private FermatTextView exchangeRateSummary;
    private FermatTextView brokerName;
    private RecyclerView recyclerView;
    private Map<ClauseType, ClauseInformation> clausesTemp;


    private CryptoCustomerWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    private OpenNegotiationDetailsAdapter adapter;
    private CustomerBrokerNegotiationInformation negotiationInfo;

    private ArrayList<MoneyType> receptionMethods;
    private List<BankAccountNumber> bankAccountList = new ArrayList<>();
    ;
    private List<String> locationList = new ArrayList<>();
    private NumberFormat numberFormat = DecimalFormat.getInstance();


    public static OpenNegotiationDetailsFragment newInstance() {
        return new OpenNegotiationDetailsFragment();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            numberFormat.setMaximumFractionDigits(8);
            clausesTemp = new HashMap<>();

            //Try to load appSession BANK_ACCOUNT_LIST data
            Object data = appSession.getData(FragmentsCommons.BANK_ACCOUNT_LIST);
            if (data == null) {
                if (bankAccountList == null) {
                    //Get saved locations from settings
                    bankAccountList = moduleManager.getListOfBankAccounts();
                }
//                Save locations to appSession BANK_ACCOUNT_LIST data
                appSession.setData(FragmentsCommons.BANK_ACCOUNT_LIST, bankAccountList);
            } else {
                bankAccountList = (List<BankAccountNumber>) data;
            }


            //Try to load appSession LOCATION_LIST data
            data = appSession.getData(FragmentsCommons.LOCATION_LIST);
            if (data == null) {

                //Get saved locations from settings
                Collection<NegotiationLocations> listAux = moduleManager.getAllLocations(NegotiationType.PURCHASE);
                for (NegotiationLocations locationAux : listAux) {
                    locationList.add(locationAux.getLocation());
                }

//                //Save locations to appSession LOCATION_LIST data
                appSession.setData(FragmentsCommons.LOCATION_LIST, locationList);
            } else {
                locationList = (List<String>) data;
            }


        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            else
                Log.e(TAG, e.getMessage(), e);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.ccw_fragment_open_negotiation_details_activity, container, false);

        setActionBarGradientColor();

        brokerImage = (ImageView) layout.findViewById(R.id.ccw_customer_image);
        brokerName = (FermatTextView) layout.findViewById(R.id.ccw_broker_name);
        sellingDetails = (FermatTextView) layout.findViewById(R.id.ccw_selling_summary);
        exchangeRateSummary = (FermatTextView) layout.findViewById(R.id.ccw_buying_exchange_rate);
        recyclerView = (RecyclerView) layout.findViewById(R.id.ccw_open_negotiation_details_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        bindData();

        return layout;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == FragmentsCommons.CANCEL_NEGOTIATION_OPTION_MENU_ID) {

            SingleTextDialog singleTextDialog = new SingleTextDialog(getActivity(), appSession, appResourcesProviderManager);

            singleTextDialog.setAcceptBtnListener(new SingleTextDialog.OnClickAcceptListener() {
                @Override
                public void onClick(String newValue) {

                    try {

                        CustomerBrokerNegotiationInformation negotiation = moduleManager.cancelNegotiation(negotiationInfo, newValue);
                        Toast.makeText(getActivity(), new StringBuilder().append("NEGOTIATION ").append(negotiationInfo.getNegotiationId()).append(" IS CANCELATED. REASON: ").append(newValue).append(". ").append(negotiation.getCancelReason()).toString(), Toast.LENGTH_LONG).show();
                        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME, appSession.getAppPublicKey());

                    } catch (CouldNotCancelNegotiationException | CantCancelNegotiationException e) {
                        Toast.makeText(getActivity(), new StringBuilder().append("ERROR IN CANCELLATION OF NEGOTIATION: ").append(FermatException.DEFAULT_MESSAGE).toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            singleTextDialog.setEditTextValue("");
            singleTextDialog.configure(R.string.ccw_cancellation_negotiation, R.string.ccw_cancellation_reason_title);
            singleTextDialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfirmCLicked(final ClauseInformation clause) {
        if (clause.getType().equals(ClauseType.CUSTOMER_BANK_ACCOUNT) && bankAccountList.size() == 0) {
            Toast.makeText(getActivity(), "Not Confirmed. The Bank Account List is Empty. Add a Bank Account in the Wallet Settings.", Toast.LENGTH_LONG).show();
        } else {
            if (clause.getType().equals(ClauseType.CUSTOMER_PLACE_TO_DELIVER) && locationList.size() == 0) {
                Toast.makeText(getActivity(), "Not Confirmed. The Locations List is Empty. Add a Location in the Wallet Settings.", Toast.LENGTH_LONG).show();
            } else {

                if (clausesTemp.get(clause.getType()) != null) {


                    if (clausesTemp.get(clause.getType()).getValue().equals(clause.getValue()))
                        putClause(clause, ACCEPTED);
                    else
                        putClause(clause, CHANGED);

                } else {
                    putClause(clause, ACCEPTED);
                }

                adapter.changeDataSet(negotiationInfo);
            }
        }
    }

    @Override
    public void onClauseClicked(final Button triggerView, final ClauseInformation clause, final int position) {
        final ClauseType type = clause.getType();
        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

        switch (type) {
            case EXCHANGE_RATE:
                actionListenerExchangeRateAndCustomerCurrencyQuantity(clause, clauses);
                break;

            case CUSTOMER_CURRENCY_QUANTITY:
                actionListenerExchangeRateAndCustomerCurrencyQuantity(clause, clauses);
                break;

            case BROKER_CURRENCY_QUANTITY:
                actionListenerBrokerCurrencyQuantity(clause, clauses);
                break;

            case CUSTOMER_PAYMENT_METHOD:
                Toast.makeText(getActivity(), "This is selected by the Broker", Toast.LENGTH_SHORT).show();
                break;

            case BROKER_BANK_ACCOUNT:
                Toast.makeText(getActivity(), "This is selected by the Broker", Toast.LENGTH_SHORT).show();
                break;

            case BROKER_PLACE_TO_DELIVER:
                Toast.makeText(getActivity(), "This is selected by the Broker", Toast.LENGTH_SHORT).show();
                break;

            case BROKER_PAYMENT_METHOD:
                actionListenerBrokerPaymentMethod(clause, clauses);
                break;

            case CUSTOMER_BANK_ACCOUNT:
                actionListenerCustomerBankAccount(clause);
                break;

            case CUSTOMER_PLACE_TO_DELIVER:
                actionListenerCustomerLocation(clause);
                break;

            case CUSTOMER_DATE_TIME_TO_DELIVER:
                actionListenerCustomerDateTimeToDelivery(clause, triggerView);
                break;

            case BROKER_DATE_TIME_TO_DELIVER:
                actionListenerBrokerDateTimeToDelivery(clause, triggerView);
                break;
        }
    }

    @Override
    public void onSendButtonClicked() {
        Map<ClauseType, ClauseInformation> mapClauses = negotiationInfo.getClauses();

        if (validateClauses(mapClauses)) {
            if (validateStatusClause(mapClauses)) {

                try {

                    if (isCreateIdentityIntraUser(negotiationInfo.getClauses())) {

                        moduleManager.updateNegotiation(negotiationInfo);

                        Toast.makeText(getActivity(), "Negotiation sent", Toast.LENGTH_LONG).show();
                        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME, this.appSession.getAppPublicKey());

                    } else
                        Toast.makeText(getActivity(), "Need to register THE WALLET USER for user BTC", Toast.LENGTH_LONG).show();

                } catch (CouldNotUpdateNegotiationException e) {
                    Toast.makeText(getActivity(), "Error sending the negotiation", Toast.LENGTH_LONG).show();
                } catch (CantSendNegotiationException e) {
                    Toast.makeText(getActivity(), "Error sending the negotiation", Toast.LENGTH_LONG).show();
                }

            } else
                Toast.makeText(getActivity(), "Need to confirm ALL the clauses", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAddNoteButtonClicked() {
        TextValueDialog dialog = new TextValueDialog(getActivity(), appSession, appResourcesProviderManager);
        dialog.configure(R.string.notes, R.string.ccw_insert_note);
        dialog.setTextFreeInputType(true);
        dialog.setTextCount(200);
        dialog.setEditTextValue(negotiationInfo.getMemo());
        dialog.setAcceptBtnListener(new TextValueDialog.OnClickAcceptListener() {
            @Override
            public void onClick(String editTextValue) {
                negotiationInfo.setMemo(editTextValue);
                adapter.changeDataSet(negotiationInfo);
            }
        });
        dialog.show();
    }

    /**
     * Set the Action Bar's gradient color
     */
    @SuppressWarnings("deprecation")
    private void setActionBarGradientColor() {

        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();

    }

    /**
     * Bind the data on the views
     */
    private void bindData() {
//        long timeInMillisVal = System.currentTimeMillis();
//        String timeInMillisStr = String.valueOf(timeInMillisVal);


        long timeInMillisVal = Calendar.getInstance().getTimeInMillis();
        String timeInMillisStr = Long.toString(timeInMillisVal);

        negotiationInfo = (CustomerBrokerNegotiationInformation) appSession.getData(FragmentsCommons.NEGOTIATION_DATA);
        ActorIdentity broker = negotiationInfo.getBroker();
        Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();


        //CLAUSES DATE
        String merchandise = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();

        String exchangeAmount = fixFormat(clauses.get(ClauseType.EXCHANGE_RATE).getValue());


        String payment = clauses.get(ClauseType.BROKER_CURRENCY).getValue();
        String amount = fixFormat(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue());
        Drawable brokerImg = getImgDrawable(broker.getProfileImage());

        //LIST MERCHANDISE TYPE
        final ArrayList<MoneyType> paymentMethods = getPaymentMethod(payment);

        //LIST PAYMENT METHOD TYPE
        receptionMethods = getPaymentMethod(merchandise);

        //VALUE DEFAULT CUSTOMER PAYMENT METHOD
        if (clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD) == null) {
            putClause(ClauseType.CUSTOMER_PAYMENT_METHOD, paymentMethods.get(0).getCode());
        }

        //VALUE DEFAULT BROKER PAYMENT METHOD
        if (clauses.get(ClauseType.BROKER_PAYMENT_METHOD) == null) {
            putClause(ClauseType.BROKER_PAYMENT_METHOD, receptionMethods.get(0).getCode());
        }

        //VALUE STATUS ACCEPTED IN CLAUSE CUSTOMER CURRENCY
        putClause(clauses.get(ClauseType.CUSTOMER_CURRENCY), ACCEPTED);

        //VALUE STATUS ACCEPTED IN CLAUSE CUSTOMER CURRENCY
        putClause(clauses.get(ClauseType.BROKER_CURRENCY), ACCEPTED);

        //VALUE STATUS ACCEPTED IN CLAUSE BROKER CRYPTO ADDRESS
        if (clauses.get(ClauseType.BROKER_CRYPTO_ADDRESS) != null) {
            putClause(clauses.get(ClauseType.BROKER_CRYPTO_ADDRESS), ACCEPTED);
        }

        //VALUE STATUS ACCEPTED IN CLAUSE CUSTOMER CRYPTO ADDRESS
        if (clauses.get(ClauseType.CUSTOMER_CRYPTO_ADDRESS) != null) {
            putClause(clauses.get(ClauseType.CUSTOMER_CRYPTO_ADDRESS), ACCEPTED);
        }

        //VALUE DEFAULT INFO PAYMENT
        putPaymentInfo(clauses);

        //VALUE DEFAULT INFO RECEPTION
        putReceptionInfo(clauses);

        if (clauses.get(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER) == null)
            putClause(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr);

        if (clauses.get(ClauseType.BROKER_DATE_TIME_TO_DELIVER) == null)
            putClause(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr);

        //VALUE TIME ZONE
        String youTimeZoneValue = TimeZone.getDefault().getID();
//        if ((clauses.get(ClauseType.CUSTOMER_TIME_ZONE) == null) || youTimeZoneValue.equals(clauses.get(ClauseType.CUSTOMER_TIME_ZONE).getValue())){
        putClause(ClauseType.CUSTOMER_TIME_ZONE, youTimeZoneValue);
//        }

        brokerImage.setImageDrawable(brokerImg);
        brokerName.setText(broker.getAlias());
        sellingDetails.setText(getResources().getString(R.string.ccw_selling_details, amount, merchandise));
        exchangeRateSummary.setText(getResources().getString(R.string.ccw_exchange_rate_summary, merchandise, exchangeAmount, payment));


        //PRINT CLAUSE STATUS TEST
        logStatusClauses(clauses);

        adapter = new OpenNegotiationDetailsAdapter(getActivity(), negotiationInfo);


        if (negotiationInfo.getStatus() != NegotiationStatus.SENT_TO_BROKER && negotiationInfo.getStatus() != NegotiationStatus.WAITING_FOR_BROKER && negotiationInfo.getStatus() != NegotiationStatus.WAITING_FOR_CLOSING) {
            adapter.setFooterListener(this);
            adapter.setClauseListener(this);
        }

        adapter.setMarketRateList(getActualExchangeRates());

        recyclerView.setAdapter(adapter);
    }

    /**
     * Return the {@link Drawable} image representation for the bytes array
     *
     * @param customerImg the bytes array representation of the image
     * @return the {@link Drawable} image representation for the bytes array or a default image
     */
    private Drawable getImgDrawable(byte[] customerImg) {

        Resources res = getResources();

        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);

    }

    /**
     * ACTION LISTENER FOR CLAUSE BROKER CURRENCY QUANTITY
     *
     * @param clause  the clause
     * @param clauses the list of clauses
     */
    private void actionListenerBrokerCurrencyQuantity(final ClauseInformation clause, final Map<ClauseType, ClauseInformation> clauses) {

        TextValueDialog clauseTextDialog = new TextValueDialog(getActivity(), appSession, appResourcesProviderManager);

        clauseTextDialog.setAcceptBtnListener(new TextValueDialog.OnClickAcceptListener() {
            @Override
            public void onClick(String newValue) {
                if (validateExchangeRate()) {

                    putClauseTemp(clause.getType(), clause.getValue());

                    //ASIGNAMENT NEW VALUE
                    //Change lostwood
                    //  newValue = getDecimalFormat(getBigDecimal(newValue));


                    newValue = fixFormat(newValue);
                    //
                    putClause(clause, newValue);

                    //CALCULATE CUSTOMER CURRENCY QUANTITY
                    final BigDecimal exchangeRate = convertToBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue());
                    final BigDecimal amountToPay = convertToBigDecimal(clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY).getValue());
                    final BigDecimal amountToBuy = amountToPay.divide(exchangeRate, 8, RoundingMode.HALF_UP);


                    //ASIGNAMENT CUSTOMER CURRENCY QUANTITY
                    final String amountToBuyStr = fixFormat(String.valueOf(amountToBuy));
                    final ClauseInformation brokerCurrencyQuantity = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY);
                    putClause(brokerCurrencyQuantity, amountToBuyStr);

                    adapter.changeDataSet(negotiationInfo);

                }
            }
        });

        clauseTextDialog.setEditTextValue(clause.getValue());
        clauseTextDialog.configure(
                clause.getType().equals(ClauseType.EXCHANGE_RATE) ? R.string.ccw_your_exchange_rate : R.string.ccw_amount_to_pay,
                clause.getType().equals(ClauseType.EXCHANGE_RATE) ? R.string.amount : R.string.ccw_value);
        clauseTextDialog.show();

    }

    /**
     * ACTION LISTENER FOR CUSTOMER PAYMENT METHOD
     *
     * @param clause  the clause
     * @param clauses the list of clauses
     */
    private void actionListenerBrokerPaymentMethod(final ClauseInformation clause, final Map<ClauseType, ClauseInformation> clauses) {

        SimpleListDialogFragment<MoneyType> dialogFragment;

        dialogFragment = new SimpleListDialogFragment<>();
        dialogFragment.configure("Reception Methods", receptionMethods);
        dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<MoneyType>() {
            @Override
            public void onItemSelected(MoneyType newValue) {

                //VALIDATE CHANGE
                putClauseTemp(clause.getType(), clause.getValue());
                //validateChange(clause.getValue(), newValue.getCode());

                //ADD SELECTED ITEN
                putClause(clause, newValue.getCode());

                //ADD CLAUSE OF THE INFO THE PAYMENT
                putReceptionInfo(clauses);

                adapter.changeDataSet(negotiationInfo);

            }
        });
        dialogFragment.show(getFragmentManager(), "receptionMethodsDialog");
    }

    /**
     * ACTION LISTENER FOR CLAUSE CUSTOMER DATE TIME TO DELIVERY
     *
     * @param clause      the clause
     * @param triggerView the view that triggered the event
     */
    private void actionListenerCustomerDateTimeToDelivery(final ClauseInformation clause, final Button triggerView) {

        ClauseDateTimeDialog clauseDateTimeDialog = new ClauseDateTimeDialog(getActivity(), Long.valueOf(clause.getValue()));

        if (triggerView.getId() == R.id.ccw_date_value)
            clauseDateTimeDialog.getDateDialog();
        else
            clauseDateTimeDialog.getTimeDialog();

        clauseDateTimeDialog.setAcceptBtnListener(new ClauseDateTimeDialog.OnClickAcceptListener() {
            @Override
            public void getDate(long newValue) {
                actionListenerDatetime(clause, String.valueOf(newValue));
            }
        });
    }

    /**
     * ACTION LISTENER FOR CLAUSE BROKER DATE TIME TO DELIVERY
     * the view that triggered the event
     */
    private void actionListenerBrokerDateTimeToDelivery(final ClauseInformation clause, final Button triggerView) {

        ClauseDateTimeDialog clauseDateTimeDialog = new ClauseDateTimeDialog(getActivity(), Long.valueOf(clause.getValue()));

        if (triggerView.getId() == R.id.ccw_date_value)
            clauseDateTimeDialog.getDateDialog();
        else
            clauseDateTimeDialog.getTimeDialog();

        clauseDateTimeDialog.setAcceptBtnListener(new ClauseDateTimeDialog.OnClickAcceptListener() {
            @Override
            public void getDate(long newValue) {
                actionListenerDatetime(clause, String.valueOf(newValue));
            }
        });

    }

    /**
     * Set the new value in the datetime clause
     *
     * @param clause   the clause
     * @param newValue the new value
     */
    private void actionListenerDatetime(ClauseInformation clause, String newValue) {
        putClauseTemp(clause.getType(), clause.getValue());
        putClause(clause, String.valueOf(newValue));
        adapter.changeDataSet(negotiationInfo);
    }

    /**
     * ACTION LISTENER FOR CLAUSE CUSTOMER BANK ACCOUNT
     *
     * @param clause the clause
     */
    private void actionListenerCustomerBankAccount(final ClauseInformation clause) {

        if (bankAccountList.size() > 0) {
            SimpleListDialogFragment<BankAccountNumber> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("bankAccount", bankAccountList);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<BankAccountNumber>() {
                @Override
                public void onItemSelected(BankAccountNumber newValue) {
                    putClauseTemp(clause.getType(), clause.getValue());
                    putClause(clause, newValue.toString());
                    adapter.changeDataSet(negotiationInfo);
                }
            });
            dialogFragment.show(getFragmentManager(), "bankAccountDialog");
        } else {
            appSession.setData(FragmentsCommons.LAST_ACTIVITY, Activities.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS.getCode());
            Toast.makeText(getActivity(), "The Bank Account List is Empty. Add Your Bank Account in the Settings Wallet.", Toast.LENGTH_LONG).show();
            changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CREATE_NEW_BANK_ACCOUNT_IN_SETTINGS, appSession.getAppPublicKey());
        }

    }

    /**
     * ACTION LISTENER FOR CUSTOMER LOCATION
     *
     * @param clause the clause
     */
    private void actionListenerCustomerLocation(final ClauseInformation clause) {

        if (locationList.size() > 0) {
            SimpleListDialogFragment<String> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("placeToDelivery", locationList);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<String>() {
                @Override
                public void onItemSelected(String newValue) {
                    putClauseTemp(clause.getType(), clause.getValue());
                    putClause(clause, newValue);
                    adapter.changeDataSet(negotiationInfo);
                }
            });
            dialogFragment.show(getFragmentManager(), "placeToDeliveryDialog");
        } else {
            Toast.makeText(getActivity(), "The Locations List is Empty. Add Your Locations in the Settings Wallet.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * ACTION LISTENER FOR CLAUSE DEFAULT
     *
     * @param clause  the clause
     * @param clauses the list of clauses
     */
    private void actionListenerExchangeRateAndCustomerCurrencyQuantity(final ClauseInformation clause, final Map<ClauseType, ClauseInformation> clauses) {

        TextValueDialog clauseTextDialog = new TextValueDialog(getActivity(), appSession, appResourcesProviderManager);

        clauseTextDialog.setAcceptBtnListener(new TextValueDialog.OnClickAcceptListener() {
            @Override
            public void onClick(String newValue) {


                //VALIDATE CHANGE
                putClauseTemp(clause.getType(), clause.getValue());

                //ASSIGN NEW VALUE
                //change lostwood

                // newValue = getDecimalFormat(getBigDecimal(newValue));
                newValue = fixFormat(newValue);
                putClause(clause, newValue);

                //change lostwood
                //CALCULATE BROKER CURRENCY QUANTITY
               /* final BigDecimal exchangeRate = new BigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue().replace(",", ""));
                final BigDecimal amountToBuy = new BigDecimal(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue().replace(",", ""));
                final BigDecimal amountToPay = amountToBuy.multiply(exchangeRate);*/


                BigDecimal amountToPay;

                final BigDecimal exchangeRate = convertToBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue());

                final BigDecimal amountToBuy = convertToBigDecimal(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue());
                amountToPay = amountToBuy.multiply(exchangeRate);


                //ASSIGN BROKER CURRENCY QUANTITY
                final String amountToPayStr = fixFormat(String.valueOf(amountToPay.doubleValue()));
                final ClauseInformation brokerCurrencyQuantityClause = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY);
                putClause(brokerCurrencyQuantityClause, amountToPayStr);

                adapter.changeDataSet(negotiationInfo);
            }
        });
        clauseTextDialog.setEditTextValue(clause.getValue());
        clauseTextDialog.configure(
                clause.getType().equals(ClauseType.EXCHANGE_RATE) ? R.string.ccw_your_exchange_rate : R.string.ccw_amount_to_buy,
                clause.getType().equals(ClauseType.EXCHANGE_RATE) ? R.string.amount : R.string.ccw_value);

        clauseTextDialog.show();
    }

    /**
     * VALIDATE CLAUSE
     *
     * @param clauses the clause
     * @return true if valid, false otherwise
     */
    private Boolean validateClauses(Map<ClauseType, ClauseInformation> clauses) {

        if (clauses != null) {


            final BigDecimal exchangeRate = convertToBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue());


            final BigDecimal amountToBuy = convertToBigDecimal(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue());


            final BigDecimal amountToPay = convertToBigDecimal(clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY).getValue());

            //VALIDATE QUANTITY
            if (exchangeRate.compareTo(BigDecimal.ZERO) <= 0) {
                Toast.makeText(getActivity(), "The exchange must be greater than zero.", Toast.LENGTH_LONG).show();
                return false;
            }

            if (amountToBuy.compareTo(BigDecimal.ZERO) <= 0) {
                Toast.makeText(getActivity(), "The  buying must be greater than zero.", Toast.LENGTH_LONG).show();
                return false;
            }

            if (amountToPay.compareTo(BigDecimal.ZERO) <= 0) {
                Toast.makeText(getActivity(), "The  paying must be greater than zero.", Toast.LENGTH_LONG).show();
                return false;
            }

            final ClauseInformation customerCurrencyClause = clauses.get(ClauseType.CUSTOMER_CURRENCY);
            final ClauseInformation brokerCurrencyClause = clauses.get(ClauseType.BROKER_CURRENCY);

            if (customerCurrencyClause.getValue().equals(brokerCurrencyClause.getValue())) {
                Toast.makeText(getActivity(), "The currency to pay is equal to currency buy.", Toast.LENGTH_LONG).show();
                return false;
            }

        } else {
            Toast.makeText(getActivity(), "Error. Information is null.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * VALIDATE EXCHANGE RATE NOT IS ZERO
     *
     * @return true if valid, false otherwise
     */
    private boolean validateExchangeRate() {

        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();


        final BigDecimal exchangeRate = convertToBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue());

        if (exchangeRate.compareTo(BigDecimal.ZERO) <= 0) {
            Toast.makeText(getActivity(), "The exchange rate must be greater than zero.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    /**
     * VALIDATE CLAUSE STATUS
     *
     * @param clauses the list of clauses
     * @return true if valid, false otherwise
     */
    private boolean validateStatusClause(Map<ClauseType, ClauseInformation> clauses) {

        String customerPaymentMethod = clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD).getValue();
        String brokerPaymentMethod = clauses.get(ClauseType.BROKER_PAYMENT_METHOD).getValue();

        for (ClauseInformation item : clauses.values()) {
            if (validateClauseUsed(item, customerPaymentMethod, brokerPaymentMethod)) {
                if ((item.getStatus() != CHANGED) && (item.getStatus() != ACCEPTED)) return false;
            }
        }

        return true;
    }

    /**
     * VALIDATE IS CLAUSE IS USED
     *
     * @param clause                the clause
     * @param customerPaymentMethod the payment method
     * @param brokerPaymentMethod   the reception method
     * @return true if valid, false otherwise
     */
    @SuppressWarnings("RedundantIfStatement")
    private boolean validateClauseUsed(ClauseInformation clause, String customerPaymentMethod, String brokerPaymentMethod) {

        final List<ClauseType> evalList = Arrays.asList(
                ClauseType.BROKER_BANK_ACCOUNT,
                ClauseType.BROKER_PAYMENT_METHOD,
                ClauseType.BROKER_CRYPTO_ADDRESS,
                ClauseType.CUSTOMER_BANK_ACCOUNT,
                ClauseType.CUSTOMER_PAYMENT_METHOD,
                ClauseType.CUSTOMER_CRYPTO_ADDRESS,
                ClauseType.BROKER_TIME_ZONE,
                ClauseType.CUSTOMER_TIME_ZONE
        );

//        if (clause.getType().equals(ClauseType.BROKER_BANK_ACCOUNT) ||
//                clause.getType().equals(ClauseType.BROKER_PAYMENT_METHOD) ||
//                clause.getType().equals(ClauseType.BROKER_CRYPTO_ADDRESS) ||
//                clause.getType().equals(ClauseType.CUSTOMER_BANK_ACCOUNT) ||
//                clause.getType().equals(ClauseType.CUSTOMER_PAYMENT_METHOD) ||
//                clause.getType().equals(ClauseType.CUSTOMER_CRYPTO_ADDRESS)
//                ) {
        if (evalList.contains(clause.getType())) {

            if (clause.getType().equals(ClauseType.BROKER_BANK_ACCOUNT) && (brokerPaymentMethod.equals(MoneyType.BANK.getCode())))
                return true;
            else if (clause.getType().equals(ClauseType.BROKER_PLACE_TO_DELIVER) && ((brokerPaymentMethod.equals(MoneyType.CASH_DELIVERY.getCode())) || (brokerPaymentMethod.equals(MoneyType.CASH_ON_HAND.getCode()))))
                return true;
            else if (clause.getType().equals(ClauseType.BROKER_CRYPTO_ADDRESS) && (!brokerPaymentMethod.equals(MoneyType.BANK.getCode())) && (!brokerPaymentMethod.equals(MoneyType.CASH_DELIVERY.getCode())) && (!brokerPaymentMethod.equals(MoneyType.CASH_ON_HAND.getCode())))
                return true;
            else if (clause.getType().equals(ClauseType.CUSTOMER_BANK_ACCOUNT) && (customerPaymentMethod.equals(MoneyType.BANK.getCode())))
                return true;
            else if (clause.getType().equals(ClauseType.CUSTOMER_PAYMENT_METHOD) && ((customerPaymentMethod.equals(MoneyType.CASH_DELIVERY.getCode())) || (customerPaymentMethod.equals(MoneyType.CASH_ON_HAND.getCode()))))
                return true;
            else if (clause.getType().equals(ClauseType.CUSTOMER_CRYPTO_ADDRESS) && (!customerPaymentMethod.equals(MoneyType.BANK.getCode())) && (!customerPaymentMethod.equals(MoneyType.CASH_DELIVERY.getCode())) && (!customerPaymentMethod.equals(MoneyType.CASH_ON_HAND.getCode())))
                return true;
            else
                return false;

        } else {
            return true;
        }

    }

    /**
     * LOG STATUS CLAUSES
     *
     * @param clause the clauses
     */
    private void logStatusClauses(Map<ClauseType, ClauseInformation> clause) {

        String co = "REFERENCE WALLET VALIDATION STATE: ";
        for (ClauseInformation item : clause.values())
            co = new StringBuilder().append(co).append("\n - ").append(item.getType().getCode()).append(": ").append(item.getStatus().getCode()).toString();

        System.out.print(new StringBuilder().append(co).append("\n").toString());
    }

    /**
     * Return the payment method for thew given currency code
     *
     * @param currency the currency code
     * @return the list of payment methods
     */
    private ArrayList<MoneyType> getPaymentMethod(String currency) {

        ArrayList<MoneyType> paymentMethods = new ArrayList<>();

        negotiationInfo.getCustomer().getPublicKey();
        negotiationInfo.getBroker().getPublicKey();

        try {
            Collection<Platforms> platforms = moduleManager.getPlatformsSupported(negotiationInfo.getCustomer().getPublicKey(), negotiationInfo.getBroker().getPublicKey(), currency);

            for (Platforms p : platforms) {
                ArrayList<MoneyType> temp = getMoneyType(p);
                for (MoneyType m : temp) {
                    paymentMethods.add(m);
                }
            }

        } catch (CantGetListActorExtraDataException e) {
            // TODO: revisar el manejo de excepciones
        }

        /*

        //ADD FIAT CURRENCY IF IS FIAT
        if (FiatCurrency.codeExists(currency)) {
            paymentMethods.add(MoneyType.BANK);
            paymentMethods.add(MoneyType.CASH_DELIVERY);
            paymentMethods.add(MoneyType.CASH_ON_HAND);
        }

        //ADD CRYPTO CURRENCY IF IS CRYPTO
        if (CryptoCurrency.codeExists(currency)) {
            paymentMethods.add(MoneyType.CRYPTO);
        }
        */

        return paymentMethods;
    }

    private ArrayList<MoneyType> getPaymentMethod2(String currency) {

        ArrayList<MoneyType> paymentMethods = new ArrayList<>();

        //ADD FIAT CURRENCY IF IS FIAT
        if (FiatCurrency.codeExists(currency)) {
            paymentMethods.add(MoneyType.BANK);
            paymentMethods.add(MoneyType.CASH_DELIVERY);
            paymentMethods.add(MoneyType.CASH_ON_HAND);
        }

        //ADD CRYPTO CURRENCY IF IS CRYPTO
        if (CryptoCurrency.codeExists(currency)) {
            paymentMethods.add(MoneyType.CRYPTO);
        }

        return paymentMethods;
    }

    private ArrayList<MoneyType> getMoneyType(Platforms p) {

        ArrayList<MoneyType> moneys = new ArrayList<>();

        switch (p) {

            case BANKING_PLATFORM:
                moneys.add(MoneyType.BANK);
                break;

            case CASH_PLATFORM:
                moneys.add(MoneyType.CASH_DELIVERY);
                moneys.add(MoneyType.CASH_ON_HAND);
                break;

            case CRYPTO_CURRENCY_PLATFORM:
                moneys.add(MoneyType.CRYPTO);
                break;

        }

        return moneys;

    }

    /**
     * GET CLAUSE OF INFO OF RECEIVED
     *
     * @param clauses the list of clauses
     */
    private void putPaymentInfo(Map<ClauseType, ClauseInformation> clauses) {

        String currencyType = clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD).getValue();
        if (currencyType != null) {
            if (currencyType.equals(MoneyType.CRYPTO.getCode())) {
                if (clauses.get(ClauseType.BROKER_CRYPTO_ADDRESS) == null) {
//                    putClause(ClauseType.BROKER_CRYPTO_ADDRESS, "Crypto Address is Generate Automatic");
                    putClause(ClauseType.BROKER_CRYPTO_ADDRESS, ACCEPTED);
                    clauses.remove(ClauseType.BROKER_BANK_ACCOUNT);
                    clauses.remove(ClauseType.BROKER_PLACE_TO_DELIVER);

                }

            } else if (currencyType.equals(MoneyType.BANK.getCode())) {
                if (clauses.get(ClauseType.BROKER_BANK_ACCOUNT) == null) {
                    putClause(ClauseType.BROKER_BANK_ACCOUNT, "The Bank Info is Intro For The Broker");
                    clauses.remove(ClauseType.BROKER_CRYPTO_ADDRESS);
                    clauses.remove(ClauseType.BROKER_PLACE_TO_DELIVER);
                }

            } else if (currencyType.equals(MoneyType.CASH_DELIVERY.getCode()) || (currencyType.equals(MoneyType.CASH_ON_HAND.getCode()))) {
                if (clauses.get(ClauseType.BROKER_PLACE_TO_DELIVER) == null) {
                    putClause(ClauseType.BROKER_PLACE_TO_DELIVER, "The Delivery Info is Intro For The Broker");
                    clauses.remove(ClauseType.BROKER_BANK_ACCOUNT);
                    clauses.remove(ClauseType.BROKER_CRYPTO_ADDRESS);
                }
            }
        }

    }

    /**
     * GET CLAUSE OF INFO OF PAYMENT
     *
     * @param clauses the list of clauses
     */
    private void putReceptionInfo(Map<ClauseType, ClauseInformation> clauses) {

        String currencyType = clauses.get(ClauseType.BROKER_PAYMENT_METHOD).getValue();
        if (currencyType != null) {
            if (currencyType.equals(MoneyType.CRYPTO.getCode())) {
                if (clauses.get(ClauseType.CUSTOMER_CRYPTO_ADDRESS) == null) {
//                    putClause(ClauseType.CUSTOMER_CRYPTO_ADDRESS, "Crypto Address is Generate Automatic");
                    putClause(ClauseType.CUSTOMER_CRYPTO_ADDRESS, ACCEPTED);
                    clauses.remove(ClauseType.CUSTOMER_BANK_ACCOUNT);
                    clauses.remove(ClauseType.CUSTOMER_PLACE_TO_DELIVER);
                }

            } else if (currencyType.equals(MoneyType.BANK.getCode())) {
                if (clauses.get(ClauseType.CUSTOMER_BANK_ACCOUNT) == null) {
                    String bankAccount = "INSERT BANK ACCOUNT IN WALLET SETTINGS.";

                    if (bankAccountList.size() > 0)
                        bankAccount = bankAccountList.get(0).toString();
//                    bankAccount = bankAccountList.get(0).getAccount();
                    putClause(ClauseType.CUSTOMER_BANK_ACCOUNT, bankAccount);
                    clauses.remove(ClauseType.CUSTOMER_CRYPTO_ADDRESS);
                    clauses.remove(ClauseType.CUSTOMER_PLACE_TO_DELIVER);

                }

            } else if (currencyType.equals(MoneyType.CASH_DELIVERY.getCode()) || (currencyType.equals(MoneyType.CASH_ON_HAND.getCode()))) {
                if (clauses.get(ClauseType.CUSTOMER_PLACE_TO_DELIVER) == null) {
                    String infoDelivery = "INSERT LOCATION IN WALLET SETTINGS.";

                    if (locationList.size() > 0)
                        infoDelivery = locationList.get(0);
                    putClause(ClauseType.CUSTOMER_PLACE_TO_DELIVER, infoDelivery);
                    clauses.remove(ClauseType.CUSTOMER_CRYPTO_ADDRESS);
                    clauses.remove(ClauseType.CUSTOMER_BANK_ACCOUNT);
                }
            }
        }

    }

    /**
     * PUT CLAUSE CLAUSE AND VALUE
     *
     * @param clause the clause
     * @param value  the value
     */
    public void putClause(final ClauseInformation clause, final String value) {

        CryptoCustomerWalletModuleClauseInformation clauseInformation = new CryptoCustomerWalletModuleClauseInformation(clause);
        clauseInformation.setValue(value);
        clauseInformation.setStatus(getStatusClauseChange(clause, value));


        negotiationInfo.getClauses().put(clause.getType(), clauseInformation);
    }

    /**
     * PUT IN THE CLAUSE THE CLAUSE TYPE AND THE VALUE
     *
     * @param clauseType the clause type
     * @param value      the value
     */
    public void putClause(final ClauseType clauseType, final String value) {
        final String clauseValue = (value != null) ? value : "";
        final ClauseInformation clauseInformation = new CryptoCustomerWalletModuleClauseInformation(clauseType, clauseValue, DRAFT);

        negotiationInfo.getClauses().put(clauseType, clauseInformation);
    }

    /**
     * PUT IN CLAUSE THE CLAUSE TYPE AND THE VALUE
     *
     * @param clauseType the clause type
     * @param status     the clasue status
     */
    public void putClause(final ClauseType clauseType, final ClauseStatus status) {
        final ClauseInformation clauseInformation = new CryptoCustomerWalletModuleClauseInformation(clauseType, "", status);
        negotiationInfo.getClauses().put(clauseType, clauseInformation);
    }

    //PUT CLAUSE CLAUSE AND STATUS
    public void putClause(final ClauseInformation clause, final ClauseStatus status) {
        final CryptoCustomerWalletModuleClauseInformation clauseInformation = new CryptoCustomerWalletModuleClauseInformation(clause);
        clauseInformation.setStatus(status);

        negotiationInfo.getClauses().put(clause.getType(), clauseInformation);
    }

    //PUT CLAUSE CLAUSE TYPE, VALUE
    public void putClauseTemp(final ClauseType clauseType, final String value) {

        if (clausesTemp.get(clauseType) == null) {
            final String clauseValue = (value != null) ? value : "";
            final ClauseInformation clauseInformation = new CryptoCustomerWalletModuleClauseInformation(clauseType, clauseValue, DRAFT);

            clausesTemp.put(clauseType, clauseInformation);
        }
    }

    private ClauseStatus getStatusClauseChange(ClauseInformation clause, String newValue) {

        ClauseStatus statusClause = clause.getStatus();
        if (clausesTemp.get(clause.getType()) != null) {
            if (!clausesTemp.get(clause.getType()).getValue().equals(newValue) && clause.getStatus() == ACCEPTED) {
                statusClause = CHANGED;
            }
        }

        return statusClause;
    }

/*    private BigDecimal getBigDecimal(String value) {
        //change lostwood
        //return new BigDecimal(value.replace(",", ""));
        try {
            return new BigDecimal(numberFormat.parse(value).toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return new BigDecimal(0);
        }
    }

    private String getDecimalFormat(BigDecimal value) {

        return numberFormat.format(value.doubleValue());
    }*/

    private BigDecimal convertToBigDecimal(String value) {

        BigDecimal convertion = new BigDecimal(0);
        try {
            if (compareLessThan1(value)) {
                numberFormat.setMaximumFractionDigits(8);
            } else {
                numberFormat.setMaximumFractionDigits(2);
            }
            convertion = new BigDecimal(String.valueOf(numberFormat.parse(numberFormat.format(
                    Double.valueOf(numberFormat.parse(value).toString())))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertion;
    }

    private String fixFormat(String value) {

        try {
            if (compareLessThan1(value)) {
                numberFormat.setMaximumFractionDigits(8);
            } else {
                numberFormat.setMaximumFractionDigits(2);
            }
            return numberFormat.format(new BigDecimal(numberFormat.parse(value).toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }

    }

    private Boolean compareLessThan1(String value) {
        Boolean lessThan1 = true;
        try {
            if (BigDecimal.valueOf(numberFormat.parse(value).doubleValue()).
                    compareTo(BigDecimal.ONE) == -1) {
                lessThan1 = true;
            } else {
                lessThan1 = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lessThan1;
    }

    private List<IndexInfoSummary> getActualExchangeRates() {
        Object data = appSession.getData(FragmentsCommons.EXCHANGE_RATES);
        return (data != null) ? (List<IndexInfoSummary>) data : null;
    }

    private boolean isCreateIdentityIntraUser(Map<ClauseType, ClauseInformation> clauses) throws CantSendNegotiationException {

        String customerCurrency = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
        String brokerCurrency = clauses.get(ClauseType.BROKER_CURRENCY).getValue();
        String currencyBTC = "BTC";

        if (customerCurrency != null) {
            if (currencyBTC.equals(customerCurrency))
                return moduleManager.isCreateIdentityIntraUser();
        }

        if (brokerCurrency != null) {
            if (currencyBTC.equals(brokerCurrency))
                return moduleManager.isCreateIdentityIntraUser();
        }

        return true;
    }
}
