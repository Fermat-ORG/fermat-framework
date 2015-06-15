package com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.utils.BusProvider;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.utils.FragmentEvent;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.utils.FragmentsEnum;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "page";
    private static final String ARG_PARAM2 = "title";

    private int mParam1;
    private String mParam2;

    public static ProfileFragment newInstance(int page, String title) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, page);
        args.putString(ARG_PARAM2, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final EditText editText = (EditText) view.findViewById(R.id.company_name);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent me) {
                //v.findViewById(R.id.company_name).setFocusable(true);
                Toast.makeText(getActivity(), "Editing Company Name.", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        final Button button = (Button) view.findViewById(R.id.delete_community_fragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(new FragmentEvent(FragmentsEnum.COMMUNITY));
            }
        });

        final Button buttonC = (Button) view.findViewById(R.id.delete_contacts_fragment);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(new FragmentEvent(FragmentsEnum.CONTACTS));
            }
        });

        addItemsOnSpinner(view, R.id.spinner_currency);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    // you need to register the activity
    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner(View view, int R_id) {
        Spinner spinner = (Spinner) view.findViewById(R_id);
        List<String> list = new ArrayList<String>();
        for (CryptoCurrency c :CryptoCurrency.values()) {
            list.add(c.getCode());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

}

