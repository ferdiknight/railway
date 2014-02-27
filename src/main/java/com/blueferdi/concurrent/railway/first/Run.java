/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blueferdi.concurrent.railway.first;

import java.util.Arrays;

/**
 *
 * @author tongyin.ty
 */
public class Run {
    public static void main(String[] args){

        Man[] location1 = new Man[10];
        Man[] location2 = new Man[10];
        Railway r1 = new Railway();
        Station in = new Station(0);
        Station out = new Station(1);

        for(int i=0;i<10;i++){
            location1[i] = new Man(i);
        }

        System.out.println("begin......");
        System.out.println(Arrays.toString(location1) + " @ location1");
        System.out.println(Arrays.toString(location2) + " @ location2");
        System.out.println();

        for(int i=0;i<location1.length;i++){
            in.enter(location1[i]);
            location1[i] = null;
        }

        Train t = r1.waitForTrain(in.getStationNo());

        Man m = null;

        while((m=in.prepareGetOn()) != null){
            t.getOn(m);
        }

        r1.send();

        t = r1.waitForTrain(out.getStationNo());

        while((m=t.takeOff()) != null){
            out.takeOff(m);
        }

        int index = 0;
        while((m=out.exit()) != null){
            location2[index++] = m;
        }

        System.out.println();
        System.out.println("end......");
        System.out.println(Arrays.toString(location1) + " @ location1");
        System.out.println(Arrays.toString(location2) + " @ location2");


    }
}
