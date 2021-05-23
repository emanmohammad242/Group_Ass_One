package com.example.groupassigment1.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.groupassigment1.Models.Category;
import com.example.groupassigment1.MySingleton;
import com.example.groupassigment1.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private List<Category> categories = new ArrayList<>();
    public ListView category_listView;
    public SearchView searchView;
    CustomAdapter customAdapter;
    String url = "http://192.168.1.114:84/Mayar/getCategoryList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        setupViews();
        loadData();

    }

    public void insert_btn_OnClick(View view) {
        Intent intent = new Intent(this, InsertCategoryActivity.class);
        startActivity(intent);
    }


    private void setupViews(){
        category_listView = findViewById(R.id.category_listView);
        searchView = findViewById(R.id.searchView);
    }

    private void loadData() {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String categoryName = jsonObject.getString("categoryName");
                                String categoryPhoto = jsonObject.getString("categoryPhoto");

                                categories.add(new Category(id, categoryName, categoryPhoto));
                            }

                            customAdapter = new CustomAdapter(categories,CategoryListActivity.this);

                            category_listView.setAdapter(customAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.searchView);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("Main"," data search"+newText);

                customAdapter.getFilter().filter(newText);



                return true;
            }
        });


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        if(id == R.id.searchView){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<Category> itemsModelsl;
        private List<Category> itemsModelListFiltered;
        private Context context;

        public CustomAdapter(List<Category> itemsModelsl, Context context) {
            this.itemsModelsl = itemsModelsl;
            this.itemsModelListFiltered = itemsModelsl;
            this.context = context;
        }



        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsModelListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.category_list_view,null);


            TextView names = view.findViewById(R.id.textView1);
            ImageView imageView = view.findViewById(R.id.image);


            String photo=itemsModelListFiltered.get(position).getCategoryPhoto();
            names.setText(itemsModelListFiltered.get(position).getCategoryName());
           Picasso.get().load(photo).into(imageView);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("main activity","item clicked");
                    Intent intent =new Intent(context, BookListActivity.class);
                    intent.putExtra("categoryData",itemsModelListFiltered.get(position).getCategoryName());
                    context.startActivity(intent);

                }
            });

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();
                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = itemsModelsl.size();
                        filterResults.values = itemsModelsl;

                    }else{
                        List<Category> resultsModel = new ArrayList<>();
                        String searchStr = constraint.toString().toLowerCase();

                        for(Category itemsModel:itemsModelsl){
                            if(itemsModel.getCategoryName().toLowerCase().contains(searchStr.toLowerCase()) ){
                                resultsModel.add(itemsModel);

                            }
                            filterResults.count = resultsModel.size();
                            filterResults.values = resultsModel;
                        }


                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    itemsModelListFiltered = (List<Category>) results.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
        }
    }



}
