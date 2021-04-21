package com.softgeeks.doorstep.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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

//This is first tab showing after login named home screen  in which at top there is a slider showing different services images
//after that list of categories showing according to backend db data
// M using Recycler view for showing multiple categories in list form on selecting category screen navigate to service description fragment
// on which all services displayed according to selected category.
public class HomeFragment extends Fragment {
    private static ViewPager mPager;
    private static int currentPage=0;
    private static int NUM_PAGES=0;
    NavController actionHomeInstance;
    //imageslider contains a list in which all drawable are store in show in top of home screen
    private List<Banner> imagesSlider=new ArrayList<> ();
    //catlists variavle stores all categories comes from backend
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
        //method used to initialize all controls in view
        mPager=view.findViewById (R.id.pager1);
        rvCategory=view.findViewById (R.id.rey_category);
        actionHomeInstance=Navigation.findNavController (getActivity (), R.id.mainNavHostFragment);
        //this method shows slider on top first we insert images in list and than passes it to sliding images adapter which add and shows in view pager
        getBanners ();

        //method for getting categories data from backend data base and populate in recycler view through category adapter
        initAdapter ();
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

// These line of codes contain a times on basis of this time view pager or top screen will move to next image automatically
        //Initially i set the time to 3 sec
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

        //Here we can get the categories and populate the list which we get from backend db to adapter named category adapter so it can be populated in recycler view
        if (((MainActivity) getContext ()).getInternetConnection ()) {
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
                                //Here all data get in the form of json and we get and store in list .

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

                                        rvCategory.setHasFixedSize (true);
                                        //maximum size of columns of categories will be three
                                        rvCategory.setLayoutManager (new GridLayoutManager (getActivity (), 3));
                                        CategoryAdapter categoryAdapter=new CategoryAdapter (actionHomeInstance, catlists, getActivity ());
                                        rvCategory.setAdapter (categoryAdapter);

                                    } else {
                                        Toast.makeText (getContext (), getString (R.string.uidErrorCategories), Toast.LENGTH_SHORT).show ();
                                    }
                                } else {
                                    Toast.makeText (getContext (), getString (R.string.uidErrorCategories), Toast.LENGTH_SHORT).show ();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace ();
                            }


                        } else {
                            Toast.makeText (getContext (), getString (R.string.uidErrorCategories), Toast.LENGTH_SHORT).show ();
                        }

                    } else {

                        new CallUtils ().showingErrorMessage (getContext (), response.errorBody (), getString (R.string.uidErrorCategories));
                    }

                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    //failure use case
                    ((MainActivity) getContext ()).hideProgressDialog ();
                    Toast.makeText (getContext (), getString (R.string.uidErrorCategories), Toast.LENGTH_SHORT).show ();

                }
            });
        }
    }
}
