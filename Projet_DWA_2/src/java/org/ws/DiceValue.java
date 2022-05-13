/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import static java.util.Arrays.sort;

/**
 *
 * @author fred2
 */
public class DiceValue {
    
    private int d1;
    private int d2;
    private int d3;
    
    private String code;
    
    private int weight;
    
    private int token;
    
    public DiceValue(int dice1, int dice2, int dice3) {
        int[] values = {dice1, dice2, dice3};
        sort(values);
        for(int i = 0; i < values.length / 2; i++)
        {
            int temp = values[i];
            values[i] = values[values.length - i - 1];
            values[values.length - i - 1] = temp;
        }
        d1 = values[0];
        d2 = values[1];
        d3 = values[2];
        code = String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3);
        setWeight();
    }
    
    private void setWeight() {
        switch(code) {
            case "421":
                weight = 18;
                token = 10;
                break;
            case "111":
                weight = 17;
                token = 7;
                break;
            case "611":
                weight = 16;
                token = 6;
                break;
            case "666":
                weight = 15;
                token = 6;
                break;
            case "511":
                weight = 14;
                token = 5;
                break;
            case "555" :
                weight = 13;
                token = 5;
            case "411":
                weight = 12;
                token = 4;
                break;
            case "444":
                weight = 11;
                token = 4;
                break;
            case "311" :
                weight = 10;
                token = 3;
                break;
            case "333" :
                weight = 9;
                token = 3;
                break;
            case "211" :
                weight = 8;
                token = 2;
                break;
            case "222" :
                weight = 7;
                token = 2;
                break;
            case "654" :
                weight = 6;
                token = 2;
                break;
            case "543" :
                weight = 5;
                token = 2;
                break;
            case "432" :
                weight = 4;
                token = 2;
                break;
            case "321" :
                weight = 3;
                token = 2;
                break;
            case "221" :
                weight = 2;
                token = 2;
                break;
            default :
                weight = 1;
                token = 1;
                break;
        }
    }
    
    public int getD1() {
        return d1;
    }
    
    public int getD2() {
        return d2;
    }
    
    public int getD3() {
        return d3;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public boolean compare(DiceValue dv) {
        if (weight == 1 && dv.getWeight() == 1) {
            return d1 > dv.getD1() && d2 > dv.getD2() && d3 > dv.getD3();
        }
        return weight > dv.getWeight();
    }
    
    public boolean isNenette() {
        return code.equals("221");
    }
    
    public boolean equals(DiceValue dv) {
        return code.equals(dv.getCode());
    }
    
    public String getCode() {
        return code;
    }
    
    public int getToken() {
        return token;
    }
    
}
