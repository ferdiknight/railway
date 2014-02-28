/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blueferdi.concurrent.railway.fourth;

import com.blueferdi.concurrent.railway.first.Man;

/**
 *
 * @author tongyin.ty
 */
public class Train {
    private final Man[] box;

    private final int CAPACITY;

    private int index = 0;

    private int no;

    public Train(int CAPACITY){
        this.CAPACITY = CAPACITY;
        this.box = new Man[this.CAPACITY];
    }

    public void setNo(int no){
        this.no = no;
    }

    public int getNo(){
        return no;
    }

    public void getOn(Man man){
        box[index] = man;
        index++;
    }

    public Man takeOff(){
        index--;
        return box[index];
    }
}
