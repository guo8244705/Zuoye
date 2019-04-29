package guoyifeng.com.example.netplwiz.zuoye;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import guoyifeng.com.example.netplwiz.zuoye.MyFragment.MyFragment1;
import guoyifeng.com.example.netplwiz.zuoye.MyFragment.MyFragment2;
import guoyifeng.com.example.netplwiz.zuoye.MyFragment.MyFragment3;
import retrofit2.http.PATCH;

public class MainActivity extends AppCompatActivity {
    private Toolbar mTb;
    private ViewPager mVp;
    private TabLayout mTab;
    private NavigationView mNv;
    private DrawerLayout mDl;
    private ArrayList<Fragment> list;
    private MyPagerAdapter pagerAdapter;
    private TextView mTv;
    private ArrayList<String> list1;
    //qwtqwtqwtqwtqwtqwtqwtqw
    //郭一峰
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolBar();
        initTab();
        initNv();
    }

    private void initNv() {
        mNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.i1){
                    View view = View.inflate(MainActivity.this, R.layout.pop_item, null);
                    final PopupWindow popupWindow = new PopupWindow(view,600,500);
                    popupWindow.showAsDropDown(mTb,200,0);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }else if (menuItem.getItemId() == R.id.i2){
                    Intent intent = new Intent();
                    intent.setAction("abc");
                    sendBroadcast(intent);
                }else if (menuItem.getItemId() == R.id.i3){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                        openCall();
                    }else{
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},100);
                    }

                }
                return false;
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (requestCode == 100){
                openCall();
            }
        }
    }

    private void openCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
    }

    private void initTab() {
        mTab.addTab(mTab.newTab().setText("A"));
        mTab.addTab(mTab.newTab().setText("B"));
        mTab.addTab(mTab.newTab().setText("C"));
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVp.setCurrentItem(tab.getPosition());
                mTv.setText(list1.get(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
    }

    private void initToolBar() {
        mTb.setTitle("");
        setSupportActionBar(mTb);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDl, mTb, R.string.app_name, R.string.app_name);
        toggle.syncState();
        mNv.setItemIconTintList(null);
    }

    private void initView() {
        mTb = (Toolbar) findViewById(R.id.tb);
        mVp = (ViewPager) findViewById(R.id.vp);
        mTab = (TabLayout) findViewById(R.id.tab);
        mNv = (NavigationView) findViewById(R.id.nv);
        mDl = (DrawerLayout) findViewById(R.id.dl);
        mTv = (TextView) findViewById(R.id.tv);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list1.add("A");
        list1.add("B");
        list1.add("C");
        list.add(new MyFragment1());
        list.add(new MyFragment2());
        list.add(new MyFragment3());
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), list);
        mVp.setAdapter(pagerAdapter);
    }
}
