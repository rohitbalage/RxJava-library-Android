package com.rrbofficial.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private final  static String TAG ="myApp";
    private String greeting="Hello from RxJava";
    private Observable <String> myObservable;
    private Observer <String> myObserver;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tvGreeting);

        myObservable = Observable.just(greeting);


        // Here  we subscribe on schedulers io.
        myObservable.subscribeOn(Schedulers.io());


        // this means the data stream move to main thread again so we can use those data for UI operations.

        myObservable.observeOn(AndroidSchedulers.mainThread());


        myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe invoked");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext invoked");

                textView.setText(s);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError invoked");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete invoked");
            }
        };
        myObservable.subscribe(myObserver);

    }
}