package com.smartdevelopers.kandie.nicedrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartdevelopers.kandie.nicedrawer.model.Country;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by 4331 on 09/07/2015.
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{

    private List<Country> countries;
    private int rowLayout;
    private Context mContext;

    public CountryAdapter(List<Country> countries, int rowLayout, Context context) {
        this.countries = countries;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Country country = countries.get(i);
        viewHolder.countryName.setText(country.name);
        viewHolder.countryDesc.setText(country.description);
//        viewHolder.countryImage.setImageDrawable(mContext.getDrawable(country.getImageResourceId(mContext)));
        viewHolder.countryImage.setImageDrawable(mContext.getResources().getDrawable(country.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount() {
        return countries == null ? 0 : countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView countryName;
        public ImageView countryImage;
        public TextView countryDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.countryName);
            countryImage = (ImageView)itemView.findViewById(R.id.countryImage);
            countryDesc=(TextView)itemView.findViewById(R.id.desc);
        }

    }

//    private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {
//        ImageView bmImage;
//
//        private DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            String urlDisplay=params[0];
//            Bitmap mIcon=null;
//            try {
//                InputStream in=new URL(urlDisplay).openStream();
//                mIcon= BitmapFactory.decodeStream(in);
//
//            }catch (Exception e){
//                Log.e("error loading image", e.getMessage());
//                e.printStackTrace();
//            }
//
//            return mIcon;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            bmImage.setImageBitmap(bitmap);
//        }
//    }
}
