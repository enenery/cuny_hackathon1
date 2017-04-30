package com.example.ekkolin.pricemyitem;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Erii on 4/29/2017.
 */

public class EditorActivity extends AppCompatActivity {
    private MainActivity mActivity;

    private Question mQuestion;
    /** EditText field to enter the pet's name */
    private EditText mNameEditText;

    /** EditText field to enter the pet's breed */
    private EditText mQuestionEditText;
    private EditText mTitleEditText;

    private String postName;
    private String postQuestion;
    private String postTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_name);
        mTitleEditText = (EditText) findViewById(R.id.edit_title);
        mQuestionEditText = (EditText) findViewById(R.id.edit_question);

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
                // Save pet to database
                saveNote();
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


    private void saveNote() {

        postName = mNameEditText.getText().toString();
        postTitle = mTitleEditText.getText().toString();
        postQuestion = mQuestionEditText.getText().toString();

        postName = postName.trim();
        postTitle = postTitle.trim();
        postQuestion = postQuestion.trim();

        // If user doesn't enter a title or content, do nothing
        // If user enters title, but no content, save
        // If user enters content with no title, give warning
        // If user enters both title and content, save

        if (!postName.isEmpty()) {
            // Check if post is being created or edited

            if (mQuestion == null) {
                // create new post

                final ParseObject post = new ParseObject("Post");
                post.put("name", postName);
                post.put("title", postTitle);
                post.put("question", postQuestion);

                post.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // Saved successfully.
                            mQuestion = new Question(post.getObjectId(), postName, postTitle, postQuestion);
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        } else {
                            // The save failed.
                            Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                            Log.d(getClass().getSimpleName(), "User update error: " + e);
                        }
                    }
                });

            }
            else {
                // update post

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");

                // Retrieve the object by id
                query.getInBackground(mQuestion.getId(), new GetCallback<ParseObject>() {
                    public void done(ParseObject post, ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data.
                            post.put("name", postName);
                            post.put("title", postTitle);
                            post.put("content", postQuestion);

                            post.saveInBackground(new SaveCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        // Saved successfully.
                                        mActivity.refreshPostList();
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
        }
        else if (postName.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditorActivity.this);
            builder.setMessage(R.string.edit_error_message)
                    .setTitle(R.string.edit_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }



}
