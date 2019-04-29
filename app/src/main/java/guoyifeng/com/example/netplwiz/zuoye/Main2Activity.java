package guoyifeng.com.example.netplwiz.zuoye;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar mPb;
    /**
     * 0%
     */
    private TextView mTv;
    /**
     * 开始下载
     */
    private Button mBt;
    private File sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initSD();
    }

    private void initView() {
        mPb = (ProgressBar) findViewById(R.id.pb);
        mTv = (TextView) findViewById(R.id.tv);
        mBt = (Button) findViewById(R.id.bt);
        mBt.setOnClickListener(this);
    }
    private void initSD() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            readSD();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 100) {
                readSD();
            }
        }
    }
    private void readSD() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sd = Environment.getExternalStorageDirectory();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                ok(sd + "/" + "abc.apk");
                break;
        }
    }

    private void ok(final String s) {
        OkHttpClient build = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url("http://cdn.banmi.com/banmiapp/apk/banmi_330.apk")
                .get()
                .build();


        Call call = build.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                long max = body.contentLength();
                upData(inputStream, s, max);
            }
        });
    }

    private void upData(InputStream inputStream, String s, long max) {
        try {
            FileOutputStream os = new FileOutputStream(new File(s));
            int count = 0;
            int len = -1;
            byte[] bys = new byte[1024 * 10];
            while ((len = inputStream.read(bys)) != -1) {
                os.write(bys, 0, len);
                count += len;
                mPb.setMax((int) max);
                mPb.setProgress(count);

                final long finalCount1 = count;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTv.setText(finalCount1 +"%");
                    }
                });
                Log.e("max", String.valueOf(max) + count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
