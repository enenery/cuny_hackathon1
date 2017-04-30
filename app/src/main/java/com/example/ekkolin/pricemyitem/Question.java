package com.example.ekkolin.pricemyitem;
import com.parse.ParseObject;
import com.parse.ParseClassName;


/**
 * Created by Erii on 4/29/2017.
 */

@ParseClassName("Question")
public class Question extends ParseObject {

    private String id;
    private String name;
    private String title;
    private String question;

    public Question() {
        super();
    }

    Question(String questionId, String name, String title, String question) {
        this.id = questionId;
        this.name = name;
        this.title = title;
        this.question = question;
    }

    Question(String name, String title, String question){
        this.name = name;
        this.title = title;
        this.question = question;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title= title;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String content) {
        this.question = content;
    }

}
