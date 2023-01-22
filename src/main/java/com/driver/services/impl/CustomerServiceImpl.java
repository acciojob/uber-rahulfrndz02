package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.CabRepository;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Autowired
	CustomerService customerService; //by me
	@Autowired
	private CabRepository cabRepository;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		//customerService.register(customer);
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		//if(customerRepository2.existsById(customerId)) {
			Customer customer = customerRepository2.findById(customerId).get();
			//customerService.deleteCustomer(customer.getCustomerId());
		customerRepository2.delete(customer);
		//}
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
//		TripBooking tripBooking = new TripBooking();
//		Cab cab = new Cab();
//		if(cab.getAvailable()){
//			Customer customer = customerRepository2.findById(customerId).get();
//			tripBooking.setTripBookingId(customer.getCustomerId());
//			tripBooking.setFromLocation(fromLocation);
//			tripBooking.setToLocation(toLocation);
//			tripBooking.setDistanceInKm(distanceInKm);
//			tripBooking.setStatus(TripStatus.CONFIRMED);
//		}
//		else{
//			throw new Exception("No cab available!");
//		}
//	  return tripBooking;


//		List<Driver> driverList =driverRepository2.findAllByOrderByDriverId();
//		Driver availableDriver = null;
//		for(Driver driver : driverList){
//			Cab driverCab = driver.getCab();
//			if (driverCab.getAvailable()){
//				availableDriver = driver;
//				break;
//			}
//		}
//		if(availableDriver==null){
//			throw new Exception("No cab available!");
//		}
//		Customer customer = customerRepository2.findById(customerId).get();
//
//		TripBooking tripBooking = new TripBooking();
//		tripBooking.setFromLocation(fromLocation);
//		tripBooking.setToLocation(toLocation);
//		tripBooking.setBill(distanceInKm * availableDriver.getCab().getPerKmRate());
//		tripBooking.setDistanceInKm(distanceInKm);
//		tripBooking.setStatus(TripStatus.CONFIRMED);
//
//		tripBooking.setCustomer(customer);
//		customer.getTripBookingList().add(tripBooking);
//
//		tripBooking.setDriver(availableDriver);
//		availableDriver.getTripBookingList().add(tripBooking);
//
//		customerRepository2.save(customer);
//		driverRepository2.save(availableDriver);
//		return tripBooking;



		List<Driver> drivers = driverRepository2.findAll();
		if(drivers.size()==0){
			throw new Exception("No cab available!");
		}
		drivers.sort(Comparator.comparingInt(Driver::getDriverId));
		Driver requiredDriver = null;
		for (Driver driver : drivers) {
			if (driver.getCab().getAvailable()) {
				requiredDriver = driver;
				break;
			}
		}
		if (requiredDriver == null)
			throw new Exception("No cab available!");

		Customer customer = customerRepository2.findById(customerId).get();
		Cab cab = requiredDriver.getCab();
		cab.setAvailable(false);
		driverRepository2.save(requiredDriver);
		cabRepository.save(cab);
		TripBooking tripBooking = new TripBooking(toLocation, fromLocation, distanceInKm, TripStatus.CONFIRMED);
		requiredDriver.getCab().setAvailable(false);
		tripBooking.setBill(requiredDriver.getCab().getPerKmRate()*distanceInKm);
		tripBooking.setDriver(requiredDriver);
		tripBooking.setCustomer(customer);
		if(Objects.isNull(requiredDriver.getTripBookingList())) {
			requiredDriver.setTripBookingList(new ArrayList<>());
		}
		requiredDriver.getTripBookingList().add(tripBooking);
		if(Objects.isNull(customer.getTripBookingList())) {
			customer.setTripBookingList(new ArrayList<>());
		}
		customer.getTripBookingList().add(tripBooking);
		tripBookingRepository2.save(tripBooking);
		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly

//		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
//		customerService.cancelTrip(tripBooking.getTripBookingId());
//
//		Driver driver = tripBooking.getDriver();
//		driver.getCab().setAvailable(true);
//		tripBooking.setStatus(TripStatus.CANCELED);

		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);
		Driver driver = tripBooking.getDriver();
		driver.getCab().setAvailable(true);
		tripBookingRepository2.save(tripBooking);

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		if(tripBookingRepository2.findById(tripId).isPresent()) {
			TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
			tripBooking.setStatus(TripStatus.COMPLETED);
			Driver driver = tripBooking.getDriver();
			driver.getCab().setAvailable(true); //getting driver's cab
			driverRepository2.save(driver);
		}
	}
}
