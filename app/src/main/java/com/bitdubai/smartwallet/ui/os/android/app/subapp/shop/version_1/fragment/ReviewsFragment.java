package com.bitdubai.smartwallet.ui.os.android.app.subapp.shop.version_1.fragment;

import android.content.Intent;
import android.graphics.Typeface;
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
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity.SentDetailActivity;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyApplication;

/**
 * Created by Natalia on 09/01/2015.
 */
public class ReviewsFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] reviews;
    private String[] dislikes;
    private String[] likes;
    private String[] rates;
    private String[] pictures;
    private String[][] transactions;



    public static ReviewsFragment newInstance(int position) {
        ReviewsFragment f = new ReviewsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{"Brant Cryder", "Teddy Truchot", "Helene Derosier","Piper faust"};
        reviews = new String[]{ "\"Cheap and tasty. Get the coffee with donuts.\"", "\"The service was wonderful.I love it.\"", "\"they constantly mess up orders if there was another dunkin donuts around I'd never go back.\"", " \"I don't like the service at this particular Dunkin store.\" ", "Insert review here", "Insert review here", "Insert review here", "Insert review here",};
        likes = new String[]{"5","3","0","4","Insert like","Insert like",};
        dislikes = new String[]{"0","1","2 ","0","Insert dislike",};
        rates = new String[]{"5","1","0","2","0","0",};
        pictures = new String[]{"brant_profile_picture", "teddy_profile_picture", "helene_profile_picture", "piper_profile_picture"};

        transactions = new String[][]{

        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shop_fragment_reviews_store, container, false);



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
/*
                if (groupPosition == 0) {
                    Intent intent;
                    intent = new Intent(getActivity(), SendToNewContactActivity.class);
                    startActivity(intent);
                    return false;
                }
                else*/
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
            ViewHolder review;



            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.shop_fragment_reviews_list_detail, parent, false);
                holder = new ViewHolder();


                review = new ViewHolder();
                review.text = (TextView) convertView.findViewById(R.id.review);
                review.text.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));



            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ViewHolder review;
            ViewHolder like;
            ViewHolder dislike;
            ImageView profile_picture;
            ImageView star1;
            ImageView star2;
            ImageView star3;
            ImageView star4;
            ImageView star5;

            ImageView send_picture;



            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.shop_fragment_reviews_list_header, parent, false);

                profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);
                switch (groupPosition)
                {
                    case 0:
                        profile_picture.setImageResource(R.drawable.brant_profile_picture);
                        break;
                    case 1:
                        profile_picture.setImageResource(R.drawable.teddy_profile_picture);
                        break;
                    case 2:
                        profile_picture.setImageResource(R.drawable.helene_profile_picture);
                        break;
                    case 3:
                        profile_picture.setImageResource(R.drawable.piper_profile_picture);
                        break;
                }



                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.contact_name);
                holder.text.setTypeface(MyApplication.getDefaultTypeface());
                convertView.setTag(holder);

                review = new ViewHolder();
                review.text = (TextView) convertView.findViewById(R.id.review);
                review.text.setTypeface(MyApplication.getDefaultTypeface());

                review.text.setText(reviews[groupPosition].toString());

                like = new ViewHolder();
                like.text = (TextView) convertView.findViewById(R.id.like_amount);
                like.text.setTypeface(MyApplication.getDefaultTypeface());
                like.text.setText(likes[groupPosition].toString());

                dislike = new ViewHolder();
                dislike.text = (TextView) convertView.findViewById(R.id.dislike_amount);
                dislike.text.setTypeface(MyApplication.getDefaultTypeface());
                dislike.text.setText(dislikes[groupPosition].toString());

                star1 = (ImageView) convertView.findViewById(R.id.star_1);
                star2 = (ImageView) convertView.findViewById(R.id.star_2);
                star3 = (ImageView) convertView.findViewById(R.id.star_3);
                star4 = (ImageView) convertView.findViewById(R.id.star_4);
                star5 = (ImageView) convertView.findViewById(R.id.star_5);
                int rate = Integer.parseInt(rates[groupPosition]);
                if (rate >= 0)
                {
                    star1.setImageResource(R.drawable.grid_background_star_full);
                }
                if (rate >= 1)
                {
                    star2.setImageResource(R.drawable.grid_background_star_full);
                }
                if (rate >= 2)
                {
                    star3.setImageResource(R.drawable.grid_background_star_full);
                }
                if (rate >= 3)
                {
                    star4.setImageResource(R.drawable.grid_background_star_full);
                }
                if (rate >= 4)
                {
                    star5.setImageResource(R.drawable.grid_background_star_full);
                }

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


