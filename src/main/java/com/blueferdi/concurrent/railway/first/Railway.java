/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blueferdi.concurrent.railway.first;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author tongyin.ty
 */
public class Railway {

    private final Train t = new Train();

    private final int stationCount = 2;

    private AtomicInteger stationNo = new AtomicInteger();

    public Train waitForTrain(int stationNo){
        while(this.stationNo.get() % stationCount != stationNo){
            Thread.yield();
        }
            return t;

    }

    public void send(){
        stationNo.getAndIncrement();
    }

}
