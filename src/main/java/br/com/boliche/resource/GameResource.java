package br.com.boliche.resource;

import javax.validation.Valid;
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
@RequestMapping("/game")
public class GameResource {
    private GameService gameService;
    private GameRepository gameRepository;

    @Autowired
    public GameResource(GameRepository gameRepository, GameService gameService) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

    @GetMapping("/{alley}/score")
    public GameDTO findAll(@PathVariable String alley) {
        return this.gameService.findAllByAlley(alley);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid Game score) {
        this.gameService.save(score);
    }

    @DeleteMapping("/{alley}")
    @ResponseStatus(HttpStatus.CREATED)
    public void delete(@PathVariable String alley) {
        this.gameRepository.findAllByAlley(alley)
            .stream()
            .forEach(this.gameRepository::delete);
    }
}
