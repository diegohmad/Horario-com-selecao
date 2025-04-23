package generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AvaliadorAG {
    private static final int PERIODOS = 5;
    private static final int SLOTS   = 20;   // 5×20 = 100

    /* fitness = –nº de conflitos (mesmo professor no mesmo slot) */
    public static double avaliacao(String[] individuo){
        int conflitos=0;
        for(int slot=0;slot<SLOTS;slot++){
            Set<String> profs=new HashSet<>();
            for(int periodo=0;periodo<PERIODOS;periodo++){
                String gene = individuo[periodo*SLOTS+slot];
                String prof = gene.substring(0,2);
                if(!profs.add(prof)) conflitos++;
            }
        }
        return -conflitos;
    }

    /* lista ordenada do melhor para o pior */
    public static List<IndividuoFitness> avaliar(List<String[][]> pop){
        List<IndividuoFitness> out=new ArrayList<>();
        for(String[][] ind:pop)
            out.add(new IndividuoFitness(ind,avaliacao(ind[0])));
        out.sort((a,b)->Double.compare(b.fitness,a.fitness));
        return out;
    }

    public static class IndividuoFitness{
        public final String[][] individuo;
        public final double fitness;
        public IndividuoFitness(String[][] i,double f){individuo=i;fitness=f;}
        public double getFitness(){return fitness;}
    }
}
