package mvp.com.zmvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import mvp.com.zmvp.R;
import mvp.com.zmvp.ui.activity.base.BaseActivity;
import mvp.com.zmvp.presenter.MainPresenter;
import mvp.com.zmvp.presenter.impl.MainPresenterImpl;
import mvp.com.zmvp.view.IMainView;
import mvp.com.zmvp.widgets.RecyclerViewBanner;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, IMainView {

    private RecyclerViewBanner recyclerBanner;
    private ArrayList<RecyclerViewBanner.BannerEntity> mDatas = new ArrayList<>();

    private MainPresenter mPresenter;

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }



    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerBanner = (RecyclerViewBanner) findViewById(R.id.recyclerBanner);
    }

    public void initData() {
        mPresenter =  new MainPresenterImpl(MainActivity.this);
    }

    @Override
    public void onBackPressedSupport() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressedSupport();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(this, EffectDemoActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(this, DataListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {
            reloadData();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void reloadData() {
        mDatas.clear();
        Random random = new Random();
        int nextInt = random.nextInt(10);
        if (nextInt % 2 == 0) {
            mDatas.add(new Entity("http://pic.58pic.com/58pic/12/46/13/03B58PICXxE.jpg"));
            mDatas.add(new Entity("http://www.jitu5.com/uploads/allimg/121120/260529-121120232T546.jpg"));
            mDatas.add(new Entity("http://pic34.nipic.com/20131025/2531170_132447503000_2.jpg"));
            mDatas.add(new Entity("http://img5.imgtn.bdimg.com/it/u=3462610901,3870573928&fm=206&gp=0.jpg"));
        } else {
            mDatas.add(new Entity("http://img0.imgtn.bdimg.com/it/u=726278301,2143262223&fm=11&gp=0.jpg"));
            mDatas.add(new Entity("http://pic51.nipic.com/file/20141023/2531170_115622554000_2.jpg"));
            mDatas.add(new Entity("http://img3.imgtn.bdimg.com/it/u=2968209827,470106340&fm=21&gp=0.jpg"));
        }

        recyclerBanner.setDatas(mDatas);
    }


    private class Entity implements RecyclerViewBanner.BannerEntity {
        String url;

        public Entity(String url) {
            this.url = url;
        }

        @Override
        public String getUrl() {
            return url;
        }
    }


    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showProgress(boolean isShow) {
        System.out.println("当前状态 isShow = " + isShow);
    }


    @Override
    public void setPresenter(MainPresenter presenter) {

    }

}
