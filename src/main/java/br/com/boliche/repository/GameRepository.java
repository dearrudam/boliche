package br.com.boliche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.boliche.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

	List<Game> findAllByAlley(String alley);
	
	List<Game> findAllByAlleyAndName(String alley, String name);
	
}
