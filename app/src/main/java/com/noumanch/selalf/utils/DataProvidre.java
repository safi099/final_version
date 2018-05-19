package com.noumanch.selalf.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

 //import com.afollestad.materialdialogs.MaterialDialog;
//import com.afollestad.materialdialogs.Theme;

import com.github.ybq.android.spinkit.SpinKitView;
import com.noumanch.selalf.R;
import com.noumanch.selalf.callbacks.RecentCallback;
import com.noumanch.selalf.fragment.HomeFragment;
import com.noumanch.selalf.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static android.content.ContentValues.TAG;


  /*** Created by Nouman Ch on 03/04/2017.
 ****/

public class DataProvidre {

    static Context context;

    private   int pageNmr = 0;
    public DataProvidre(Context c){
        context = c;

    }






    //

    // for Daily popular or TodaysPicture Fragment pictures data extraction
    public static   class GetLatest extends AsyncTask<Void, Void, Void> {
        public ProgressDialog pDialog;
        //  RecyclerView rvv;
        Context cont;
        static  int page = 0;
        HomeFragment.SimpleStringRecyclerViewAdapter adapter;
        RecyclerView recyclerView;
        static  int test = 0;
        RecentCallback callback;
        boolean fromSwipeView;
            SpinKitView centralProgressBar;
        public    GetLatest(Context context, HomeFragment.SimpleStringRecyclerViewAdapter adapter, RecyclerView recyclerView, int pag, RecentCallback callback, boolean fromSwipeView ,SpinKitView centralProgress){
            this.centralProgressBar = centralProgress;
            this.fromSwipeView = fromSwipeView;
            this.adapter = adapter;
            cont = context;
            this.recyclerView = recyclerView;
            page  = pag;
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            centralProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler call = new HttpHandler();
            String url = cont.getResources().getString(R.string.products_list_url)+"&output_format=JSON";//+page+".10&output_format=JSON";

            Log.wtf(TAG, "doInBackground: latest url is "+url );

            // Making a request to url and getting response

             String jsonStr = call.makeServiceCall(cont,url);

            Log.i("MessageLatest", "Response from latest: " + jsonStr);
            Log.i("MessageLatest","Response from latest:page nmr = "+page);
            if (jsonStr != null) {
                try {

                    JSONObject main = new JSONObject(jsonStr);

                    JSONArray products  = main.getJSONArray("products");



                         /*******************************************
                         *                                          *
                         * Save the latest images in preferences    *
                         *                                          *
                         *******************************************/



                        if (page==0){
                            StaticVariables.saveLatest(cont,jsonStr);

                            HomeFragment.dataList.clear();

                            int nmrOfRows  = main.getInt("total_rows");

                            HomeFragment.nmrOfP = nmrOfRows;

                        }


                        //JSONArray msg  = jsonObj.getJSONArray("msg");
                        page += 10;
                        for (int i = 0; i < products.length(); i++) {
                            // Getting JSON Array node
                            JSONObject object = products.getJSONObject(i);

                            String id   = object.getString("id");
                            JSONArray names  = object.getJSONArray("name");

                            String name = names.getJSONObject(0).getString("value");
                            String nameArabic = names.getJSONObject(1).getString("value");
                            String price      = object.getString("price");
                            if (price.endsWith("000000")){
                                price = price.substring(0,price.length()-3);
                            }
                            JSONObject associations = object.getJSONObject("associations");
                            String imagePath = "";
                            ArrayList<String > imagesz = new ArrayList<>();
                            if (associations.has("images")) {


                                JSONArray images = associations.getJSONArray("images");

                                for (int j = 0; j < images.length(); j++) {
                                    JSONObject image = images.getJSONObject(j);
                                    if (image.has("id")){
                                        imagePath = image.getString("id");
                                        imagesz.add(cont.getResources().getString(R.string.image_base_url)+""+id+"/"+imagePath+"/?ws_key=11111111111111111111111111111111");
                                    }
                                }
                            }
                            JSONArray stock_availables = associations.getJSONArray("stock_availables");
                            ArrayList<String> attributes = new ArrayList<>();
                            for (int j = 0; j < stock_availables.length(); j++) {
                                JSONObject dum  = stock_availables.getJSONObject(j);
                                String  s = dum.getString("id_product_attribute");
                                attributes.add(s);
                            }





                            Product product = new Product(name,id,price,imagesz);
                            product.setId_product_attribute(attributes);
                            Log.wtf("produxt", "doInBackground: "+product.toString());
                            HomeFragment.dataList.add(product);


                        }
                          Log.i("MessageLatest","Status Data Fetching from interent is complete flag is true");


                } catch (JSONException e) {
                    Log.i("MessageLatest", "Json parsing error: " + e.getMessage());
                    e.printStackTrace();
                    if (page>1){
                        Log.i("MessageLatest","Json Error fail to load data from server and preferences data is already loaded so here we are doing nothing");

                        //check that we have already load data from preferences but on furhter call data from net is
                        //null so we just show message
                        callback.onLimited();
                    }else{
                        Log.i("MessageLatest","Json Error fail to load data from server loading from preferences");

                        new DataProvidre(cont).getOfflineLatest(cont);
                        page+=10;

                        callback.onSwipeSucess();
                     }

                }
            } else {
                 if (page>1){
                     Log.i("MessageLatest","fail to load data from server and preferences data is already loaded so here we are doing nothing");

                     //check that we have already load data from preferences but on furhter call data from net is
                     //null so we just show message
                    callback.onLimited();
                }else{
                     Log.i("MessageLatest","fail to load data from server loading from preferences");

                     new DataProvidre(cont).getOfflineLatest(cont);
                    page+=10;

                     callback.onSwipeSucess();
                  }
              }
             return null;
         }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //if (centralProgressBar!=null)
                //centralProgressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();

            if (centralProgressBar!=null)
                centralProgressBar.setVisibility(View.GONE);
            if (HomeFragment.dataList!=null && HomeFragment.dataList.size()!=0){

                Log.e("Info","adding nativeadview beofre adding size is "+HomeFragment.dataList.size());
                 adapter.notifyDataSetChanged();
             }
        }
    }

    private void getOfflineLatest(Context cont) {

        String dataString = StaticVariables.getLatest(cont);
        try {

            JSONObject main = new JSONObject(dataString);
            boolean flag = main.getBoolean("flag");
            if (flag) {




                int nmrOfRows = main.getInt("total_rows");

                HomeFragment.nmrOfP = nmrOfRows;


                JSONArray jsonObj = main.getJSONArray("msg");
                //JSONArray msg  = jsonObj.getJSONArray("msg");
                for (int i = 0; i < jsonObj.length(); i++) {
                    // Getting JSON Array node
                    JSONObject object = jsonObj.getJSONObject(i);

                    String imagePath = object.getString("filename");
                    String detailPath = object.getString("link");
                    String media_id = object.getString("media_id");

                    String views = object.getString("views");
                    String motivated = object.getString("motivated");
                    String description = object.getString("description");
                    String downloads = object.getString("downloads");
                    String thumbnail = object.getString("thumbnail");
                    String fullsize  = object.getString("fullsize_image");
                    String status ="";
                    if (object.has("status"))
                        status    = object.getString("status");

                    Log.i("Value of file", imagePath);

                    //Product dump = new Product(imagePath, detailPath, media_id, motivated,views,downloads,status,thumbnail,fullsize,description);
                     //HomeFragment.dataList.add(dump);

                }


            }
        } catch (JSONException e) {
            Log.i(TAG, "Json parsing error: " + e.getMessage());
            e.printStackTrace();

        }



    }


 }


