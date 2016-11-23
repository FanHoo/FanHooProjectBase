package fanhoo.com.fanhoosimple;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import fanhoo.com.fanhoosimple.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.textview)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView.setText("11111111111");
    }
}
