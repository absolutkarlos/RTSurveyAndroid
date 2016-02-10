package id_app.rt_survey.Tabs_Items;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id_app.rt_survey.R;

/**
 * Created by Carlos_Lopez on 2/10/16.
 */
public class Tab_Pictures extends Fragment {


    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tab_info,container,false);
        return view;
    }


}
