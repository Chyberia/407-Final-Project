/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject;

/**
 *
 * @author Cole Yonkers
 */
public class Person {
    int infected;
    int infectedTimer;
    int immunityTimer;
    int socialGroupSize;
    int socialGroupSize2;
    int carrier;
    double vaccinationEffectiveness;
    int[] socialGroupConnections;
    int[] socialGroupConnections2;
    int fullImmunity;
    public Person(){
        
        infected = 0;
        infectedTimer = 0;
        immunityTimer = 0;
        vaccinationEffectiveness = 0;
        fullImmunity = 0;
    }
    
    public Person(int socialGroupSize){
        infected = 0;
        infectedTimer = 0;
        immunityTimer = 0;
        vaccinationEffectiveness = 0;
        socialGroupSize2 = 0;
        socialGroupConnections = new int[socialGroupSize];
        socialGroupConnections2 = new int[1000];
        carrier = 0;
    }
   
    
    
    public void setInfected(){
        infected = 1;        
    }
    
    
        //TODO: take an integer argument, so the timer can last a custom amount.
    public void infectionCountdown(int timer){    
        infectedTimer = timer;    
        infected = 1;
    }
    
        public void infectionCountdown(){

            infectedTimer--;
            if(infectedTimer == 0){
                
                infected = 0;
                
            }
            
        }
        
    
    
    public void setImmunity(){
        
    fullImmunity = 1;    
        
    }
    
    
    public void immunityCountdown(int timer){
        if(immunityTimer == 0){
        immunityTimer = timer;
        }
        else{
            immunityTimer--;
        }
        
    }
        
    public void immunityCountdown(){
        
            immunityTimer--;
        
        
    }
    

    public void decrementInfection(){
        
        //decrement how long someone still has to be infected,if its 0 change infection status. 
        if(infectedTimer > 0){      
            infectedTimer--;
        }      
                        
            if(infectedTimer == 0){
                infected = 0;               
            }          
        
        
      
        
    }
    
    
}
