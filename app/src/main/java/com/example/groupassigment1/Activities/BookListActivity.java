package com.example.groupassigment1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.groupassigment1.Models.Book;
import com.example.groupassigment1.Models.Category;
import com.example.groupassigment1.MySingleton;
import com.example.groupassigment1.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private List<Book> books = new ArrayList<>();
    public ListView book_listView;

    CustomAdapter customAdapter;
    String category = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        setupViews();
        loadData();
    }

    private void setupViews(){
        book_listView = findViewById(R.id.book_listView);
    }
    public void insert_btn_OnClick(View view) {

        Intent intent = new Intent(this,InsertBookActivity.class);
        intent.putExtra("categoryData",category);
        startActivity(intent);
    }

    private void loadData() {

        category = getIntent().getStringExtra("categoryData");
        String url = "http:/192.168.1.114:84/Mayar/getBookList.php?cat="+category;
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String bookName = jsonObject.getString("bookName");
                                String bookPhoto = jsonObject.getString("bookPhoto");
                                String bookCategory = jsonObject.getString("bookCategory");
                                String author = jsonObject.getString("author");
                                String publisher = jsonObject.getString("publisher");
                                String originalLanguage = jsonObject.getString("originalLanguage");
                                String releaseDate = jsonObject.getString("releaseDate");

                                books.add(new Book(id, bookName, bookPhoto ,bookCategory,author,publisher,originalLanguage,releaseDate));

                            }

                            customAdapter = new CustomAdapter(books,BookListActivity.this);

                            book_listView.setAdapter(customAdapter);
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

        private List<Book> itemsModelsl;
        private List<Book> itemsModelListFiltered;
        private Context context;
        private Button up_date_btn;

        public CustomAdapter(List<Book> itemsModelsl, Context context) {
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
            View view = getLayoutInflater().inflate(R.layout.book_list_view,null);


            TextView names = view.findViewById(R.id.textView1);
            ImageView imageView = view.findViewById(R.id.image);
            up_date_btn=view.findViewById(R.id.update_btn);

            String photo=itemsModelListFiltered.get(position).getBookPhoto();
            names.setText(itemsModelListFiltered.get(position).getBookName());
            Picasso.get().load(photo).into(imageView);

            up_date_btn.setOnClickListener(new View.OnClickListener()
                                           {

                                               @Override
                                               public void onClick(View v) {

                                                   Intent intent =new Intent(context, EditBookActivity.class);
                                                   String ids =itemsModelListFiltered.get(position).getId()+"";
                                                   intent.putExtra("id",ids);
                                                   intent.putExtra("bookName",itemsModelListFiltered.get(position).getBookName());
                                                   intent.putExtra("bookPhoto",itemsModelListFiltered.get(position).getBookPhoto());
                                                   intent.putExtra("bookCategory",itemsModelListFiltered.get(position).getBookCategory());
                                                   intent.putExtra("author",itemsModelListFiltered.get(position).getAuthor());
                                                   intent.putExtra("publisher",itemsModelListFiltered.get(position).getPublisher());
                                                   intent.putExtra("originalLanguage",itemsModelListFiltered.get(position).getOriginalLanguage());
                                                   intent.putExtra("releaseDate",itemsModelListFiltered.get(position).getReleaseDate());
                                                   context.startActivity(intent);
                                               }
                                           }
            );


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("main activity","item clicked");
                    Intent intent =new Intent(context, DetailesBookActivity.class);
           ;
                    intent.putExtra("bookName",itemsModelListFiltered.get(position).getBookName());
                    intent.putExtra("bookPhoto",itemsModelListFiltered.get(position).getBookPhoto());
                    intent.putExtra("bookCategory",itemsModelListFiltered.get(position).getBookCategory());
                    intent.putExtra("author",itemsModelListFiltered.get(position).getAuthor());
                    intent.putExtra("publisher",itemsModelListFiltered.get(position).getPublisher());
                    intent.putExtra("originalLanguage",itemsModelListFiltered.get(position).getOriginalLanguage());
                    intent.putExtra("releaseDate",itemsModelListFiltered.get(position).getReleaseDate());
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
                        List<Book> resultsModel = new ArrayList<>();
                        String searchStr = constraint.toString().toLowerCase();

                        for(Book itemsModel:itemsModelsl){
                            if(itemsModel.getBookName().toLowerCase().contains(searchStr.toLowerCase()) ){
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

                    itemsModelListFiltered = (List<Book>) results.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
        }
    }




}