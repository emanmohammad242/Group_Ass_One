package com.example.groupassigment1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.groupassigment1.R;
import com.squareup.picasso.Picasso;

public class DetailesBookActivity extends AppCompatActivity {

    private TextView bookName_txt,bookPhoto_txt,bookAuthor_txt,publisher_txt,originalLanguage_txt,releaseDate_txt;
    private ImageView imageBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes_book);
        setupViews();
        String URL = getIntent().getStringExtra("bookPhoto");

        bookName_txt.setText(getIntent().getStringExtra("bookName"));
        bookAuthor_txt.setText(getIntent().getStringExtra("author"));
        publisher_txt.setText(getIntent().getStringExtra("publisher"));
        originalLanguage_txt.setText(getIntent().getStringExtra("originalLanguage"));
        releaseDate_txt.setText(getIntent().getStringExtra("releaseDate"));

        Picasso.get().load(URL).into(imageBook);
    }

    public void setupViews(){
        imageBook = findViewById(R.id.imageBook);
        bookName_txt = findViewById(R.id.bookName_txt);
        bookPhoto_txt = findViewById(R.id.bookPhoto_txt);
        bookAuthor_txt = findViewById(R.id.bookAuthor_txt);
        publisher_txt = findViewById(R.id.publisher_txt);
        originalLanguage_txt = findViewById(R.id.originalLanguage_txt);
        releaseDate_txt = findViewById(R.id.releaseDate_txt);
    }
}