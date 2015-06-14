package com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bitdubai.smartwallet.R;
import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;

import java.util.ArrayList;


public  class AccountDetailAllFragment extends Fragment {

    private static final String ARG_POSITION = "position";


    View rootView;
    ExpandableListView lv;
    private String[] types;
    private String[] contacts  = new String[]{};
    private ArrayList<String> contact_list = new ArrayList<>();
    private ArrayList<String> amounts_list = new ArrayList<>();
    private ArrayList<String> whens_list = new ArrayList<>();
    private ArrayList<String> pictures_list = new ArrayList<>();
    private ArrayList<String> notes_list = new ArrayList<>();
    private ArrayList<String> types_list = new ArrayList<>();
    private String[] amounts;
    private String[] whens;
    private String[] notes;
    private String[] pictures;
    private String[][] transactions;
    String[][] transactions_amounts;
    private String[][] transactions_whens;
    private String[] debit_credit;
    private String[] types_all;
    private String[] contacts_all;
    private String[] amounts_all;
    private String[] whens_all;
    private String[] notes_all;
    private String[] pictures_all;
    private static int tabId;




    public static AccountDetailAllFragment newInstance(int position) {
        AccountDetailAllFragment f = new AccountDetailAllFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        tabId = position;
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
String debits = "";
        types_all = new String[]{ "received", "received", "sent", "refill","sent", "refill" , "received", "sent","received", "refill"};
        debit_credit = new String[]{"credit",
                "credit",
                "debit",
                "credit",
                "debit",
                "credit",
                "credit",
                "debit",
                "credit",
                "credit"};
        contacts_all = new String[]{"Lucia Alarcon De Zamacona",
                "Juan Luis R. Pons",
                "Luis Fernando Molina",
                "Kalustyan´s",
                "Guillermo Villanueva",
                "Kings Super Market",
                "Karina Rodriguez",
                "Mariana Duyos",
                "Taylor Backus",
                "D´Agostino"};
        amounts_all = new String[]{"$200.00", "$3,000.00", "$325.00", "$350.00", "$1,400.00", "$1,500.00", "$400.00"," $25.00","$600.00", "$250.00"};
        whens_all = new String[]{"4 hours ago", "5 hours ago", "yesterday", "yesterday", "yesterday", "yesterday", "31 dec 14", "23 may 14", "1 year ago", "5 sep 14"};
        notes_all = new String[]{"new telephone", "old desk", "electricity bill ", "for electricity bill", "flat rent", "for this week expenses","car oil", "more pictures","computer monitor", "refill test"};
        pictures_all = new String[]{"lucia_profile_picture", "juan_profile_picture", "luis_profile_picture", "refill_2", "guillermo_profile_picture", "refill_4","karina_profile_picture","mariana_profile_picture","taylor_profile_picture","refill_1"};

        //tab credit
     /*   if(tabId == 1) {
            for (int i = 0; i < debit_credit.length; i++) {
                if (debit_credit[i] == "credit") {
                    contact_list.add(contacts_all[i]);
                    amounts_list.add(amounts_all[i]);
                    whens_list.add(whens_all[i]);
                    pictures_list.add(pictures_all[i]);
                    notes_list.add(notes_all[i]);
                    types_list.add(types_all[i]);

                    }
            }

               types = (String[])types_list.toArray(new String[types_list.size()]);
                contacts = (String[])contact_list.toArray(new String[contact_list.size()]);
                amounts = (String[])amounts_list.toArray(new String[amounts_list.size()]);
                whens = (String[])whens_list.toArray(new String[whens_list.size()]);
                notes = (String[])notes_list.toArray(new String[notes_list.size()]);
                pictures = (String[])pictures_list.toArray(new String[pictures_list.size()]);


        }*/
        //tab debit
        if(tabId == 0) {
            for (int i = 0; i < debit_credit.length; i++) {
                if (debit_credit[i] == "debit") {
                    contact_list.add(contacts_all[i]);
                    amounts_list.add(amounts_all[i]);
                    whens_list.add(whens_all[i]);
                    pictures_list.add(pictures_all[i]);
                    notes_list.add(notes_all[i]);
                    types_list.add(types_all[i]);
                }
            }
            try
            {
                types = (String[])types_list.toArray(new String[types_list.size()]);
                contacts = (String[])contact_list.toArray(new String[contact_list.size()]);
                amounts = (String[])amounts_list.toArray(new String[amounts_list.size()]);
                whens = (String[])whens_list.toArray(new String[whens_list.size()]);
                notes = (String[])notes_list.toArray(new String[notes_list.size()]);
                pictures = (String[])pictures_list.toArray(new String[pictures_list.size()]);
            }
            catch(Exception e){
                String error = e.getMessage();
            }

        }

        //tab all
        if(tabId == 2) {
            types = types_all;
            contacts = contacts_all;
            amounts = amounts_all;
            whens = whens_all;
            notes = notes_all;
            pictures = pictures_all;
        }

        transactions = new String[][]{

                {},
                {},
                {},
                {},

        };

        transactions_amounts = new String[][]{

                {},
                {},
                {},
                {},

        };

        transactions_whens = new String[][]{

                {},
                {},
                {},
                {},
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_account_detail_all_list, container, false);



        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(contacts, transactions));
        lv.setGroupIndicator(null);

        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
           public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

