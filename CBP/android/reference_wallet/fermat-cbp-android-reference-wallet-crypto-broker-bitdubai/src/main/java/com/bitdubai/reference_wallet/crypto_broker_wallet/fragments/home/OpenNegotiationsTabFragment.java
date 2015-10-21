package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.VerticalChild;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.VerticalParent;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.CbpVerticalExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenNegotiationsTabFragment extends FermatWalletFragment implements ExpandableRecyclerAdapter.ExpandCollapseListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private RecyclerView mRecyclerView;
    private CbpVerticalExpandableAdapter mExpandableAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static OpenNegotiationsTabFragment newInstance() {
        return new OpenNegotiationsTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_open_negotiations, container, false);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.open_negotiations_recycler_view);

        // Create a new adapter with 20 test data items
        mExpandableAdapter = new CbpVerticalExpandableAdapter(getActivity(), setUpTestData(5));

        // Attach this activity to the Adapter as the ExpandCollapseListener
        mExpandableAdapter.setExpandCollapseListener(this);

        // Set the RecyclerView's adapter to the ExpandableAdapter we just created
        mRecyclerView.setAdapter(mExpandableAdapter)
        ;
        // Set the layout manager to a LinearLayout manager for vertical list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }


    /**
     * Save the instance state of the adapter to keep expanded/collapsed states when rotating or
     * pausing the activity.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        mExpandableAdapter.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemExpanded(int position) {
        //String toastMessage = getString(R.string.item_expanded, position);
        Toast.makeText(getActivity(), "item expanded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemCollapsed(int position) {
        //String toastMessage = getString(R.string.item_collapsed, position);
        Toast.makeText(getActivity(), "Item collapsed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to set up test data used in the RecyclerView.
     * <p/>
     * Each child list item contains a string.
     * Each parent list item contains a number corresponding to the number of the parent and a string
     * that contains a message.
     * Each parent also contains a list of children which is generated in this. Every odd numbered
     * parent gets one child and every even numbered parent gets two children.
     *
     * @return A List of Objects that contains all parent items. Expansion of children are handled in the adapter
     */
    private List<VerticalParent> setUpTestData(int numItems) {
        List<VerticalParent> verticalParentList = new ArrayList<>();

        for (int i = 0; i < numItems; i++) {
            List<VerticalChild> childItemList = new ArrayList<>();

            VerticalChild verticalChild = new VerticalChild();
            verticalChild.setChildText("hijo 1: " + i);
            childItemList.add(verticalChild);

            // Evens get 2 children, odds get 1
            if (i % 2 == 0) {
                VerticalChild verticalChild2 = new VerticalChild();
                verticalChild2.setChildText("hijo 2" + i);
                childItemList.add(verticalChild2);
            }

            VerticalParent verticalParent = new VerticalParent();
            verticalParent.setChildItemList(childItemList);
            verticalParent.setParentNumber(i);
            verticalParent.setParentText("texto padre");
            if (i == 0) {
                verticalParent.setInitiallyExpanded(true);
            }
            verticalParentList.add(verticalParent);
        }

        return verticalParentList;
    }

}
