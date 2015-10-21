package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.VerticalChild;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.VerticalParent;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.VerticalExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link expadable.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link expadable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class expadable extends FermatWalletFragment implements ExpandableRecyclerAdapter.ExpandCollapseListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private RecyclerView mRecyclerView;
    private VerticalExpandableAdapter mExpandableAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     */
    // TODO: Rename and change types and number of parameters
    public static expadable newInstance() {
        expadable fragment = new expadable();

        return fragment;
    }

    public expadable() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.transaciotn_main_fragment_receive, container, false);


        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.transactions_recycler_view);

        // Create a new adapter with 20 test data items
        mExpandableAdapter = new VerticalExpandableAdapter(getActivity(), setUpTestData(5));

        // Attach this activity to the Adapter as the ExpandCollapseListener
        mExpandableAdapter.setExpandCollapseListener(this);

        // Set the RecyclerView's adapter to the ExpandableAdapter we just created
        mRecyclerView.setAdapter(mExpandableAdapter);
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

    /**
     * Load the expanded/collapsed states of the adapter back into the view when done rotating or
     * resuming the activity.
     */
//    @Override
//    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        mExpandableAdapter.onRestoreInstanceState(savedInstanceState);
//    }

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



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    /**
     * Method to set up test data used in the RecyclerView.
     *
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
            //verticalChild.setChildText(getString(R.string.child_text, i));
            verticalChild.setChildText("holas");
            childItemList.add(verticalChild);

            // Evens get 2 children, odds get 1
            if (i % 2 == 0) {
                VerticalChild verticalChild2 = new VerticalChild();
                //verticalChild2.setChildText(getString(R.string.second_child_text, i));
                verticalChild2.setChildText("holas2222");
                childItemList.add(verticalChild2);
            }

            VerticalParent verticalParent = new VerticalParent();
            verticalParent.setChildItemList(childItemList);
            verticalParent.setParentNumber(i);
            //verticalParent.setParentText(getString(R.string.parent_text, i));
            verticalParent.setParentText("parent text");
            if (i == 0) {
                verticalParent.setInitiallyExpanded(true);
            }
            verticalParentList.add(verticalParent);
        }

        return verticalParentList;
    }

}
