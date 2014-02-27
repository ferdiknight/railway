/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blueferdi.concurrent.railway.first;

/**
 *
 * @author tongyin.ty
 */
public class Man {

    private final int i;

    private String name;

    public Man(int i)
    {
        this.i = i;
        this.name = Integer.toString(i);
    }

    public Man(String name){
        this.i = 0;
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return Integer.toString(i) + ":" + this.name;
    }

}
