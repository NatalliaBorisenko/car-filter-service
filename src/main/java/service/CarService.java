package service;

import dto.CarDto;
import filter.CarFilter;

import java.util.List;

/**
 * Service interface for filtering cars based on specified filter conditions.
 */
public interface CarService {
    List<CarDto> filterCars(CarFilter filter);
}
