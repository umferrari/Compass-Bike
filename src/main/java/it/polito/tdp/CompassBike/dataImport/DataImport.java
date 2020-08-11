package it.polito.tdp.CompassBike.dataImport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.CompassBike.DAO.BikesDAO;
import it.polito.tdp.CompassBike.DAO.RentalsDAO;
import it.polito.tdp.CompassBike.DAO.StationsDAO;
import it.polito.tdp.CompassBike.model.Station;

public class DataImport {
	
	public void parseJSONStations(String directory) {
		// TODO Controllo errore impossibile leggere file
		List<StationData> stations = ParseJSONStations.parse(directory);
		StationsDAO.addStation(stations);
	}
	
	public void parseCSVRentals(String directory) {
		// TODO Controllo errore impossibile leggere file
		List<Rental> allRentals = ParseCSVRentals.parse(directory);
		Map<Integer, Station> stationsIdMap = StationsDAO.getAllStations();
		
		List<BikeData> bikes = new ArrayList<>();
		List<Rental> rentals = new ArrayList<>();
		for(Rental rental : allRentals) {
			if(stationsIdMap.containsKey(rental.getStartStationId()) && stationsIdMap.containsKey(rental.getEndStationId())) {
				rentals.add(rental);
				bikes.add(new BikeData(rental.getBikeId(), rental.getEndStationId()));
			}
		}
		
		BikesDAO.addBike(bikes);
		RentalsDAO.addRental(rentals);
	}

}
