/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueferdi.concurrent.railway.fourth;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author tongyin.ty
 */
public class MultiTrainRailway {

    private final int stationCount = 2;
    private final Train[] trains;
    private final AtomicInteger[] stationNos;

    private int index = 0;

    public MultiTrainRailway(int capacity) {
        trains = new Train[capacity];
        stationNos = new AtomicInteger[capacity];
    }

    public Train waitForTrain(int stationNo,int trainNo) {

        while (stationNos[trainNo].get() % stationCount != stationNo){
            Thread.yield();
        }

        return trains[trainNo];

    }

    public Train waitForTrain(int stationNo) {

        while (true) {
            for (int i = 0; i < index; i++) {
                if (stationNos[i].get() % stationCount == stationNo) {
                    return trains[i];
                }
            }
            Thread.yield();
        }

    }

    public void register(Train train) {
        if (index < trains.length) {
            trains[index] = train;
            train.setNo(index);
            stationNos[index] = new AtomicInteger();
            index++;
        }
    }

    public void send(int trainNo) {
        if (trainNo < index) {
            stationNos[trainNo].getAndIncrement();
        }
    }

}
