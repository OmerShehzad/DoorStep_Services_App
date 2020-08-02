package com.softgeeks.doorstep.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.adapter.CategoryAdapter;
import com.softgeeks.doorstep.adapter.SlidingImagesAdapter;
import com.softgeeks.doorstep.model.Banner;
import com.softgeeks.doorstep.model.GetCategories;
import com.softgeeks.doorstep.model.GetCategoriesResponce;
import com.softgeeks.doorstep.network.CallUtils;
import com.softgeeks.doorstep.network.GetDataService;
import com.softgeeks.doorstep.network.RetrofitClientInstance;
import com.softgeeks.doorstep.views.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private static ViewPager mPager;
    private static int currentPage=0;
    private static int NUM_PAGES=0;
    private List<Banner> imagesSlider=new ArrayList<> ();
    private List<GetCategoriesResponce> catlists=new ArrayList<> ();
private RecyclerView rvCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate (R.layout.fragment_home, container, false);
        initViews (view);
        return view;
    }

    private void initViews(View view) {
        mPager= view.findViewById (R.id.pager1);
        rvCategory= view.findViewById (R.id.rey_category);
        getBanners ();
        initAdapter();
    }

    private void initAdapter() {
        getCategories ();
    }

    private void getBanners() {
        imagesSlider.add (new Banner (R.drawable.ic_home_first));
        imagesSlider.add (new Banner (R.drawable.ic_home_fifth));
        imagesSlider.add (new Banner (R.drawable.ic_home_third));
        imagesSlider.add (new Banner (R.drawable.ic_home_fourth));
        init (imagesSlider);
    }

    private void init(List<Banner> imageArray) {

        mPager.setAdapter (new SlidingImagesAdapter (getContext (), imageArray));


        // Auto start of viewpager
        final Handler handler=new Handler ();
        final Runnable Update=new Runnable () {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage=0;
                }
                mPager.setCurrentItem (currentPage++, true);
            }
        };
        Timer swipeTimer=new Timer ();
        swipeTimer.schedule (new TimerTask () {
            @Override
            public void run() {
                handler.post (Update);
            }
        }, 3000, 3000);


    }


    public void getCategories() {
        catlists=new ArrayList<> ();
        ((MainActivity) getContext ()).showProgressDialog ("", "Loading categories..", getContext ());
        GetDataService service=RetrofitClientInstance.getRetrofitLoginInstance (getContext ()).create (GetDataService.class);
        Call<Object> call=service.getCategoriesInstance (new GetCategories ("rider"));
        call.enqueue (new Callback<Object> () {
            @RequiresApi(api=Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                ((MainActivity) getContext ()).hideProgressDialog ();
                if (response.isSuccessful ()) {
                    if (response.body () != null) {
                        try {
                            if (new JSONObject ((LinkedTreeMap) response.body ()).get ("Code").toString ().equalsIgnoreCase ("200.0")) {
                                if (new JSONObject ((LinkedTreeMap) response.body ()).get ("Message").toString ().equalsIgnoreCase ("categories_found")) {
                                    for (int categories=0; categories < new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).length (); categories++) {
                                        catlists.add (new GetCategoriesResponce (new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("car_id"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("user_id"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("owner_name"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("mobile"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("plate"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("image"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("color"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("start_date"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("end_date"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("price"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("city"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("area"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("is_driver"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("status"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("created_at"),
                                                new JSONObject (new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("cars").toString ()).get (categories).toString ()).getString ("updated_at")));
                                    }

                                    rvCategory.setHasFixedSize(true);
                                    rvCategory.setLayoutManager(new GridLayoutManager (getActivity(), 3));
                                    CategoryAdapter categoryAdapter = new CategoryAdapter (catlists, getActivity());
                                    rvCategory.setAdapter(categoryAdapter);

                                } else {
                                    Toast.makeText (getContext (), "Something wrong occurred in getting services categories, Refresh again", Toast.LENGTH_SHORT).show ();
                                }
                            } else {
                                Toast.makeText (getContext (), "Something wrong occurred in getting services categories, Refresh again", Toast.LENGTH_SHORT).show ();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }


                    } else {
                        Toast.makeText (getContext (), "Something wrong occurred in getting services categories, Refresh again", Toast.LENGTH_SHORT).show ();
                    }

                } else {

                    new CallUtils ().showingErrorMessage (getContext (), response.errorBody (), "Something wrong occurred in getting services categories data, Refresh again");
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                ((MainActivity) getContext ()).hideProgressDialog ();
                Toast.makeText (getContext (), "Something wrong occurred in getting services categories, Refresh again", Toast.LENGTH_SHORT).show ();

            }
        });
    }
}
