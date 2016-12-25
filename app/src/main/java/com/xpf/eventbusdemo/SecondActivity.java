package com.xpf.eventbusdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xpf.eventbusdemo.domain.FirstEvent;
import com.xpf.eventbusdemo.domain.SecondEvent;
import com.xpf.eventbusdemo.domain.ThirdEvent;

import org.greenrobot.eventbus.EventBus;

public class SecondActivity extends Activity implements View.OnClickListener {

    private Button btn_event1;
    private Button btn_event2;
    private Button btn_event3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btn_event1 = (Button) findViewById(R.id.btn_event1);
        btn_event2 = (Button) findViewById(R.id.btn_event2);
        btn_event3 = (Button) findViewById(R.id.btn_event3);
        btn_event1.setOnClickListener(this);
        btn_event2.setOnClickListener(this);
        btn_event3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_event1:
                // 使用EventBus中的Post方法来实现发送消息,发送过去的是我们FirstEvent类的实例
                EventBus.getDefault().post(new FirstEvent("第二个页面的FirstEvent按钮被点击了!"));
                break;
            case R.id.btn_event2:
                EventBus.getDefault().post(new SecondEvent("第二个页面的SecondEvent按钮被点击了!"));
                break;
            case R.id.btn_event3:
                EventBus.getDefault().post(new ThirdEvent("第二个页面的ThirdEvent按钮被点击了!"));
                break;
        }
    }
}
