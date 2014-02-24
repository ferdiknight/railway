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

    private Train train;

    private final AtomicInteger stationNo = new AtomicInteger();

    public Train waitForIn(int stationNo){
        while(this.stationNo.get() % 2 != stationNo){
            Thread.yield();
        }

        return train;
    }

    public void setTrain(Train train){
        this.train = train;
    }

    public void send(){
        stationNo.getAndIncrement();
    }


}
