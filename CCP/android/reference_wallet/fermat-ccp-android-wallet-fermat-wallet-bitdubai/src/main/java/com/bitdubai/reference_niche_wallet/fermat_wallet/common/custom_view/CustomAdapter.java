package com.bitdubai.reference_niche_wallet.fermat_wallet.common.custom_view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;

import java.util.ArrayList;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapter extends BaseAdapter implements View.OnClickListener {
          
         /*********** Declare Used Variables *********/
         private Activity activity;
         private ArrayList data;
         private static LayoutInflater inflater=null;
         public Resources res;
         ListComponent tempValues=null;
         int i=0;
          
         /*************  CustomAdapter Constructor *****************/
         public CustomAdapter(Activity a, ArrayList d,Resources resLocal) {
              
                /********** Take passed values **********/
                 activity = a;
                 data=d;
                 res = resLocal;
              
                 /***********  Layout inflator to call external xml layout () ***********/
                  inflater = ( LayoutInflater )activity.
                                              getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              
         }
      
         /******** What is the size of Passed Arraylist Size ************/
         public int getCount() {
              
             if(data.size()<=0)
                 return 1;
             return data.size();
         }
      
         public Object getItem(int position) {
             return position;
         }
      
         public long getItemId(int position) {
             return position;
         }
          
         /********* Create a holder Class to contain inflated xml file elements *********/
         public static class ViewHolder{
              
             public TextView txtLastTransactions;
             public TextView txtSeeAlltransactions;
             public TextView txtViewTitleTransaction;
             public TextView txtViewDetailTransaction;
             public ImageView imageView_transaction;
      
         }
      
         /****** Depends upon data size called for each row , Create each ListView row *****/
         public View getView(int position, View convertView, ViewGroup parent) {
              
             View vi = convertView;
             ViewHolder holder;
              
             if(convertView==null){
                  
                 /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                 vi = inflater.inflate(R.layout.wallet_manager_transaction_view, null);
                  
                 /****** View Holder Object to contain tabitem.xml file elements ******/
 
                 holder = new ViewHolder();
                 holder.txtLastTransactions = (TextView) vi.findViewById(R.id.txtLastTransactions);
                 holder.txtSeeAlltransactions=(TextView)vi.findViewById(R.id.txtSeeAlltransactions);
                 holder.txtViewTitleTransaction=(TextView) vi.findViewById(R.id.txtViewTitleTransaction);
                 holder.txtViewDetailTransaction=(TextView) vi.findViewById(R.id.txtViewDetailTransaction);
                 holder.imageView_transaction=(ImageView)vi.findViewById(R.id.imageView_transaction);
                  
                /************  Set holder with LayoutInflater ************/
                 vi.setTag( holder );
             }
             else 
                 holder=(ViewHolder)vi.getTag();
              
             if(data.size()<=0) {
                 holder.txtLastTransactions.setText("No Data");
                  
             }
             else {
                 /***** Get each Model object from Arraylist ********/
                 tempValues=null;
                 tempValues = (ListComponent) data.get( position );
                  
                 /************  Set Model values in Holder elements ***********/
 
                  holder.txtViewTitleTransaction.setText( tempValues.getTitleTransaction());
                  holder.txtViewDetailTransaction.setText( tempValues.getDetailTransaction() );
                  /*holder.imageView_transaction.setImageResource(
                               res.getIdentifier(
                               "com.bitdubai.reference_niche_wallet.fermat_wallet:drawable/"+tempValues.getImageUrl()
                               ,null,null));
                   */
                   
                  /******** Set Item Click Listner for LayoutInflater for each row *******/
 
                  vi.setOnClickListener(new OnItemClickListener( position ));
             }
             return vi;
         }
          
         @Override
         public void onClick(View v) {
                 Log.v("CustomAdapter", "=====Row button clicked=====");
         }
          
         /********* Called when Item click in ListView ************/
         private class OnItemClickListener  implements View.OnClickListener {
             private int mPosition;
              
             OnItemClickListener(int position){
                  mPosition = position;
             }
              
             @Override
             public void onClick(View view) {


                 //Toast.makeText(activity,"holas"+mPosition,Toast.LENGTH_SHORT).show();
                 data.set(0, data.get(mPosition+1));
                 notifyDataSetChanged();
               //CustomListViewAndroidExample sct = (CustomListViewAndroidExample)activity;
 
              /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
 
                 //sct.onItemClick(mPosition);
             }               
         }   
     }