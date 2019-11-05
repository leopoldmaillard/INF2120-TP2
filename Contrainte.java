import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
public class Contrainte extends ArrayList< Intervalle > {
   
    public Contrainte(){
    	super();
    }

    public Contrainte( Collection< Intervalle > collection ) {
    	super(collection);
    	this.trierContrainte();
    	this.disjonction();    	
    }


    public boolean add( Intervalle intervalle ){
    	super.add(intervalle);
    	this.trierContrainte();
    	this.disjonction();
    	
        return true ;
    }

    public void remove( Intervalle intervalle ){
        
    	int i = 0;

    	int taille = this.size();
    	while( i < taille) {
    		 
    		// borne inferieure se trouve à gauche de l'intervalle i
    		// et la borne superieure traverse l'intervalle i 
    		// 		deux cas : borne superieure dans l'intervale i
    		//                 borne superieure à droite de l'intervalle i
    		if ((this.get(i).debut >= intervalle.debut ) &&
    				(intervalle.fin >= this.get(i).debut)){
    			
    			// borne superieure se trouve à droite de l'intervalle i
    			// on supprime totalement l'intervalle i
    			if (intervalle.fin >= this.get(i).fin) {
    				super.remove(i);
    				taille--;
    				i--; //LEO
    				
    			// borne superieure se trouver dans l'intervalle i 
    			// on supprime une partie de l'intervalle i 
    			} else {
    				super.add(i, new Intervalle(intervalle.fin, this.get(i).fin));
    				super.remove(i+1);
    			}
    			
    		// borne inférieure se trouve au milieu de l'intervalle i
    		} else if ((this.get(i).debut < intervalle.debut ) &&
    				(intervalle.debut < this.get(i).fin)) {
    			
    			// borne superieure se trouve dans l'intervalle
    			// l'intervalle se coupe en deux
    			if (intervalle.fin < this.get(i).fin) {
    				Intervalle inter1 = new Intervalle(this.get(i).debut, intervalle.debut);
    				Intervalle inter2 = new Intervalle(intervalle.fin, this.get(i).fin);
    				super.add(i, inter1);
    				super.add(i+1, inter2);
    				super.remove(i+2);
    				taille++;
    			
    			// borne superieure se trouver au-dela de l'intervalle i
    			// on ne garde que le début de l'intervalle i
    			} else {
    				super.add(i, new Intervalle(this.get(i).debut, intervalle.debut));
    				super.remove(i+1);
    			}		
    		}	
    		i += 1;
    	}
    }


    public boolean appartient( int v ){     // algo indeterministe normalement
    	boolean resultat = false;
    	
    	for( int i = 0 ; i < this.size(); i++ ) {
    		if ( v >= this.get(i).debut && v < this.get(i).fin ) {
    			resultat = true ;
    		}
    	}
        return resultat ;
    }

    public int taille(){

    	int i = 0;
    	int compteurTotal = 0;
    	int compteur = 0;
    	while ( i < this.size()) {
    		compteur = this.get(i).fin - this.get(i).debut;
    		compteurTotal = compteur + compteurTotal;
    		i++;
    	}
        return compteurTotal;
    }


    public Contrainte union( Contrainte contrainte ){
    	Contrainte resultat = this ;
    	for (int i = 0 ; i < contrainte.size() ; i++) {
    		resultat.add(contrainte.get(i));
    	}
    	return resultat ;
    }


    public Contrainte intersection( Contrainte contrainte ){
     	
    	Contrainte res = new Contrainte();
    	
    	for (int i = 0 ; i < this.size(); i++) {
    		for (int j = 0 ; j < contrainte.size() ; j++) {
    			
    			if (!(this.get(i).intersection(contrainte.get(j)).isEmpty())){
    			res.add(this.get(i).intersection(contrainte.get(j)).get(0));
    			}
    		}
    	}    	
        return res ;
    }

    public Contrainte soustraction( Contrainte contrainte ){
    	Contrainte resultat = this ;
    	for (int i = 0 ; i < contrainte.size() ; i++) {
    		resultat.remove(contrainte.get(i));
    	}
    	return resultat ;
    }
 
    @Override
    public String toString() {
    	String str = "";
    	for (int i = 0 ; i < this.size(); i++) {
    		str = str + "[" + this.get(i).debut + " ; " + this.get(i).fin +"]";
    	}
    	return str ;
    }
    
    //Classes supplémentaires
    
    //Tri des intervalles d'une contrainte (par borne supérieure croissante)
    public void trierContrainte() {
    	
    	boolean enOrdre ;
    	Intervalle temp ;
    	do {
    		enOrdre = true ;
    		for ( int i = 0 ; i < this.size() - 1 ;  i++) {
    			if (this.get(i).fin > this.get(i+1).fin) {
    				temp = this.get(i);
    				this.set(i, this.get(i+1));
    				this.set(i+1, temp);
    				enOrdre = false ;
    			}
    		}
    	}while ( !enOrdre);	
    }
    
    //Va disjoindre les intervalles de la contrainte comme spécifié dans les invariants de la contrainte
    public void disjonction () {
    	int taille = this.size() ;
    	
    	for (int i = 0 ; i < taille ; i++ ) {
    		for (int j = i + 1 ; j < taille ; j++) {
    			
    			//Si l'intervalle suivant est inclu dans l'intervalle courant, on le supprime
    			if (this.get(i).debut <= this.get(j).debut && this.get(i).fin >= this.get(j).fin) { 
        			super.remove(j);
        			taille--;
        			j = i ;
        		}
        		
    			//Si l'intervalle courant est inclu dans l'intervalle suivant, on le supprime
        		else if (this.get(i).debut >= this.get(j).debut && this.get(i).fin <= this.get(j).fin) {
        			super.remove(i);
        			taille--;
        			j = i ;
        		}
        		
    			//Disjonction
        		else if (this.get(i).fin >= this.get(j).debut) {
        			this.set(i, new Intervalle ( this.get(i).debut ,  this.get(j).fin ));
        			super.remove(j);
        			taille--;
        			j = i ;
        		}	
    		}
    	}
    	
    }   
}
