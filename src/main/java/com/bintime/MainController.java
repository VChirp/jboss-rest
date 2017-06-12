package com.bintime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    @ResponseBody
    @RequestMapping(value = "/getprice", method = RequestMethod.GET)
    public Product binRange(@RequestParam String mpn, @RequestParam Integer availability, @RequestParam Integer pricesort) {//TODO Make availability and price optional

        Jedis jedis = new Jedis("localhost");
        System.out.println("Connection to server sucessfully");
        //check whether server is running or not

        System.out.println("Server is running: " + jedis.ping());
        Set<String> prSet = jedis.keys("pr:*");
        Iterator<String> prIt = prSet.iterator();
        Product prod = new Product();
        while (prIt.hasNext()) {
            String prKey = prIt.next();
            if (jedis.hmget(prKey, "mpn").get(0).equals(mpn)) {
                List<Offer> list = new LinkedList<>();
                prod.setId(Integer.parseInt(jedis.hmget(prKey, "id").get(0)));
                prod.setStatus(jedis.hmget(prKey, "status").get(0));
                prod.setMpn(jedis.hmget(prKey, "mpn").get(0));
                String[] ofIds = jedis.hgetAll(prKey).get("offerIds").split(",");
                for (String i : ofIds) {
                    Set<String> ofSet = jedis.keys("of:*");
                    Iterator<String> ofIt = ofSet.iterator();
                    while (ofIt.hasNext()) {
                        String ofKey = ofIt.next();
                        if (jedis.hmget(ofKey, "id").get(0).equals(i)) {
                            System.out.println(jedis.hgetAll(ofKey));

                            list.add(new Offer(Integer.parseInt(jedis.hmget(ofKey, "id").get(0)),
                                    Double.parseDouble(jedis.hmget(ofKey, "price").get(0)),
                                    Integer.parseInt(jedis.hmget(ofKey, "stock").get(0))));

                        }
                    }
                }
                prod.setArray(list);
                break;
            } else {
                System.out.println("Didn`t found prod");
            }
        }
//        Offer of = new Offer();
//        of.setId(42);
//        of.setPrice(112.12);
//        of.setStock(3);
//
//        Offer of2 = new Offer();
//        of2.setId(44);
//        of2.setPrice(112.12);
//        of2.setStock(2);


        //TODO Filter by availability
        //TODO Filter by pricesort


//        Product prod = new Product();
//        prod.setMpn("SA-12KP");
//        prod.setStatus("Ok");
//        prod.setId(43);
//        prod.setArray(list);
        return prod;
    }
}
