package com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;

import java.util.UUID;


public class ProjectsFragment extends FermatFragment {

    /**
     * STATIC
     */
    private static final String ARG_POSITION = "position";
    /**
     * UI
     */
    private View rootView;
    /**
     * PLATFORM
     */
    private WalletFactorySubAppSession subAppSession;


    public static ProjectsFragment newInstance(int position, com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession subAppSession) {
        ProjectsFragment f = new ProjectsFragment();
        f.setSubAppSession((WalletFactorySubAppSession) subAppSession);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallet_factory_main_fragment, container, false);
        rootView.findViewById(R.id.text_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWizard(WizardTypes.CWP_WALLET_FACTORY_CREATE_NEW_PROJECT);
            }
        });
        return rootView;
    }

    public void setSubAppSession(WalletFactorySubAppSession subAppSession) {
        this.subAppSession = subAppSession;
    }


    public class WalletFactoryProject implements com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject {

        private String developerPublicKey;

        private UUID id;

        private String name;

        private Wallets type;

        private int icono;

        private String detail;

        public WalletFactoryProject(String developerPublicKey, UUID id, String name, Wallets type) {
            this.developerPublicKey = developerPublicKey;
            this.id = id;
            this.name = name;
            this.type = type;
        }

        @Override
        public String getDeveloperPublicKey() {
            return null;
        }

        @Override
        public UUID getId() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public Wallets getType() {
            return null;
        }

        @Override
        public String getPath() {
            return null;
        }

        public String getDetail() {
            return detail;
        }

        public int getIcono() {
            return icono;
        }
    }

    public class WalletFactoryProjectProposal implements com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal {


        private UUID id;
        private String alias;
        private FactoryProjectState state;


        @Override
        public UUID getId() {
            return null;
        }

        @Override
        public String getAlias() {
            return null;
        }

        @Override
        public FactoryProjectState getState() {
            return null;
        }

        @Override
        public String getPath() {
            return null;
        }


    }

}
