package com.xpf.eventbusdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xpf.eventbusdemo.domain.FirstEvent;
import com.xpf.eventbusdemo.domain.SecondEvent;
import com.xpf.eventbusdemo.domain.ThirdEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends Activity {

    private Button btn_start;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 在要接收消息的页面的OnCreate()中注册EventBus
        EventBus.getDefault().register(this);

        btn_start = (Button) findViewById(R.id.btn_start);
        text = (TextView) findViewById(R.id.text);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 告知观察者事件发生时通过EventBus.post函数实现，这个过程叫做事件的发布，观察者被告知事件发生叫做事件的接收，
     * 是通过下面的订阅函数实现的。
     * onEvent:如果使用onEvent作为订阅函数，那么该事件在哪个线程发布出来的，onEvent就会在这个线程中运行，也就是说
     * 发布事件和接收事件线程在同一个线程。使用这个方法时，在onEvent方法中不能执行耗时操作，如果执行耗时操作容易导致事件分发延迟。
     * onEventMainThread:如果使用onEventMainThread作为订阅函数，那么不论事件是在哪个线程中发布出来的，onEventMainThread都会
     * 在UI线程中执行，接收事件就会在UI线程中运行，这个在Android中是非常有用的，因为在Android中只能在UI线程中跟新UI，所以
     * 在onEvnetMainThread方法中是不能执行耗时操作的。
     * onEventBackground:如果使用onEventBackgrond作为订阅函数，那么如果事件是在UI线程中发布出来的，那么onEventBackground
     * 就会在子线程中运行，如果事件本来就是子线程中发布出来的，那么onEventBackground函数直接在该子线程中执行。
     * onEventAsync：使用这个函数作为订阅函数，那么无论事件在哪个线程发布，都会创建新的子线程在执行onEventAsync.
     */

    // 订阅事件，如果对方发送了就会收到对应的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent1(FirstEvent event) {
        // 在收到FirstEvent实例后，我们将其中携带的消息取出，Toast出去并传到TextView中
        String msg = "onMessageEvent收到了消息：" + event.getmMsg();
        text.setText(msg);
        Log.e("TAG", "onEventMainThread收到了消息：" + event.getmMsg());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent2(SecondEvent event) {
        Log.e("TAG", "onEventBackgroundThread收到了消息：" + event.getmMsg());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent3(ThirdEvent event) {
        Log.e("TAG", "onEventAsyncThread收到了消息：" + event.getmMsg());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在OnDestroy()方法中解注册EventBus
        EventBus.getDefault().unregister(this);
    }
}
