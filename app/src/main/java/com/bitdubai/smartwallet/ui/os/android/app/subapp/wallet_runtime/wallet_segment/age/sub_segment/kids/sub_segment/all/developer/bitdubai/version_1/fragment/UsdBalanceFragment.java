package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

/**
 * Created by ciencias on 25.11.14.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;

import com.bitdubai.smartwallet.R;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;



public class UsdBalanceFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    private String[] tickets;

    //Definimos el marco por el cual podemos arrastrar la imagen
    private ViewGroup marco;
    //Definimos la imagen que vasmo arrastrar
    private ImageView imagen;
    //Variables para centrar la imagen bajo el dedo
    private int xDelta;
    private int yDelta;

    public static UsdBalanceFragment newInstance(int position) {
        UsdBalanceFragment f = new UsdBalanceFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tickets = new String[]{"usd_1", "usd_1", "usd_1", "usd_5", "usd_10", "usd_20", "usd_100", "usd_1", "usd_5", "usd_5", "usd_10", "usd_20"};

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.wallets_kids_fragment_usd_balance2, container, false); //Contains empty RelativeLayout
        marco = (ViewGroup)view.findViewById(R.id.marco);

        for (int i = 0; i < tickets.length; i++) {
            final ImageView imageTicket= new ImageView(container.getContext());
            imageTicket.setTag(1);


           /* RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT );
            if(esPar(i))
             params.addRule(RelativeLayout.ALIGN_RIGHT,i);
            else
                params.addRule(RelativeLayout.ALIGN_LEFT,i);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            imageTicket.setLayoutParams(params);*/

            switch (tickets[i]) {
                case "usd_1":
                    imageTicket.setImageResource(R.drawable.usd_1);

                    imageTicket.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 1) {
                                imageTicket.setImageResource(R.drawable.usd_1);
                                v.setTag(2);
                            }
                            else{
                                imageTicket.setImageResource(R.drawable.usd_1_b);
                                v.setTag(1);
                            }
                        }

                    });
                    imageTicket.setOnLongClickListener(new OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {


                            return false;
                        }
                    });
                    break;
                case "usd_5":
                    imageTicket.setImageResource(R.drawable.usd_5);
                    imageTicket.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 1) {
                                imageTicket.setImageResource(R.drawable.usd_5);
                                v.setTag(2);
                            }
                            else{
                                imageTicket.setImageResource(R.drawable.usd_5_b);
                                v.setTag(1);
                            }
                        }

                    });
                    imageTicket.setOnLongClickListener(new OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {


                            return false;
                        }
                    });
                    break;
                case "usd_10":
                    imageTicket.setImageResource(R.drawable.usd_10);
                    imageTicket.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 1) {
                                imageTicket.setImageResource(R.drawable.usd_10);
                                v.setTag(2);
                            }
                            else{
                                imageTicket.setImageResource(R.drawable.usd_10_b);
                                v.setTag(1);
                            }
                        }

                    });
                    imageTicket.setOnLongClickListener(new OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {

                            //add two image and remove de current image
                            imageTicket.setVisibility(View.INVISIBLE);

                            final ImageView imagen1= new ImageView(container.getContext());

                            imagen1.setTag(1);
                            imagen1.setImageResource(R.drawable.usd_5);
                            imagen1.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    if ((Integer)v.getTag() == 1) {
                                        imagen1.setImageResource(R.drawable.usd_5);
                                        v.setTag(2);
                                    }
                                    else{
                                        imagen1.setImageResource(R.drawable.usd_5_b);
                                        v.setTag(1);
                                    }
                                }


                            });

                            imagen1.setOnTouchListener(new theTouchListener());
                            marco.addView(imagen1);

                            final ImageView imagen2= new ImageView(container.getContext());
                            //Señalamos la imagen a mostrar

                            imagen2.setTag(1);
                            imagen2.setImageResource(R.drawable.usd_5);
                            imagen2.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    if ((Integer)v.getTag() == 1) {
                                        imagen2.setImageResource(R.drawable.usd_5);
                                        v.setTag(2);
                                    }
                                    else{
                                        imagen2.setImageResource(R.drawable.usd_5_b);
                                        v.setTag(1);
                                    }
                                }


                            });

                            imagen2.setOnTouchListener(new theTouchListener());
                            marco.addView(imagen2);

                            return false;
                        }
                    });
                    break;
                case "usd_20":
                    imageTicket.setImageResource(R.drawable.usd_20);
                    imageTicket.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 1) {
                                imageTicket.setImageResource(R.drawable.usd_20);
                                v.setTag(2);
                            }
                            else{
                                imageTicket.setImageResource(R.drawable.usd_20_b);
                                v.setTag(1);
                            }
                        }

                    });

                    imageTicket.setOnLongClickListener(new OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {

                            //add two image and remove de current image
                            imageTicket.setVisibility(View.INVISIBLE);

                            final ImageView imagen1= new ImageView(container.getContext());

                            imagen1.setTag(1);
                            imagen1.setImageResource(R.drawable.usd_5);
                            imagen1.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    if ((Integer)v.getTag() == 1) {
                                        imagen1.setImageResource(R.drawable.usd_10);
                                        v.setTag(2);
                                    }
                                    else{
                                        imagen1.setImageResource(R.drawable.usd_10_b);
                                        v.setTag(1);
                                    }
                                }


                            });

                            imagen1.setOnTouchListener(new theTouchListener());
                            marco.addView(imagen1);

                            final ImageView imagen2= new ImageView(container.getContext());
                            //Señalamos la imagen a mostrar

                            imagen2.setTag(1);
                            imagen2.setImageResource(R.drawable.usd_10);
                            imagen2.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    if ((Integer)v.getTag() == 1) {
                                        imagen2.setImageResource(R.drawable.usd_10);
                                        v.setTag(2);
                                    }
                                    else{
                                        imagen2.setImageResource(R.drawable.usd_10_b);
                                        v.setTag(1);
                                    }
                                }


                            });

                            imagen2.setOnTouchListener(new theTouchListener());
                            marco.addView(imagen2);

                            return false;
                        }
                    });
                    break;
                case "usd_100":
                    imageTicket.setImageResource(R.drawable.usd_100);
                    imageTicket.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 1) {
                                imageTicket.setImageResource(R.drawable.usd_100);
                                v.setTag(2);
                            }
                            else{
                                imageTicket.setImageResource(R.drawable.usd_100_b);
                                v.setTag(1);
                            }
                        }

                    });
                    imageTicket.setOnLongClickListener(new OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {

                            //add two image and remove de current image
                            imageTicket.setVisibility(View.INVISIBLE);

                            final ImageView imagen1= new ImageView(container.getContext());

                            imagen1.setTag(1);
                            imagen1.setImageResource(R.drawable.usd_50);
                            imagen1.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    if ((Integer)v.getTag() == 1) {
                                        imagen1.setImageResource(R.drawable.usd_50);
                                        v.setTag(2);
                                    }
                                    else{
                                        imagen1.setImageResource(R.drawable.usd_50_b);
                                        v.setTag(1);
                                    }
                                }


                            });

                            imagen1.setOnTouchListener(new theTouchListener());
                            marco.addView(imagen1);

                            final ImageView imagen2= new ImageView(container.getContext());
                            //Señalamos la imagen a mostrar

                            imagen2.setTag(1);
                            imagen2.setImageResource(R.drawable.usd_50);
                            imagen2.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    if ((Integer)v.getTag() == 1) {
                                        imagen2.setImageResource(R.drawable.usd_50);
                                        v.setTag(2);
                                    }
                                    else{
                                        imagen2.setImageResource(R.drawable.usd_50_b);
                                        v.setTag(1);
                                    }
                                }


                            });

                            imagen2.setOnTouchListener(new theTouchListener());
                            marco.addView(imagen2);

                            return false;
                        }
                    });
                    break;

            }




            imageTicket.setOnTouchListener(new theTouchListener());
            marco.addView(imageTicket);
        }


        return view;
    }

    public static boolean esPar(int numero) {
        //Si el resto de numero entre 2 es 0, el numero es par
        if (numero % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    private final class theTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            //Recogemos las coordenadas del dedo
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            //Dependiendo de la accion recogida..
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                //Al tocar la pantalla
                case MotionEvent.ACTION_DOWN:
                    //Recogemos los parametros de la imagen que hemo tocado
                    RelativeLayout.LayoutParams Params =
                            (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xDelta = X - Params.leftMargin;
                    yDelta = Y - Params.topMargin;
                    return false;
                case MotionEvent.ACTION_UP:
                    //Al levantar el dedo simplemento mostramos un mensaje
                    //Toast.makeText(this, "Soltamos", Toast.LENGTH_LONG).show();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    //No hace falta utilizarlo
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    //No hace falta utilizarlo
                    break;
                case MotionEvent.ACTION_MOVE:
                    //Al mover el dedo vamos actualizando
                    //los margenes de la imagen para
                    //crear efecto de arrastrado
                    RelativeLayout.LayoutParams layoutParams =
                            (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - xDelta;
                    layoutParams.topMargin = Y - yDelta;
                    //Qutamos un poco de margen para
                    //que la imagen no se deforme
                    //al llegar al final de la pantalla y pueda ir más allá
                    //probar también el codigo omitiendo estas dos líneas
                   // layoutParams.rightMargin = -50;
                   // layoutParams.bottomMargin = -50;
                    //le añadimos los nuevos
                    //parametros para mover la imagen
                    view.setLayoutParams(layoutParams);
                    return false;
            }
            //Se podría decir que 'dibujamos'
            //la posición de la imagen en el marco.
            marco.invalidate();
            return false;
        }
    }
}

