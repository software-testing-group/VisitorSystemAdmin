package beike.visitorsystem.utils;

import com.alibaba.fastjson.JSONArray;

import java.util.*;
import java.util.stream.Stream;

public class CountProvinces {


    private Map<String,String> provinces = new HashMap<String,String>();

    public CountProvinces(){
        provinces.put("11","\u5317\u4eac");
        provinces.put("12","\u5929\u6d25");
        provinces.put("13","\u6cb3\u5317");
        provinces.put("14","\u5c71\u897f");
        provinces.put("15","\u5185\u8499\u53e4");
        provinces.put("21","\u8fbd\u5b81");
        provinces.put("22","\u5409\u6797");
        provinces.put("23","\u9ed1\u9f99\u6c5f");
        provinces.put("31","\u4e0a\u6d77");
        provinces.put("32","\u6c5f\u82cf");
        provinces.put("33","\u6d59\u6c5f");
        provinces.put("34","\u5b89\u5fbd");
        provinces.put("35","\u798f\u5efa");
        provinces.put("36","\u6c5f\u897f");
        provinces.put("37","\u5c71\u4e1c");
        provinces.put("41","\u6cb3\u5357");
        provinces.put("42","\u6e56\u5317");
        provinces.put("43","\u6e56\u5357");
        provinces.put("44","\u5e7f\u4e1c");
        provinces.put("45","\u5e7f\u897f");
        provinces.put("46","\u6d77\u5357");
        provinces.put("50","\u91cd\u5e86");
        provinces.put("51","\u56db\u5ddd");
        provinces.put("52","\u8d35\u5dde");
        provinces.put("53","\u4e91\u5357");
        provinces.put("54","\u897f\u85cf");
        provinces.put("61","\u9655\u897f");
        provinces.put("62","\u7518\u8083");
        provinces.put("63","\u9752\u6d77");
        provinces.put("64","\u5b81\u590f");
        provinces.put("65","\u65b0\u7586");
        provinces.put("71","\u53f0\u6e7e");
        provinces.put("81","\u9999\u6e2f");
        provinces.put("91","\u6fb3\u95e8");
    }

    public Map<String,Integer> CountProvincesNumber(List<String> identityCard){
        Map<String,Integer> number = new HashMap<String,Integer>();
        for(int i = 0;i<identityCard.size();i++){
            if(identityCard.get(i).length()==18){
                String key1 = identityCard.get(i).substring(0,2);
                String key = key1+"0000";
                if(provinces.containsKey(key1)){
                    if(number.containsKey(key)){
                        int value = number.get(key);
                        value = value + 1;
                        number.put(key,value);
                    }else{
                        number.put(key,1);
                    }
                }
            }
        }

        return number;
    }

    public Map<String,Integer> CountProvincesNameNumber(List<String> identityCard){
        Map<String,Integer> number = new HashMap<String,Integer>();
        for(int i = 0;i<identityCard.size();i++){
            if(identityCard.get(i).length()==18){
                String key = identityCard.get(i).substring(0,2);
                if(provinces.containsKey(key)){
                    String name = provinces.get(key);
                    if(number.containsKey(name)){
                        int value = number.get(name);
                        value = value+1;
                        number.put(name,value);
                    }else{
                        number.put(name,1);
                    }
                }
            }
        }
        return number;
    }


}
