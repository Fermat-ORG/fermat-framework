package com.bitdubai.sub_app.wallet_publisher.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_identity.publisher.exceptions.CantSingMessageException;
import com.bitdubai.fermat_api.layer.dmp_identity.publisher.interfaces.PublisherIdentity;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.sub_app.wallet_publisher.R;

import java.util.List;

/**
 * Created by natalia on 09/07/15.
 */
public class MainFragment extends FermatFragment {

    private static final String ARG_POSITION = "position";

    private WalletPublisherModuleManager walletPublisherManager;

    private Typeface typeface;

    private List<InformationPublishedComponent> informationPublishedComponentList;

    public static MainFragment newInstance(int position,SubAppsSession subAppsSession) {
        MainFragment f = new MainFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            this.informationPublishedComponentList = walletPublisherManager.getPublishedComponents(new PublisherIdentity() {
                @Override
                public String getAlias() {
                    return null;
                }

                @Override
                public String getPublicKey() {
                    return "04D707E1C33B2C82AE81E3FACA2025D1E0E439F9AAFD52CA844D3AFA47A0480093EF343790546F1E7C1BB454A426E054E26F080A61B1C0083C25EE77C7F97C6A80";
                }

                @Override
                public String getWebsiteurl() {
                    return null;
                }

                @Override
                public String createMessageSignature(String mensage) throws CantSingMessageException {
                    return null;
                }
            });

        } catch (CantGetPublishedComponentInformationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View mainView = inflater.inflate(R.layout.wallet_publisher_main_fragment, container, false);
        TableLayout tableLayout = (TableLayout) mainView.getRootView().findViewById(R.id.publishedComponents);

        tableLayout.addView(createTableRow(null, 1));


        return mainView;
    }


    private TableRow createTableRow(InformationPublishedComponent informationPublishedComponent, int idTableRow){


        // Make TR
        TableRow tableRow = new TableRow(getActivity());
        tableRow.setId(idTableRow);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        // Make TV to hold the details
        TextView detailstv = new TextView(getActivity());
        detailstv.setId(200 + idTableRow);
        detailstv.setText("Column 1");
        tableRow.addView(detailstv);

        // Make TV to hold the detailvals
        TextView valstv = new TextView(getActivity());
        valstv.setId(300 + idTableRow);
        valstv.setText("Column 1");
        tableRow.addView(valstv);

        return  tableRow;
    }


    public void setWalletPublisherManager(WalletPublisherModuleManager walletPublisherManager) {
        this.walletPublisherManager = walletPublisherManager;
    }
}
