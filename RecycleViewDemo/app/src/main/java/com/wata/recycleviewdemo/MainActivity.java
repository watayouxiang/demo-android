package com.wata.recycleviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        loadListData(false, true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list_normal) {
            loadListData(false, true);
            return true;
        } else if (id == R.id.action_list_vertical_reverse) {
            loadListData(true, true);
            return true;
        } else if (id == R.id.action_list_horizontal) {
            loadListData(false, false);
            return true;
        } else if (id == R.id.action_list_horizontal_reverse) {
            loadListData(true, false);
            return true;
        } else if (id == R.id.action_grid_normal) {
            loadGridData(false, true);
            return true;
        } else if (id == R.id.action_grid_vertical_reverse) {
            loadGridData(true, true);
            return true;
        } else if (id == R.id.action_grid_horizontal) {
            loadGridData(false, false);
            return true;
        } else if (id == R.id.action_grid_horizontal_reverse) {
            loadGridData(true, false);
            return true;
        } else if (id == R.id.action_staggered_normal) {
            loadStaggeredData(false, true);
            return true;
        } else if (id == R.id.action_staggered_vertical_reverse) {
            loadStaggeredData(true, true);
            return true;
        } else if (id == R.id.action_staggered_horizontal) {
            loadStaggeredData(false, false);
            return true;
        } else if (id == R.id.action_staggered_horizontal_reverse) {
            loadStaggeredData(true, false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }

    private void loadListData(boolean reverse, boolean vertical) {
        // 给RecycleView加载数据

        // 1. 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置是否反向显示
        layoutManager.setReverseLayout(reverse);
        // 设置显示的方向
        layoutManager.setOrientation(vertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(layoutManager);
        // 设置RecycleView的Item间分割线
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));

        // 2. 设置适配器
        List<DataBean> datas = new ArrayList<DataBean>();
        DataBean bean;
        for (int i = 0; i < 20; i++) {
            bean = new DataBean();
            bean.icon = R.mipmap.ic_launcher;
            if (i < 10) {
                bean.name = "图片-0" + i;
            } else {
                bean.name = "图片-" + i;
            }
            datas.add(bean);
        }
        mRecycleView.setAdapter(new ListAdapter(this, datas));

    }

    private void loadGridData(boolean reverse, boolean vertical) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setReverseLayout(reverse);
        layoutManager.setOrientation(vertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(layoutManager);


        List<DataBean> datas = new ArrayList<DataBean>();
        DataBean bean;
        for (int i = 0; i < 20; i++) {
            bean = new DataBean();
            bean.icon = R.mipmap.ic_launcher;
            if (i < 10) {
                bean.name = "图片-0" + i;
            } else {
                bean.name = "图片-" + i;
            }
            datas.add(bean);
        }
        mRecycleView.setAdapter(new GridAdapter(this, datas));
    }

    private void loadStaggeredData(boolean reverse, boolean vertical) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, vertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(reverse);
        mRecycleView.setLayoutManager(layoutManager);


        List<DataBean> datas = new ArrayList<DataBean>();
        int[] photoArr = {R.mipmap.ic_launcher, R.mipmap.dsf, R.mipmap.tt03, R.mipmap.dsa, R.mipmap.tt04, R.mipmap.tt05, R.mipmap.dasda};
        DataBean bean;
        for (int i = 0; i < photoArr.length; i++) {
            bean = new DataBean();
            bean.icon = photoArr[i];
            bean.name = "图片-" + i;
            datas.add(bean);
        }
        mRecycleView.setAdapter(new StaggeredAdapter(this, datas));
    }
}
