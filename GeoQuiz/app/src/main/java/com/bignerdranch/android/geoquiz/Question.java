package com.bignerdranch.android.geoquiz;

/**
 * Created by admin on 5/9/16.
 */
public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mUserCheated;

    public Question (int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isUserCheated() {
        return mUserCheated;
    }

    public void setUserCheated(boolean userCheated) { mUserCheated = userCheated; }
}
