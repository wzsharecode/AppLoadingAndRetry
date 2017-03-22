package cn.loadingandretry;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.Listener.OnLoadingAndRetryListener;
import com.cn.base.LoadingAndRetryManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnyViewTestActivity extends AppCompatActivity {

    @InjectView(R.id.ll)
    LinearLayout ll;
    @InjectView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    LoadingAndRetryManager mLoadingAndRetryManager;
    @InjectView(R.id.id_textview)
    TextView idTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyview_test);
        ButterKnife.inject(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                R.color.blue,
                R.color.blue,
                R.color.colorAccent);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        ;
        swipeRefreshLayout.setProgressBackgroundColor(R.color.white);
//        swipeRefreshLayout.setPadding(20, 20, 20, 20);
//        swipeRefreshLayout.setProgressViewOffset(true, 100, 200);
//        swipeRefreshLayout.setDistanceToTriggerSync(50);
        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AnyViewTestActivity.this.refreashTextView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(idTextview, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryRefreashTextView(retryView);
            }
        });

        refreashTextView();


    }

    private void refreashTextView() {
        mLoadingAndRetryManager.showLoading();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Math.random() > 0.6) {
                    mLoadingAndRetryManager.showContent();
                } else {
                    mLoadingAndRetryManager.showRetry();
                }

            }
        }.start();


    }

    public void retryRefreashTextView(View retryView) {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyViewTestActivity.this.refreashTextView();
            }
        });
    }


}
