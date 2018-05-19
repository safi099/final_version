package com.noumanch.selalf.fragment;

 import android.app.AlertDialog;
 import android.content.Context;
 import android.content.Intent;
 import android.os.Bundle;
 import android.support.annotation.NonNull;
 import android.support.annotation.Nullable;
 import android.support.v4.app.Fragment;
 import android.support.v7.widget.LinearLayoutManager;
 import android.support.v7.widget.RecyclerView;
 import android.util.Log;
 import android.util.TypedValue;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 import android.widget.Button;
 import android.widget.ImageView;
 import android.widget.LinearLayout;
 import android.widget.ProgressBar;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.afollestad.materialdialogs.DialogAction;
 import com.afollestad.materialdialogs.MaterialDialog;
 import com.bumptech.glide.Glide;
 import com.noumanch.selalf.R;
 import com.noumanch.selalf.activities.ChooseCategory;
 import com.noumanch.selalf.activities.ConfirmInfo;
 import com.noumanch.selalf.activities.Login;
 import com.noumanch.selalf.activities.UserOrders;
 import com.noumanch.selalf.model.Order;
 import com.noumanch.selalf.model.Product;
 import com.noumanch.selalf.utils.Database;
 import com.noumanch.selalf.utils.StaticVariables;

 import java.util.ArrayList;

public class CartFragment extends Fragment {

    public static CartFragment newInstance() {

        return new CartFragment();
    }

    RecyclerView cart_recycerview;
    SimpleStringRecyclerViewAdapter adapter;
    ArrayList<Product> orderArrayList= new ArrayList<>();
    LinearLayout main_layot;
    Button shopnow,checkout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        cart_recycerview  = v.findViewById(R.id.cart_recycerview);
        main_layot        = v.findViewById(R.id.main_layot);
        checkout        = v.findViewById(R.id.checkout);
        shopnow        = v.findViewById(R.id.shopnow);
        cart_recycerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SimpleStringRecyclerViewAdapter(getActivity(),orderArrayList);
        cart_recycerview.setAdapter(adapter);


        return v;
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
            public final ImageView mainImage,deleteItem;
            public final TextView delieryDate,name,total_quality;
            public final ProgressBar progressBar1;
            public ItemViewHolder(View view) {
                super(view);
                mView = view;
                mainImage = (ImageView) view.findViewById(R.id.main_image);
                deleteItem = (ImageView) view.findViewById(R.id.deleteItem);
                delieryDate = (TextView) view.findViewById(R.id.deliery_date);
                name = (TextView) view.findViewById(R.id.name_item);
                total_quality = (TextView) view.findViewById(R.id.total_quality);
                progressBar1 = (ProgressBar) view.findViewById(R.id.progress_bar1);
            }
        }

        public SimpleStringRecyclerViewAdapter(Context context, ArrayList<Product> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            this.context = context;
        }
        @Override
        public SimpleStringRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e("Info", "view type is menu");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_cart, parent, false);
            return new SimpleStringRecyclerViewAdapter.ItemViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final SimpleStringRecyclerViewAdapter.ItemViewHolder holder, final int position) {
            try {
                holder.name.setText(mValues.get(position).getName());
                holder.total_quality.setText("Quantity: "+mValues.get(position).getQuantity());
                /*if (mValues.get(position).getPrice().endsWith("000")){
                    mValues.get(position).setPrice(mValues.get(position).getPrice().substring(0,mValues.get(position).getPrice().length()-3))  ;
                }*/
                if (mValues.get(position).getPrice().endsWith("0000")){
                    mValues.get(position).setPrice(mValues.get(position).getPrice().substring(0,mValues.get(position).getPrice().length()-3));
                }
                holder.delieryDate.setText("Price: KWD "+mValues.get(position).getPrice());
                //holder.totalItems.setText(mValues.get(position).getProducts().get(0).getName());
                //holder.address.setText(mValues.get(position).getAddresses());
                holder.deleteItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Database database=new Database(holder.deleteItem.getContext());
                        database.deleteProduct(mValues.get(position).getId());
                        database.close();
                        Toast.makeText(context, "Product removed ", Toast.LENGTH_SHORT).show();
                        mValues.remove(position);
                        notifyDataSetChanged();
                  /*      new MaterialDialog.Builder(holder.delieryDate.getContext())
                                .title(R.string.title)
                                .content(R.string.content)
                                .positiveText(R.string.agree)
                                .negativeText(R.string.disagree)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        Database database= new Database(holder.delieryDate.getContext());
                                        database.deleteProduct(mValues.get(position).getId());
                                        database.close();
                                        Toast.makeText(context, "Product removed ", Toast.LENGTH_SHORT).show();
                                        mValues.remove(position);
                                        notifyDataSetChanged();

                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();*/
                    }
                });
                String imagePath="";
                if (mValues.get(position).getImage().size()==1){

                    imagePath = mValues.get(position).getImage().get(0).substring(1,mValues.get(position).getImages().get(0).length()-1);
                }else {
                    imagePath = mValues.get(position).getImage().get(0).substring(1,mValues.get(position).getImages().get(0).length());
                }
                //Log.wtf("cart", "onBindViewHolder: "+mValues.get(position).getImage().get(0) );
                Glide.with(holder.delieryDate.getContext())
                        .load(imagePath)
                        .into(holder.mainImage);

            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),ProductDetail.class));
                }
            });
            holder.progressBar1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),ProductDetail.class));
                }
            });
        }
        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        orderArrayList.clear();
        Database d = new Database(getActivity());
        ArrayList<Product> products = d.getProducts();
        if (products!=null) {

            if (products.size()>0){
                main_layot.setVisibility(View.GONE);
                shopnow.setVisibility(View.GONE);
                checkout.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < products.size(); i++) {
                orderArrayList.add(products.get(i));
            }
        }

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), ""+StaticVariables.getUser(getActivity()), Toast.LENGTH_SHORT).show();
              /*  if (StaticVariables.getUser(getActivity())!=null){*/
                    startActivity(new Intent(getActivity(), ConfirmInfo.class));
              /*  }else {
                    Toast.makeText(getActivity(), "You need to login first", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), Login.class));
                }*/
            }
        });

        adapter.notifyDataSetChanged();
    }
}
