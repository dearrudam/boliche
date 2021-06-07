package br.com.boliche.resource;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.boliche.dto.GameDTO;
import br.com.boliche.entity.Game;
import br.com.boliche.repository.GameRepository;
import br.com.boliche.service.GameService;

@RestController
@RequestMapping("/game/{alley}")
public class GameResource {

	private GameService gameService;
	private GameRepository gameRepository;
	
	@Autowired
	public GameResource(GameService gameService, GameRepository gameRepository) {
		this.gameService = gameService;
		this.gameRepository = gameRepository;
	}

	@GetMapping("/score")
	public GameDTO findAll(@PathVariable String alley) {
		return this.gameService.findAllByAlley(alley);
	}
	
	@PostMapping("/score")
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody Game score) {
		this.gameService.save(score);
	}
	
	@Transactional
	@DeleteMapping
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteByAlley(@PathVariable String alley) {
		this.gameRepository.deleteAllByAlley(alley);
	}
	
}
