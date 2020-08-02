package com.softgeeks.doorstep.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.utils.Session;
import com.softgeeks.doorstep.views.MainActivity;


public class WelcomeFragment extends Fragment {
    private ViewPager viewPagerWelcome;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    NavController actionWelcomeInstance;
    private TextView btnSkip, btnNext;
    private Session session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate (R.layout.fragment_splash, container, false);


        // Checking for first time launch - before calling setContentView()
        actionWelcomeInstance=Navigation.findNavController (getActivity (), R.id.mainNavHostFragment);
        inittViews(view);
        session = new Session(getContext ());
        if (!session.isFirstTimeLaunch()) {
            // launchHomeScreen();

        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getActivity (). getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        layouts = new int[]{
                R.layout.welcome_first_slide,
                R.layout.welcome_sec_slide,
                R.layout.welcome_third_slide,
        };

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPagerWelcome.setAdapter(myViewPagerAdapter);
        viewPagerWelcome.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity ()).mSessionStoreManager.saveBooleanData (getString (R.string.isLogin),true);
                ((MainActivity)getActivity ()).mSessionStoreManager.saveStringData (getString (R.string.loginSession),"guest");
                actionWelcomeInstance.navigate (R.id.action_welcome_to_home);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPagerWelcome.setCurrentItem(current);
                } else {
                    ((MainActivity)getActivity ()).mSessionStoreManager.saveBooleanData (getString (R.string.isLogin),false);
                    actionWelcomeInstance.navigate (R.id.action_welcome_to_login);
                }
            }
        });



   return view;
    }


    private void inittViews(View view) {
        viewPagerWelcome = view. findViewById(R.id.viewPagerWelcome);
        dotsLayout = view.findViewById(R.id.layoutDots);
        btnSkip =  view.findViewById(R.id.btn_skip);
        btnNext = view. findViewById(R.id.btn_next);
    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity ());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPagerWelcome.getCurrentItem() + i;
    }

    private void launchHomeScreen() {



    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity ().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity ().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
