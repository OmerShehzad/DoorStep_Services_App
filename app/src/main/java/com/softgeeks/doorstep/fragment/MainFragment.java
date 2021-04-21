package com.softgeeks.doorstep.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.softgeeks.doorstep.R;

import java.util.ArrayList;
import java.util.List;

//This fragment class all tabs related code defined (what should be on first tab ,which fragment/screen opens in 2nd tab and similarly for third and 4th one)
//For tabs m using bottom navigation ex library which creates tab automatically
public class MainFragment extends Fragment {
    private ViewPager uidViewPager;
    private BottomNavigationViewEx uidNavigationView;
    private NavController actionHomeController;
    private VpAdapter adapter;
    private List<Fragment> fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate (R.layout.fragment_main, container, false);

        initData ();
        initViews (view);
        initEvent ();
        return view;
    }

    private void initViews(View root) {
//all control should be initialized here in intViews method and what controls should be visible or not defined here
        uidViewPager=root.findViewById (R.id.uidViewPager);
        uidNavigationView=root.findViewById (R.id.uidNavigationView);
        actionHomeController=Navigation.findNavController (getActivity (), R.id.mainNavHostFragment);
//al list of fragment added above i initdata method shall be passed in vpAdapter which further connect these fragments together according to their position in list
        adapter=new VpAdapter (getChildFragmentManager (), fragments);
        uidViewPager.setAdapter (adapter);
        uidNavigationView.setupWithViewPager (uidViewPager);

        uidNavigationView.setTextVisibility (true);
        uidNavigationView.enableAnimation (false);
        uidNavigationView.enableShiftingMode (false);
    }

    private void initData() {
//this method initializes a list named fragment in which all fragment added in a list
        fragments=new ArrayList<> (5);


        HomeFragment homeFragment=new HomeFragment ();
        MyBookingsFragment myBookingsFragment=new MyBookingsFragment ();
        NotificationFragment notificationFragment=new NotificationFragment ();
        ProfileFragment profileFragment=new ProfileFragment ();


        // add to fragments for adapter
        fragments.add (homeFragment);
        fragments.add (myBookingsFragment);
        fragments.add (notificationFragment);
        fragments.add (profileFragment);


    }

    private static class VpAdapter extends FragmentStatePagerAdapter {
        //this viewpager adapter collects a list of fragment initialized above and bind together according to list item position
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super (fm);
            this.data=data;
        }

        @Override
        public int getCount() {
            return data.size ();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get (position);
        }
    }


    private void initEvent() {

        // set listener to change the current item of view pager when click bottom nav item
        uidNavigationView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener () {
            private int previousPosition=-1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position=0;
                switch (item.getItemId ()) {
//what is the position value if specific tab is selected should be defined here in switch case statements
                    case R.id.navigation_home:

                        position=0;
                        break;

                    case R.id.navigation_sales:
                        position=1;

                        break;
                    case R.id.navigation_notification:
                        position=2;

                        break;
                    case R.id.navigation_account:
                        position=3;
                }
                if (previousPosition != position) {
                    uidViewPager.setCurrentItem (position, false);
                    previousPosition=position;
                }

                return true;
            }
        });

        // set listener to change the current checked item of bottom nav when scroll view pager
        uidViewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                uidNavigationView.setCurrentItem (position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
