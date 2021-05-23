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
import android.widget.Toast;

import com.example.groupassigment1.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class InsertCategoryActivity extends AppCompatActivity {

    private EditText categoryName_txt, imageUrl_txt;
    String categoryName = "", imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_category);
        setupViews();
    }

    private void setupViews() {
        categoryName_txt = findViewById(R.id.bookName_txt);
        imageUrl_txt = findViewById(R.id.imageUrl_txt);
    }

    public void add_btn_OnClick(View view) {
        String restUrl = "http://192.168.1.114:84/Mayar/addCategory.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else {
            InsertCategoryActivity.SendPostRequest runner = new InsertCategoryActivity.SendPostRequest();
            runner.execute(restUrl);
        }
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {

        String text = "";
        categoryName = categoryName_txt.getText().toString().trim();
        imageUrl = imageUrl_txt.getText().toString().trim();

        if (!categoryName.equals("") && !imageUrl.equals("")) {
            String data = URLEncoder.encode("category_name", "UTF-8")
                    + "=" + URLEncoder.encode(categoryName, "UTF-8");

            data += "&" + URLEncoder.encode("category_imageId", "UTF-8") + "="
                    + URLEncoder.encode(imageUrl, "UTF-8");



            BufferedReader reader = null;

            // Send data
            try {
                // Defined URL  where to send data
                URL url = new URL(restUrl);

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }


                text = sb.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if ((reader != null)) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
           Toast.makeText(InsertCategoryActivity.this, s, Toast.LENGTH_LONG).show();
            if(s.trim().equals("Insert Category Success") ){
                Intent intent = new Intent(InsertCategoryActivity.this, CategoryListActivity.class);
                startActivity(intent);
                finish();
            }else if (s.trim().equals("Failed To insert Category")) {
                Toast.makeText(InsertCategoryActivity.this, "This book is already inserted", Toast.LENGTH_LONG).show();
            }
        }
    }
}