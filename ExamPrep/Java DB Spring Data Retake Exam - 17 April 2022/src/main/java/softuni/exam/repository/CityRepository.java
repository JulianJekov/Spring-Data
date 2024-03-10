package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByCityName(String cityName);

    //List<City> findByPopulationLessThanAndForecastsDayOfWeekOrderByForecastsMaxTemperatureDescForecastsIdAsc
            //(int less, DayOfWeek dayOfWeek);
}
