package com.bintime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.*;

@Controller
public class MainController {

    @ResponseBody
    @RequestMapping(value = "/getprice", method = RequestMethod.GET)
    public Product binRange(@RequestParam String mpn, @RequestParam Integer availability, @RequestParam Integer pricesort) {//TODO Make availability and price optional

        Jedis jedis = new Jedis("localhost");

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
                    for (String ofKey : ofSet) {
                        if (jedis.hmget(ofKey, "id").get(0).equals(i)) {
                            Offer offer = new Offer();
                            //Filter by availability
                            if (availability == 0) {
                                offer.setId(Integer.parseInt(jedis.hmget(ofKey, "id").get(0)));
                                offer.setStock(Integer.parseInt(jedis.hmget(ofKey, "stock").get(0)));
                                offer.setPrice(Double.parseDouble(jedis.hmget(ofKey, "price").get(0)));
                                list.add(offer);
                            } else if (availability == 1) {
                                if (jedis.hmget(ofKey, "stock").get(0).equals("1") || jedis.hmget(ofKey, "stock").get(0).equals("2")) {
                                    offer.setId(Integer.parseInt(jedis.hmget(ofKey, "id").get(0)));
                                    offer.setStock(Integer.parseInt(jedis.hmget(ofKey, "stock").get(0)));
                                    offer.setPrice(Double.parseDouble(jedis.hmget(ofKey, "price").get(0)));
                                    list.add(offer);
                                }
                            } else if (availability == 2) {
                                if (jedis.hmget(ofKey, "stock").get(0).equals("2")) {
                                    offer.setId(Integer.parseInt(jedis.hmget(ofKey, "id").get(0)));
                                    offer.setStock(Integer.parseInt(jedis.hmget(ofKey, "stock").get(0)));
                                    offer.setPrice(Double.parseDouble(jedis.hmget(ofKey, "price").get(0)));
                                    list.add(offer);
                                }

                            }
                        }
                    }
                }
                // Filter by pricesort
                if (pricesort == 0) {
                    prod.setArray(list);
                } else if (pricesort == 1) {
                    Collections.sort(list, new Comparator<Offer>() {
                        @Override
                        public int compare(Offer o1, Offer o2) {
                            return Double.compare(o1.getPrice(), o2.getPrice());
                        }
                    });
                    prod.setArray(list);
                } else if (pricesort == 2) {
                    Collections.sort(list, new Comparator<Offer>() {
                        @Override
                        public int compare(Offer o1, Offer o2) {
                            return Double.compare(o2.getPrice(), o1.getPrice());
                        }
                    });
                    prod.setArray(list);
                }
                break;
            } else {
                System.out.println("Didn`t found prod");
            }
        }

        return prod;
    }
}
