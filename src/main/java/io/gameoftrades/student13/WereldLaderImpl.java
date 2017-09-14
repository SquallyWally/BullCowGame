package io.gameoftrades.student13;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import static io.gameoftrades.model.kaart.Coordinaat.op;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;
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
            
            
            String[] lengtematen = input.readLine().replaceAll("\\s+","").split(",");
            breedte = Integer.parseInt(lengtematen[0]);
            lengte = Integer.parseInt(lengtematen[1]);
            
            kaart = new Kaart(breedte, lengte);
                                  
            //optie 1
            /*for(int i = 0; i < lengte; i++){
                for(int j = 0; j< breedte; j++){
                    c = op(i,j);
                    tt = TerreinType.fromLetter((char)input.read());
                    Terrein t = new Terrein(kaart, c, tt);
                }
                input.skip(2); //skip enter
                
            }*/
            
            //optie 2
            
            try{
            for(int i = 0; i < lengte; i++){
                String tts = input.readLine().replaceAll("\\s+","");
                for(int j = 0; j< breedte; j++){
                    c = op(j,i);
                    tt = TerreinType.fromLetter(tts.charAt(j));
                    Terrein t = new Terrein(kaart, c, tt);
                }                              
            }
            }catch(StringIndexOutOfBoundsException  e){
//                System.out.println("Kaart inhoud klopt niet");
            }
            
            aantalSteden = Integer.parseInt(input.readLine().replaceAll("\\s+",""));
            
            for(int k = 0; k < aantalSteden; k++){
                String[] stadArray = input.readLine().replaceAll("\\s+","").split(",");
                    c = op(Integer.parseInt(stadArray[0]), Integer.parseInt(stadArray[1]));
                if(Integer.parseInt(stadArray[0]) != 0 && Integer.parseInt(stadArray[1]) != 0){
                    stad = new Stad(c, stadArray[2]);
                    stedenLijst.add(stad); 
                }else{
                    throw new IllegalArgumentException();
                }
            }
            
            aantalMarkt = Integer.parseInt(input.readLine().replaceAll("\\s+",""));
            for(int l = 0; l < aantalMarkt; l++){
                String[] handelArray = input.readLine().replaceAll("\\s+","").split(",");
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
        } catch (StringIndexOutOfBoundsException e){
        
        }return null;
    }
}