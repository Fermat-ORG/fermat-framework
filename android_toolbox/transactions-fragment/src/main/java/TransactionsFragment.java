import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.customviews.clelia.transactionsfragment.R;

import java.util.ArrayList;

import TransactionsFragment.Utility.SpacesItemDecoration;

/**
 * Created by Clelia López on 3/14/16
 */
public class TransactionsFragment
        extends Fragment {

    /**
     * Attributes
     */
    private static ArrayList<Bundle> transactionsList;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private TransactionsAdapter transactionsAdapter;

    private static TransactionItemView customView = null;
    private static boolean withCustomView = false;

    private static OnRefreshItemsListener listener;

    private Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    /**
     * Create fragment
     *
     * @param transactions - ArrayList< Bundle > with parameters
     * @return - Fragment
     */
    public static TransactionsFragment newInstance(ArrayList<Bundle> transactions) {
        TransactionsFragment transactionsFragment = new TransactionsFragment();
        transactionsList = transactions;
        return transactionsFragment;
    }

    /**
     * Create fragment
     *
     * @param transactions - ArrayList< Bundle > with parameters
     * @param view - Custom TransactionItemView created with Builder class
     * @return - Fragment
     */
    public static TransactionsFragment newInstance(ArrayList<Bundle> transactions, TransactionItemView view) {
        TransactionsFragment transactionsFragment = new TransactionsFragment();
        transactionsList = transactions;
        customView = view;
        withCustomView = true;
        return transactionsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.transactions_fragment, null);
        swipeContainer = (SwipeRefreshLayout) linearLayout.getChildAt(0);
        recyclerView = (RecyclerView) swipeContainer.getChildAt(1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            recyclerView.addItemDecoration(new SpacesItemDecoration(26));
        else
            recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        return linearLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if(transactionsList != null) {
            if(withCustomView) {
                transactionsAdapter = new TransactionsAdapter(context, transactionsList, customView, recyclerView);
            } else
                transactionsAdapter = new TransactionsAdapter(context, transactionsList, recyclerView);
            recyclerView.setAdapter(transactionsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(listener != null)
                    listener.onRefreshItems(transactionsAdapter);
                swipeContainer.setRefreshing(false);
            }
        });
    }

    /**
     * Observer
     */
    public interface OnRefreshItemsListener {
        void onRefreshItems(TransactionsAdapter adapter);
    }

    public static class TransactionListener {

        public void setListener(OnRefreshItemsListener externalListener) {
            listener = externalListener;
        }
    }
}