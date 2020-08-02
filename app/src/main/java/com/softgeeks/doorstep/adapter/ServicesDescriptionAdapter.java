package com.softgeeks.doorstep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.model.Services;

import java.util.List;

/**
 * Created by Ghulam Qadir on 02,August,2020
 */
public class ServicesDescriptionAdapter extends RecyclerView.Adapter<ServicesDescriptionAdapter.MyViewHolder> {

    private List<Services> servicesList;
    private Context context;
    NavController actionController;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvServicesDesName, tvServicesDesPrice,tvServicesDesTime,tvViewDetails,tvAdd;
        private ImageView ivServicesDes;


        public MyViewHolder(View view) {
            super (view);
            tvServicesDesName=view.findViewById (R.id.tvServicesDesName);
            tvServicesDesPrice=view.findViewById (R.id.tvServicesDesPrice);
            ivServicesDes=view.findViewById (R.id.ivServicesDes);
            tvServicesDesTime=view.findViewById (R.id.tvServicesDesTime);
            tvViewDetails=view.findViewById (R.id.tvViewDetails);
            tvAdd=view.findViewById (R.id.tvAdd);



        }
    }


    public ServicesDescriptionAdapter(NavController actionController, Context context, List<Services> servicesList) {
        this.servicesList=servicesList;
        this.context=context;
        this.actionController=actionController;

    }

    @Override
    public ServicesDescriptionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.item_services_des, parent, false);

        return new ServicesDescriptionAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final ServicesDescriptionAdapter.MyViewHolder holder, int position) {
        final Services services=servicesList.get (position);
        holder.tvServicesDesName.setText (services.getServiceName ());
        holder.tvServicesDesPrice.setText ("Rs ." + services.getServicePrice ());
        holder.tvServicesDesTime.setText ( services.getServiceAvailableTime ());
        Glide.with (context)
                .load (services.getServiceImgURl ())
                .error (context.getResources ().getDrawable (R.drawable.slider))
                .placeholder (context.getResources ().getDrawable (R.drawable.slider))
                .into (holder.ivServicesDes);


    }

    @Override
    public int getItemCount() {
        return servicesList.size ();
    }
}
