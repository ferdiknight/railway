/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueferdi.concurrent.railway;

import com.blueferdi.concurrent.railway.train.Train;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author tongyin.ty
 */
public class Railway {

    private final int capacity;
    private final int stationCount = 2;
    private int count = 0;
    private final Train[] trains;
    private final AtomicInteger[] stationNo;

    public Railway(int capacity){
        this.capacity = capacity;
        trains = new Train[this.capacity];
        stationNo = new AtomicInteger[this.capacity];
    }

    public Train waitForIn(int stationNo,int trainNo) {

        while (this.stationNo[trainNo].get() % stationCount != stationNo) {
            Thread.yield();
        }
        return trains[trainNo];
    }

    public int registerTrain(Train train) {
        this.trains[count] = train;
        stationNo[count] = new AtomicInteger();
        return count++;
    }

    public void send(int trainNo) {
        stationNo[trainNo].getAndIncrement();
    }

}
