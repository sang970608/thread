package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    int value = 0;
    private boolean isRun = false; //스레드의 실행여부를 위한 boolean 변수 선언

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Button showBtn = findViewById(R.id.showBtn);

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("스레드에서 받은 값 : " + value);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        /* 스레드 실행 */
        BackgroundThread thread = new BackgroundThread();
        isRun = true;
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        /* 스레드 중지 */
        isRun = false;
        value = 0; //value값 다시 0으로 초기화
    }

    class BackgroundThread extends Thread{

        @Override
        public void run() {
            while(isRun){ // isRun 변수가 true가 되면
                try{
                    Thread.sleep(1000); //1초 지연
                    value++; //value 값 1 증가
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            try{
                this.join(); //스레드가 끝나기를 기다림
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        /* isRun이 true인 동안은 계속해서 1초마다 value 값을 1씩 증가시킨다.
         * 그러다가 isRun이 false가 되면 스레드가 메인 스레드와 합쳐지고 끝이난다.*/

    }
}