package com.likelioninu.save_inu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class select_page extends AppCompatActivity {

    String url;
    private long pressedtime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_button);

        Button bt_saveHackerton = findViewById(R.id.escape_hackerton);
        Button bt_saveInu = findViewById(R.id.save_inu);

        bt_saveInu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                url = "https://save-inu-hyuuuub.c9users.io/";
                intentURL();

                Intent intent = new Intent(select_page.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt_saveHackerton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                url = "https://save-hackathon-hyuuuub.c9users.io/";
                intentURL();

                Intent intent = new Intent(select_page.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void intentURL(){
        SharedPreferences ssp = getSharedPreferences("WW2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ssp.edit();
        editor.putString("currentURL", url).commit();
    }

    @Override
    public void onBackPressed() {
        Toast toast = null;
        if (System.currentTimeMillis() >= pressedtime + 2000) {
            pressedtime = System.currentTimeMillis();
            toast = Toast.makeText(this, "종료해도 시간은 간다구욧!!", Toast.LENGTH_SHORT); //한번 클릭 시 토스트문구
            toast.show();
            return;
        } else {

            if(toast != null){
                toast.cancel();
            }

            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            finish();
        }
        super.onBackPressed();
    }
}
