import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.customviews.clelia.transactionsfragment.R;

import java.util.ArrayList;

import TransactionsFragment.Custom.CaviarTextView;

/**
 * Created by Clelia López on 3/14/16
 */
public class TransactionsAdapter
        extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    /**
     * Attributes
     */
    private RecyclerView recyclerView;
    private ArrayList<Bundle> data;
    private TransactionItemView view = null;
    private boolean withCustomView = false;
    private static Context context;

    private static OnTransactionItemListener listener = null;

    /**
     * Holder class
     */
    public class ViewHolder
            extends RecyclerView.ViewHolder {

        CaviarTextView toValueTextView;
        CaviarTextView fromValueTextView;
        CaviarTextView dateTextView;
        ImageView stateImageView;
        CaviarTextView stateTextView;
        CaviarTextView noteTextView;
        CaviarTextView signTextView;
        CaviarTextView unitTextView;
        CaviarTextView amountTextView;
        Drawable completeDrawable;
        Drawable pendingDrawable;


        public ViewHolder(TransactionItemView view) {
            super(view);

            toValueTextView = view.getToValueTextView();
            fromValueTextView = view.getFromValueTextView();
            dateTextView = view.getDateTextView();
            stateImageView = view.getStateImageView();
            stateTextView = view.getStateTextView();
            noteTextView = view.getNoteTextView();
            signTextView = view.getSignTextView();
            unitTextView = view.getUnitTextView();
            amountTextView = view.getAmountTextView();
            completeDrawable = view.getCompleteDrawable();
            pendingDrawable = view.getPendingDrawable();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TransactionItemView itemView = (TransactionItemView) view;
                    if(listener != null)
                        listener.onTransactionClick(data.get(getAdapterPosition()), itemView);
                }
            });
        }
    }

    public TransactionsAdapter(Context mContext, ArrayList<Bundle> data, RecyclerView recyclerView) {
        context = mContext;
        this.data = data;
        this.recyclerView = recyclerView;
    }

    public TransactionsAdapter(Context mContext, ArrayList<Bundle> data, TransactionItemView view, RecyclerView recyclerView) {
        context = mContext;
        this.data = new ArrayList<>();
        this.data = data;
        this.view = view;
        this.recyclerView = recyclerView;
        withCustomView = true;
    }

    @Override
    public TransactionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(withCustomView)
            return new ViewHolder(view);
        else
            return new ViewHolder(new TransactionItemView(context));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.toValueTextView.setText(data.get(position).getString("to"));
        holder.fromValueTextView.setText(data.get(position).getString("from"));
        holder.dateTextView.setText(data.get(position).getString("date"));
        holder.stateTextView.setText(data.get(position).getString("state"));
        holder.noteTextView.setText(data.get(position).getString("note"));
        holder.signTextView.setText(data.get(position).getString("sign"));
        holder.unitTextView.setText(data.get(position).getString("unit"));
        holder.amountTextView.setText(data.get(position).getString("amount"));

        String type = data.get(position).getString("type");
        if(type != null)
            if(type.equalsIgnoreCase("income")) {
                holder.signTextView.setText("+");
                holder.signTextView.setTextColor(ContextCompat.getColor(context, R.color.green_a700));
                holder.unitTextView.setTextColor(ContextCompat.getColor(context, R.color.green_a700));
                holder.amountTextView.setTextColor(ContextCompat.getColor(context, R.color.green_a700));
            } else {
                holder.signTextView.setText("-");
                holder.signTextView.setTextColor(ContextCompat.getColor(context, R.color.red_200));
                holder.unitTextView.setTextColor(ContextCompat.getColor(context, R.color.red_200));
                holder.amountTextView.setTextColor(ContextCompat.getColor(context, R.color.red_200));
            }

        String state = data.get(position).getString("state");
        if(state != null)
            if(state.equalsIgnoreCase("complete"))
                holder.stateImageView.setImageDrawable(holder.completeDrawable);
            else
                holder.stateImageView.setImageDrawable(holder.pendingDrawable);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Clear data set
     */
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    /**
     * Add new items on refresh, at the end of the list
     *
     * @param items - the new data set
     */
    public void addDataAtEnd(ArrayList<Bundle> items) {
        data.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Add new items on refresh
     *
     * @param items - the new data set
     */
    public void addDataAtBeginning(ArrayList<Bundle> items) {
        items.addAll(data);
        data.clear();
        data.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * @return - ArrayList with all data items
     */
    public ArrayList<Bundle> getData() {
        return data;
    }

    /**
     * Observer
     */
    public interface OnTransactionItemListener {
        void onTransactionClick(Bundle data, TransactionItemView transactionItemView);
    }

    public static class TransactionListener {

        public void setListener(OnTransactionItemListener externalListener) {
            listener = externalListener;
        }
    }
}