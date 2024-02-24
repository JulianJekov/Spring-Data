package org.example.springdataautomappingobjects.repositories;

import org.example.springdataautomappingobjects.domain.dtos.GameTitleAndPriceViewDto;
import org.example.springdataautomappingobjects.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("select g.title as title, g.price as price from Game g")
    List<GameTitleAndPriceViewDto> findAllByTitleAndPrice();

    Game findByTitle(String gameTitle);
}
