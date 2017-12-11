
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author beysimeryalmaz
 */
public class GantChart {
    
    public ArrayList<Integer> processIds=new ArrayList<>();
    public ArrayList<Integer> exeTimes=new ArrayList<>();
    
    public void printIds(){
        for(Integer id:processIds)
            System.out.print(" P"+id+" |");
    }
    
    public void printExTimes(){
        for(Integer time:exeTimes){
            if(time<10)
                System.out.print("    "+time);
            else
                System.out.print("   "+time);
    }}
    
    
    
    
}
