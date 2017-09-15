/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student13;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

public class PadImpl implements Pad{

    Richting[] richtingen;
    int bewegingstijd;
    
    public PadImpl(Richting[] richtingen, int bewegingstijd){
        this.bewegingstijd = bewegingstijd;
        this.richtingen = richtingen;
    }
    
    @Override
    public int getTotaleTijd() {
        return bewegingstijd;
    }

    @Override
    public Richting[] getBewegingen() {
        return richtingen;
    }

    @Override
    public Pad omgekeerd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //flip Richting[]
    }

    @Override
    public Coordinaat volg(Coordinaat crdnt) {
        //ga met een loop vanaf het meegegeven startcoordinaat Richtingen[] af om het eindcordinaat van het pad te bepalen en return deze.
        Coordinaat eindcoordinaat = crdnt;
        for(int i=0; i < this.richtingen.length; i++){
            eindcoordinaat = eindcoordinaat.naar(richtingen[i]);
        }
        return eindcoordinaat;
    }
    
}
