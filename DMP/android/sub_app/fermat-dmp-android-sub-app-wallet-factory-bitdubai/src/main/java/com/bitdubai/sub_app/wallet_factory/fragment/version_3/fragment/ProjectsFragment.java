package com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;

import java.util.List;
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


    public class WalletDescriptorFactoryProject implements com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletDescriptorFactoryProject {

        private String developerPublicKey;

        private String publisherIdentityKey;

        private String description;

        private UUID id;

        private String name;

        private Wallets type;

        private int icono;

        private String detail;

        private DescriptorFactoryProjectType descriptorFactoryProjectType;

        private List<LanguageDescriptorFactoryProject> languageDescriptorFactoryProjectList;

        private List<SkinDescriptorFactoryProject> skinDescriptorFactoryProjectList;

        public WalletDescriptorFactoryProject(String developerPublicKey, UUID id, String name, Wallets type, String publisherIdentityKey, String description, DescriptorFactoryProjectType descriptorFactoryProjectType) {
            this.developerPublicKey = developerPublicKey;
            this.publisherIdentityKey = publisherIdentityKey;
            this.description = description;
            this.descriptorFactoryProjectType = descriptorFactoryProjectType;
            this.type = type;
            this.id = id;
            this.name = name;
            this.type = type;
        }

        @Override
        public String getDeveloperPublicKey() {
            return developerPublicKey;
        }

        @Override
        public String getPublisherIdentityKey()  {
            return publisherIdentityKey;
        }

        @Override
        public DescriptorFactoryProjectType getDescriptorProjectType() {return descriptorFactoryProjectType; }

        @Override
        public String getDescription() {return description;}

        @Override
        public UUID getId() {
            return null;
        }

        @Override
        public UUID getWalletId() {return null;}

        @Override
        public String getName() {
            return null;
        }

        @Override
        public Wallets getWalletType() {
            return null;
        }

        @Override
        public String getPath() {
            return null;
        }

        @Override
        public FactoryProjectState getState() {
            return null;
        }

        public String getDetail() {
            return detail;
        }

        public int getIcono() {
            return icono;
        }

        @Override
        public List<LanguageDescriptorFactoryProject> getLanguages() { return languageDescriptorFactoryProjectList; };

        @Override
        public List<SkinDescriptorFactoryProject> getSkins() {return skinDescriptorFactoryProjectList; }
    }

}
