package cn.edu.bistu.cs.xiong.wordsbook;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import cn.edu.bistu.cs.xiong.wordsbook.util.HttpUtil;
import cn.edu.bistu.cs.xiong.wordsbook.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchOnline extends AppCompatActivity {

    private WordItemFragment mWordItemFragment;
    private String query;
    private TextView result;
    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_online);

        Button translate_Eenglish = (Button)findViewById(R.id.translate_English);
        Button translate_Chinese = (Button)findViewById(R.id.translate_Chinese);

        input = (EditText)findViewById(R.id.input);
        result=(TextView)findViewById(R.id.result);


        translate_Eenglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                query = input.getText().toString();
                requestEnglishWords(query);
            }
        });

        translate_Chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                query = input.getText().toString();
                requestChineseWords(query);
            }
        });



    }

    public void requestEnglishWords(String query) {
        String sign = MD5.md5("0d8450467b93d740" + query + 2 + "GlStxQJYhtPBSaATw1BQhnl3xXau75ER");
        String wordsUrl = "http://openapi.youdao.com/api?q=" + query + "&from=EN&to=zh_CHS&appKey=0d8450467b93d740&salt=2&sign=" + sign;
        HttpUtil.sendOkHttpRequest(wordsUrl, new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseText = response.body().string();
                //Toast.makeText(MainActivity.this, responseText, Toast.LENGTH_SHORT).show();
                final Words words = Utility.handleWordsResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (words != null && words.ErrorCode.equals("0")) {
                            showWordsInfo(words);
                        } else {
                            Toast.makeText(SearchOnline.this,"获取信息失败", Toast.LENGTH_SHORT).show();
                            showJson(responseText);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SearchOnline.this, "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void requestChineseWords(String query) {
        String sign = MD5.md5("0d8450467b93d740" + query + 2 + "GlStxQJYhtPBSaATw1BQhnl3xXau75ER");
        String wordsUrl = "http://openapi.youdao.com/api?q=" + query + "&from=zh_CHS&to=EN&appKey=0d8450467b93d740&salt=2&sign=" + sign;
        HttpUtil.sendOkHttpRequest(wordsUrl, new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseText = response.body().string();
                //Toast.makeText(MainActivity.this, responseText, Toast.LENGTH_SHORT).show();
                final Words words = Utility.handleWordsResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (words != null && words.ErrorCode.equals("0")) {
                            showWordsInfo(words);
                        } else {
                            Toast.makeText(SearchOnline.this,"获取信息失败", Toast.LENGTH_SHORT).show();
                            showJson(responseText);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SearchOnline.this, "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showWordsInfo(Words  words) {
        String us_phonetic = words.us_phonetic;
        if(!us_phonetic.isEmpty())
            result.setText("/" + us_phonetic + "/" + "\n");
        for (int i=0; i<words.explains.size();i++) {
            result.append(words.explains.get(i) + "\n");
        }
        for (int i=0; i<words.sentence.size();i++) {
            result.append(words.sentence.get(i) + "\n");
        }
    }

    private void showJson(String s) {
        result.setText(s);
    }

    /**
     * 更新单词列表
     */
    private void RefreshWordItemFragment() {
        /**
         * 在Activity中我们可以调用FragmentManager的findFragmentById()方法来获取一个ragment实例
         * 进而调用Fragment的方法
         * 以实现Activity与Fragment之间的通信
         */
        mWordItemFragment = (WordItemFragment) getFragmentManager().findFragmentById(R.id.wordslist);
        mWordItemFragment.refreshWordsList();
    }
}
