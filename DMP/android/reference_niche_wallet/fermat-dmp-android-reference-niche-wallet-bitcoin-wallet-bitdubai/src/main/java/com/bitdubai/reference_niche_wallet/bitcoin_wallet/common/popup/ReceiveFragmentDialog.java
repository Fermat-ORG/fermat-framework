package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 */
public class ReceiveFragmentDialog extends Dialog implements
        View.OnClickListener {


    private String pluginKey;
    public Activity c;
    public Dialog d;

    ListView list;
    String[] web = {
            "Not logging",
            "Minimal logging",
            "Moderate logging",
            "Agressive logging"
    } ;

    List<String> lstEnum;

    Integer[] img ={
            R.drawable.ic_action_accept_grey,
            0,
            0,
            0
    };

    public ReceiveFragmentDialog(Activity a) {
        super(a);
        this.pluginKey=pluginKey;
        testing();
        //loadEnumsLogger();
        // TODO Auto-generated constructor stub
        this.c = a;
        setLogLevelImage();

    }

    private void testing(){
        lstEnum=new ArrayList<>();
        for(int i=0;i<LogLevel.values().length;i++){
            lstEnum.add(LogLevel.values()[i].getDisplayName());
        }
    }
    private void setLogLevelImage(){
//        if(logger.logLevel!=null) {
//            switch (logger.logLevel) {
//                case NOT_LOGGING:
//                    img = new Integer[]{
//                            1, 0, 0, 0
//                    };
//                    break;
//                case MINIMAL_LOGGING:
//                    img = new Integer[]{
//                            0, 1, 0, 0
//                    };
//                    break;
//                case MODERATE_LOGGING:
//                    img = new Integer[]{
//                            0, 0, 1, 0
//                    };
//                    break;
//                case AGGRESSIVE_LOGGING:
//                    img = new Integer[]{
//                            0, 0, 0, 1
//                    };
//                    break;
//            }
//        }else{
//            logger.logLevel= LogLevel.NOT_LOGGING;
//        }
    }

        /*private void loadEnumsLogger(){
            LogLevel[] enum_logLevel = LogLevel.values();
            List<String> lstEnum = new ArrayList<String>();
            for(int i=0;i<enum_logLevel.length;i++){
                lstEnum.add(enum_logLevel[i].getDisplayName());
            }
        }
        private void setIconSelected(){
            if(logger!=null){
                logger.logLevel.getCode();
            }else{
                //logger=new LogLevel(LogLevel.);
            }

        }*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup);



//        CustomList adapter = new
//                CustomList(c, lstEnum, img);
//        //list = (ListView) findViewById(R.id.listView);
//        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(c, web[+position] + " activated", Toast.LENGTH_SHORT).show();
                String item =list.getItemAtPosition(position).toString();
//                if(item.compareTo(LogLevel.NOT_LOGGING.toString())==0) {
//                    changeLogLevel(pluginKey, LogLevel.NOT_LOGGING, logger.classHierarchyLevels.getFullPath());
//                    logger.logLevel = LogLevel.NOT_LOGGING;
//                }else if (item.compareTo(LogLevel.MINIMAL_LOGGING.toString())==0){
//                    changeLogLevel(pluginKey, LogLevel.MINIMAL_LOGGING, logger.classHierarchyLevels.getFullPath());
//                    logger.logLevel = LogLevel.MINIMAL_LOGGING;
//                }else if(item.compareTo(LogLevel.MODERATE_LOGGING.toString())==0){
//                    changeLogLevel(pluginKey, LogLevel.MODERATE_LOGGING, logger.classHierarchyLevels.getFullPath());
//                    logger.logLevel = LogLevel.MODERATE_LOGGING;
//                }else if (item.compareTo(LogLevel.AGGRESSIVE_LOGGING.toString())==0){
//                    changeLogLevel(pluginKey, LogLevel.AGGRESSIVE_LOGGING, logger.classHierarchyLevels.getFullPath());
//                    logger.logLevel = LogLevel.AGGRESSIVE_LOGGING;
//                }
                dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
            /*if (i == R.id.btn_yes) {
                c.finish();

            } else if (i == R.id.btn_no) {
                dismiss();

            } else {
            }*/
        dismiss();
    }

//    public class CustomList extends ArrayAdapter<String> {
//
//        private final Activity context;
//        private final List<String> listEnumsToDisplay;
//        private final Integer[] imageId;
//        public CustomList(Activity context,
//                          List<String> listEnumsToDisplay, Integer[] imageId) {
//            super(context, R.layout.list_single, listEnumsToDisplay);
//            this.context = context;
//            this.listEnumsToDisplay = listEnumsToDisplay;
//            this.imageId = imageId;
//
//        }
//        @Override
//        public View getView(int position, View view, ViewGroup parent) {
//            LayoutInflater inflater = context.getLayoutInflater();
//            View rowView= inflater.inflate(R.layout.list_single, null, true);
//            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
//
//            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
//            txtTitle.setTextColor(Color.WHITE);
//            txtTitle.setText(listEnumsToDisplay.get(position));
//            //txtTitle.setText(LogLevel.MINIMAL_LOGGING.toString());
//
//            setLogLevelImage();
//            if(imageId[position]!=0){
//                imageView.setImageResource(R.drawable.ic_action_accept_grey);
//            }
//
//            return rowView;
//        }
//    }

}
