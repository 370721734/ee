package com.tangyx.video.utils;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UrlPath {
    /**
     * get方法的文件下载
     * <p>
     * 特别说明 android中的progressBar是google唯一的做了处理的可以在子线程中更新UI的控件
     *
     * @param path
     */
    private void httpDown(final String path) {
        new Thread() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection connection;
                try {
                    //统一资源
                    url = new URL(path);
                    //打开链接
                    connection = (HttpURLConnection) url.openConnection();
                    //设置链接超时
                    connection.setConnectTimeout(4000);
                    //设置允许得到服务器的输入流,默认为true可以不用设置
                    connection.setDoInput(true);
                    //设置允许向服务器写入数据，一般get方法不会设置，大多用在post方法，默认为false
                    connection.setDoOutput(true);//此处只是为了方法说明
                    //设置请求方法
                    connection.setRequestMethod("GET");
                    //设置请求的字符编码
                    connection.setRequestProperty("Charset", "utf-8");
                    //设置connection打开链接资源
                    connection.connect();
                    //得到链接地址中的file路径
                    String urlFilePath = connection.getURL().getFile();
                    //得到url地址总文件名 file的separatorChar参数表示文件分离符
                    String fileName = urlFilePath.substring(urlFilePath.lastIndexOf(File.separatorChar) + 1);
                    //创建一个文件对象用于存储下载的文件 此次的getFilesDir()方法只有在继承至Context类的类中
                    // 可以直接调用其他类中必须通过Context对象才能调用，得到的是内部存储中此应用包名下的文件路径
                    //如果使用外部存储的话需要添加文件读写权限，5.0以上的系统需要动态获取权限 此处不在不做过多说明。
                    File file = new File(getFilesDir(), fileName);
                    //创建一个文件输出流
                    FileOutputStream outputStream = new FileOutputStream(file);
                    //得到链接的响应码 200为成功
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        //得到服务器响应的输入流
                        InputStream inputStream = connection.getInputStream();
                        //获取请求的内容总长度
                        int contentLength = connection.getContentLength();
                        //设置progressBar的Max
//                        mPb.setMax(contentLength);
                        //创建缓冲输入流对象，相对于inputStream效率要高一些
                        BufferedInputStream bfi = new BufferedInputStream(inputStream);
                        //此处的len表示每次循环读取的内容长度
                        int len;
                        //已经读取的总长度
                        int totle = 0;
                        //bytes是用于存储每次读取出来的内容
                        byte[] bytes = new byte[1024];
                        while ((len = bfi.read(bytes)) != -1) {
                            //每次读取完了都将len累加在totle里
                            totle += len;
                            //每次读取的都更新一次progressBar
//                            mPb.setProgress(totle);
                            //通过文件输出流写入从服务器中读取的数据
                            outputStream.write(bytes, 0, len);
                        }
                        //关闭打开的流对象
                        outputStream.close();
                        inputStream.close();
                        bfi.close();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(this, "下载完成!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static final String OUTPUT_DIR_NAME = "TXUGC";
    String path;
    private String getFilesDir() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
        String time = sdf.format(new Date(currentTime));
        String outputDir = Environment.getExternalStorageDirectory() + File.separator + OUTPUT_DIR_NAME;
        File outputFolder = new File(outputDir);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
        String tempOutputPath = outputDir + File.separator + "TXUGC_" + time + ".mp4";
        return tempOutputPath;
    }
}
