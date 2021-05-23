package com.example.groupassigment1.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.groupassigment1.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class EditBookActivity extends AppCompatActivity {
    private EditText bookName_txt,bookPhoto_txt,bookAuthor_txt,publisher_txt,originalLanguage_txt,releaseDate_txt;
    String bookName="",bookPhoto="",bookCategory="",bookAuthor="",publisher="",originalLanguage="",releaseDate="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        setupViews();
        bookName_txt.setText(getIntent().getStringExtra("bookName"));
        bookPhoto_txt.setText(getIntent().getStringExtra("bookPhoto"));
        bookAuthor_txt.setText(getIntent().getStringExtra("author"));
        publisher_txt.setText(getIntent().getStringExtra("publisher"));
        originalLanguage_txt.setText(getIntent().getStringExtra("originalLanguage"));
        releaseDate_txt.setText(getIntent().getStringExtra("releaseDate"));



    }
    public void setupViews(){
        bookName_txt = findViewById(R.id.bookName_txt);
        bookPhoto_txt = findViewById(R.id.bookPhoto_txt);
        bookAuthor_txt = findViewById(R.id.bookAuthor_txt);
        publisher_txt = findViewById(R.id.publisher_txt);
        originalLanguage_txt = findViewById(R.id.originalLanguage_txt);
        releaseDate_txt = findViewById(R.id.releaseDate_txt);
    }

    public void upDate_btn_OnClick(View view){
        String restUrl = "http://192.168.1.114:84/Mayar/editBook.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            EditBookActivity.SendPostRequest runner = new SendPostRequest();
            runner.execute(restUrl);
        }

        Intent intent = new Intent(EditBookActivity.this,CategoryListActivity.class);
        startActivity(intent);

    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {

        String id = getIntent().getStringExtra("id");
        bookName = bookName_txt.getText().toString();
        bookPhoto = bookPhoto_txt.getText().toString();
        bookAuthor = bookAuthor_txt.getText().toString();
        publisher = publisher_txt.getText().toString();
        originalLanguage = originalLanguage_txt.getText().toString();
        releaseDate = releaseDate_txt.getText().toString();



        String data = URLEncoder.encode("id", "UTF-8")
                + "=" + URLEncoder.encode(id, "UTF-8");

        data += "&" + URLEncoder.encode("bookName", "UTF-8") + "="
                + URLEncoder.encode(bookName, "UTF-8");

        data += "&" + URLEncoder.encode("bookPhoto", "UTF-8") + "="
                + URLEncoder.encode(bookPhoto, "UTF-8");

        data += "&" + URLEncoder.encode("author", "UTF-8") + "="
                + URLEncoder.encode(bookAuthor, "UTF-8");

        data += "&" + URLEncoder.encode("publisher", "UTF-8") + "="
                + URLEncoder.encode(publisher, "UTF-8");

        data += "&" + URLEncoder.encode("originalLanguage", "UTF-8") + "="
                + URLEncoder.encode(originalLanguage, "UTF-8");

        data += "&" + URLEncoder.encode("releaseDate", "UTF-8") + "="
                + URLEncoder.encode(releaseDate, "UTF-8");



        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally{
            if ((reader != null)) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Show response on activity
        return text;
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                return processRequest(strings[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }

}