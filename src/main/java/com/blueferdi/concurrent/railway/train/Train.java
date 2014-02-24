/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueferdi.concurrent.railway.train;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author tongyin.ty
 */
public class Train {

    private final int CAPACITY;

     private final AtomicInteger stationNo = new AtomicInteger();

    private final long[] items;

    private int index = 0;

    public Train(int capacity) {
        this.CAPACITY = capacity;
        items = new long[CAPACITY];
    }

    public void next(){
        stationNo.getAndIncrement();
    }

    public int getStationNo(){
        return stationNo.get();
    }

    /**
     * return capacity of this train
     * @return
     */
    public int capacity() {
        return this.CAPACITY;
    }

    /**
     * put all items to train,station should check the number of the items
     *
     * @param items
     */
    public void put(long[] items) {
        System.arraycopy(items, 0, this.items, 0, items.length);
        index += items.length;
    }

    /**
     * return how many items will be taked off
     * @param station
     * @return
     */
    public int takeOffNo(int station) {
        return index;
    }

    /**
     * take all the items to input array
     * @param station
     * @param items
     */
    public void takeOff(int station, long[] items) {
        System.arraycopy(this.items, 0, items, 0, index);
        index = 0;
    }

}
