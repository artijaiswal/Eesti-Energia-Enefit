package ee.energia.testassignment;

import java.util.ArrayList;
import ee.energia.testassignment.price.EnergyPrice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestAssignmentApplication {

	public static void main(String[] args) {

		SpringApplication.run(TestAssignmentApplication.class, args);

		int batteryLevel =20;
		int startTime = 2;
		final ArrayList<EnergyPrice> energyPrices = new ArrayList<>();
		energyPrices.add(new EnergyPrice(13, 10, 1, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(10, 9, 2, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(8, 7, 3, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(10, 9, 4, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(8, 7, 5, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(10, 8, 6, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(11, 9, 7, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(15, 13, 8, 1, 1, 2019));
		
		System.out.print("Amount of saved money ..... ");

		System.out.print(ChargePlanner.savedMoney(batteryLevel,energyPrices,startTime));
	}
}
