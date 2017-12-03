package ru.spbu.math;

public class TestDocument {
    private int id;
    private String title;
    private String text;

    public TestDocument(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
