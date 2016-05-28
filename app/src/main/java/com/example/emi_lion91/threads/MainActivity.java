package com.example.emi_lion91.threads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private List<String> numbers = new ArrayList<String>();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Create(View view){

        new CreateFile().execute();
    }

    public void Load(View view){

        new ReadFile().execute();
    }

    public void Clear(View view){
        ListView listview = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        adapter.clear();
        if (listview != null) {
            listview.setAdapter(adapter);
        }

    }

    class ReadFile extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            String filename = "numbers.txt";
            int i = 0;
            try {
                Scanner s = new Scanner(new File(String.valueOf(getApplicationContext().getFilesDir()), filename));
                while(s.hasNext()) {
                    numbers.add(s.next());
                    Thread.sleep(250);
                    publishProgress((int) ((++i/(float)10)*100));
                }
                s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, numbers);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ListView listview = (ListView) findViewById(R.id.listView);
            listview.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            if(values[0] == progressBar.getMax()){
                progressBar.setProgress(0);
            }
            else {
                progressBar.setProgress(values[0]);
            }
            super.onProgressUpdate(values);
        }
    }

    class CreateFile extends AsyncTask<Void, Integer, Void>{
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            if(values[0] == progressBar.getMax()){
                progressBar.setProgress(0);
            }
            else {
                progressBar.setProgress(values[0]);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            String filename = "numbers.txt";
            File file = new File(getApplicationContext().getFilesDir(), filename);
            String number;
            FileOutputStream outputStream;

            try{
                outputStream = openFileOutput(filename, getApplicationContext().MODE_PRIVATE);
                for(int i = 1; i <= 10; i++){
                    outputStream.write((String.valueOf(i) + "\n").getBytes());
                    Thread.sleep(250);
                    publishProgress((int) ((i/(float)10)*100));
                }
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

