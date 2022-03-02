package com.mkrlabs.programmingheroquiz.repository.shred_pref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    Context context;
    private SharedPreferences default_preference;

    private final String HIGH_SCORE="High_Score";
    public SharedPref(Context context) {
        this.context = context;
        default_preference = context.getSharedPreferences("config",Context.MODE_PRIVATE);
    }


    public  void setHighScore(int score){
        default_preference.edit().putInt(HIGH_SCORE,score).apply();
    }

    public int getHighScore(){
        return default_preference.getInt(HIGH_SCORE,0);
    }
}