              //  Intent intent;
               // intent = new Intent(getActivity(), SentDetailActivity.class);
                //startActivity(intent);

                return true;
            }
        });

        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

               // if (groupPosition == 0) {
                //    Intent intent;
                 //   intent = new Intent(getActivity(), SendToNewContactActivity.class);
                  //  startActivity(intent);
                   // return false;
                //}
                //else
                //{
                    return false;
                //}
            }
        });


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

            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;
            ViewHolder type;


            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_account_detail_all, parent, false);
                holder = new ViewHolder();

                type = new ViewHolder();
                type.text = (TextView) convertView.findViewById(R.id.type);
                type.text.setTypeface(MyApplication.getDefaultTypeface());

                holder.text = (TextView) convertView.findViewById(R.id.notes);
                holder.text.setTypeface(MyApplication.getDefaultTypeface());
                convertView.setTag(holder);

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);
                amount.text.setTypeface(MyApplication.getDefaultTypeface());

                amount.text.setText(transactions_amounts[groupPosition][childPosition].toString());

                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);
                when.text.setTypeface(MyApplication.getDefaultTypeface());

                when.text.setText(transactions_whens[groupPosition][childPosition].toString());

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;
            ViewHolder note;
            ImageView profile_picture;
            ViewHolder type;



            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_account_detail_all, parent, false);

                profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);


                    switch (pictures[groupPosition])
                    {
                        case "lucia_profile_picture":
                            profile_picture.setImageResource(R.drawable.lucia_profile_picture);
                            break;
                        case "juan_profile_picture":
                            profile_picture.setImageResource(R.drawable.juan_profile_picture);
                            break;
                        case "luis_profile_picture":
                            profile_picture.setImageResource(R.drawable.luis_profile_picture);
                            break;
                        case "refill_2":
                            profile_picture.setImageResource(R.drawable.refill_2);
                            break;
                        case "guillermo_profile_picture":
                            profile_picture.setImageResource(R.drawable.guillermo_profile_picture);
                            break;
                        case "refill_4":
                            profile_picture.setImageResource(R.drawable.refill_4);
                            break;
                        case "karina_profile_picture":
                            profile_picture.setImageResource(R.drawable.karina_profile_picture);
                            break;
                        case "mariana_profile_picture":
                            profile_picture.setImageResource(R.drawable.mariana_profile_picture);
                            break;
                        case "taylor_profile_picture":
                            profile_picture.setImageResource(R.drawable.taylor_profile_picture);
                            break;
                        case "refill_1":
                            profile_picture.setImageResource(R.drawable.refill_1);
                    }

             /*   switch (groupPosition)
                {
                    case 0:
                        profile_picture.setImageResource(R.drawable.lucia_profile_picture);
                        break;
                    case 1:
                        profile_picture.setImageResource(R.drawable.juan_profile_picture);
                        break;
                    case 2:
                        profile_picture.setImageResource(R.drawable.luis_profile_picture);
                        break;
                    case 3:
                        profile_picture.setImageResource(R.drawable.refill_2);
                        break;
                    case 4:
                        profile_picture.setImageResource(R.drawable.guillermo_profile_picture);
                        break;
                    case 5:
                        profile_picture.setImageResource(R.drawable.refill_4);
                        break;
                    case 6:
                        profile_picture.setImageResource(R.drawable.karina_profile_picture);
                        break;
                    case 7:
                        profile_picture.setImageResource(R.drawable.mariana_profile_picture);
                        break;
                    case 8:
                        profile_picture.setImageResource(R.drawable.taylor_profile_picture);
                        break;
                    case 9:
                        profile_picture.setImageResource(R.drawable.refill_1);
                }*/



                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.contact_name);
                holder.text.setTypeface(MyApplication.getDefaultTypeface());
                convertView.setTag(holder);

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);
                amount.text.setTypeface(MyApplication.getDefaultTypeface());

                amount.text.setText(amounts[groupPosition].toString());

                type = new ViewHolder();
                type.text = (TextView) convertView.findViewById(R.id.type);
                type.text.setTypeface(MyApplication.getDefaultTypeface());
                type.text.setText(types[groupPosition].toString());

                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);
                when.text.setTypeface(MyApplication.getDefaultTypeface());
                when.text.setText(whens[groupPosition].toString());

                note = new ViewHolder();
                note.text = (TextView) convertView.findViewById(R.id.notes);
                note.text.setTypeface(MyApplication.getDefaultTypeface());

                note.text.setText(notes[groupPosition].toString());

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getGroup(groupPosition).toString());



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

