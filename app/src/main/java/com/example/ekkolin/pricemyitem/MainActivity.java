package com.example.ekkolin.pricemyitem;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<Question> questions =new ArrayList<>();
    QuestionAdapter mAdapter;
    private Question mQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.mobile_list);

        //mAdapter = new QuestionAdapter(this,R.layout.list_item_layout, questions);
        mAdapter = new QuestionAdapter(MainActivity.this, questions);
        mListView.setAdapter(mAdapter);
        refreshPostList();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Question mQuestion = questions.get(position);
                Intent mIntent = new Intent(MainActivity.this, PostActivity.class);
                mIntent.putExtra("questionId", mQuestion.getId());
                mIntent.putExtra("questionName", mQuestion.getName());
                mIntent.putExtra("questionTitle", mQuestion.getTitle());
                mIntent.putExtra("questionContent", mQuestion.getQuestion());
                mIntent.addFlags(position); //Optional parameters
                startActivity(mIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh: {
                refreshPostList();
                break;
            }

            case R.id.action_new: {
                Intent intent = new Intent(this, PostActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.action_settings: {
                // Do something when user selects Settings from Action Bar overlay
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchQuestions() {
        ParseQuery<Question> query = ParseQuery.getQuery("Post");

        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> objects, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    for(int i = 0; i <objects.size();i++){
                        ParseObject p = objects.get(i);
                        String mName = p.getString("name");
                        Log.i("mName = ", mName);
                        String mTitle = p.getString("title");
                        Log.i("mTitle = ", mTitle);
                        String mQuestion = p.getString("question");
                        Log.i("mQuestion = ", mQuestion);
                        questions.add(new Question(mName, mTitle, mQuestion));
                    }
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });

        //adapter = new QuestionAdapter(MainActivity.this, questions);
        mAdapter.notifyDataSetChanged();
        //ListView listView = (ListView) findViewById(R.id.list);
        //listView.setAdapter(adapter);

    }

    public void askQuestion(View view){
        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
        startActivity(intent);
    }

    public void refreshPostList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    Log.i("refreshPost", "1");
                    // If there are results, update the list of questions
                    // and notify the adapter
                    questions.clear();
                    for (ParseObject post : postList) {
                        mQuestion = new Question(post.getObjectId(), post
                                .getString("name"), post.getString("title"), post.getString("question"));

                        questions.add(new Question(post.getObjectId(), post.getString("name"), post.getString("title"), post.getString("question")));
                    }

                    mAdapter = new QuestionAdapter(MainActivity.this, questions);
                    mAdapter.notifyDataSetChanged();

                    //ListView listView = (ListView) findViewById(R.id.list);
                    //listView.setAdapter(adapter);

                } else {
                    Log.i("refreshPostList", "2");
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }

            }

        });

    }


}
