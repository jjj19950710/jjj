package cn.edu.bistu.cs.xiong.wordsbook.util;



import org.json.JSONArray;
import org.json.JSONObject;

import cn.edu.bistu.cs.xiong.wordsbook.Words;


public class Utility {

    /**
     * 将返回的JSON数据解析成Words实体类
     */
    public static Words handleWordsResponse(String response) {
        try {

            JSONObject jsonObject = new JSONObject(response);
            Words words = new Words();
            words.ErrorCode=jsonObject.getString("errorCode");
            if (jsonObject.has("basic")) {
                if(jsonObject.getJSONObject("basic").has("us-phonetic"))
                    words.us_phonetic = jsonObject.getJSONObject("basic").getString("us-phonetic");
                else
                    words.us_phonetic = "";
                JSONArray explains = jsonObject.getJSONObject("basic").getJSONArray("explains");
                for (int i = 0; i < explains.length(); i++) {
                    words.explains.add((String)explains.get(i));
                }
                words.sentence.add("");
            }
            else {
                words.us_phonetic = "";
                words.explains.add("");
                JSONArray translation = jsonObject.getJSONArray("translation");
                for (int i = 0; i < translation.length(); i++) {
                    words.sentence.add((String)translation.get(i));
                }
            }
            return words;
        } catch (Exception e) {
            e.printStackTrace();
          }
        return null;
    }

}
