package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment;


import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;

import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyApplication;


public class HomeFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<HomeFragment.Model>> {

    CustomArrayAdapter mAdapter;

    private static final String ARG_POSITION = "position";

    private int position;

    public static HomeFragment newInstance(int position) {
        HomeFragment f = new HomeFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }



    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        System.out.println("HomeFragment.onActivityCreated");

        // Initially there is no data
        setEmptyText("No Data Here");

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new CustomArrayAdapter(getActivity());
        setListAdapter(mAdapter);

        // Start out with a progress indicator.
        setListShown(false);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behavior here.
        Log.i("HomeFragment", "Item clicked: " + id);
    }

    @Override
    public Loader<List<Model>> onCreateLoader(int arg0, Bundle arg1) {
        System.out.println("HomeFragment.onCreateLoader");
        return new DataListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Model>> arg0, List<Model> data) {
        mAdapter.setData(data);
        System.out.println("HomeFragment.onLoadFinished");
        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Model>> arg0) {
        mAdapter.setData(null);
    }




    public static class DataListLoader extends AsyncTaskLoader<List<Model>> {

        List<Model> mModels;

        public DataListLoader(Context context) {
            super(context);
        }

        @Override
        public List<Model> loadInBackground() {
            System.out.println("DataListLoader.loadInBackground");

            // You should perform the heavy task of getting data from
            // Internet or database or other source
            // Here, we are generating some Sample data

            // Create corresponding array of entries and load with data.
            List<Model> entries = new ArrayList<Model>(8);
            entries.add(new Model("Java", "2"));
            entries.add(new Model("Java", "2"));
            entries.add(new Model("Java", "2"));
            entries.add(new Model("Java", "2"));
            entries.add(new Model("Java", "2"));
            entries.add(new Model("Java", "2"));
            entries.add(new Model("Java", "2"));
            entries.add(new Model("Java", "2"));
            entries.add(new Model("Java", "2"));



            return entries;
        }

        /**
         * Called when there is new data to deliver to the client.  The
         * super class will take care of delivering it; the implementation
         * here just adds a little more logic.
         */
        @Override public void deliverResult(List<Model> listOfData) {
            if (isReset()) {
                // An async query came in while the loader is stopped.  We
                // don't need the result.
                if (listOfData != null) {
                    onReleaseResources(listOfData);
                }
            }
            List<Model> oldApps = listOfData;
            mModels = listOfData;

            if (isStarted()) {
                // If the Loader is currently started, we can immediately
                // deliver its results.
                super.deliverResult(listOfData);
            }

            // At this point we can release the resources associated with
            // 'oldApps' if needed; now that the new result is delivered we
            // know that it is no longer in use.
            if (oldApps != null) {
                onReleaseResources(oldApps);
            }
        }

        /**
         * Handles a request to start the Loader.
         */
        @Override protected void onStartLoading() {
            if (mModels != null) {
                // If we currently have a result available, deliver it
                // immediately.
                deliverResult(mModels);
            }


            if (takeContentChanged() || mModels == null) {
                // If the data has changed since the last time it was loaded
                // or is not currently available, start a load.
                forceLoad();
            }
        }

        /**
         * Handles a request to stop the Loader.
         */
        @Override protected void onStopLoading() {
            // Attempt to cancel the current load task if possible.
            cancelLoad();
        }

        /**
         * Handles a request to cancel a load.
         */
        @Override public void onCanceled(List<Model> apps) {
            super.onCanceled(apps);

            // At this point we can release the resources associated with 'apps'
            // if needed.
            onReleaseResources(apps);
        }

        /**
         * Handles a request to completely reset the Loader.
         */
        @Override protected void onReset() {
            super.onReset();

            // Ensure the loader is stopped
            onStopLoading();

            // At this point we can release the resources associated with 'apps'
            // if needed.
            if (mModels != null) {
                onReleaseResources(mModels);
                mModels = null;
            }
        }

        /**
         * Helper function to take care of releasing resources associated
         * with an actively loaded data set.
         */
        protected void onReleaseResources(List<Model> apps) {}

    }





    public class CustomArrayAdapter extends ArrayAdapter<Model> {
        private final LayoutInflater mInflater;

        public CustomArrayAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_2);
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        public void setData(List<Model> data) {
            clear();
            if (data != null) {
                for (Model appEntry : data) {
                    add(appEntry);
                }
            }
        }

        /**
         * Populate new items in the list.
         */
        @Override public View getView(int position, View convertView, ViewGroup parent) {
            String[] account_types;
            String[] balances;
            String[] balances_available;
            String[] account_aliases;

            String[] contacts;
            String[] amounts;
            String[] whens;
            String[] notes;

            contacts = new String[]{"","","","Stephanie Himonidis","Kimberly Brown","Ginny Kaltabanis","Piper Faust","Taylor Backus","Stephanie Himonidis","Kimberly Brown" ,"","","",""};
            amounts = new String[]{"","", "", "$290.00","$600.00","$50.00","$30.00","$600.00","50.00","$80,000.00","","","","",""};
            whens = new String[]{"", "", "", "3 Feb 14","1 year ago","1 year ago","2 years ago","1 year ago","1 year ago","2 year ago","","","",""};
            notes = new String[]{"","", "",  "conference ticket","computer monitor","bag","computer keyboard","Computer monitor","Pen","Apartment in Dubai","","","",""};



            account_types = new String[]{"1 current and 2 saving accounts"};

            balances = new String[]{"$5,693.50"};
            balances_available = new String[]{"$1,970.00 available"};

            View view;
            view = mInflater.inflate(R.layout.wallets_teens_fragment_home_list_item, parent, false);
            //if (convertView == null) {

            //} else {
            //    view = convertView;
            //}

            ImageView account_picture;
            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;
            ViewHolder note;


            TextView tv;

            switch (position)
            {
                case 0:
                    view = mInflater.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);

                    tv = ((TextView)view.findViewById(R.id.title));
                    tv.setText("Total balance in all accounts");
                    tv.setTypeface(MyApplication.getDefaultTypeface());

                    break;

                case 1:
                    //never gets here

                    view = mInflater.inflate(R.layout.wallets_teens_fragment_home_list_item, parent, false);

                    tv = ((TextView)view.findViewById(R.id.balance));
                    tv.setText(balances[position-1]);
                    tv.setTypeface(MyApplication.getDefaultTypeface());

                    tv = ((TextView)view.findViewById(R.id.balance_available));
                    tv.setText(balances_available[position-1]);
                    tv.setTypeface(MyApplication.getDefaultTypeface());



                    tv = ((TextView)view.findViewById(R.id.account_type));
                    tv.setText(account_types[position-1]);
                    tv.setTypeface(MyApplication.getDefaultTypeface());

                    break;

                case 2:
                    view = mInflater.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);

                    tv = ((TextView)view.findViewById(R.id.title));
                    tv.setText("Requests received waiting to be accepted");
                    tv.setTypeface(MyApplication.getDefaultTypeface());

                    break;

                case 3: case 4:case 5:case 6:case 8:

                    view = mInflater.inflate(R.layout.wallets_teens_multiple_fragments_request_received_list_item, parent, false);
                    account_picture = (ImageView) view.findViewById(R.id.profile_picture);


                    holder = new ViewHolder();
                    holder.text = (TextView) view.findViewById(R.id.contact_name);
                    holder.text.setTypeface(MyApplication.getDefaultTypeface());
                    holder.text.setText(contacts[position].toString());

                    amount = new ViewHolder();
                    amount.text = (TextView) view.findViewById(R.id.amount);
                    amount.text.setTypeface(MyApplication.getDefaultTypeface());

                    amount.text.setText(amounts[position].toString());

                    when = new ViewHolder();
                    when.text = (TextView) view.findViewById(R.id.when);
                    when.text.setTypeface(MyApplication.getDefaultTypeface());

                    when.text.setText(whens[position].toString());

                    note = new ViewHolder();
                    note.text = (TextView) view.findViewById(R.id.notes);
                    note.text.setTypeface(MyApplication.getDefaultTypeface());

                    note.text.setText(notes[position].toString());

                    switch (position)
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
                    }
                    break;
                case 7:
                    view = mInflater.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);
                    tv = ((TextView)view.findViewById(R.id.title));
                    tv.setText("Requests sent waiting to be accepted");
                    tv.setTypeface(MyApplication.getDefaultTypeface());
                    break;
            }
            return view;
        }
    }

    private class ViewHolder {
        TextView text;
    }

    public static class Model {

        private String name;
        private String id;

        public Model(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

}