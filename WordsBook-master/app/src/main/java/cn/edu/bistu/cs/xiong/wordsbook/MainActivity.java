package cn.edu.bistu.cs.xiong.wordsbook;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;

import cn.edu.bistu.cs.xiong.wordsbook.wordcontract.Words;

public class MainActivity extends AppCompatActivity implements WordItemFragment.OnFragmentInteractionListener, WordDetailFragment.OnFragmentInteractionListener {

    private WordItemFragment mWordItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WordsDB wordsDB = WordsDB.getWordsDB();
        if (wordsDB != null) {
            wordsDB.close();
        }
    }

    /**
     * 初始化加载菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 处理菜单项的选择事件
     *
     * @param item 菜单项
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                searchWords();
                break;
            case R.id.insert:
                insertWords();
                break;
            case R.id.search_online:
                Intent intent=
                        new Intent(MainActivity.this,SearchOnline.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * 更新单词列表
     */
    private void RefreshWordItemFragment() {
        /**
         * 在Activity中我们可以调用FragmentManager的findFragmentById()方法来获取一个fragment实例
         * 进而调用Fragment的方法
         * 以实现Activity与Fragment之间的通信
         */
        mWordItemFragment = (WordItemFragment) getFragmentManager().findFragmentById(R.id.wordslist);
        mWordItemFragment.refreshWordsList();
    }

    /**
     * 更新单词列表
     */
    private void RefreshWordItemFragment(String strWord) {
        mWordItemFragment = (WordItemFragment) getFragmentManager().findFragmentById(R.id.wordslist);
        mWordItemFragment.refreshWordsList(strWord);
    }

    //新增对话框
    private void insertWords() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        new AlertDialog.Builder(this)
                .setTitle("增加单词")
                //加载布局
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strWord = ((EditText) tableLayout.findViewById(R.id.txtWord)).getText().toString();
                        String strMeaning = ((EditText) tableLayout.findViewById(R.id.txtMeaning)).getText().toString();
                        String strSample = ((EditText) tableLayout.findViewById(R.id.txtSample)).getText().toString();
                        //插入
                        WordsDB wordsDB = WordsDB.getWordsDB();
                        wordsDB.InsertUserSql(strWord, strMeaning, strSample);
                        //更新
                        RefreshWordItemFragment();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }

    //删除对话框
    private void DeleteDialog(final String strId) {
        new AlertDialog.Builder(this)
                .setTitle("删除单词")
                .setMessage("确定删除?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除
                        WordsDB wordsDB = WordsDB.getWordsDB();
                        wordsDB.DeleteUseSql(strId);
                        //更新列表
                        RefreshWordItemFragment();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create().show();
    }

    //修改对话框
    private void UpdateDialog(final String strId, final String strWord, final String strMeaning, final String strSample) {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        ((EditText) tableLayout.findViewById(R.id.txtWord)).setText(strWord);
        ((EditText) tableLayout.findViewById(R.id.txtMeaning)).setText(strMeaning);
        ((EditText) tableLayout.findViewById(R.id.txtSample)).setText(strSample);
        new AlertDialog.Builder(this)
                .setTitle("修改单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strNewMeaning = ((EditText) tableLayout.findViewById(R.id.txtMeaning)).getText().toString();
                        String strNewSample = ((EditText) tableLayout.findViewById(R.id.txtSample)).getText().toString();
                        //更新单词
                        WordsDB wordsDB = WordsDB.getWordsDB();
                        wordsDB.UpdateUseSql(strId, strWord, strNewMeaning, strNewSample);
                        //更新列表
                        RefreshWordItemFragment();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create()
                .show();
    }

    //查找对话框
    private void searchWords() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.searchterm, null);
        new AlertDialog.Builder(this)
                .setTitle("查找单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String searchWord = ((EditText) tableLayout.findViewById(R.id.txtSearchWord)).getText().toString();
                        //更新列表,显示要查找的信息
                        RefreshWordItemFragment(searchWord);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create()
                .show();
    }

    /**
     * 当用户在单词详细Fragment中单击时回调此函数
     */
    @Override
    public void onWordDetailClick(Uri uri) {

    }



    //判断是否手机是否处于横屏
    private boolean isLand() {
        //获取设备的配置信息
        Configuration mConfiguration = this.getResources().getConfiguration();
        //获取屏幕方向
        int ori = mConfiguration.orientation;
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            return true;
        }
        return false;
    }
    //动态添加Fragment
    private void ChangeWordDetailFragment(String id) {
        //1,创建待添加的Fragment实例
        WordDetailFragment fragment = new WordDetailFragment();
        //Bundle类用于传递信息，
        Bundle bundle = new Bundle();
        //采用key -value形式
        bundle.putString(WordDetailFragment.ARG_ID, id);
        //传递
        fragment.setArguments(bundle);
        //2,获取FragmentManager实例
        FragmentManager fragmentManager = getFragmentManager();
        //3,开启一个事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //4,向容器内添加或者替换碎片
        fragmentTransaction.replace(R.id.worddetail, fragment);
        //5,提交事务
        fragmentTransaction.commit();
    }


    /**
     * 当用户在单词列表Fragment中单击某个单词时回调此函数
     * 判断如果横屏的话，则需要在右侧单词详细Fragment中显示
     */
    @Override
    public void onWordItemClick(String id) {

        if (isLand()) {//横屏的话则在右侧的WordDetailFragment中显示单词详细信息
            ChangeWordDetailFragment(id);
        } else {
            Intent intent = new Intent(MainActivity.this, WordDetailActivity.class);
            intent.putExtra(WordDetailFragment.ARG_ID, id);
            startActivity(intent);
        }

    }

    @Override
    public void onDeleteDialog(String strId) {
        DeleteDialog(strId);
    }

    @Override
    public void onUpdateDialog(String strId) {
        WordsDB wordsDB = WordsDB.getWordsDB();
        if (wordsDB != null && strId != null) {
            Words.WordDescription item = wordsDB.getSingleWord(strId);
            if (item != null) {
                UpdateDialog(strId, item.word, item.meaning, item.sample);
            }

        }

    }
}
