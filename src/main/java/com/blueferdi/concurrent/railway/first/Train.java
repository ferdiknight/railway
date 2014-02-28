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
public class Train {

    private final LinkedList<Man> queue = new LinkedList<Man>();

    private int no;

    public void setNo(int no){
        this.no = no;
    }

    public int getNo(){
        return no;
    }

    public void getOn(Man man){
        queue.addLast(man);
    }

    public Man takeOff(){
        return queue.isEmpty()?null:queue.removeFirst();
    }


}
