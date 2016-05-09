import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import picker_unit_selector.CaviarEditText;
import picker_unit_selector.CaviarSpinner;


/**
 * Created by Clelia López on 2/23/16
 */

public class SpinnerFragment
        extends Fragment {

    private final String TAG = SpinnerFragment.class.getSimpleName();

    private static CaviarSpinner spinner;
    private static CaviarEditText editor;

    protected static OnItemClickListenerListener listener = null;


    /**
     * Create a fragment from a custom spinner (Caviar spinner)
     *
     * @param caviarSpinner - Custom spinner already set up
     * @return - SpinnerFragment
     */
    public static SpinnerFragment newInstance(CaviarSpinner caviarSpinner, CaviarEditText caviarEditText) {
        SpinnerFragment fragment = new SpinnerFragment();
        spinner = caviarSpinner;
        editor = caviarEditText;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return spinner;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener != null)
                    listener.onItemClick(spinner.getSelectedItem(), editor);
            }
        });
    }

    public interface OnItemClickListenerListener {
        void onItemClick(String coin, CaviarEditText caviarEditText);
    }

    public static class SpinnerListener {

        public void setListener(OnItemClickListenerListener externalListener) {
            listener = externalListener;
        }
    }
}
