package br.com.boliche.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.boliche.dto.GameDTO;
import br.com.boliche.dto.PlayerDTO;
import br.com.boliche.entity.Game;
import br.com.boliche.repository.GameRepository;

@Service
public class GameService {

	private GameRepository gameRepository;
		
	@Autowired
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
	
	public void save(Game game) {
		// calculei
		this.gameRepository.save(game);
	}

	public GameDTO findAllByAlley(String alley) {
		List<Game> game = this.gameRepository.findAllByAlley(alley);
		
		GameDTO gameDTO = new GameDTO();
			gameDTO.setAlley(alley);
			
		List<PlayerDTO> players = game.stream()
			.map(g -> new PlayerDTO(g.getName()))
			.collect(Collectors.toList());
		
		gameDTO.setPlayers(players);
		
		return gameDTO;
	}
	
}
