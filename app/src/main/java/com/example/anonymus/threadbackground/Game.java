package com.example.anonymus.threadbackground;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game extends AppCompatActivity {

    AtomicBoolean isBuuton = new AtomicBoolean();
    Button btnButton;
    ImageView bau,cua,tom,ca,huou,ga,one,tow,three;
    TextView tickHuou,tickGa,tickBau,tickCa,tickCua,tickTom;
    TextView point;
    TextView first,second;
    SharedPreferences sharedPreferences;

    int drawImage[] = {R.drawable.huou,R.drawable.bau,R.drawable.ga,R.drawable.ca,R.drawable.cua,R.drawable.tom};
    int moneys = 50000;
    int CountClick[] = new int[6];

    Myrandom myrandom;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        setWidget();
        setStart();
        sharedPreferences = getSharedPreferences("scores",this.MODE_PRIVATE);
        first.setText("1 : "+String.valueOf(sharedPreferences.getInt("first",50000)));
        second.setText("2 : "+String.valueOf(sharedPreferences.getInt("second",0 )));
        sharedPreferences.registerOnSharedPreferenceChangeListener(prefListener);


        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //true là xốc false là mở
                if (isBuuton.get()){
                    isBuuton.set(false);
                    btnButton.setText("Mở");
                    myrandom = new Myrandom();
                    myrandom.start();

                }else{
                    isBuuton.set(true);
                    btnButton.setText("Xốc");
                    int Image1 = drawImage[random(0,5)];
                    int Image2 = drawImage[random(0,5)];
                    int Image3 = drawImage[random(0,5)];
                    one.setBackgroundResource(Image1);
                    tow.setBackgroundResource(Image2);
                    three.setBackgroundResource(Image3);

                    for (int i =0;i<6;i++){
                        if (drawImage[i]==Image1){
                            moneys = moneys + CountClick[i]*2*10000;
                            count ++;
                        }
                        if(drawImage[i]==Image2){
                            if (count!=0){
                                moneys = moneys + CountClick[i]*10000;
                            }else{
                                moneys = moneys + CountClick[i]*2*10000;
                                count++;
                            }

                        }
                        if(drawImage[i]==Image3){
                            if (count!=0){
                                moneys = moneys + CountClick[i]*10000;
                            }else{
                                moneys = moneys + CountClick[i]*2*10000;
                            }
                            count = 0;
                        }
                        count = 0;
                    }


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    int first = sharedPreferences.getInt("first",50000);
                    int second = sharedPreferences.getInt("second",0);
                    if (moneys>first){
                        editor.putInt("first",moneys);
                        editor.putInt("second",first);
                        editor.apply();
                    }else if (moneys > second){
                        editor.putInt("second",moneys);
                        editor.apply();
                    }
                    point.setText(String.valueOf(moneys)+" K");
                    setStart();

                }
            }
        });


        bau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue(1,tickBau);
            }
        });

        huou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setValue(0,tickHuou);
            }
        });

        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue(3,tickCa);
            }
        });

        tom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            setValue(5,tickTom);
            }
        });

        ga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               setValue(2,tickGa);
            }
        });

        cua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setValue(4,tickCua);
            }
        });



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("check",isBuuton.get());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isBuuton.set(savedInstanceState.getBoolean("check"));
    }

    private void setWidget(){
        isBuuton.set(true);
        btnButton = findViewById(R.id.btnButton);
        one = findViewById(R.id.one);
        tow = findViewById(R.id.tow);
        three = findViewById(R.id.three);

        huou = findViewById(R.id.huou);
        bau = findViewById(R.id.bau);
        ga = findViewById(R.id.ga);
        ca = findViewById(R.id.ca);
        tom = findViewById(R.id.tom);
        cua = findViewById(R.id.cua);

        tickHuou = findViewById(R.id.tickHuou);
        tickBau = findViewById(R.id.tickBau);
        tickGa = findViewById(R.id.tickGa);
        tickCa = findViewById(R.id.tickCa);
        tickTom= findViewById(R.id.tickTom);
        tickCua= findViewById(R.id.tickCua);
        point = findViewById(R.id.money);
        point.setText(String.valueOf(String.valueOf(moneys)+" K"));

        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
    }

    private void setStart(){
        tickHuou.setVisibility(View.GONE);
        tickBau.setVisibility(View.GONE);
        tickTom.setVisibility(View.GONE);
        tickCa.setVisibility(View.GONE);
        tickGa.setVisibility(View.GONE);
        tickCua.setVisibility(View.GONE);
        for (int i=0;i<6;i++){
            CountClick[i]=0;
        }
    }

    private void setValue(int i,TextView textView){
        if (moneys >= 10000){
            CountClick[i] += 1;
            moneys = moneys - 10000;
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(CountClick[i]));
        }
    }

    public static int random(int min, int max) {
        try {
            Random rn = new Random();
            int range = max - min + 1;
            int randomNum = min + rn.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public class Myrandom extends Thread{
        @Override
        public void run() {
            for (int i=0;i<6;i++){
                ChangeImage(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
        }

    }

    private void ChangeImage(final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                one.setBackgroundResource(drawImage[random(0,5)]);
                tow.setBackgroundResource(drawImage[random(0,5)]);
                three.setBackgroundResource(drawImage[random(0,5)]);
                if (i==5){
                    one.setBackgroundResource(R.drawable.gray);
                    tow.setBackgroundResource(R.drawable.gray);
                    three.setBackgroundResource(R.drawable.gray);
                }
            }
        });
    }

    private SharedPreferences.OnSharedPreferenceChangeListener prefListener=
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences sharedPrefs,
                                                      String key) {
                        first.setText("1 : "+String.valueOf(sharedPreferences.getInt("first",50000)));
                        second.setText("2 : "+String.valueOf(sharedPreferences.getInt("second",0 )));
                }
            };

}