package id_app.rt_survey;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id_app.rt_survey.MaterialTab.SlidingTabLayout;
import id_app.rt_survey.Tabs_Items.Tab_Info;
import id_app.rt_survey.Tabs_Survey.Tab_company;

/**
 * Created by Carlos_Lopez on 2/8/16.
 */
public class Item_Fragment extends Fragment{

    private ViewPager viewPager;
    private SlidingTabLayout material_tab;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.item_fragment,container,false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager1);
        viewPager.setAdapter(new MyFragmentAdapter(getActivity().getSupportFragmentManager()));
        material_tab = (SlidingTabLayout) view.findViewById(R.id.material_tab1);
        material_tab.setViewPager(viewPager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //DEPRECATED BUG
        if(Build.VERSION.SDK_INT<23){
            material_tab.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
            material_tab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else{
            material_tab.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent,null));
            material_tab.setBackgroundColor(getResources().getColor(R.color.colorPrimary,null));
        }
        material_tab.setDistributeEvenly(true);

    }

    private class MyFragmentAdapter extends FragmentPagerAdapter {

        String[] tabs;

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            tabs=getResources().getStringArray(R.array.TAB_ITEM);
        }

        @Override
        public Fragment getItem(int position) {

            if(position==0)
            {
                Tab_Info f=new Tab_Info();
                return f;
            }
            else if(position==1)
            {
                Tab_Info f=new Tab_Info();
                return f;
            }
            else if(position==2)
            {
                Tab_Info f=new Tab_Info();
                return f;
            }
            else if(position==3)
            {
                Tab_Info f=new Tab_Info();
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



}
