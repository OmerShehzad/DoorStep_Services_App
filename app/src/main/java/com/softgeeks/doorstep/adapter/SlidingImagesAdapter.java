package com.softgeeks.doorstep.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.model.Banner;

import java.util.List;


public class SlidingImagesAdapter extends PagerAdapter {
// this adapter is used for showing banners on top , adapter connect the list items together according to singe  item layout design in xml

    private List<Banner> bannerList;
    private LayoutInflater inflater;
    private Context context;

    //constructor which is used to call this model class and pass list of images accordingly
    public SlidingImagesAdapter(Context context, List<Banner> imagesArray) {
        this.context=context;
        this.bannerList=imagesArray;
        inflater=LayoutInflater.from (context);


    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView ((View) object);
    }

    @Override
    public int getCount() {
        return bannerList.size ();
    }

    //initialzation of single item layout and initialization of its object are done here

    //set data from list sould also be done from here
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout=inflater.inflate (R.layout.item_slider_images, view, false);

        assert imageLayout != null;
        final ImageView ivSlider=(ImageView) imageLayout
                .findViewById (R.id.ivSlider);
        ivSlider.setImageResource (bannerList.get (position).getBannerImg ());


        view.addView (imageLayout, 0);


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals (object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
