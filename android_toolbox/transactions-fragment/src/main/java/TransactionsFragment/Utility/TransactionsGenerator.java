package TransactionsFragment.Utility;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Clelia López on 3/14/16
 */
public class TransactionsGenerator {

    public static ArrayList<Bundle> createTransactions(int numberOfTransactions) {
        String toFrom[] = {"Amazon", "Best Buy", "New Egg", "bitDubai", "Bank of America"};
        String state[] = {"Complete", "Pending"};
        String type[] = {"income", "outcome"};
        String note = "Generic note ";
        String unit[] = {"BTC", "mBTC", "$"};
        HashMap<Integer,String> hashMap = new HashMap<>();
        hashMap.put(1, "January");
        hashMap.put(2, "February");
        hashMap.put(3, "March");
        hashMap.put(4, "April");
        hashMap.put(5, "May");
        hashMap.put(6, "June");
        hashMap.put(7, "July");
        hashMap.put(8, "August");
        hashMap.put(9, "September");
        hashMap.put(10, "October");
        hashMap.put(11, "November");
        hashMap.put(12, "December");

        ArrayList<Bundle> data = new ArrayList<>();
        Bundle bundle;

        for(int i=0; i<numberOfTransactions; i++) {
            bundle = new Bundle();
            bundle.putString("to", toFrom[generateRandomInteger(0,4)]);
            bundle.putString("from", toFrom[generateRandomInteger(0,4)]);

            String date = hashMap.get(generateRandomInteger(0,11)) + " " +
                    generateRandomInteger(1,31) + ", " +
                    generateRandomInteger(1950,2016);

            bundle.putString("date",date);
            bundle.putString("type", type[generateRandomInteger(0,1)]);
            bundle.putString("state", state[generateRandomInteger(0,1)]);
            bundle.putString("note", note + i);

            if(generateRandomInteger(1,10) <= 3)
                bundle.putString("sign", "");
            else
                bundle.putString("sign", "-");

            bundle.putString("unit", unit[generateRandomInteger(0,2)]);
            bundle.putString("amount", String.valueOf(generateRandomInteger(5, 1000)));

            data.add(bundle);
        }

        return data;
    }

    public static int generateRandomInteger(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
