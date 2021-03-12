package com.example.rxjava_throttlefirst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding4.view.RxView;

import java.util.TimerTask;
import java.util.TooManyListenersException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    Button button;
    private static final long firstReq = System.currentTimeMillis();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn);

        RxView.clicks(button)
                .throttleFirst(4000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Unit>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        makeSout("<--- Subscribed --->");
                    }

                    @Override
                    public void onNext(@NonNull Unit unit) {
                        makeSout("onNext : " + String.valueOf(System.currentTimeMillis() - firstReq));
                        makeToast("Button Clicked");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        makeSout("Error : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        makeSout("<--- Completed --->");
                    }
                });
    }

    private void makeToast(String message) {
        Toast.makeText(this , message , Toast.LENGTH_SHORT).show();
    }

    private void makeSout(String message){

        System.out.println(message);
    }
}