package com.bitdubai.sub_app.wallet_store.common.models.old;



import java.util.ArrayList;

/**
 * Created by mati on 11/05/15.
 */

// Clase intermedia que conecta la interfaz con la base de datos
public class ItemsBD {

    //agrupación por tipos de Wallets
    public static final int ALL_WALLETS=0;
    public static final int DISCOUNT_WALLETS=1;
    public static final int REGULAR_WALLETS=2;
    public static final int NEARBY_WALLETS=3;

    //Division por tipos de Wallets
    public static final String REGULAR_WALLETS_STR="regular";
    public static final String DISCOUNT_WALLETS_STR="discount";



    private ArrayList<App> mlist=new ArrayList<App>();

    public ItemsBD(){

    }

    //
    public ArrayList<App> cargarDatosPorTipoApp(int tipoWallet){


        //datos que despues van a ser reemplazados por la base de datos
        String[] accepted_nearby =
                {"yes",
                        "yes",
                        "yes",
                        "yes",
                        "yes",
                        "no",
                        "yes",
                        "no",
                        "yes",
                        "no",
                        "yes",
                        "no",
                        "no",
                        "no",
                        "no",
                        "no",
                        "no"
                };
        String[] installed =
                {"true",
                        "true",
                        "true",
                        "true",
                        "true",
                        "true",
                        "true",
                        "true",
                        "false",
                        "false",
                        "false",
                        "true",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false"
                };
        String[] company_names =
                {"Girl's wallet",
                        "Boy's wallet",
                        "Ladies",
                        "Young",
                        "Boca Junior's wallet",
                        "Carrefour's wallet",
                        "Gucci's wallet",
                        "Bank Itau's wallet",
                        "Mc donal's wallet",
                        "Van's wallet",
                        "Samsung's wallet",
                        "Popular Bank's wallet",
                        "Sony's wallet",
                        "BMW's wallet",
                        "HP's wallet",
                        "Billabong's wallet",
                        "Starbuck's wallet"

                };
        String[] types =
                {"regular",
                        "discount",
                        "regular",
                        "discount",
                        "regular",
                        "discount",
                        "regular",
                        "discount",
                        "regular",
                        "regular",
                        "discount",
                        "discount",
                        "regular",
                        "regular",
                        "discount",
                        "regular",
                        "discount"
                };

        String[] company_picture =
                {"wallet_store_cover_photo_girl",
                        "wallet_store_cover_photo_boy",
                        "wallet_store_cover_photo_lady",
                        "wallet_store_cover_photo_young",
                        "wallet_store_cover_photo_boca_juniors",
                        "wallet_store_cover_photo_carrefour",
                        "wallet_store_cover_photo_gucci",
                        "wallet_store_cover_photo_bank_itau",
                        "wallet_store_cover_photo_mcdonals",
                        "wallet_store_cover_photo_vans",
                        "wallet_store_cover_photo_samsung",
                        "wallet_store_cover_photo_bank_popular",
                        "wallet_store_cover_photo_sony",
                        "wallet_store_cover_photo_bmw",
                        "wallet_store_cover_photo_hp",
                        "wallet_store_cover_photo_billabong",
                        "wallet_store_cover_photo_starbucks"

                };

        String[] company_addresses =
                {"by bitDubai",
                        "by bitDubai",
                        "by bitDubai",
                        "by bitDubai",
                        "by Boca Junios",
                        "by carrefour",
                        "by Gucci",
                        "by Bank Itau",
                        "by McDonals",
                        "by Vans",
                        "by Samsung",
                        "by bitDubai",
                        "by Sony",
                        "by BMW",
                        "by HP",
                        "by Billabong",
                        "by starbucks"

                };

        String[] company_horario = {"Free",
                "Free",
                "Free",
                "$1.00 / month",
                "Free",
                "$1.00 / month",
                "Free",
                "$1.00 / month",
                "Free",
                "$1.00 / month",
                "$1.00 / month",
                "Free",
                "Free",
                "$1.00 / month",
                "Free",
                "$1.00 / month",
                "$1.00 / month"
        };
        String[] company_telefono =
                {"No transaction fees",
                        "No transaction fees",
                        "0.10% transaction fees",
                        "No transaction fees",
                        "0.15% transaction fees",
                        "No transaction fees",
                        "0.10% transaction fees",
                        "No transaction fees",
                        "0.10% transaction fees",
                        "0.15% transaction fees",
                        "No transaction fees",
                        "No transaction fees",
                        "No transaction fees",
                        "0.10% transaction fees",
                        "No transaction fees",
                        "No transaction fees",
                        "0.10% transaction fees"
                };

        String[] company_descriptions = {"Classic, redefined. See what's new at our Westchester location!.",
                "Smoking is allowed inside this clubby shop known for its hand-rolled cigars made ...",
                "Basic pizzeria serving a variety of NYC-style slices in a small, counter-service space.",
                "Pasta dishes, sandwiches & salads for take-out or eating in at this pint-sized Italian deli.",
                "Sophisticated Greek dishes, happy hour small plates & organic boutique wines in a ...",
                "Hair Salon, Day Spa",
                "Standard 24/7 diner with retro looks, basic fare & close proximity to Madison Square...",
                "Huge historic Jazz Age (1929) hotel offers over 900 rooms and suites across from Penn Station.",
                "Long-running chain serving signature donuts, breakfast sandwiches & a variety of coffee ...",
                "Home-away-from-home Australian pub offering grilled kangaroo, Aussie beers & rugby ...",
                "Counter-serve bakery/cafe chain serving sandwiches, salads & more, known for its ...",
                "Counter-serve setup offering deli sandwiches & wraps, hot entrees, salads & breakfast items.",
                "Counter-serve chain for ready-made sandwiches plus breakfast, coffee, soups & ...",
                "Casual counter-serve chain for build-your-own sandwiches & salads, with health-conscious ...",
                "Buzzy after-work-geared bar/eatery serving drinks & American fare in a cozy, ... ",
                "A bar pours mojitos up front & a brick-lined dining room in the back serves traditional ...",
                "Tiny, art-adorned coffeehouse pairing its brews with baked goods, sandwiches & a ...",

        };
        String[] company_sites = {"bitDubai.com",
                "bitDubai.com",
                "bitDubai.com",
                "bitDubai.com",
                "bitBubai.com",
                "carrefour.com",
                "gucci.com",
                "Bankitau.com",
                "mcdonals.com",
                "vans.com",
                "samsung.com",
                "bitDubai.com",
                "sony.com",
                "bmw.com",
                "HP",
                "billabong.com ",
                "starbucks.com"


        };


        //se le ingresa el tipo de wallet como String a la funcion
        switch (tipoWallet){
            case(ALL_WALLETS): {
                walletItems(company_sites, company_descriptions, company_picture, company_names, types, company_horario,
                        company_telefono, company_addresses, installed, null);
                break;
            }
            case(DISCOUNT_WALLETS): {
                walletItems(company_sites, company_descriptions, company_picture, company_names, types, company_horario,
                        company_telefono, company_addresses, installed, DISCOUNT_WALLETS_STR);
                break;
            }
            case(REGULAR_WALLETS): {
                walletItems(company_sites,company_descriptions,company_picture,company_names,types,company_horario,
                        company_telefono,company_addresses,installed,REGULAR_WALLETS_STR);
                break;
            }
        }
        return mlist;

    }
    public ArrayList<App> buscarPorNombreCompania(ArrayList<App> lstApp,String companyName){
        ArrayList<App> lstDev = new ArrayList<App>();
        for(App item: lstApp){
            if(item.getCompany().contains(companyName)){
                lstDev.add(item);
            }
        }
        return lstDev;
    }

