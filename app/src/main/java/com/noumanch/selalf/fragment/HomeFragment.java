package com.noumanch.selalf.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.noumanch.selalf.R;
import com.noumanch.selalf.activities.Dashboard;
import com.noumanch.selalf.callbacks.RecentCallback;
import com.noumanch.selalf.model.Product;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.noumanch.selalf.utils.DataProvidre;
import com.noumanch.selalf.utils.StaticVariables;

//import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class HomeFragment extends Fragment implements RecentCallback {


    public static int nmrOfP = 0;
    public int pageNmr = 10;
    public static ArrayList<Product> dataList;

    private LinearLayoutManager mLinearLayoutManager;
    private boolean userScrolled = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private View rv;
    private RecyclerView recyclerView;
    //private SwipeRefreshLayout swipeview;
    SimpleStringRecyclerViewAdapter adapter;
    private SpinKitView spinKitView;

    FloatingActionButton fab;
    public static final int ITEMS_PER_AD = 10;
    public static final int HEIGHT = 300;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** hide the fab button*/
        //  fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        rv = inflater.inflate(
                R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) rv.findViewById(R.id.recyclerview);
        //swipeview = (SwipeRefreshLayout) rv.findViewById(R.id.swipeview);
        spinKitView =  rv.findViewById(R.id.spin_kit);

        dataList = new ArrayList<>();




        /*swipeview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!StaticVariables.isNetworkAvailable(getActivity())) {
                    swipeview.setRefreshing(false);
                    //Snackbar.make(swipeview, "Internet is not connected", Snackbar.LENGTH_SHORT).show();
                }
                if (dataList.size() > 0) {

                    if (spinKitView.getVisibility() == View.VISIBLE) {
                        return;
                    }
                    pageNmr = 0;
                    spinKitView.setVisibility(View.VISIBLE);
                    dataList.clear();
                    new DataProvidre.GetLatest(getActivity(), adapter, recyclerView, pageNmr, HomeFragment.this, true,spinKitView).execute();
                    pageNmr += 10;
                }
            }
        });*/
        setupRecyclerView(recyclerView);


        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        // Create a custom SpanSizeLookup where the first item spans both columns
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 1;
                }
                /*if (LatestFragment.dataList.get(position) instanceof NativeAdView)
                    return 2;
                else */if (HomeFragment.dataList.get(position) instanceof Product)
                    return 1;
                return 2;
            }
        });
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SimpleStringRecyclerViewAdapter(getActivity(), dataList, recyclerView);
        this.mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.setAdapter(adapter);
        spinKitView.setVisibility(View.VISIBLE);
        new DataProvidre.GetLatest(getActivity(), adapter, recyclerView, pageNmr, HomeFragment.this, false, spinKitView).execute();
        pageNmr += 10;

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Here get the child count, item count and visibleitems
                // from layout manager

                visibleItemCount = mLinearLayoutManager.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                pastVisiblesItems = mLinearLayoutManager
                        .findFirstVisibleItemPosition();

                // Now check if userScrolled is true and also check if
                // the item is end then update recycler view and set
                // userScrolled to false
                if (userScrolled
                        && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                    userScrolled = false;
                    if (nmrOfP < pageNmr) {
                        //Snackbar.make(swipeview, "Latest Category has " + getString(R.string.no_more_images), Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (!StaticVariables.isNetworkAvailable(getActivity())) {
                        Snackbar.make(recyclerView, "Internet is not Connected", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (spinKitView.getVisibility() == View.VISIBLE) {
                        return;
                    }
                    // updateRecyclerView();
                    //materialProgressBar.setVisibility(View.VISIBLE);
                    new DataProvidre.GetLatest(getActivity(), adapter, recyclerView, pageNmr, HomeFragment.this, false, spinKitView).execute();
                    pageNmr += 10;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // If scroll state is touch scroll then set userScrolled
                // true
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;

                }
            }
        });

