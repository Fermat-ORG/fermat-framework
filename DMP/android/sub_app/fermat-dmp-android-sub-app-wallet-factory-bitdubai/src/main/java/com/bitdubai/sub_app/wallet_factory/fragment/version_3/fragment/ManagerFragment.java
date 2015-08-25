package com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;


public class ManagerFragment extends FermatFragment {
    View rootView;
    ExpandableListView lv;
    String[] contacts;
    String[] amounts;
    String[] whens;
    String[] notes;
    String[] account_types;
    String[] balances;
    String[] balances_available;
    private String[][] transactions;
    private static final String ARG_POSITION = "position";

    private int position;

    private WalletFactorySubAppSession subAppSession;

    public static ManagerFragment newInstance(int position,com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession subAppSession) {
        ManagerFragment f = new ManagerFragment();
        f.setSubAppSession((WalletFactorySubAppSession)subAppSession);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{"","","","Stephanie Himonidis","Kimberly Brown","Ginny Kaltabanis","Piper Faust","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };
        amounts = new String[]{"","", "", "$290.00","$600.00","$50.00","$30.00","$600.00","50.00","$80,000.00"};
        whens = new String[]{"", "", "", "3 Feb 14","1 year ago","1 year ago","2 years ago","1 year ago","1 year ago","2 year ago"};
        notes = new String[]{"","", "",  "conference ticket","computer monitor","bag","computer keyboard","Computer monitor","Pen","Apartment in Dubai"};
        transactions = new String[][]{

        };

        account_types = new String[]{"1 current and 2 saving accounts"};
        balances = new String[]{"$5,693.50"};
        balances_available = new String[]{"$1,970.00 available"};

        /*try {
            //List<DescriptorFactoryProject> lstFactoryProjects= subAppSession.getWalletDescriptorFactoryProjectManager().getAllWalletFactoryProjects();

            //WalletFactoryProjectProposalManager walletFactoryProjectProposalManager= subAppSession.getWalletDescriptorFactoryProjectManager().getWalletFactoryProjectProposalManager(lstFactoryProjects.get(0));

            //List<WalletFactoryProjectProposal> walletFactoryProjectProposal= walletFactoryProjectProposalManager.getProposals();

        } catch (CantGetWalletFactoryProjectsException e) {
            e.printStackTrace();
        } catch (CantGetWalletFactoryProjectProposalsException e) {
            e.printStackTrace();
        }*/


        //ApplicationSession.changeColor(Color.parseColor("#F0E173"), super.getActivity().getResources());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_send_and_receive, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(contacts, transactions));
        lv.setGroupIndicator(null);

        lv.setOnItemClickListener(null);


       /* lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition == 0) {

                    Intent intent;
                    intent = new Intent(getActivity(), SendToNewContactActivity.class);
                    startActivity(intent);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });*/

    }

    public void setSubAppSession(WalletFactorySubAppSession subAppSession) {
        this.subAppSession = subAppSession;
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] contacts;
        private String[][] transactions;

        public ExpandableListAdapter(String[] contacts, String[][] transactions) {
            this.contacts = contacts;
            this.transactions = transactions;
            inf = LayoutInflater.from(getActivity());
        }





        @Override
        public int getGroupCount() {
            return contacts.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return transactions[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return contacts[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return transactions[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            return convertView;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {


       ImageView account_picture;
            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;
            ViewHolder note;


            TextView tv;
            if (groupPosition == 0) {
                convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);

                tv = ((TextView)convertView.findViewById(R.id.title));
                tv.setText("Total balance in all accounts");
                //tv.setTypeface(ApplicationSession.getDefaultTypeface());
            }
            else
            {
                if (1 == 1) {
                    switch (groupPosition)
                    {

                        case 1:
                            //never gets here

                            convertView = inf.inflate(R.layout.wallets_teens_fragment_home_list_item, parent, false);

                            tv = ((TextView)convertView.findViewById(R.id.balance));
                            tv.setText(balances[groupPosition-1]);
                            //tv.setTypeface(ApplicationSession.getDefaultTypeface());

                            tv = ((TextView)convertView.findViewById(R.id.balance_available));
                            tv.setText(balances_available[groupPosition-1]);
                            //tv.setTypeface(ApplicationSession.getDefaultTypeface());



                            tv = ((TextView)convertView.findViewById(R.id.account_type));
                            tv.setText(account_types[groupPosition-1]);
                            //tv.setTypeface(ApplicationSession.getDefaultTypeface());

                            break;

                        case 2:
                            convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);

                            tv = ((TextView)convertView.findViewById(R.id.title));
                            tv.setText("Requests received waiting to be accepted");
                            //tv.setTypeface(ApplicationSession.getDefaultTypeface());

                            break;

                        case 3: case 4:case 5:case 6:case 8:case 9:

                        convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_request_received_list_item, parent, false);
                        account_picture = (ImageView) convertView.findViewById(R.id.profile_picture);


                        holder = new ViewHolder();
                        holder.text = (TextView) convertView.findViewById(R.id.contact_name);
                        //holder.text.setTypeface(ApplicationSession.getDefaultTypeface());
                        holder.text.setText(contacts[groupPosition].toString());

                        amount = new ViewHolder();
                        amount.text = (TextView) convertView.findViewById(R.id.amount);
                        //amount.text.setTypeface(ApplicationSession.getDefaultTypeface());

                        amount.text.setText(amounts[groupPosition].toString());

                        when = new ViewHolder();
                        when.text = (TextView) convertView.findViewById(R.id.when);
                        //when.text.setTypeface(ApplicationSession.getDefaultTypeface());

                        when.text.setText(whens[groupPosition].toString());

                        note = new ViewHolder();
                        note.text = (TextView) convertView.findViewById(R.id.notes);
//                        note.text.setTypeface(ApplicationSession.getDefaultTypeface());

                        note.text.setText(notes[groupPosition].toString());

                        ImageView  send_message = (ImageView) convertView.findViewById(R.id.icon_edit_profile);
                        send_message.setTag("ContactsChatActivity|"+contacts[groupPosition].toString());


                        switch (groupPosition)
                        {

                            case 3:
                                account_picture.setImageResource(R.drawable.stephani_profile_picture);
                                break;
                            case 4:
                                account_picture.setImageResource(R.drawable.kimberly_profile_picture);
                                break;
                            case 5:
                                account_picture.setImageResource(R.drawable.ginny_profile_picture);
                                break;
                            case 6:
                                account_picture.setImageResource(R.drawable.piper_profile_picture);
                                break;

                            case 8:
                                account_picture.setImageResource(R.drawable.stephani_profile_picture);
                                break;
                            case 9:
                                account_picture.setImageResource(R.drawable.kimberly_profile_picture);
                                break;
                        }
                        break;
                        case 7:
                            convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);
                            tv = ((TextView)convertView.findViewById(R.id.title));
                            tv.setText("Requests sent waiting to be accepted");
  //                          tv.setTypeface(ApplicationSession.getDefaultTypeface());
                            break;
                    }
                }
            }


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }



        private class ViewHolder {
            TextView text;
        }
    }



}

