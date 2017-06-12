package com.bintime;

import lombok.Data;

import java.util.List;

@Data
class Product {
    private int id;
    private String mpn;
    private List<Offer> array;
    private String status;
}
