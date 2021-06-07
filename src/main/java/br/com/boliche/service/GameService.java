package br.com.boliche.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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
        final List<Game> gameState = this.gameRepository.findAllByAlley(game.getAlley());
        gameState.add(game);
        final GameDTO newStateGame = this.getGame(game.getAlley(), gameState);
        this.gameRepository.findById()
        this.gameRepository.save(game);
    }

    public GameDTO findAllByAlley(String alley) {
        List<Game> game = this.gameRepository.findAllByAlley(alley);
        GameDTO gameDTO = getGame(alley, game);
        return gameDTO;
    }

    private GameDTO getGame(final String alley, final List<Game> game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setAlley(alley);
        final LinkedHashMap<String, PlayerDTO> playersMap = new LinkedHashMap<>();
        final AtomicReference<PlayerDTO> lastPlayer = new AtomicReference<>(null);
        final AtomicInteger rodadaCount = new AtomicInteger(0);
        game.stream()
            .forEach(jogada -> {
                PlayerDTO playerDTO = playersMap.computeIfAbsent(jogada.getName(), name -> {
                    return new PlayerDTO(name);
                });
                // TODO verificar ordem das rodadas

//                if (Objects.equals(lastPlayer.get(), playerDTO)) {
//                    if (playerDTO.ehUltimaRodada()
//                        || rodadaCount.incrementAndGet() == 3) {
//                        throw new RuntimeException("jogada inválida: Jogador "
//                                                       + playerDTO.getName() +
//                                                       " não deveria ter jogado novamente");
//                    }
//                }else{
//                    rodadaCount.set(0);
//                }
//                if (!playerDTO.podeJogarNovamente()) {
//                    throw new RuntimeException("jogada inválida: Jogador "
//                                                   + playerDTO.getName() +
//                                                   " não deveria ter jogado novamente");
//                }
                playerDTO.adicionarJogada(jogada);
                lastPlayer.set(playerDTO);
            });

        gameDTO.setPlayers(playersMap.values().stream()
                               .collect(Collectors.toCollection(LinkedList::new)));
        return gameDTO;
    }
}
