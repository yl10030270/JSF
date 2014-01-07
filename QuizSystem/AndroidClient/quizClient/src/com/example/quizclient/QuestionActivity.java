package com.example.quizclient;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.quizclient.util.Utilities;

public class QuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        // Show the Up button in the action bar.
        setupActionBar();
        Intent intent = getIntent();
        Integer value = intent.getIntExtra("quizId", -1);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new getQuestionList()
                    .execute("http://10.0.2.2:8080/quiz/rest/quiz/" + value);
        } else {
            showAlert("Error", "Internet not avaliable", true);
        }
        findViewById(R.id.submit_quiz_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlert("Result", "Score:\n\nAverage:", false);
                    }
                });
    }

    private class getQuestionList extends AsyncTask<String, Void, String> {
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

        private RadioGroup buildRadioGroup(JSONObject obj) throws JSONException {
            RadioGroup rg = new RadioGroup(QuestionActivity.this);
            rg.setOrientation(RadioGroup.VERTICAL);
            JSONArray jArr = obj.getJSONArray("choices");
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject choiceObj = jArr.getJSONObject(i);
                RadioButton rb = new RadioButton(QuestionActivity.this);
                rb.setText(choiceObj.getString("choiceIndex") + "."
                        + choiceObj.getString("choiceText") + ".");
                rg.addView(rb);
            }
            return rg;
        }

        private void buildTableRow(TableLayout table, JSONObject obj, int index)
                throws JSONException {
            TableRow row = new TableRow(QuestionActivity.this);
            LinearLayout ll = new LinearLayout(QuestionActivity.this);
            ll.setOrientation(LinearLayout.VERTICAL);
            TextView questiontext = new TextView(QuestionActivity.this);
            questiontext.setTypeface(Typeface.DEFAULT_BOLD);
            questiontext.setTextSize(20);
            questiontext.setText("Q" + index + ". " + obj.getString("text"));
            ll.addView(questiontext);
            ll.addView(buildRadioGroup(obj));
            row.addView(ll);
            row.setPadding(0, 0, 0, 10);
            table.addView(row);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jArr = new JSONArray(result);
                TableLayout table = (TableLayout) findViewById(R.id.questionTable);
                for (int i = 0; i < jArr.length(); i++) {
                    JSONObject obj = jArr.getJSONObject(i);
                    buildTableRow(table, obj, i + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String message, boolean noButton) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder.setMessage(message).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close current
                        // activity
                        QuestionActivity.this.finish();
                    }
                });
        if (noButton) {
            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
        }
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
        getMenuInflater().inflate(R.menu.question, menu);
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
