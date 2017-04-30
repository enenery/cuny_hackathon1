package com.example.ekkolin.pricemyitem;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erii on 4/29/2017.
 */

public class PostActivity extends Activity {
    ListView mListView;
    ArrayAdapter<String> mAdapter;

    private TextView nameTextView;
    private TextView titleTextView;
    private TextView questionTextView;
    String questionID;
    String[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        Intent intent = getIntent();
        questionID = intent.getStringExtra("questionId");
        String name = intent.getStringExtra("questionName");
        String title = intent.getStringExtra("questionTitle");
        String question = intent.getStringExtra("questionContent");

        Log.i("id,name,title,question", questionID + " " + name + " " + title + " " + question);

        mListView = (ListView) findViewById(R.id.list);
        //answers = fetchAnswers(questionID);


        String[] fakeAnswers = {"Be the first person to answer this question!",
                "Try harder!",
                "Get more sunlight.",
                "Sorry, you can't.",
                "I don't know.",
                "I see your struggle.",
                "Can you paint your body?"};

        if(answers == null) {
            for (int i = 0; i < fakeAnswers.length; i++)
                Log.i("fakeAnswers", "[" + i + "] = " + fakeAnswers[i]);
            mAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, fakeAnswers);
        }else
        mAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, answers);


        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        nameTextView = (TextView) (findViewById(R.id.name));
        titleTextView = (TextView) (findViewById(R.id.title));
        questionTextView = (TextView) (findViewById(R.id.question));

        nameTextView.setText("Name: " + name);
        titleTextView.setText("Title:    " + title);
        questionTextView.setText("Question: " + question);

    }

    public void answerQuestion(View view){
        Intent intent = new Intent(PostActivity.this, AnswerActivity.class);
        intent.putExtra("questionId", questionID);
        startActivity(intent);
    }

   /* public void fetchAnswers() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("_id", questionID);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    ParseObject post = postList.get(0);
                    // If there are results, update the list of questions
                    // and notify the adapter

                    for (ParseObject post : postList) {
                        mQuestion = new Question(post.getObjectId(), post
                                .getString("name"), post.getString("title"), post.getString("question"));

                        questions.add(new Question(post.getObjectId(), post.getString("name"), post.getString("title"), post.getString("question")));
                    }

                } else {
                    Log.i("refreshPostList", "2");
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }

            }

        });

    }*/
}
