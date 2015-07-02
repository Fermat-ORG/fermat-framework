package com.bitdubai.sub_app.developer.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bitdubai.sub_app.developer.R;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.sub_app.developer.common.Databases;
import com.bitdubai.sub_app.developer.common.Resource;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.DatabaseToolsDatabaseListFragment</code>
 * haves all methods for the database tools activity of a developer
 * <p/>
 * <p/>
 * Created by MAti
 *
 * @version 1.0
 */
public class DatabaseToolsDatabaseTableRecordListFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;

    private DatabaseTool databaseTools;

    private DeveloperDatabase developerDatabase;
    private DeveloperDatabaseTable developerDatabaseTable;

    List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList;

    private static Platform platform = new Platform();




    private Resource resource;

    LinearLayout base;
    TableLayout tableLayout;

    public static DatabaseToolsDatabaseTableRecordListFragment newInstance(int position) {
        DatabaseToolsDatabaseTableRecordListFragment f = new DatabaseToolsDatabaseTableRecordListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ToolManager toolManager = platform.getToolManager();
            try {
                databaseTools = toolManager.getDatabaseTool();
            } catch (Exception e) {
                showMessage("CantGetToolManager - " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception ex) {
            showMessage("Unexpected error get Transactions - " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        rootView = inflater.inflate(R.layout.database_table_record, container, false);
        //String[] row = null;
        //String[] column=null;
        List<String> columnNames = null;
        List<String> values=null;

        try {
            if (resource.type== Databases.TYPE_ADDON) {
                Addons addon = Addons.getByKey(resource.resource);
                this.developerDatabaseTableRecordList = databaseTools.getAddonTableContent(addon, developerDatabase, developerDatabaseTable);
            } else if (resource.type== Databases.TYPE_PLUGIN) {
                Plugins plugin = Plugins.getByKey(resource.resource);
                this.developerDatabaseTableRecordList = databaseTools.getPluginTableContent(plugin, developerDatabase, developerDatabaseTable);
            }

            columnNames = developerDatabaseTable.getFieldNames();
            //column = new String[columnNames.size()];
            int i=0;
            //for(String s:columnNames){
            //    column[i]=s;
            //    i++;
            //}
            // Get ListView object from xml
            //final ListView listView = (ListView) rootView.findViewById(R.id.lista1);

            //TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelDatabase);
            //labelDatabase.setText(developerDatabase.getName()+" - Table "+developerDatabaseTable.getName()+" records");

            String[] listString;

            if (developerDatabaseTableRecordList.size() > 0) {
                /*listString = new String[developerDatabaseTableRecordList.size()+1];
                listString[0] = strJoin(columnNames, " | ");
                for(int i = 1; i < developerDatabaseTableRecordList.size()+1 ; i++){
                    listString[i] = strJoin(developerDatabaseTableRecordList.get(i-1).getValues(), " | ");
                }*/
                //row = new String[developerDatabaseTableRecordList.size()];
                i=0;
                values =new ArrayList<String>();
                for(DeveloperDatabaseTableRecord dtr:developerDatabaseTableRecordList){

                    //row[i]=dtr.toString();
                    //esto es solo para la primera tabla
                    values=(dtr.getValues());
                    i++;
                }
            } else {
                //listString = new String[0];

            }

            //ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
            //        android.R.layout.simple_list_item_1, android.R.id.text1, listString);

            //listView.setAdapter(adapter);
        } catch (Exception e) {
            showMessage("DatabaseTools Database Table List Fragment onCreateView Exception - " + e.getMessage());
            e.printStackTrace();
        }


        base=(LinearLayout)rootView.findViewById(R.id.base);

        //mati
        //tableLayout = (TableLayout) rootView.findViewById(R.id.tableLayout);

        //BuildTable(5, 8);

        ScrollView sv = new ScrollView(getActivity());
        //if(row!=null && column!=null) {
            //TableLayout tableLayout = createTableLayout(row, column, row.length, column.length);
            TableLayout tableLayout = createTable(values, columnNames, developerDatabaseTableRecordList.size(), columnNames.size());
            HorizontalScrollView hsv = new HorizontalScrollView(getActivity());

            hsv.addView(tableLayout);
            sv.addView(hsv);
            base.addView(sv);
        //}
        return rootView;
    }

    private TableLayout createTable(List<String> rv, List<String> cv,int rowCount, int columnCount){

        TableLayout tableLayout= new TableLayout(getActivity());

        //TableRow tbrow0 = new TableRow(getActivity());
        /*TextView tv0 = new TextView(getActivity());
        tv0.setText(" Sl.No ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(" Product ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(" Unit Price ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText(" Stock Remaining ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        */
        TableRow tbrow0 = new TableRow(getActivity());
        for (int i = 0;i<rv.size();i++){
            TextView tv0 = new TextView(getActivity());
            tv0.setText(cv.get(i));
            tv0.setTextColor(Color.WHITE);
            tbrow0.addView(tv0);
        }
        tableLayout.addView(tbrow0);

        tbrow0 = new TableRow(getActivity());
        for (int i = 0; i < cv.size(); i++) {
            TextView tv0 = new TextView(getActivity());
            tv0.setText(rv.get(i));
            tv0.setTextColor(Color.WHITE);
            tbrow0.addView(tv0);

        }
        tableLayout.addView(tbrow0);
        return tableLayout;
    }

    private TableLayout createTableLayout(List<String> rv, List<String> cv,int rowCount, int columnCount) {
        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout tableLayout = new TableLayout(getActivity());
        tableLayout.setBackgroundColor(Color.BLACK);

        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        for (int i = 0; i < rowCount; i++) {
            // 3) create tableRow
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setBackgroundColor(Color.BLACK);

            for (int j= 0; j < columnCount; j++) {
                // 4) create textView
                TextView textView = new TextView(getActivity());
                //  textView.setText(String.valueOf(j));
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                String s1 = Integer.toString(i);
                String s2 = Integer.toString(j);
                String s3 = s1 + s2;
                int id = Integer.parseInt(s3);
                Log.d("TAG", "-___>" + id);
                if (i ==0 && j==0){
                    textView.setText("MOSTACHO");
                } else if(i==0){
                    Log.d("TAAG", "set Column Headers");
                    textView.setText("JUAN");
                }else if( j==0){
                    Log.d("TAAG", "Set Row Headers");
                    textView.setText("CARLOS");
                }else {
                    textView.setText(""+id);
                    // check id=23
                    if(id==23){
                        textView.setText("ID=23");

                    }
                }

                // 5) add textView to tableRow
                tableRow.addView(textView, tableRowParams);
            }

            // 6) add tableRow to tableLayout
            tableLayout.addView(tableRow, tableLayoutParams);
        }

        return tableLayout;
    }

    private void BuildTable(int rows, int cols) {

        // outer for loop
        for (int i = 1; i <= rows; i++) {

            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= cols; j++) {

                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                //tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setPadding(5, 5, 5, 5);
                tv.setText("R " + i + ", C" + j);

                row.addView(tv);

            }

            if(tableLayout!=null)
            tableLayout.addView(row);

        }
    }

    public static String strJoin(List<String> aArr, String sSep) {
        StringBuilder sbStr = new StringBuilder();
        for (int i = 0, il = aArr.size(); i < il; i++) {
            if (i > 0)
                sbStr.append(sSep);
            sbStr.append(aArr.get(i));
        }
        return sbStr.toString();
    }

    //show alert
    private void showMessage(String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
        //alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }

    public void setDeveloperDatabase(DeveloperDatabase developerDatabase) {
        this.developerDatabase = developerDatabase;
    }

    public void setDeveloperDatabaseTable(DeveloperDatabaseTable developerDatabaseTable) {
        this.developerDatabaseTable = developerDatabaseTable;
    }
    public void setResource(Resource resource) {
        this.resource = resource;
    }
}