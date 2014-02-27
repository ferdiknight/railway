/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blueferdi.concurrent.railway;

import com.blueferdi.concurrent.railway.train.Train;

/**
 *
 * @author tongyin.ty
 */
public class TestMultiTrainsOnOneRailwayWithMPSC {

    public void testRailway(){

        final int trainNumbers = 8;
        final int capacity = 2 * 1024;
        final Railway r1 = new Railway(trainNumbers);

        for(int i=0;i<trainNumbers;i++){
            r1.registerTrain(new Train(capacity));
        }

        new Thread(){

            final long[] station = new long[capacity];

            @Override
            public void run(){
                int trainIndex = 0;
                while(true){
                    final int trainNo = trainIndex % trainNumbers;
                    Train train = r1.waitForIn(1,trainNo);
                    train.takeOff(1, station);
                    r1.send(trainNo);
                    trainIndex++;
                }
            }

        }.start();



    }


}
