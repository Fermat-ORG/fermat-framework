package com.bitdubai.smartwallet.wallets.teens;

import android.content.Intent;
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
import com.bitdubai.smartwallet.walletframework.MyApplication;
import com.bitdubai.smartwallet.walletframework.SendToNewContactActivity;
import com.bitdubai.smartwallet.walletframework.SentDetailActivity;

/**
 * Created by Natalia on 19/12/2014.
 */
public class HistoryFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] amounts;
    private String[] totalAmount;
    private String[] historyCount;
    private String[] whens;
    private String[] notes;
    private String[] pictures;
    private String[][] transactions;
    String[][] transactions_amounts;
    private String[][] transactions_whens;



    public static HistoryFragment newInstance(int position) {
        HistoryFragment f = new HistoryFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{"cambiar ", "todas ", "las personas", "por productos "};
        amounts = new String[]{"montos", "como este", "este", "y este"};
        whens = new String[]{"todas la fechas", "2 hours ago", "today 9:24 AM", "yesterday"};
        notes = new String[]{ "pone algo aca ", "aca", "aca", "y aca"};
        totalAmount = new String[]{"aca van los precios de lo que se compro ","en todos ","este","este tambien"};
        historyCount = new String[] {"","","",""}; //queda vacio aca, borra esto despues
        pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{

                {"","",""},
                {"","","","","",""},
                {""},
                {""}
        };

        transactions_amounts = new String[][]{

                {},
                {"","",""},
                {"","","","","","",""},
                {""},
                {""}
        };

        transactions_whens = new String[][]{

                {},
                {"","",""},
                {"","","","","","",""},
                {""},
                {""}
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_history_store, container, false);



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

                Intent intent;
                intent = new Intent(getActivity(), SentDetailActivity.class);
                startActivity(intent);

                return true;
            }
        });

        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition == 0) {
                    Intent intent;
                    intent = new Intent(getActivity(), SendToNewContactActivity.class);
                    startActivity(intent);
                    return false;
                }
                else
                {
                    return false;
                }
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


            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_history_list_detail, parent, false);
                holder = new ViewHolder();

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
            ImageView send_picture;
            ViewHolder total;
            ViewHolder history;



                //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
                // if (convertView == null) {
                if (1 == 1) {
                    convertView = inf.inflate(R.layout.wallets_teens_fragment_history_list_header, parent, false);

                    profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);
                    switch (groupPosition)
                    {
                        case 0:
                            profile_picture.setImageResource(R.drawable.new_contact_profile_picture);
                            break;
                        case 1:
                            profile_picture.setImageResource(R.drawable.luis_profile_picture);
                            break;
                        case 2:
                            profile_picture.setImageResource(R.drawable.guillermo_profile_picture);
                            break;
                        case 3:
                            profile_picture.setImageResource(R.drawable.pedro_profile_picture);
                            break;
                        case 4:
                            profile_picture.setImageResource(R.drawable.mariana_profile_picture);
                            break;

                    }



                    holder = new ViewHolder();
                    holder.text = (TextView) convertView.findViewById(R.id.contact_name);
                    holder.text.setTypeface(MyApplication.getDefaultTypeface());
                    convertView.setTag(holder);

                    amount = new ViewHolder();
                    amount.text = (TextView) convertView.findViewById(R.id.amount);
                    amount.text.setTypeface(MyApplication.getDefaultTypeface());

                    amount.text.setText(amounts[groupPosition].toString());

                    when = new ViewHolder();
                    when.text = (TextView) convertView.findViewById(R.id.when);
                    when.text.setTypeface(MyApplication.getDefaultTypeface());

                    when.text.setText(whens[groupPosition].toString());

                    total = new ViewHolder();
                    total.text = (TextView) convertView.findViewById(R.id.total_amount);
                    total.text.setTypeface(MyApplication.getDefaultTypeface());
                    total.text.setText(totalAmount[groupPosition].toString());

                    history = new ViewHolder();
                    history.text = (TextView) convertView.findViewById(R.id.history_count);
                    history.text.setTypeface(MyApplication.getDefaultTypeface());
                    history.text.setText(historyCount[groupPosition].toString());

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


