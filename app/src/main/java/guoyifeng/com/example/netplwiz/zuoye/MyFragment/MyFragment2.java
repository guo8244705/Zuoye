package guoyifeng.com.example.netplwiz.zuoye.MyFragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import guoyifeng.com.example.netplwiz.zuoye.R;
import guoyifeng.com.example.netplwiz.zuoye.bean.UpLoaBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment2 extends Fragment implements View.OnClickListener {


    /**
     * 拍照
     */
    private Button mBt;
    /**
     * 相册
     */
    private Button mBt1;
    private ImageView mIv;
    private File file;
    private Uri uri;

    public MyFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_fragment2, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mBt = (Button) view.findViewById(R.id.bt);
        mBt.setOnClickListener(this);
        mBt1 = (Button) view.findViewById(R.id.bt1);
        mBt1.setOnClickListener(this);
        mIv = (ImageView) view.findViewById(R.id.iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                toCmera();
                break;
            case R.id.bt1:
                toPhoto();
                break;
        }
    }

    private void toPhoto() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openPhoto();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        }
    }

    private void toCmera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cmera();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 100) {
                cmera();
            } else if (requestCode == 200) {
                openPhoto();
            }
        }
    }

    private void openPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 20);
    }

    private void cmera() {
        file = new File(getContext().getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            uri = FileProvider.getUriForFile(getContext(), "abc", file);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10) {
                uploadFile(file);
            }
        } else if (requestCode == 20) {
            Uri data1 = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(data1));
                mIv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                uploadFile(file);
            }
        }
    }

    private void uploadFile(File file) {
        OkHttpClient build = new OkHttpClient.Builder().build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);

        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "qwqt")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();

        Request request = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(body)
                .build();

        Call call = build.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("qwgqwgqwgqwgq", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final UpLoaBean bean = new Gson().fromJson(string, UpLoaBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean != null) {
                            Glide.with(getContext()).load(bean.getData().getUrl()).into(mIv);
                        } else {
                            Log.e("wqgqw", "失败");
                        }
                    }
                });
            }
        });
    }
}
