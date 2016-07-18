package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by root on 15/07/16.
 */
public class IntraUserDisconectDialog extends FermatDialog<ReferenceAppFermatSession<IntraUserModuleManager>, SubAppResourcesProviderManager> {

        /**
         * UI components
         */
        private FermatButton negativeBtn ;
        private FermatTextView mUsername;
        private ImageView mImageprofile;


        private final IntraUserInformation intraUserInformation;
        private final IntraUserLoginIdentity identity            ;

        public IntraUserDisconectDialog(final Activity activity,
                               final ReferenceAppFermatSession intraUserSubAppSession,
                               final SubAppResourcesProviderManager subAppResources,
                               final IntraUserInformation intraUserInformation,
                               final IntraUserLoginIdentity identity) {

            super(activity, intraUserSubAppSession, subAppResources);
            this.intraUserInformation = intraUserInformation;
            this.identity             = identity;
        }


        @SuppressLint("SetTextI18n")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mUsername = (FermatTextView) findViewById(R.id.user_name);
            mImageprofile = (ImageView) findViewById(R.id.image_user_profile);
            negativeBtn = (FermatButton) findViewById(R.id.negative_button);

            setUpScreen();


        }

        private void setUpScreen(){
            mUsername.setText(intraUserInformation.getName());

            byte[] profileImage = intraUserInformation.getProfileImage();
            if (profileImage != null && profileImage.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);
                mImageprofile.setImageBitmap(bitmap);
            }else{
                mImageprofile.setVisibility(View.GONE);

            }

            negativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteContactConfirmationDialog deleteContactConfirmationDialog = null;
                    deleteContactConfirmationDialog = new DeleteContactConfirmationDialog(
                            (Activity)getActivity(),getSession(),getResources(),intraUserInformation,identity);
                    deleteContactConfirmationDialog.setTitle("DELETE CONNECTION");
                    deleteContactConfirmationDialog.setDescription("Are you sure you want to delete this connection?");
                    deleteContactConfirmationDialog.show();
                    deleteContactConfirmationDialog.setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            dismiss();
                        }
                    });
                }
            });
        }


        @Override
        protected int setLayoutId() {
            return R.layout.delete_intra_user_connection;
        }

        @Override
        protected int setWindowFeature() {
            return Window.FEATURE_NO_TITLE;
        }
}
