package com.example.ekkolin.pricemyitem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

/**
 * Created by Erii on 4/30/2017.
 */

public class AnswerActivity extends AppCompatActivity {

    /** EditText field to enter the pet's breed */
    private EditText mAnswerEditText;

    private String postAnswer;
    private String questionID;

    private MainActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        mAnswerEditText = (EditText) findViewById(R.id.edit_title);

        Intent intent = getIntent();
        questionID = intent.getStringExtra("questionId");

        Log.i("questionId = ", questionID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save answer to database
                saveAnswer();
                Log.i("saveAnswer", "pressed");
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAnswer() {

        postAnswer = mAnswerEditText.getText().toString();

        postAnswer = postAnswer.trim();

        // If user doesn't enter a title or content, do nothing
        // If user enters title, but no content, save
        // If user enters content with no title, give warning
        // If user enters both title and content, save

        if (!postAnswer.isEmpty()) {
            // Check if post is being created or edited

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
                query.whereEqualTo("_id",questionID);

                query.getInBackground(questionID, new GetCallback<ParseObject>() {
                    public void done(ParseObject post, ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data.
                            post.put("answer", postAnswer);
                            Log.i("answer", postAnswer);
                            post.saveInBackground(new SaveCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        // Saved successfully.
                                        //mActivity.refreshPostList();
                                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // The save failed.
                                        Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                                        Log.d(getClass().getSimpleName(), "User update error: " + e);
                                    }
                                }
                            });
                        }
                    }
                });
            }

            Log.i("here", "why");
        }

    }





