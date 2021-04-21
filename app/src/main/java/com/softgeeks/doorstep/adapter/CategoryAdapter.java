package com.softgeeks.doorstep.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.model.GetCategoriesResponce;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//his adapter class is used to populate data of categories get from db and inflates items layout
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<GetCategoriesResponce> listdata;
    Context context;
    NavController actionHomeInstance;

    public CategoryAdapter(NavController actionHomeInstance,List<GetCategoriesResponce> listdata, Context context) {
        this.listdata=listdata;
        this.context=context;
        this.actionHomeInstance=actionHomeInstance;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from (parent.getContext ());
        View listItem=layoutInflater.inflate (R.layout.category_item, parent, false);
        ViewHolder viewHolder=new ViewHolder (listItem);
        return viewHolder;
    }
    // set data on controls like text view and set the images of categories on image view by downloading first through glide library and set image in
    //imageview
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GetCategoriesResponce myListData=listdata.get (position);
        holder.txtTitle.setText (myListData.getOwner_name ());
       Glide.with (context).load ("http://rentacar.softgeeksdigital.com/public/uploads/"+myListData.getImage ()).placeholder (R.drawable.slider).into (holder.imageView);
        holder.lvlCategory.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle ();
                bundle.putString ("categoryID",myListData.getCar_id ());
                actionHomeInstance.navigate (R.id.action_home_to_services_des,bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size ();
    }

    //here initialize all controls of layout
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.lvl_category)
        LinearLayout lvlCategory;

        public ViewHolder(View itemView) {
            super (itemView);
            ButterKnife.bind (this, itemView);
        }
    }
}