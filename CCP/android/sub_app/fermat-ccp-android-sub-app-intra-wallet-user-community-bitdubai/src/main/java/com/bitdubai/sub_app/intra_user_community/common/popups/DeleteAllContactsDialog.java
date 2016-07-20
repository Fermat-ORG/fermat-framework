package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by Gian Barboza on 15/07/16.
 */
public class DeleteAllContactsDialog extends FermatDialog<ReferenceAppFermatSession<IntraUserModuleManager>, SubAppResourcesProviderManager> implements View.OnClickListener {

        /**
         * UI components
         */

        private final IntraUserLoginIdentity identity;

        private FermatButton positiveBtn;
        private FermatButton negativeBtn;

        public DeleteAllContactsDialog(final Activity activity,
                                                final ReferenceAppFermatSession intraUserSubAppSession,
                                                final SubAppResourcesProviderManager subAppResources,
                                                final IntraUserLoginIdentity identity) {

            super(activity, intraUserSubAppSession, subAppResources);
            this.identity= identity;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            positiveBtn = (FermatButton)   findViewById(R.id.positive_button);
            negativeBtn = (FermatButton)   findViewById(R.id.negative_button);

            positiveBtn.setOnClickListener(this);
            negativeBtn.setOnClickListener(this);

        }
        @Override
        protected int setLayoutId() {
            return R.layout.delete_all_connection_contact_dialog;
        }

        @Override
        protected int setWindowFeature() {
            return Window.FEATURE_NO_TITLE;
        }

        @Override
        public void onClick(View v) {

            int i = v.getId();

            if (i == R.id.positive_button) {

                /*try {
                    if (intraUserInformation != null && identity != null) {
                        getSession().getModuleManager().acceptIntraUser(identity.getPublicKey(), intraUserInformation.getName(), intraUserInformation.getPublicKey(), intraUserInformation.getProfileImage());
                        getSession().setData(SessionConstants.NOTIFICATION_ACCEPTED,Boolean.TRUE);
                        Toast.makeText(getContext(), intraUserInformation.getName() + " Accepted connection request", Toast.LENGTH_SHORT).show();
                    } else {
                        super.toastDefaultError();
                    }
                    dismiss();
                } catch (final CantAcceptRequestException e) {

                    super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                    super.toastDefaultError();
                }
                dismiss();
*/
            } else if (i == R.id.negative_button) {
                dismiss();
            }
    }
}
