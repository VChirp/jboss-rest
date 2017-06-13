package com.bintime;

import lombok.Data;


@Data
class Offer {
    private int id;
    private double price;
    private int stock;

    boolean isAvailable(Integer availability) {
        if (availability == null) return true;
        if (availability == 0) {
            return true;
        } else if (availability == 1) {
            if (this.stock == 1 || this.stock == 2) {
                return true;
            }
        } else if (availability == 2) {
            if (this.stock == 2) {
                return true;
            }
        } else
            throw new RuntimeException("Wrong availability");
        return false;
    }
}
