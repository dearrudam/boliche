package br.com.boliche.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.boliche.dto.GameDTO;
import br.com.boliche.dto.PlayerDTO;
import br.com.boliche.dto.FrameDTO;
import br.com.boliche.entity.Game;
import br.com.boliche.repository.GameRepository;
import br.com.boliche.util.ScoreUtils;

@Service
public class GameService {

	private GameRepository gameRepository;
		
	@Autowired
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
	
	public void save(Game game) {
		List<Game> games = this.gameRepository.findAllByAlleyAndName(game.getAlley(), game.getName());
		Game lastGame = games.stream().max(Comparator.comparing(Game::getId)).orElse(null);
		if(!ObjectUtils.isEmpty(lastGame) && lastGame.getPins() + game.getPins() > 10) {
			throw new IllegalArgumentException("A pontuação passou de 10 pontos");
		}
		this.gameRepository.save(game);
	}
	
	public GameDTO findAllByAlley(String alley) {
		List<Game> game = this.gameRepository.findAllByAlley(alley);
		Map<String, List<Game>> players = game.stream().collect(Collectors.groupingBy(Game::getName));
		
		GameDTO gameDTO = new GameDTO(alley);
				
		players.entrySet().stream().forEach(m -> {
			PlayerDTO player = new PlayerDTO(m.getKey());
			
			for(Game g: m.getValue()) {
				if(player.getCurrentFrame().isStrike() || player.getCurrentFrame().isDone()) {
					player.addFrame(new FrameDTO());
				}
				player.getCurrentFrame().addBall(g.getPins());
			}
			
			gameDTO.addPlayer(player);
			
		});	
		
		ScoreUtils.calculate(gameDTO.getPlayers());
		
		return gameDTO;
	}
	
}
