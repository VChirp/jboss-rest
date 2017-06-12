package com.bintime;

import lombok.Data;

import java.util.List;

/**
 * Created by Chirp on 08.06.2017.
 */
@Data
public class Product {
    private int id;
    private String mpn;
    private List<Offer> array;
    private String status;
}
