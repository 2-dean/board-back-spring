package com.board.board.domain;

public class File {
    private int fnum;
    int bnum;
    String oriName;
    String saveName;
    String savePath;

    public int getFnum() {
        return fnum;
    }

    public int getBnum() {
        return bnum;
    }

    public String getOriName() {
        return oriName;
    }

    public String getSaveName() {
        return saveName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setFnum(int fnum) {
        this.fnum = fnum;
    }

    public void setBnum(int bnum) {
        this.bnum = bnum;
    }

    public void setOriName(String oriName) {
        this.oriName = oriName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public String toString() {
        return "File{" +
                "fnum=" + fnum +
                ", bnum=" + bnum +
                ", oriName='" + oriName + '\'' +
                ", saveName='" + saveName + '\'' +
                ", savePath='" + savePath + '\'' +
                '}';
    }
}