/*        Product p = new Product("Basket A","23","2500","abc.jpg");
        Product p7 = new Product("Basket2","23","KWD 35.000","abc.jpg");
        Product p2 = new Product("Basket B","23","1800","abc.jpg");
        Product p3 = new Product("Basket C","23","2800","abc.jpg");
        Product p4 = new Product("Basket D","23","4800","abc.jpg");
        Product p5 = new Product("Basket E","23","3500","abc.jpg");
        Product p6 = new Product("Basket3","43","KWD 35.000","abc.jpg");
        dataList.add(p7);
        dataList.add(p6);
        dataList.add(p);
        dataList.add(p5);
        dataList.add(p2);
        dataList.add(p3);
        dataList.add(p4);*/
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSwipeSucess() {

        stopSwipe();
    }

    @Override
    public void onLimited() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinKitView.setVisibility(View.GONE);
   //             Snackbar.make(swipeview, "Internet is not working correctly", Snackbar.LENGTH_SHORT).show();



            }
        });
    }

    public void stopSwipe() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
              /*  if (swipeview.isRefreshing())
                    swipeview.setRefreshing(false);*/

                spinKitView.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();

                //final NativeExpressAdView adView = new NativeExpressAdView(getActivity());
                //AdView adView = new AdView(getActivity());
                //final Banne
                Log.e("Info", "sizeo fo datalist before is " + HomeFragment.dataList.size());
                //LatestFragment.dataList.add(LatestFragment.dataList.size(), adView);
                Log.e("Info", "sizeo fo datalist after is " + HomeFragment.dataList.size());
                adapter.notifyDataSetChanged();
            }
        });
    }


    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ItemViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
         public ArrayList<Product> mValues;
        private static final int MENU_ITEM_VIEW_TYPE = 0;


        private Context context;




        @Override
        public int getItemViewType(int position) {

            if (mValues.get(position) instanceof Product) {
                return MENU_ITEM_VIEW_TYPE;
            }
            return position;
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {


            public final View mView;
            public final ImageView mImageView1;
            public final TextView product_name,product_price;
            public final ProgressBar progressBar1;

            public ItemViewHolder(View view) {
                super(view);
                mView = view;
                mImageView1 = (ImageView) view.findViewById(R.id.image);
                product_name = (TextView) view.findViewById(R.id.product_name);
                product_price = (TextView) view.findViewById(R.id.product_price);
                progressBar1 = (ProgressBar) view.findViewById(R.id.progress_bar1);
            }
        }


        public SimpleStringRecyclerViewAdapter(Context context, ArrayList<Product> items, RecyclerView vr) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            this.context = context;

        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                Log.e("Info", "view type is menu");
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_product, parent, false);
                return new ItemViewHolder(view);



        }


        @Override
        public void onBindViewHolder(final ItemViewHolder holder, final int position) {
           try {

               holder.product_name.setText(mValues.get(position).getName());
               holder.product_price.setText("KWD "+mValues.get(position).getPrice());

                    Glide.with(holder.itemView.getContext())
                            .load(mValues.get(position).getImage().get(0))
                            .into(holder.mImageView1);
                } catch (Exception e) {

                    e.printStackTrace();
                }
                holder.mImageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            action(holder,position);
                        } catch (Exception e) {

                        }

                    }
                });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    action(holder,position);
                }
            });
            holder.mImageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    action(holder,position);
                }
            });
            holder.progressBar1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    action(holder,position);
                }
            });
            holder.product_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    action(holder,position);
                }
            });
            holder.product_price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    action(holder,position);
                }
            });
            }



            public void action(ItemViewHolder holder, int position){
                Intent i  =new Intent(context,ProductDetail.class);
                i.putExtra("data",mValues.get(position));
                ((Dashboard)holder.progressBar1.getContext()).startActivityForResult(i,1299);
            }
        @Override
        public int getItemCount() {
            return mValues.size();
        }

    }


}
