package ee.energia.testassignment;

import ee.energia.testassignment.planning.ChargePlan;
import ee.energia.testassignment.price.EnergyPrice;
import ee.energia.testassignment.price.EnergyPriceComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChargePlanner {

    // the capability of the charger to charge certain amount of energy into the battery in 1 hour
    public final static int CHARGER_POWER = 50;

    // maximum battery level possible
    public final static int MAX_LEVEL = 100;

    // battery level required by the end of the charging
    public final static int REQUIRED_LEVEL = 100;

    /**
     * Method calculates the optimal hourly charge plan.
     * Method finds the cheapest hour to charge the battery (if multiple then the earliest)
     * and uses it to charge the battery up to the {@link ChargePlanner#REQUIRED_LEVEL}.
     * If {@link ChargePlanner#CHARGER_POWER} limitation does not allow to do this in one hour,
     * then method finds the next cheapest opportunities and uses them until {@link ChargePlanner#REQUIRED_LEVEL} is met.
     *
     * Method returns the array of {@link ChargePlan} objects that represent the hourly time slot
     * and the capacity that we need to charge during that hour to charge the battery.
     *
     * @param batteryLevel initial battery level when the charger is connected
     * @param energyPrices the list of the energy prices from the moment when charger is connected until the moment when battery needs to be charged
     *                     there is an assumption that battery is connected the first second of the first given hour and disconnected the last second of the last given hour
     * @return
     */
    public static ArrayList<ChargePlan> calculateChargePlan(int batteryLevel, ArrayList<EnergyPrice> energyPrices) {
        ArrayList<ChargePlan> definedPlan = new ArrayList<>();
        ArrayList<EnergyPrice> sortedEnergyPrices = new ArrayList<>(energyPrices);
        List<Integer>hoursList = new ArrayList<Integer>();
        if(ChargePlanner.REQUIRED_LEVEL==batteryLevel)
            return definedPlan;
        // How much battery need to charge
        int chargingLeft = ChargePlanner.REQUIRED_LEVEL - batteryLevel;
       // how many times needs to charge
       int timeCharging = chargingLeft/ChargePlanner.CHARGER_POWER;
       timeCharging = timeCharging+1;

       Collections.sort(sortedEnergyPrices, new EnergyPriceComparator());
       for(int i =0;i< timeCharging;i++)
       {
           hoursList.add(sortedEnergyPrices.get(i).getHour());
       }
       Collections.sort(hoursList);
       int capacity =0;
       for(int j =0;j< energyPrices.size();j++)
       {
           if(hoursList.contains(energyPrices.get(j).getHour()))
           {
            capacity = Math.min(ChargePlanner.REQUIRED_LEVEL - batteryLevel, ChargePlanner.CHARGER_POWER);
            definedPlan.add(new ChargePlan(capacity, energyPrices.get(j).getHour(), 1, 2019));
            batteryLevel = capacity + batteryLevel;
           }
           else
           {
            definedPlan.add(new ChargePlan(0, energyPrices.get(j).getHour(), 1, 2019));
           }
       }
        return definedPlan;
    }

    /* Charge price 
    with classical way to charge when the changing would start straight away when connected to the charger, 
    and will charge until charged to maximum.  */
    public static int PriceChargeWithClassicalWay(int batteryLevel, ArrayList<EnergyPrice> energyPrices,int startTime)
    {
        int totalPrice =0;
        if(batteryLevel == ChargePlanner.REQUIRED_LEVEL)
            return 0;
        else
        {
            int leftCapacity = ChargePlanner.REQUIRED_LEVEL - batteryLevel;
            // how many times needs to charge
           int timeCharging = leftCapacity/ChargePlanner.CHARGER_POWER;
           

           startTime = startTime -1;

           
           while(timeCharging>=0)
           {
            totalPrice = totalPrice +  energyPrices.get(startTime).getBidPrice();
            startTime = startTime+1;
            timeCharging = timeCharging-1;
           }
        }
        return totalPrice;
    }

    public static int PriceChargeWithOptimalWay(int batteryLevel,ArrayList<EnergyPrice> energyPrices)
    {
        ArrayList<ChargePlan> definedPlan = new ArrayList<>();
        definedPlan = calculateChargePlan(batteryLevel,energyPrices);
        int chargePrice =0;
        for(int i =0;i<definedPlan.size();i++)
        {
            if(definedPlan.get(i).getCapacity()>0)
            {
                chargePrice = chargePrice + energyPrices.get(definedPlan.get(i).getHour()-1).getBidPrice() ;
            }
        }

        return chargePrice;
    }

    public static int savedMoney(int batteryLevel,ArrayList<EnergyPrice> energyPrices,int startTime)
    {
         
          int classicalWay= PriceChargeWithClassicalWay(batteryLevel,energyPrices,startTime);
          int optimalWay = PriceChargeWithOptimalWay(batteryLevel,energyPrices);

          return classicalWay- optimalWay;
    }
}
