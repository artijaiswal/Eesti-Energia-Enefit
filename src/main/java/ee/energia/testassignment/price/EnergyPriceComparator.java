package ee.energia.testassignment.price;
 
import java.util.Comparator;
 
public class EnergyPriceComparator 
                                implements Comparator<EnergyPrice> {
 
    @Override
    public int compare(EnergyPrice eng1, EnergyPrice eng2) {
 
        // all comparison
        int compareBid = eng1.getBidPrice()-eng2.getBidPrice();
        int difference = (eng1.getBidPrice()-eng1.getAskPrice()) - (eng2.getBidPrice()-eng2.getAskPrice());
        int compareHours = eng1.getHour()-eng2.getHour();
 
        // 3-level comparison using if-else block
        if(compareBid == 0) {
            return difference==0? compareHours: difference;
        }
        else {
            return compareBid;
        }
    }
}