package br.com.boliche.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.boliche.dto.GameDTO;
import br.com.boliche.entity.Game;
import br.com.boliche.service.GameService;

@RestController
@RequestMapping("/game/{alley}/score")
public class GameResource {

	private GameService gameService;
	
	@Autowired
	public GameResource(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping
	public GameDTO findAll(@PathVariable String alley) {
		return this.gameService.findAllByAlley(alley);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody Game score) {
		this.gameService.save(score);
	}
	
}
