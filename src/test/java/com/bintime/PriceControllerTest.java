package com.bintime;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

public class PriceControllerTest {

    @Mock
    private Jedis jedisMock;
    private PriceController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        controller = new PriceController(jedisMock);
    }

    @Test(expected = RuntimeException.class)
    public void mpnMandatory() throws Exception {
        controller.getPrice(null, null, null);
    }

    @Test
    public void nullOffersIdStringOK() throws Exception {
        HashMap<String, String> testProductMap = new HashMap<>();
        testProductMap.put("id", "11");
        testProductMap.put("status", "ok");
        testProductMap.put("mpn", "SA-11");
        when(jedisMock.hgetAll("pr:SA-11")).thenReturn(testProductMap);

        Product price = controller.getPrice("SA-11", null, null);

        Assert.assertNotNull(price);
    }

    @Test
    public void emptyOffersIdStringOK() throws Exception {
        HashMap<String, String> testProductMap = new HashMap<>();
        testProductMap.put("id", "11");
        testProductMap.put("status", "ok");
        testProductMap.put("mpn", "SA-11");
        testProductMap.put("offerIds", "");
        when(jedisMock.hgetAll("pr:SA-11")).thenReturn(testProductMap);

        Product price = controller.getPrice("SA-11", null, null);

        Assert.assertNotNull(price);
    }

    @Test(expected = RuntimeException.class)
    public void missingOffer() throws Exception {
        HashMap<String, String> testProductMap = new HashMap<>();
        testProductMap.put("id", "11");
        testProductMap.put("status", "ok");
        testProductMap.put("mpn", "SA-11");
        testProductMap.put("offerIds", "1");
        when(jedisMock.hgetAll("pr:SA-11")).thenReturn(testProductMap);

        Product price = controller.getPrice("SA-11", null, null);

        Assert.assertNotNull(price);
    }

    @Test
    public void offersIdStringWithOneOfferOK() throws Exception {
        HashMap<String, String> testProductMap = new HashMap<>();
        testProductMap.put("id", "11");
        testProductMap.put("status", "ok");
        testProductMap.put("mpn", "SA-11");
        testProductMap.put("offerIds", "1");
        when(jedisMock.hgetAll("pr:SA-11")).thenReturn(testProductMap);
        HashMap<String, String> testOfferMap = new HashMap<>();
        testOfferMap.put("id", "1");
        testOfferMap.put("price", "124.2");
        testOfferMap.put("stock", "0");
        when(jedisMock.hgetAll("of:1")).thenReturn(testOfferMap);

        Product price = controller.getPrice("SA-11", null, null);

        Assert.assertNotNull(price);
        Assert.assertEquals(price.getArray().get(0).getPrice(),124.2,0.00001);
    }

}
