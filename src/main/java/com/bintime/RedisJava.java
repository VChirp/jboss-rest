package com.bintime;

/**
 * Created by Chirp on 09.06.2017.
 */

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
        mapProd2.put("offerIds", "3,4");
        jedis.hmset("pr:2", mapProd2);
        System.out.println(jedis.hgetAll("pr:2"));

        Map<String, String> mapProd1 = new HashMap<>();
        mapProd1.put("mpn", "SA-12KP");
        mapProd1.put("status", "ok");
        mapProd1.put("id", "11");
        mapProd1.put("offerIds", "1,2");
        jedis.hmset("pr:1", mapProd1);
        System.out.println(jedis.hgetAll("pr:1"));

        Map<String, String> mapOf1 = new HashMap<>();
        mapOf1.put("id", "1");
        mapOf1.put("price", "126.8");
        mapOf1.put("stock", "0");
        jedis.hmset("of:1", mapOf1);

        Map<String, String> mapOf2 = new HashMap<>();
        mapOf2.put("id", "2");
        mapOf2.put("price", "126.8");
        mapOf2.put("stock", "0");
        jedis.hmset("of:2", mapOf2);

        Map<String, String> mapOf3 = new HashMap<>();
        mapOf3.put("id", "3");
        mapOf3.put("price", "126.8");
        mapOf3.put("stock", "0");
        jedis.hmset("of:3", mapOf3);

        Map<String, String> mapOf4 = new HashMap<>();
        mapOf4.put("id", "4");
        mapOf4.put("price", "126.8");
        mapOf4.put("stock", "0");
        jedis.hmset("of:4", mapOf4);


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
