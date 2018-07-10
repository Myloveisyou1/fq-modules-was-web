package com.fq.modules.was.web.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

public class HttpClientUtils {


    /**
     * post方式
     * 目前主用在.net
     *
     * @param url
     * @param map
     * @return
     */
    public static String doPost(String url, Map<String,Object> map){
        if (CommonUtil.isEmpty(map)){
            map=new HashMap<>();
        }
        String charset="UTF-8";
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,Object> elem = (Map.Entry<String, Object>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()+""));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            // 关闭连接,释放资源
            try {
                if(!CommonUtil.isEmpty(httpClient)){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }




    public static void main(String[] args) {
        Map<String,Object> param= new HashMap<>(1);
        param.put("password", "BTC");
        param.put("account", "2N4Dg92NwH4Lp1CWuU9m6ehP4TXuJ6f7Xu3");
        System.out.println(doPost("http://10.45.0.43:89/Coin/testpostparameter",param));
    }


}
