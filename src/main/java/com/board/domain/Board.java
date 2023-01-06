package com.board.domain;

public class Board {

    private int bnum;
    private String title;
    private String content;
    private String name;
    private String regdate;

    public int getBnum() {
        return bnum;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setBnum(int bnum) {
        this.bnum = bnum;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    @Override
    public String toString() {
        return "Board{" +
                "bnum=" + bnum +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", regdate='" + regdate + '\'' +
                '}';
    }
}
