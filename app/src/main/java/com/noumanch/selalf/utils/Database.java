package com.noumanch.selalf.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.noumanch.selalf.model.Order;
import com.noumanch.selalf.model.Product;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by macy on 11/28/17.
 */

public class Database extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "WINSLOSSES_DB";
    // Table name
    private static final String TABLE_PRODUCT = "LOGIN_TABLE";

    // Table Columns names FOR LOGIN
    private static final String KEY_ID         = "id";
    private static final String KEY_NAME       = "name";
    private String KEY_ID_PRODUCT_ATTRIBUTE    = "id_product_attribute";
    private String KEY_QUANTITY                = "quantity";
    private String KEY_IMAGE                   = "image";
    private String KEY_PRICE                   = "price";
    private String KEY_PROD_ID                 = "p_id";
    private String KEY_GIFTTEXT                = "gift";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {



        String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PRODUCT +
                "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_PROD_ID + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_ID_PRODUCT_ATTRIBUTE + " TEXT,"
                + KEY_QUANTITY + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_GIFTTEXT + " TEXT"
                +")";

        db.execSQL(CREATE_PROFILE_TABLE);

    }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            if (oldVersion >= newVersion)
                return;
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);


            // Create tables again
            onCreate(db);
        }
    // Adding new profile
    public int addProduct(Product product, int attributeValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_PROD_ID          , product.getId());
        values.put(KEY_NAME          , product.getName());
        values.put(KEY_IMAGE           , product.getImage().toString());
        values.put(KEY_ID_PRODUCT_ATTRIBUTE         , attributeValue);
        values.put(KEY_QUANTITY  , product.getQuantity());
        values.put(KEY_PRICE       , product.getPrice());
        values.put(KEY_GIFTTEXT       , product.getGiftText());

        // Inserting Row
        long ID = db.insert(TABLE_PRODUCT, null, values);

        db.close();
        return (int) ID;
    }

    public ArrayList<Product> getProducts(){

        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursor = db.rawQuery("select * from "+TABLE_PRODUCT,null);


        if (cursor== null){
            return null;

        }
        if (cursor != null){
            if (cursor.getCount()>0){

                if (cursor .moveToFirst()) {

                    while (cursor.isAfterLast() == false) {
                        String id   = cursor.getString(1);
                        String name = cursor.getString(2);
                        String id_product_attribute = cursor.getString(3);
                        String quantity = cursor.getString(4);
                        String image    = cursor.getString(5);
                        String price    = cursor.getString(6);
                        String gift    = cursor.getString(7);
                        //String price    = cursor.getString(7);
                        Product dump = new Product(name,id,price,"");
                        String[] stringsImage = image.split(",");
                        ArrayList<String> simage=new ArrayList<>();
                        for (int i = 0; i < stringsImage.length; i++) {
                            simage.add(stringsImage[i]);
                        }
                        dump.setImages(simage);
                        dump.setGiftText(gift);
                        String[] strings = id_product_attribute.split(",");
                        ArrayList<String> s=new ArrayList<>();
                        for (int i = 0; i < strings.length; i++) {
                            s.add(strings[i]);
                        }
                        dump.setId_product_attribute(s);
                        dump.setQuantity(quantity);
                        //Log.i("value","retrieved value is "+name+","+wins+", "+losses+","+pic);

                        list.add(dump);
                        cursor.moveToNext();

                    }
                }
            }else{
                Log.i("cursoer","empty cursor");
            }
            cursor.close();
            db.close();
            return list;
        }
//        printTableData(TABLE_CONTACTS);
        return null;
    }

    public boolean deleteProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_PRODUCT,KEY_PROD_ID+" =?",new String[]{id});
        if (i>0)
        return true;
        return false;
    }
}



