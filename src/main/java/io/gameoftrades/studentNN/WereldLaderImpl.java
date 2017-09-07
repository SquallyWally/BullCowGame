package io.gameoftrades.studentNN;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import static io.gameoftrades.model.kaart.Coordinaat.op;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;
import static io.gameoftrades.model.kaart.TerreinType.fromLetter;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WereldLaderImpl implements WereldLader {
    
    @Override
    public Wereld laad(String resource){
        
        try {
            //
            // Gebruik this.getClass().getResourceAsStream(resource) om een resource van het classpath te lezen.
            BufferedReader input = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(resource)));
            
            int lengte;
            int breedte;
            TerreinType tt;
            Coordinaat c;
            Kaart kaart;
            int aantalSteden;
            int aantalMarkt;
            Stad stad;
            List<Stad> stedenLijst = new ArrayList<>();
            Handel handel;
            HandelType ht;
            Handelswaar hw;
            int prijs;
            List<Handel> handelLijst = new ArrayList();
            
            
            String[] lengtematen = input.readLine().split(",");
            lengte = Integer.parseInt(lengtematen[0]);
            breedte = Integer.parseInt(lengtematen[1]);
            
            //if(lengte > 0 && breedte > 0){
                kaart = new Kaart(lengte, breedte);
            //}
            
            for(int i = 0; i < lengte; i++){
                for(int j = 0; j< breedte; j++){
                    c = op(i,j);
                    tt = fromLetter((char)input.read());
                    Terrein t = new Terrein(kaart, c, tt);
                }
                
            }
            aantalSteden = Integer.parseInt(input.readLine());
            
            for(int k = 0; k < aantalSteden; k++){
                String[] stadArray = input.readLine().split(",");
                c = op(Integer.parseInt(stadArray[0]), Integer.parseInt(stadArray[1]));
                stad = new Stad(c, stadArray[2]);
                stedenLijst.add(stad);
            }
            
            aantalMarkt = Integer.parseInt(input.readLine());
            for(int l = 0; l < aantalMarkt; l++){
                String[] handelArray = input.readLine().split(",");
                stad = null; // temp fix
                for(Stad s: stedenLijst){
                    if (s.getNaam().equals(handelArray[0])){
                        stad = s;
                    }
                }
                ht = HandelType.valueOf(handelArray[1]);
                hw = new Handelswaar(handelArray[2]);
                prijs = Integer.parseInt(handelArray[3]); 
                handel = new Handel(stad, ht, hw, prijs);
                handelLijst.add(handel);             
            }
            
            Markt markt = new Markt(handelLijst);
            Wereld wereld = new Wereld(kaart, stedenLijst, markt);
            //
            // Kijk in src/test/resources voor voorbeeld kaarten.
            //
            // TODO Laad de wereld!
            //
            return wereld;
        } catch (IOException ex) {
            Logger.getLogger(WereldLaderImpl.class.getName()).log(Level.SEVERE, null, ex);
        }return null;
    }
}
