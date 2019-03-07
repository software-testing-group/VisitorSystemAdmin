package beike.visitorsystem.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RoutePlanning {
    static String key = "PKMBZ-2LS3J-S7VFZ-FSDTR-DSZRZ-YDFEH";
public String getPolyline(double fromlat,double fromlng,double tolat,double tolng) throws Exception{
    String url="https://apis.map.qq.com/ws/direction/v1/walking/?from="+fromlat+","+fromlng+"&to="+tolat+","+tolng+"&key="+key;
    URL serverUrl = new URL(url);
    HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    //必须设置false，否则会自动redirect到重定向后的地址
    conn.setInstanceFollowRedirects(false);
    conn.connect();
    String result = getReturn(conn);
    return result;
}
    /*请求url获取返回的内容*/
    public static String getReturn(HttpURLConnection connection) throws IOException {
        StringBuffer buffer = new StringBuffer();
        //将返回的输入流转换成字符串
        try(InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);){
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            String result = buffer.toString();
            return result;
        }
    }
    public static void main(String[] args){

        try {
            System.out.println(new RoutePlanning().getPolyline(24.43666,118.09534,24.43637,118.09873));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
