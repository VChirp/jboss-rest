package com.bintime;

import lombok.Data;


@Data
public class Offer {
   public Offer(int setId,double setPrice, int setStock){
       int id=setId;
       double price=setPrice;
       int stock=setStock;
    }
}
