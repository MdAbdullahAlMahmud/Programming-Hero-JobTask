package com.mkrlabs.programmingheroquiz.model;

public class ShuffleAnsKey {
    private String ans;
    private String key;

    public ShuffleAnsKey(String ans, String key) {
        this.ans = ans;
        this.key = key;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
