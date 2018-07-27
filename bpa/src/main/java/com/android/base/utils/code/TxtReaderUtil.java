package com.android.base.utils.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TxtReaderUtil {

    /**
     * 通过一个InputStream获取内容
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016/10/29 2:32
     * <br> UpdateTime: 2016/10/29 2:32
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param inputStream
     *         InputStream
     */
    public static String getString(InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 通过txt文件的路径获取其内容
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016/10/29 2:32
     * <br> UpdateTime: 2016/10/29 2:32
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param netUrl
     *         "http://code.taobao.org/svn/Amos/publictxt/public.txt"
     */
    public static String getStringNet(String netUrl) {
        InputStream inputStream = null;
        try {
            URL url = new URL(netUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            inputStream = con.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getString(inputStream);
    }

    /**
     * 通过txt文件的路径获取其内容
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016/10/29 2:32
     * <br> UpdateTime: 2016/10/29 2:32
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param filepath
     *         本地文件路径
     */
    public static String getStringLocal(String filepath) {
        File file = new File(filepath);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return getString(fileInputStream);
    }
}