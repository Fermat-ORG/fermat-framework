package com.bitdubai.sub_app.intra_user_community.common.popups;
import android.os.Bundle;
import android.view.View;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.sub_app.intra_user_community.R;
import android.app.Activity;
import android.view.View.OnClickListener;


public class AccpetMessage extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccp_connection_accepted);
        //Obteniendo una instancia del boton show_pet_button
        FermatTextView text = (FermatTextView)findViewById(R.id.text_connection_success);
        //Registrando la escucha sobre la actividad Main
        text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){


    }

}






