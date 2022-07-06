package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.model.Car;

public interface CarService {
	List<Car> getAllCars();
	void saveCar(Car car);
	Car getCarById(long id);
	void deleteCarById(long id);
	Page<Car> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}