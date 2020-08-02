package com.softgeeks.doorstep.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.adapter.CategoryAdapter;
import com.softgeeks.doorstep.adapter.ServicesDescriptionAdapter;
import com.softgeeks.doorstep.adapter.SlidingImagesAdapter;
import com.softgeeks.doorstep.model.Banner;
import com.softgeeks.doorstep.model.GetCategories;
import com.softgeeks.doorstep.model.GetCategoriesResponce;
import com.softgeeks.doorstep.model.GetServices;
import com.softgeeks.doorstep.model.Services;
import com.softgeeks.doorstep.network.CallUtils;
import com.softgeeks.doorstep.network.GetDataService;
import com.softgeeks.doorstep.network.RetrofitClientInstance;
import com.softgeeks.doorstep.views.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicesDescriptionFragment extends Fragment {
    private List<Banner> imagesSlider=new ArrayList<> ();
    private RecyclerView rvServicesDescription;
    private List<Services> servicesList=new ArrayList<> ();
    private NavController actionServicesInstance;
    private ServicesDescriptionAdapter servicesDescriptionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate (R.layout.fragment_services_description, container, false);
        initViews (view);
        return view;
    }

    private void initViews(View view) {
        rvServicesDescription=view.findViewById (R.id.rvServicesDescription);
        actionServicesInstance=Navigation.findNavController (getActivity (), R.id.mainNavHostFragment);
        getServices ();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        int indicatorWidth=(int) (TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources ().getDisplayMetrics ()) + 0.5f);
        int indicatorHeight=(int) (TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources ().getDisplayMetrics ()) + 0.5f);
        int indicatorMargin=(int) (TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_DIP, 6,
                getResources ().getDisplayMetrics ()) + 0.5f);

        ViewPager viewpager=view.findViewById (R.id.vpServiceDes);

        CircleIndicator indicator=view.findViewById (R.id.indServiceDes);
        Config config=new Config.Builder ().width (indicatorWidth)
                .height (indicatorHeight)
                .margin (indicatorMargin)
                .animator (R.animator.indicator_animator)
                .animatorReverse (R.animator.indicator_animator_reverse)
                .drawable (R.drawable.black_radius_square)
                .build ();
        indicator.initialize (config);
        imagesSlider.add (new Banner (R.drawable.ic_home_first));
        imagesSlider.add (new Banner (R.drawable.ic_home_fifth));
        imagesSlider.add (new Banner (R.drawable.ic_home_third));
        imagesSlider.add (new Banner (R.drawable.ic_home_fourth));
        viewpager.setAdapter (new SlidingImagesAdapter (getContext (), imagesSlider));
        indicator.setViewPager (viewpager);
    }


    public void getServices() {
        servicesList=new ArrayList<> ();
        ((MainActivity) getContext ()).showProgressDialog ("", "Loading services..", getContext ());
        GetDataService service=RetrofitClientInstance.getRetrofitLoginInstance (getContext ()).create (GetDataService.class);
        Call<Object> call=service.getServicesInstance (new GetServices ("rider"));
        call.enqueue (new Callback<Object> () {
            @RequiresApi(api=Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                ((MainActivity) getContext ()).hideProgressDialog ();
                if (response.isSuccessful ()) {
                    if (response.body () != null) {
                        try {
                            if (new JSONObject ((LinkedTreeMap) response.body ()).get ("Code").toString ().equalsIgnoreCase ("200.0")) {
                                if (new JSONObject ((LinkedTreeMap) response.body ()).get ("Message").toString ().equalsIgnoreCase ("services_found")) {
                                    for (int services=0; services < new JSONArray (new JSONObject ((LinkedTreeMap) response.body ()).get ("services").toString ()).length (); services++) {
                                    }


                                    servicesDescriptionAdapter=new ServicesDescriptionAdapter (actionServicesInstance, getContext (), servicesList);
                                    RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager (getContext ());
                                    rvServicesDescription.setLayoutManager (mLayoutManager);
                                    rvServicesDescription.setItemAnimator (new DefaultItemAnimator ());
                                    rvServicesDescription.setAdapter (servicesDescriptionAdapter);
                                } else {
                                    Toast.makeText (getContext (), "Something wrong occurred in getting services , Refresh again", Toast.LENGTH_SHORT).show ();
                                }
                            } else {
                                Toast.makeText (getContext (), "Something wrong occurred in getting services , Refresh again", Toast.LENGTH_SHORT).show ();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace ();
                            Toast.makeText (getContext (), "Something wrong occurred in getting services , Refresh again", Toast.LENGTH_SHORT).show ();
                        }


                    } else {
                        Toast.makeText (getContext (), "Something wrong occurred in getting services , Refresh again", Toast.LENGTH_SHORT).show ();
                    }

                } else {

                    new CallUtils ().showingErrorMessage (getContext (), response.errorBody (), "Something wrong occurred in getting services  data, Refresh again");
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                ((MainActivity) getContext ()).hideProgressDialog ();
                Toast.makeText (getContext (), "Something wrong occurred in getting services, Refresh again", Toast.LENGTH_SHORT).show ();

            }
        });
    }


}
