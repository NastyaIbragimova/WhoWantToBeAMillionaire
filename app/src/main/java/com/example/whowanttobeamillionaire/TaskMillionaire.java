package com.example.whowanttobeamillionaire;

public class TaskMillionaire {

    private final String[] question;
    private final int level;

    public TaskMillionaire(String[] question, int level) {
        this.question = question;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public String[] getQuestion() {
        return question;
    }
}
