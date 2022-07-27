package com.inovex.bikroyik.adapter;

/**
 * Created by DELL on 8/5/2018.
 */

public class Ranking {
    String rankName;
    String rankPoint;
    String grade;
    String id;

    public Ranking( String rankName, String rankPoint, String grade, String id) {

        this.rankName = rankName;
        this.rankPoint = rankPoint;
        this.grade = grade;
        this.id = id;
    }


    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getRankPoint() {
        return rankPoint;
    }

    public void setRankPoint(String rankPoint) {
        this.rankPoint = rankPoint;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
