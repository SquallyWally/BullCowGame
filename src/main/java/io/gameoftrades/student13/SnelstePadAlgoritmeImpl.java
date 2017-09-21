/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student13;

import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import static io.gameoftrades.model.kaart.Coordinaat.op;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
import java.util.ArrayList;

public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme{
    
    Coordinaat startco;
    Coordinaat eindco;
    @Override
    @SuppressWarnings("empty-statement")
    
    public Pad bereken(Kaart kaart, Coordinaat crdnt, Coordinaat crdnt1) {
        Pad padar = null; //=new PadImpl;
        startco = crdnt;
        eindco = crdnt1;
                
        Richting[] nozw = Richting.values();
        Richting[] richtingen;
        ArrayList<Pad> padenlijst = new ArrayList<Pad>();       
        ArrayList<Coordinaat> nietBezocht = new ArrayList<Coordinaat>();
        
        for(int y = 0; y < kaart.getHoogte(); y++){
            for(int x = 0; x < kaart.getBreedte(); x++){
                if(kaart.getTerreinOp(op(x,y)).getTerreinType().isToegankelijk()){
                    nietBezocht.add(op(x,y));
                }            
            }
        }
        
        Pad startpad = getStartPad(kaart, startco);
        padenlijst.add(startpad);
        
        while(!nietBezocht.isEmpty() && padar == null){
            
            int afstandVanafStart = Integer.MAX_VALUE;
            Pad currentPad = null;
            
            for(Pad p: padenlijst){
                if(p.getTotaleTijd() < afstandVanafStart && nietBezocht.contains(p.volg(startco))){
                    afstandVanafStart = p.getTotaleTijd();
                    currentPad = p;
                }
            }
            
            for(int i = 0; i< nozw.length; i++){
                if(kaart.isOpKaart(currentPad.volg(startco).naar(nozw[i])) && nietBezocht.contains(currentPad.volg(startco).naar(nozw[i]))){
                    Coordinaat buur = currentPad.volg(startco).naar(nozw[i]);
                    
                    Richting[] tempRichtingen = currentPad.getBewegingen();
                    richtingen = new Richting[tempRichtingen.length + 1];
                    for(int z = 0; z < tempRichtingen.length; z++){
                        richtingen[z] = tempRichtingen[z];
                    }
                    richtingen[richtingen.length - 1] = nozw[i];
                    
                    
                    Pad tempPad = new PadImpl(richtingen, (currentPad.getTotaleTijd() + kaart.getTerreinOp(buur).getTerreinType().getBewegingspunten()));
                    System.out.println(tempPad.volg(startco).getX() + " " + tempPad.volg(startco).getY());
                    if (tempPad.volg(startco).getX() ==eindco.getX() && tempPad.volg(startco).getY() ==eindco.getY()){
                        System.out.println("gelukt!");
                        padar = tempPad;
                        //return padar;
                    }
                    if(bevatCoordinaat(padenlijst, buur)){
                        for(Pad p: padenlijst){
                            if(p.volg(startco) == buur && p.getTotaleTijd() <= tempPad.getTotaleTijd()){
                                break;
                            }
                            else if(p.volg(startco) == buur && p.getTotaleTijd() > tempPad.getTotaleTijd()){
                                padenlijst.remove(p);
                                padenlijst.add(tempPad);
                            }
                        }
                    }
                    else padenlijst.add(tempPad);
                }   
            }
            nietBezocht.remove(currentPad.volg(startco));
        }
        System.out.println("hallo");
        return padar;
    }
    
    public Pad getStartPad(Kaart kaart, Coordinaat startco){
        Richting[] richtingen = new Richting[0];
        Pad startPad = new PadImpl(richtingen, kaart.getTerreinOp(startco).getTerreinType().getBewegingspunten());
        return startPad;
    }
    
    public boolean bevatCoordinaat(ArrayList<Pad> p, Coordinaat c){
        for(Pad pad: p){
            if(pad.volg(startco) == c){
                return true;
            }
        }
        return false;
    }
    
}
