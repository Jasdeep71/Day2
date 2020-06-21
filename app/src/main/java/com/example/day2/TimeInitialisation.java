package com.example.day2;

public class TimeInitialisation {
    static TimeInitialisation continu = new TimeInitialisation(0,0,0);
    private double PresentScore;
    private double TotalScore;
    private double PreviousScore;
    TimeInitialisation(){

    }
    TimeInitialisation(double presentScore, double totalScore, double previousScore) {
        PresentScore = presentScore;
        TotalScore = totalScore;
        PreviousScore = previousScore;
    }

    public double getPresentScore() {
        return PresentScore;
    }

    public void setPresentScore(double presentScore) {
        PresentScore = presentScore;
    }

    public double getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(double totalScore) {
        TotalScore = totalScore;
    }

    public double getPreviousScore() {
        return PreviousScore;
    }

    public void setPreviousScore(double previousScore) {
        PreviousScore = previousScore;
    }

}
