package com.jobportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "faqs")
public class FAQ {

    @Id
    private String id;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String question;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String answer;

    public FAQ() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
