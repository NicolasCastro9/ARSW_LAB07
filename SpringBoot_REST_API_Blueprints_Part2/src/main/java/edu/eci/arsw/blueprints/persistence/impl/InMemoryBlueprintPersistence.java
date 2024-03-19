/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(120, 160),new Point(116, 115)};
        Point[] pts2 =new Point[]{new Point(110, 130),new Point(125, 120)};
        Point[] pts3 =new Point[]{new Point(110, 220),new Point(135, 130)};
        Point[] pts4=new Point[]{new Point(10, 80),new Point(150, 90)};
        Point[] pts5 =new Point[]{new Point(80, 50),new Point(190, 10)};
        Point[] pts6 =new Point[]{new Point(70, 80),new Point(170, 180)};
        Point[] pts7=new Point[]{new Point(80, 90),new Point(150, 110)};
        Point[] pts8 =new Point[]{new Point(110, 130),new Point(125, 120)};
        Blueprint bp=new Blueprint("autor_1", "bps_1",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        Blueprint bp2 =new Blueprint("autor_1", "bps_2",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        Blueprint bp3 =new Blueprint("autor_2", "bps_3",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        Blueprint bp4=new Blueprint("coquito", "pruebacoquito",pts4);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
        Blueprint bp5 =new Blueprint("coquito", "pruebacoquito2",pts5);
        blueprints.put(new Tuple<>(bp5.getAuthor(),bp5.getName()), bp5);
        Blueprint bp6 =new Blueprint("Nicolas", "pruebaNicolas",pts6);
        blueprints.put(new Tuple<>(bp6.getAuthor(),bp6.getName()), bp6);
        Blueprint bp7=new Blueprint("Nicolas", "pruebaNicolas2",pts7);
        blueprints.put(new Tuple<>(bp7.getAuthor(),bp7.getName()), bp7);
        Blueprint bp8 =new Blueprint("virginia", "virginiaprueba",pts8);
        blueprints.put(new Tuple<>(bp8.getAuthor(),bp8.getName()), bp8);

        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }



    /**
     * Obtiene un plano arquitectónico específico dado el nombre del autor y el nombre del plano.
     * 
     * @param author El nombre del autor del plano arquitectónico.
     * @param bprintname El nombre del plano arquitectónico.
     * @return El plano arquitectónico correspondiente al autor y nombre dados.
     * @throws BlueprintNotFoundException Si no se encuentra ningún plano con el autor y nombre especificados.
     */
    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }





    /**
     * Obtiene todos los planos arquitectónicos creados por un autor específico.
     * 
     * @param author El nombre del autor de los planos arquitectónicos a buscar.
     * @return Un conjunto de planos arquitectónicos creados por el autor especificado.
     * @throws BlueprintNotFoundException Si no se encuentra ningún plano para el autor especificado.
     */
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> bp = new HashSet<>();
        Set<Tuple<String, String>> keys = blueprints.keySet();

        for(Tuple<String, String> tuple : keys){
            if(tuple.getElem1().equals(author)){
                bp.add(blueprints.get(tuple));
            }
        }

        return bp;
    }



    /**
     * Obtiene todos los planos arquitectónicos registrados en el sistema.
     * 
     * @return Un conjunto de todos los planos arquitectónicos registrados.
     * @throws BlueprintNotFoundException Si no se encuentran planos arquitectónicos registrados en el sistema.
     */
    @Override
    public Set<Blueprint> getAllBlueprint() throws BlueprintNotFoundException {
        Set<Blueprint> bp = new HashSet<>();
        Set<Tuple<String, String>> keys = blueprints.keySet();

        for(Tuple<String, String> tuple : keys){
            bp.add(blueprints.get(tuple));
        }

        return bp;
    }

    @Override
    public void deleteBlueprint(String author,String bpname) throws BlueprintNotFoundException {
        Tuple tuple = new Tuple<>(author, bpname);
        if(!blueprints.containsKey(tuple)){
            throw new BlueprintNotFoundException("The given blueprint not  exists: " + bpname);
        }
        blueprints.remove(tuple);
    }

    
    
}
