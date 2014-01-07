package com.example.quizclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.quizclient.util.Utilities;

public class QuizListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);
        // Show the Up button in the action bar.
        setupActionBar();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new getQuizList().execute("http://10.0.2.2:8080/quiz/rest/quiz/user/1");
        } else {
            showAlert("Error", "Internet not avaliable");
        }
    }

    private class getQuizList extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                System.out.println(urls[0]);
                Log.d("a", urls[0]);
                return Utilities.connectGet(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jObj = new JSONObject(result);
                JSONArray takenJArr = jObj.getJSONArray("taken");
                List<String> takens = new ArrayList<String>();
                for (int i = 0; i < takenJArr.length(); i++) {
                    JSONObject obj = takenJArr.getJSONObject(i);
                    StringBuilder sb = new StringBuilder();
                    sb.append("QuizID: " + obj.getInt("id") + "\t\t");
                    sb.append("Week: " + obj.getInt("Week") + "\t\t");
                    sb.append("Score: " + obj.getInt("Score") + "%");
                    takens.add(sb.toString());
                }
                JSONArray nonTakenJArr = jObj.getJSONArray("nonTaken");
                List<String> nonTakens = new ArrayList<String>();
                for (int i = 0; i < nonTakenJArr.length(); i++) {
                    JSONObject obj = nonTakenJArr.getJSONObject(i);
                    StringBuilder sb = new StringBuilder();
                    sb.append("QuizID: " + obj.getInt("id") + "\t\t");
                    sb.append("Week: " + obj.getInt("Week") + "\t\t\t\t");
                    sb.append("Avaliable");
                    nonTakens.add(sb.toString());
                }
                ArrayAdapter<String> takenAdapter = new ArrayAdapter<String>(
                        QuizListActivity.this,
                        android.R.layout.simple_list_item_1, takens);
                ListView takenListView = (ListView) findViewById(R.id.takenQuizList);
                takenListView.setAdapter(takenAdapter);
                ArrayAdapter<String> nontakenAdapter = new ArrayAdapter<String>(
                        QuizListActivity.this,
                        android.R.layout.simple_list_item_1, nonTakens);
                ListView nontakenListView = (ListView) findViewById(R.id.QuizList);
                nontakenListView.setAdapter(nontakenAdapter);
                nontakenListView
                        .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0,
                                    View arg1, int arg2, long arg3) {
                                Intent intent = new Intent(
                                        QuizListActivity.this,
                                        QuestionActivity.class);
                                String quiz = (String) arg0
                                        .getItemAtPosition(arg2);
                                Scanner sn = new Scanner(quiz);
                                sn.next();
                                intent.putExtra("quizId", sn.nextInt());
                                sn.close();
                                Log.d("a", "before sent intent");
                                startActivity(intent);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close current
                                // activity
                                QuizListActivity.this.finish();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
