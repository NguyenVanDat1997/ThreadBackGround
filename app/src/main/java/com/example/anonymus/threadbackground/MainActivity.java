package com.example.anonymus.threadbackground;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStart,btnPause,btnResume;
    TextView textView;
    ProgressBar progressBar;
    AtomicBoolean isCheck = new AtomicBoolean();
    int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        btnPause = findViewById(R.id.btnPause);
        btnPause.setOnClickListener(this);

        btnResume = findViewById(R.id.btnResume);
        btnResume.setOnClickListener(this);

        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setProgress(0);
        progressBar.setMax(100);





    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStart:
                isCheck.set(true);
                status = 0;
                new Thread(progess).start();
                break;
            case R.id.btnPause:
                onPause();
                break;
            case R.id.btnResume:
                onResume();
                break;
            default:
                break;
        }
    }

    private Runnable progess = new Runnable() {
        @Override
        public void run() {
            for (int i=status;i<=100&&isCheck.get();i++){
                UpdateUi(i);
                SystemClock.sleep(500);
            }
        }
    };

    private void UpdateUi(final int i){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(i);
                textView.setText(String.valueOf(i) +"%");
                status = i;

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isCheck.set(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCheck.set(true);

        if(status > 0){
            new Thread(progess).start();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option,menu);

        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.game:
                Intent intent = new Intent(this,Game.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return  true;
    }
}
