package com.bintime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.*;

@Controller
public class MainController {
    @Autowired
    private Jedis jedis;

    @ResponseBody
    @RequestMapping(value = "/getprice", method = RequestMethod.GET)
    public Product getPrice(@RequestParam String mpn,
                            @RequestParam(required = false) Integer availability,
                            @RequestParam(required = false) Integer pricesort) {

        System.out.println("Server is running: " + jedis.ping());
        Map<String, String> productMap = jedis.hgetAll("pr:" + mpn);
        if (productMap.isEmpty())
            throw new RuntimeException("No such key for this mpn");

        //TODO Change prod and of to normal variant
        Product product = getProduct(productMap);
        if (!mpn.equals(product.getMpn()))
            throw new RuntimeException("mpn doesn't equal mpnValue");
        List<Offer> offers = offersFor(productMap);
        Iterator<Offer> it = offers.iterator();
        while (it.hasNext()) {
            Offer offer = it.next();
            if (!offer.isAvailable(availability)) {
                it.remove();
            }
        }
        sortByPriceSort(pricesort, offers);
        product.setArray(offers);
        return product;
    }

    private Product getProduct(Map<String, String> productMap) {
        Product product = new Product();
        int id = Integer.parseInt(productMap.get("id"));
        String mpnValue = productMap.get("mpn");
        String status = productMap.get("status");
        product.setMpn(mpnValue);
        product.setId(id);
        product.setStatus(status);
        return product;
    }

    private List<Offer> offersFor(Map<String, String> productMap) {

        String[] offerIds = productMap.get("offerIds").split(",");
        List<Offer> offersList = new LinkedList<>();
        for (String offerId : offerIds) {
            Map<String, String> offerMap = jedis.hgetAll("of:" + offerId);
            if (offerMap.isEmpty()) {
                throw new RuntimeException("Can't find offer id: " + offerId);
            }
            Offer offer = getOffer(offerMap);
            if (offer.getId() != Integer.parseInt(offerId)) {
                throw new RuntimeException("Ids don't match");
            }
            offersList.add(offer);
        }
        return offersList;
    }

    private Offer getOffer(Map<String, String> offerMap) {
        Offer offer = new Offer();
        offer.setId(Integer.parseInt(offerMap.get("id")));
        offer.setStock(Integer.parseInt(offerMap.get("stock")));
        offer.setPrice(Double.parseDouble(offerMap.get("price")));
        return offer;
    }

    private void sortByPriceSort(Integer pricesort, List<Offer> list) {
        if (pricesort == null) return;
        if (pricesort == 1) {
            Collections.sort(list, new Comparator<Offer>() {
                @Override
                public int compare(Offer o1, Offer o2) {
                    return Double.compare(o1.getPrice(), o2.getPrice());
                }
            });

        } else if (pricesort == 2) {
            Collections.sort(list, new Comparator<Offer>() {
                @Override
                public int compare(Offer o1, Offer o2) {
                    return Double.compare(o2.getPrice(), o1.getPrice());
                }
            });
        } else throw new RuntimeException("Wrong pricesort");
    }

//        private void validation(String offerIds){
//        if (offerIds == null){
//            throw new RuntimeException("offerIds aren`t exist");
//        }else if(offerIds.trim().length() == 0){
//            throw new RuntimeException("offerIds are empty");
//        }else if(offerIds.contains()){
//            throw new RuntimeException("Wrong symbol");
//        }else if (){
//
//        }
//    }

}
