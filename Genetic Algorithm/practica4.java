package net.sf.jclec;

import java.util.Comparator;

import net.sf.jclec.IFitness;
import net.sf.jclec.IIndividual;
import net.sf.jclec.base.AbstractEvaluator;

import net.sf.jclec.intarray.IntArrayIndividual;
import net.sf.jclec.fitness.SimpleValueFitness;
import net.sf.jclec.fitness.ValueFitnessComparator;


public class practica4 extends AbstractEvaluator {

	private static final long serialVersionUID = 2L;

	private Comparator<IFitness> COMPARATOR;

    //We will minimize the value
	private boolean maximize = false;

    //number of employees for the night turn
	private static final int [] night = {
		5, 3, 2, 4, 3, 2, 2
	};

    //number of employees for the morning turn
	private static final int [] morning = {
		7, 8, 9, 5, 7, 2, 5
	};

    //number of employees for the afternoon turn
    private static final int [] afternoon = {
		9, 10, 10, 7, 11, 2, 2
	};


	@Override
	public Comparator<IFitness> getComparator() {
		// Set fitness comparator (if necessary)
		if (COMPARATOR == null)
			COMPARATOR = new ValueFitnessComparator(!maximize);

		// Return comparator
		return COMPARATOR;
	}

	@Override
	protected void evaluate(IIndividual ind) {

		//Obtained genotype
		int [] genotypeN =((IntArrayIndividual)ind).getGenotype();

		//fitness value
                float valor = 0;

                //number of employees per day
                int [] dia = new int[7];

                int cont=1;

                //initialization days vector
                for(int i=0; i<7; i++){
                    dia[i] = 0;
                }

                //for each genotype...
                for ( int i = 0; i<genotypeN.length; i++){

                    //a 4 window to obtain the total employees per day
                    for(int j=1; j<4; j++){

                        //can't i rest 4? (Monday, Tuesday or Wednesday)
                        if(i-j<0){
                            //lets go to the previous week (from Sunday back)
                            dia[i] = dia[i] + genotypeN[genotypeN.length-cont];
                            cont++;
                        }
                        //i can rest 4 (Thursday, Friday, Saturday y Sunday)
                        else{
                            dia[i] = dia[i] + genotypeN[i-j];
                        }
                    }

                    cont = 1; //restarting...

                    //sum of employees from previous days with the ones of today
                    dia[i] = dia[i] + genotypeN[i];
                }

                
                int tmp = 0;
                //for each day of the week...
                //we see for how many units they differ from what we are looking for
                for ( int i=0; i<genotypeN.length; i++){
                    tmp = dia[i] - night[i] - afternoon[i] - morning[i];

                    //we didn't reach the number of employees for that day?
                    if(tmp<0){
                        //we penalize it giving it a very hight value
                        tmp *= -1; //converting to possitive...
                        tmp *= 100;
                    }

                    //the total value will be the value of the passed days 
                    //plus the one of today
                    valor = valor + tmp;

                 //If we want to print the process...
                 //System.out.println("======" + valor + "+" + dia[i] + "-" + (night[i] + afternoon[i] + morning[i]) + "=" + valor);
		}
               // System.out.println();

                //asigning the fitness value for this genotype. 
		ind.setFitness (new SimpleValueFitness(valor));
	}
}
