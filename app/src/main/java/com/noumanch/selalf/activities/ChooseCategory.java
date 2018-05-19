package com.noumanch.selalf.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;
import com.noumanch.selalf.R;
import com.noumanch.selalf.model.Category;
import com.noumanch.selalf.utils.Database;
import com.noumanch.selalf.utils.StaticVariables;

import java.util.ArrayList;
import java.util.Locale;

public class ChooseCategory extends AppCompatActivity {


    int imagesForCategories[] = new int[]{
            R.drawable.b2b_corporate_gifts_copy, R.drawable.football_night_copy,
            R.drawable.i_love_you, R.drawable.sympathy_copy, R.drawable.birth_of_new_baby_copy,
            R.drawable.from_kuwait_to_you, R.drawable.im_sorry, R.drawable.thank_you,
            R.drawable.birthday, R.drawable.get_well_soon, R.drawable.promotion,
            R.drawable.wedding_anniversary, R.drawable.engagement, R.drawable.graduation,
            R.drawable.suits_all_occasions, R.drawable.wedding_anniversary
    };
    String[] catIds = {
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20",
        "21",
        "22",
        "23",
        "24",
        "25",
        "26"

};
     RecyclerView recyclerView ;

    ArrayList<Category> dataList = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!StaticVariables.language) {


            String languageToLoad = "ar"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

        }
        setContentView(R.layout.activity_choose_category);

        recyclerView = findViewById(R.id.choose_category);

        String[] categoriesIds  = getResources().getStringArray(R.array.ids_of_categories);
        String[] categoriesNames  = getResources().getStringArray(R.array.categories);

        for (int i = 0; i < imagesForCategories.length; i++) {
            Category c = new Category(catIds[i],categoriesNames[i],imagesForCategories[i]);
            Log.wtf("ids", "onCreate: "+categoriesNames[i] );
            dataList.add(c);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(ChooseCategory.this,2));
        SimpleStringRecyclerViewAdapter adapter = new SimpleStringRecyclerViewAdapter(ChooseCategory.this,dataList,recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        setupBottomNavigation();

    }



    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ItemViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        public ArrayList<Category> mValues;
        private static final int MENU_ITEM_VIEW_TYPE = 0;


        private Context context;




        @Override
        public int getItemViewType(int position) {

            if (mValues.get(position) instanceof Category) {
                return MENU_ITEM_VIEW_TYPE;
            }
            return position;
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {


            public final View mView;
            public final ImageView mImageView1;
            public final TextView categoryName;


            public ItemViewHolder(View view) {
                super(view);
                mView = view;
                mImageView1 = (ImageView) view.findViewById(R.id.image_category);
                categoryName = (TextView) view.findViewById(R.id.category_name);

            }
        }


        public SimpleStringRecyclerViewAdapter(Context context, ArrayList<Category> items, RecyclerView vr) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            this.context = context;

        }

        @Override
        public SimpleStringRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            Log.e("Info", "view type is menu");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_category, parent, false);
            return new SimpleStringRecyclerViewAdapter.ItemViewHolder(view);



        }


        @Override
        public void onBindViewHolder(final SimpleStringRecyclerViewAdapter.ItemViewHolder holder, final int position) {
            try {

                 holder.categoryName.setText(mValues.get(position).getName());

                Glide.with(holder.itemView.getContext())
                        .load(mValues.get(position).getImage())
                        .into(holder.mImageView1);
            } catch (Exception e) {

                e.printStackTrace();
            }
            holder.mImageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                    } catch (Exception e) {

                    }

                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(),ShowCategoryItems.class);
                    intent.putExtra("type","choose category");
                    intent.putExtra("category_id",mValues.get(position).getId());
                    //Toast.makeText(context, " "+mValues.get(position).getId()+" "+mValues.get(position).getName(), Toast.LENGTH_SHORT).show();
                    intent.putExtra("category_name",mValues.get(position).getName());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
            holder.mImageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(holder.itemView.getContext(),ShowCategoryItems.class);
                    i.putExtra("type","choose category");
                    i.putExtra("category_id",mValues.get(position).getId());
                    i.putExtra("category_name",mValues.get(position).getName());
                    Log.wtf("id", "onClick: "+mValues.get(position).getId());
                    //Toast.makeText(context, " "+mValues.get(position).getId()+" "+mValues.get(position).getName(), Toast.LENGTH_SHORT).show();
                    holder.itemView.getContext().startActivity(i);
                }
            });
        }



        @Override
        public int getItemCount() {
            return mValues.size();
        }

    }
    private void setupBottomNavigation() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


//        bottomNavigation.setItemIconTintList(null);
// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.eee, android.R.color.black);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.basket_bottm, android.R.color.black);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.cart_bottom, android.R.color.black);
      //  AHBottomNavigationItem item4 = new AHBottomNavigationItem("", R.drawable.person, android.R.color.black);
        bottomNavigation.removeAllItems();
// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        //bottomNavigation.addItem(item4);
// Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setAccentColor(getColor(R.color.colorPrimary));

// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

// Enable the translation of the FloatingActionButton
//        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setInactiveColor(Color.parseColor("#E30614"));
        bottomNavigation.setAccentColor(Color.parseColor("#000000"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));
// Add or remove notification for each item
        if (new Database(ChooseCategory.this).getProducts()!=null){
            int v  =new Database(ChooseCategory.this).getProducts().size();
            if (v!=0){

                bottomNavigation.setNotification(""+v, 2);
            }
        }

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position) {
                    case 0:

                        Intent i1 = new Intent();
                        i1.putExtra("goTo","home");
                        setResult(RESULT_OK,i1);
                        finish();
                        break;
                    case 1:
                        Intent i4 = new Intent();
                        i4.putExtra("goTo","profile");
                        setResult(RESULT_OK,i4);
                        finish();
                        break;
                    case 2:
                        Intent iii = new Intent();
                        iii.putExtra("goTo","cart");
                        setResult(RESULT_OK,iii);
                        finish();
                        break;
                    case 3:
                        Intent ii = new Intent();
                        ii.putExtra("goTo","basket");
                        setResult(RESULT_OK,ii);
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
                /*switch (y) {
                    case 0:

                        loadHomeFragment();
                        break;
                    case 1:
                        loadProfileFragment();
     break;
                    case 2:

                        loadCartFragment();
                        break;
                    case 3:
                        loadBasketsFragment();
                        break;
                }*/
            }
        });

        //you can add some logic (hide it if the count == 0)
/*        if (badgeCount > 0) {
            ActionItemBadge.update(this, mBottomNavigationView.getMenu().findItem(R.id.action_cart), getDrawable(R.drawable.cart_bottom), ActionItemBadge.BadgeStyles.DARK_GREY, badgeCount);
        } else {
            ActionItemBadge.hide(mBottomNavigationView.getMenu().findItem(R.id.action_cart));
        }*/

        /*//If you want to add your ActionItem programmatically you can do this too. You do the following:
        new ActionItemBadgeAdder().act(this).menu(mBottomNavigationView.getMenu()).title("tsst").itemDetails(0, SAMPLE2_ID, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(bigStyle, 1);*/
        ;
/*        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:

                        loadHomeFragment();
                        return true;
                    case R.id.action_profile:
                        loadProfileFragment();
                        return true;
                    case R.id.action_cart:
                        int badgeCount= 2;
                        badgeCount--;
                        ActionItemBadge.update(item, badgeCount);
                        loadCartFragment();
                        return true;
                    case R.id.action_baskets:
                        loadBasketsFragment();
                        return true;
                }
                return false;
            }
        });*/
    }
}
