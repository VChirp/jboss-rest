package com.bintime;

import lombok.Data;

import java.util.List;

@Data
class Product {
    private int id;
    private String mpn;
    private String status;
    private List<Offer> array;
}
