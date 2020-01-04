/* 
 * @author Lisa Casino
 * @author Léopold Maillard
 */

import java.util.Arrays;

public class Intervalle {
	
	int debut ;
	int fin ;
   
    public Intervalle( int debut , int fin ){
    	this.debut = debut;
		this.fin = fin ;
    }

    public boolean appartient( int v ){
    	return ( v < fin ) && ( v >= debut );
    }
    
    public boolean equals ( Intervalle inter ) {
    	boolean res = false ;
    	if ( !(inter == null ) && ( inter.debut == this.debut && inter.fin == this.fin ) )
			res = true ;
    	return res ;
    }

    public Contrainte union( Intervalle intervalle ){
        return new Contrainte( Arrays.asList( this , intervalle ) );
    }


    public Contrainte intersection( Intervalle intervalle ){
    	Contrainte resultat = new Contrainte();
    	
    	//On revient à la définition d'une intersection
    	if ( !( this.fin < intervalle.debut || intervalle.fin < this.debut ) ) {
    		Intervalle inter = new Intervalle ( Math.max( this.debut , intervalle.debut ) , Math.min( this.fin , intervalle.fin ) );
    		
    		//Il faut gérer le fait que nos intervalles sont ouverts à droite
    		if ( inter.debut != inter.fin ) {
    			resultat.add( inter );
    		}
    	}    	
    	return resultat ; 
    }


    public Contrainte soustraction( Intervalle intervalle ){
        Contrainte resultat = new Contrainte();
        resultat.add(this);
        resultat.remove( intervalle );
        return resultat ;
    }
    
    @Override
    public String toString() {
    	return "["+this.debut+" , "+this.fin+"[";
    }
    
    @Override
    public boolean equals (Object o) {
    	boolean res = false ;
    	if (o instanceof Intervalle) {
    		Intervalle inter = (Intervalle)o ;
    	
    		if ( !( inter == null ) && ( inter.debut == this.debut && inter.fin == this.fin ) )
    			res = true ;
    	}
    	return res ;
    }  
}