    //se ingresa el tipo de wallet null para cargar todos los items
    private void walletItems(String[] company_sites,String[] company_descriptions,
                             String[] company_picture,String[] company_names,String[] types,String[] company_horario,
                             String[] company_telefono,String[] company_addresses,String[] installed,String tipoWallet){
        //
        if(tipoWallet!=null) {
            for (int i = 0; i < types.length; i++) {
                if(types[i] == tipoWallet) {
                    App item = new App();
                    item.setTitle(company_sites[i]);
                    item.setDescription(company_descriptions[i]);
                    item.setPicture(company_picture[i]);
                    item.setCompany(company_names[i]);
                    item.setOpen_hours(company_horario[i]);
                    item.setPhone(company_telefono[i]);
                    item.setAddress(company_addresses[i]);
                    item.setRate((float) Math.random() * 5);
                    item.setValue((int) Math.floor((Math.random() * (500 - 80 + 1))) + 80);
                    item.setFavorite((float) Math.random() * 5);
                    item.setTimetoarraive((float) Math.random() * 5);
                    item.setSale((float) Math.random() * 5);
                    item.setInstalled((installed[i] == "true") ? true : false);
                    item.setLocation((int) Math.floor((Math.random() * (2))));
                    mlist.add(item);
                }
            }
        }else{
            for (int i = 0; i < types.length; i++) {
                    App item = new App();
                    item.setTitle(company_sites[i]);
                    item.setDescription(company_descriptions[i]);
                    item.setPicture(company_picture[i]);
                    item.setCompany(company_names[i]);
                    item.setOpen_hours(company_horario[i]);
                    item.setPhone(company_telefono[i]);
                    item.setAddress(company_addresses[i]);
                    item.setRate((float) Math.random() * 5);
                    item.setValue((int) Math.floor((Math.random() * (500 - 80 + 1))) + 80);
                    item.setFavorite((float) Math.random() * 5);
                    item.setTimetoarraive((float) Math.random() * 5);
                    item.setSale((float) Math.random() * 5);
                    item.setInstalled((installed[i] == "true") ? true : false);
                    item.setLocation((int) Math.floor((Math.random() * (2))));
                    mlist.add(item);
            }
        }
    }

    //se ingresa la region actual y devuelve todas las Wallets con la misma región
    public ArrayList<App> nearbyWalletsItems(int location){
        cargarDatosPorTipoApp(ALL_WALLETS);
        ArrayList<App> listaAppCercanas = new ArrayList<App>();
        for(App item:mlist){
            if(item.getLocation()==location){
                listaAppCercanas.add(item);
            }
        }
        return listaAppCercanas;
    }

    //busqueda por nombre de compania, esto puede ser por cualquier tipo de datos mas adelante
    public ArrayList<App> cargarDatosPorQueryBusqueda(String query_search) {
        ArrayList<App> lstApp = new ArrayList<App>();

        //carga de datos que luego va a ser solo un query a la bd
        cargarDatosPorTipoApp(ALL_WALLETS);
        for(App item:mlist){
            if(item.getCompany().compareTo(query_search)==0){
                lstApp.add(item);
            }
        }


        return lstApp;
    }





}
