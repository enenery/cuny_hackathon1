package com.example.ekkolin.pricemyitem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erii on 4/29/2017.
 */

public class QuestionAdapter extends ArrayAdapter<Question> {

    private View listItemView;

    public QuestionAdapter(Context context, ArrayList<Question> list){
        super(context, 0, list);
    }
    public QuestionAdapter(Context context){
        super(context, 0);
    }

    public QuestionAdapter(Context context, int textViewResourceId, List objects) {
        super(context,textViewResourceId, objects);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_layout, parent, false);
        }

        Question currentQuestion = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        nameTextView.setText(currentQuestion.getName());

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(currentQuestion.getTitle());

        return listItemView;
    }

}
