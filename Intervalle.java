import java.util.Arrays;

public class Intervalle {
	
	int debut ;
	int fin ;
   
    public Intervalle( int debut, int fin ){
    	this.debut = debut;
		this.fin = fin ;
    }

    public boolean appartient( int v ){
    	return (v < fin) && (v >= debut);
    }
    
    public boolean equals ( Intervalle inter ) {
    	boolean res = false ;
    	if (inter == null) {
    		res = false ;
    	}
    	else if (inter.debut == this.debut && inter.fin == this.fin) {
    		res = true;
    	}
    	return res ;
    }

    public Contrainte union( Intervalle intervalle ){
        return new Contrainte(Arrays.asList(this, intervalle));
    }


    public Contrainte intersection( Intervalle intervalle ){
    	Contrainte resultat = new Contrainte();
    	
    	if (!(this.fin < intervalle.debut || intervalle.fin < this.debut)) {
    		Intervalle inter = new Intervalle (Math.max(this.debut,intervalle.debut) , Math.min(this.fin, intervalle.fin));
    		
    		if (inter.debut != inter.fin) {
    			resultat.add(inter);
    		}
    	}    	
    	return resultat ; 
    }


    public Contrainte soustraction( Intervalle intervalle ){
        Contrainte resultat = new Contrainte();
        resultat.add(this);
        resultat.remove(intervalle);
        return resultat ;
    }
    
    @Override
    public String toString() {
    	return "["+this.debut+" , "+this.fin+"[";
    }
    
    @Override
    public boolean equals (Object o) {
    	Intervalle inter = (Intervalle)o ;
    	
    	boolean res = false ;
    	if (inter == null) {
    		res = false ;
    	}
    	else if (inter.debut == this.debut && inter.fin == this.fin) {
    		res = true;
    	}
    	return res ;
    }
    
}
