package fanhoo.com.projectbase;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import fanhoo.com.projectbase.base.BaseActivity;
import fanhoo.com.projectbase.base.BaseViewHolder;
import fanhoo.com.projectbase.base.adapter.BaseRecyclerAdapter;

public class MainActivity extends BaseActivity {

    @BindView(R.id.textview)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void init() {
        textView.setText("11111111111");
    }

    class adapter extends BaseRecyclerAdapter<BaseViewHolder, Man> {

        @Override
        protected BaseViewHolder getViewHolder(View view, int viewType) {
            return null;
        }

        @Override
        protected int getItemViewId(int viewType) {
            return 0;
        }

        @Override
        protected void bindViewHolder(BaseViewHolder holder, Man man) {

        }


    }

    class Hol extends BaseViewHolder {

        public Hol(View itemView) {
            super(itemView);
        }
    }

    class Hol1 extends BaseViewHolder {

        public Hol1(View itemView) {
            super(itemView);
        }
    }
}
