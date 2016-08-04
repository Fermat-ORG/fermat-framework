package com.bitdubai.sub_app.chat_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.interfaces.ErrorConnectingFermatNetwork;

/**
 * ErrorConnectingFermatNetworkDialog
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ErrorConnectingFermatNetworkDialog
        extends FermatDialog<ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager>,
        SubAppResourcesProviderManager>
        implements View.OnClickListener {

    /**
     * Interfaces
     */
    private ErrorConnectingFermatNetwork errorConnectingFermatNetwork;
    /**
     * onClick listeners
     */
    private View.OnClickListener leftClick;
    private View.OnClickListener rightClick;
    /**
     * Positive and negative button option text
     */
    private CharSequence leftButton;
    private CharSequence rightButton;
    /**
     * UI components
     */
    private FermatTextView mDescription;
    private CharSequence description;

    public ErrorConnectingFermatNetworkDialog(final Activity a,
                                              final ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> chatUserSubAppSession,
                                              final SubAppResourcesProviderManager subAppResources) {

        super(a, chatUserSubAppSession, subAppResources);

    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FermatTextView positiveButtonView = (FermatTextView) findViewById(R.id.positive_button);
        FermatTextView negativeButtonView = (FermatTextView) findViewById(R.id.negative_button);
        mDescription = (FermatTextView) findViewById(R.id.description);
        positiveButtonView.setOnClickListener(rightClick);
        negativeButtonView.setOnClickListener(leftClick);
        mDescription.setText(description != null ? description : "");
        positiveButtonView.setText(leftButton != null ? leftButton : "");
        negativeButtonView.setText(rightButton != null ? rightButton : "");
        positiveButtonView.setOnClickListener(leftClick);
        negativeButtonView.setOnClickListener(rightClick);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.cht_comm_dialog_error_connect_network;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.negative_button) {
            if (errorConnectingFermatNetwork != null) {
                errorConnectingFermatNetwork.errorConnectingFermatNetwork(false);
            }

        }
        dismiss();
    }

    /**
     * Set positive button listener and text
     *
     * @param text    CharSequence
     * @param onClick View.OnClickListener
     */
    public void setLeftButton(CharSequence text, View.OnClickListener onClick) {
        leftClick = onClick;
        leftButton = text;
    }

    /**
     * Set negative button listener and text
     *
     * @param text    CharSequence
     * @param onClick View.OnClickListener
     */
    public void setRightButton(CharSequence text, View.OnClickListener onClick) {
        rightClick = onClick;
        rightButton = text;
    }

    public void setErrorConnectingFermatNetwork(ErrorConnectingFermatNetwork errorConnectingFermatNetwork) {
        this.errorConnectingFermatNetwork = errorConnectingFermatNetwork;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }
}