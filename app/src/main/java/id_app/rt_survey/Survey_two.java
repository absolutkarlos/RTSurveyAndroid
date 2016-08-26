package id_app.rt_survey;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import id_app.rt_survey.MaterialTab.SlidingTabLayout;
import id_app.rt_survey.Tabs_Survey.Tab_Building;
import id_app.rt_survey.Tabs_Survey.Tab_Contact;
import id_app.rt_survey.Tabs_Survey.Tab_Site;
import id_app.rt_survey.Tabs_Survey.Tab_company;
import id_app.rt_survey.Utilities.CustomViewPager;

/**
 * Created by Carlos_Lopez on 2/8/16.
 */
public class Survey_two extends Fragment {

    private CustomViewPager viewPager;
    private SlidingTabLayout material_tab;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.survey_two,container,false);
        viewPager = (CustomViewPager) view.findViewById(R.id.view_pager);

        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(new MyFragmentAdapter(getActivity().getSupportFragmentManager()));

        material_tab = (SlidingTabLayout) view.findViewById(R.id.material_tab);
        material_tab.setViewPager(viewPager);
        material_tab.setDistributeEvenly(true);

        material_tab.setOnClickListener(new CustomTabClickListener());

        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(Build.VERSION.SDK_INT<23){
            material_tab.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
            material_tab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else{
            material_tab.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent,null));
            material_tab.setBackgroundColor(getResources().getColor(R.color.colorPrimary,null));
        }

    }

    private class CustomTabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }

    private class MyFragmentAdapter extends FragmentStatePagerAdapter {

        String[] tabs;

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            tabs=getResources().getStringArray(R.array.TAB);
        }

        @Override
        public Fragment getItem(int position) {

            if(position==0)
            {
                Tab_company f=new Tab_company();
                return f;
            }else if(position==1)
            {
                Tab_Contact f=new Tab_Contact();
                return f;
            }else if(position==2)
            {
                Tab_Site f=new Tab_Site();
                return f;
            }else if(position==3)
            {
                Tab_Building f=new Tab_Building();
                return f;
            }else{
                return null;
            }

        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

}
