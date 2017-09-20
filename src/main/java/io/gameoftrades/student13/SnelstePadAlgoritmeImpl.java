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
import static io.gameoftrades.model.kaart.Richting.NOORD;
import static io.gameoftrades.model.kaart.Richting.OOST;
import static io.gameoftrades.model.kaart.Richting.WEST;
import static io.gameoftrades.model.kaart.Richting.ZUID;
import java.util.ArrayList;

public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme{
    
    Coordinaat startco;
    Coordinaat eindco;
    @Override
    @SuppressWarnings("empty-statement")
    
    public Pad bereken(Kaart kaart, Coordinaat crdnt, Coordinaat crdnt1) {
        Pad padar = null;
        
        //WAAROM ZORRO? WAAROM?
        startco = op(crdnt.getX() - 1, crdnt.getY() - 1);
        eindco = op(crdnt1.getX() - 1, crdnt1.getY() - 1);
                        
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
        
        for(Coordinaat c: nietBezocht){
            if(c.getX() == 24 && c.getY() == 48){
                System.out.println("zou moeten werken");
            }
        }
        Pad startpad = getStartPad(kaart, startco);
        padenlijst.add(startpad);
        
        while(!nietBezocht.isEmpty()){            
            int afstandVanafStart = Integer.MAX_VALUE;
            Pad currentPad = null;
            
            for(Pad p: padenlijst){
                if(p.getTotaleTijd() < afstandVanafStart && nietBezocht.contains(p.volg(startco))){
                    afstandVanafStart = p.getTotaleTijd();
                    currentPad = p;
                }
            }
            
            for(int i = 0; i< nozw.length; i++){
                if(currentPad.volg(startco) == null) System.out.println("wtf");
                Coordinaat currentEindco = currentPad.volg(startco);
                
                if(currentEindco.getX()==0 && nozw[i].equals(WEST) || currentEindco.getX()== kaart.getBreedte() && nozw[i].equals(OOST) || currentEindco.getY()== 0 && nozw[i].equals(NOORD)|| currentEindco.getY()== kaart.getHoogte() && nozw[i].equals(ZUID)) continue;
                
                if(kaart.isOpKaart(currentEindco.naar(nozw[i])) && nietBezocht.contains(currentEindco.naar(nozw[i]))){
                    Coordinaat buur = currentEindco.naar(nozw[i]);                    
                    Richting[] tempRichtingen = currentPad.getBewegingen();
                    richtingen = new Richting[tempRichtingen.length + 1];
                    
                    for(int z = 0; z < tempRichtingen.length; z++){
                        richtingen[z] = tempRichtingen[z];
                    }
                    richtingen[richtingen.length - 1] = nozw[i];
                    
                    
                    Pad tempPad = new PadImpl(richtingen, (currentPad.getTotaleTijd() + kaart.getTerreinOp(buur).getTerreinType().getBewegingspunten()));
                    //System.out.println(tempPad.volg(startco).getX() + " " + tempPad.volg(startco).getY());
                    
                    if (tempPad.volg(startco).getX() ==eindco.getX() && tempPad.volg(startco).getY() ==eindco.getY()){
                        System.out.println("gelukt!");
                        return tempPad;                        
                    }
                    if(bevatCoordinaat(padenlijst, buur)){
                        for(Pad p: padenlijst){
                            if(p.volg(startco).getX() == buur.getX() && p.volg(startco).getY() == buur.getY() && p.getTotaleTijd() <= tempPad.getTotaleTijd()){
                                break;
                            }
                            else if(p.volg(startco).getX() == buur.getX() && p.volg(startco).getY() == buur.getY() &&  p.getTotaleTijd() > tempPad.getTotaleTijd()){
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
        return padar;
    }
    
    public Pad getStartPad(Kaart kaart, Coordinaat startco){
        Richting[] richtingen = new Richting[0];
        Pad startPad = new PadImpl(richtingen, kaart.getTerreinOp(startco).getTerreinType().getBewegingspunten());
        //Pad startPad = new PadImpl(richtingen, 0);
        return startPad;
    }
    
    public boolean bevatCoordinaat(ArrayList<Pad> p, Coordinaat c){
        for(Pad pad: p){
            if(pad.volg(startco).getX() == c.getX() && pad.volg(startco).getY() == c.getY()){
                return true;
            }
        }
        return false;
    }
    
}
