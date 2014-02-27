/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blueferdi.concurrent.railway.first;

import java.util.LinkedList;

/**
 *
 * @author tongyin.ty
 */
public class Station {

    private final int stationNo;

    private final LinkedList<Man> enterQueue = new LinkedList<Man>();

    private final LinkedList<Man> exitQueue = new LinkedList<Man>();

    public Station(int stationNo){
        this.stationNo = stationNo;
    }

    public final int getStationNo(){
        return this.stationNo;
    }

    public void enter(Man man){
        enterQueue.addLast(man);
    }

    public Man prepareGetOn(){
        return enterQueue.isEmpty()?null:enterQueue.removeFirst();
    }

    public void takeOff(Man man){
        exitQueue.addLast(man);
    }

    public Man exit(){
        return exitQueue.isEmpty()?null:exitQueue.removeFirst();
    }


}
