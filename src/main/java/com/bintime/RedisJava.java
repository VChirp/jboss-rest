package com.bintime;


import redis.clients.jedis.Jedis;

import java.util.*;

public class RedisJava {
    static Jedis jedis = new Jedis("localhost");

    public static void main(String[] args) {
        //Connecting to Redis server on localhost
        System.out.println("Connection to server sucessfully");
        jedis.flushAll();


        RedisJava rj = new RedisJava();
        rj.putProduct("SA-11", "ok", "11", "1,2,3");
        rj.putProduct("SA-12", "ok", "12", "4,5,6");
        rj.putProduct("SA-13", "ok", "13", "7,8");

        rj.putOffer("1", "124.2", "0");
        rj.putOffer("2", "134.2", "1");
        rj.putOffer("3", "144.2", "2");
        rj.putOffer("4", "154.2", "1");
        rj.putOffer("5", "164.2", "0");
        rj.putOffer("6", "174.2", "2");
        rj.putOffer("7", "184.2", "1");
        rj.putOffer("8", "184.3", "0");

    }

    private void putProduct(String mpn, String status, String id, String offerIds) {
        Map<String, String> mapProd1 = new HashMap<>();
        mapProd1.put("mpn", mpn);
        mapProd1.put("status", status);
        mapProd1.put("id", id);
        mapProd1.put("offerIds", offerIds);
        jedis.hmset("pr:" + mpn, mapProd1);
        System.out.println(jedis.hgetAll("pr:" + mpn));
    }

    private void putOffer(String ofId, String price, String stock) {
        Map<String, String> mapOf1 = new HashMap<>();
        mapOf1.put("id", ofId);
        mapOf1.put("price", price);
        mapOf1.put("stock", stock);
        jedis.hmset("of:" + ofId, mapOf1);
    }

}
