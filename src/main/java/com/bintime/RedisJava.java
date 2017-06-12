package com.bintime;


import redis.clients.jedis.Jedis;

import java.util.*;

public class RedisJava {
    public static void main(String[] args) {
        //Connecting to Redis server on localhost
        Jedis jedis = new Jedis("localhost");
        System.out.println("Connection to server sucessfully");
        //check whether server is running or not
        System.out.println("Server is running: " + jedis.ping());
        //TODO Read about Map redis https://redis.io/topics/data-types

        Map<String, String> mapProd2 = new HashMap<>();
        mapProd2.put("mpn", "SA-14KP");
        mapProd2.put("status", "ok");
        mapProd2.put("id", "12");
        mapProd2.put("offerIds", "3,4,7");
        jedis.hmset("pr:2", mapProd2);
        System.out.println(jedis.hgetAll("pr:2"));

        Map<String, String> mapProd1 = new HashMap<>();
        mapProd1.put("mpn", "SA-12KP");
        mapProd1.put("status", "ok");
        mapProd1.put("id", "11");
        mapProd1.put("offerIds", "1,2,5");
        jedis.hmset("pr:1", mapProd1);
        System.out.println(jedis.hgetAll("pr:1"));

        Map<String, String> mapProd3 = new HashMap<>();
        mapProd3.put("mpn", "SA-16KP");
        mapProd3.put("status", "ok");
        mapProd3.put("id", "11");
        mapProd3.put("offerIds", "6,8");
        jedis.hmset("pr:3", mapProd3);
        System.out.println(jedis.hgetAll("pr:3"));

        Map<String, String> mapOf1 = new HashMap<>();
        mapOf1.put("id", "1");
        mapOf1.put("price", "146.8");
        mapOf1.put("stock", "0");
        jedis.hmset("of:1", mapOf1);

        Map<String, String> mapOf2 = new HashMap<>();
        mapOf2.put("id", "2");
        mapOf2.put("price", "1156.8");
        mapOf2.put("stock", "2");
        jedis.hmset("of:2", mapOf2);

        Map<String, String> mapOf3 = new HashMap<>();
        mapOf3.put("id", "3");
        mapOf3.put("price", "1146.8");
        mapOf3.put("stock", "1");
        jedis.hmset("of:3", mapOf3);

        Map<String, String> mapOf4 = new HashMap<>();
        mapOf4.put("id", "4");
        mapOf4.put("price", "16.8");
        mapOf4.put("stock", "1");
        jedis.hmset("of:4", mapOf4);

        Map<String, String> mapOf5 = new HashMap<>();
        mapOf5.put("id", "5");
        mapOf5.put("price", "142.8");
        mapOf5.put("stock", "0");
        jedis.hmset("of:5", mapOf5);

        Map<String, String> mapOf6 = new HashMap<>();
        mapOf6.put("id", "6");
        mapOf6.put("price", "152.3");
        mapOf6.put("stock", "1");
        jedis.hmset("of:6", mapOf6);

        Map<String, String> mapOf7 = new HashMap<>();
        mapOf7.put("id", "7");
        mapOf7.put("price", "152.8");
        mapOf7.put("stock", "2");
        jedis.hmset("of:7", mapOf7);

        Map<String, String> mapOf8 = new HashMap<>();
        mapOf8.put("id", "8");
        mapOf8.put("price", "162.2");
        mapOf8.put("stock", "2");
        jedis.hmset("of:8", mapOf8);


//        System.out.println(jedis.hmget("of:1", "id", "price", "stock"));

//        Set<String> prSet = jedis.keys("pr:*");
//        Iterator<String> prIt = prSet.iterator();
//        while (prIt.hasNext()) {
//            String key = prIt.next();
//            if (jedis.hmget(key, "mpn").get(0).equals(search)) {
//                String[] ofIds = jedis.hgetAll(key).get("offerIds").split(",");
//                for (String i : ofIds) {
//                    Set<String> ofSet = jedis.keys("of:*");
//                    Iterator<String> ofIt = ofSet.iterator();
//                    while (ofIt.hasNext()) {
//                        String j = ofIt.next();
//                        if (jedis.hmget(j, "id").get(0).equals(i)) {
//                            System.out.println(jedis.hgetAll(j));
//                        }
//                    }
//                }
//                break;
//            } else System.out.println("Didn`t found prod");
//        }
    }
}
