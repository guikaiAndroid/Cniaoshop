package com.guikai.cniaoshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.guikai.cniaoshop.bean.User;

/*
 * Time:         2018/10/24 23:31
 * Creator:      Anding
 * Note:         TODO
 */
public class BaseActivity  extends AppCompatActivity {
    public void startActivity(Intent intent, boolean isNeedLogin){

        if(isNeedLogin){
            User user =CniaoApplication.getmInstance().getUser();
            if(user !=null){
                super.startActivity(intent);
            }
            else{

                CniaoApplication.getmInstance().putIntent(intent);
                Intent loginIntent = new Intent(this, LoginActivity.class);
                super.startActivity(intent);

            }

        }
        else{
            super.startActivity(intent);
        }

    }
}
