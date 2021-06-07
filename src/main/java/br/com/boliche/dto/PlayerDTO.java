package br.com.boliche.dto;

import br.com.boliche.entity.Game;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.LinkedList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(
    of = { "name" }
)
public class PlayerDTO {
    private String name;
    private LinkedList<FrameDTO> frames;

    public PlayerDTO(String name) {
        this(name, new LinkedList<>());
    }

    public PlayerDTO(String name, LinkedList<FrameDTO> frames) {
        this.name = name;
        this.frames = frames;
    }

    @JsonIgnore
    public boolean fezStrike() {
        final FrameDTO last = getLastFrame();
        if (last == null) {
            return false;
        }
        if (hasStrikeLasBall(last)) {
            return true;
        }
        return false;
    }

    private boolean hasStrikeLasBall(final FrameDTO last) {
        return !last.getBalls().isEmpty() && last.getBalls().getLast() == 10;
    }

    @JsonIgnore
    public boolean podeJogarNovamente() {
        final FrameDTO last = getLastFrame();
        if (last == null) {
            return true;
        }
        if (!this.ehUltimaRodada()
            && (hasStrikeLasBall(last)
            || last.getBalls().size() == 2)) {
            return false;
        }
        if ((this.ehUltimaRodada()
            && last.getBalls().size() == 3)
            || (this.ehUltimaRodada()
            && last.getBalls().size() == 2
            && !hasStrikeLasBall(last)
            && !hasSpire(last))) {
            return false;
        }
        return true;
    }

    private FrameDTO getLastFrame() {
        return this.getFrames().isEmpty() ? null : this.getFrames().getLast();
    }

    @JsonIgnore
    public boolean ehUltimaRodada() {
        return this.getFrames().size() == 10;
    }

    public void adicionarJogada(final Game jogada) {
        final FrameDTO last = getLastFrame();
        if (last == null) {
            FrameDTO frame = new FrameDTO();
            add(false,jogada, frame);
            this.getFrames().add(frame);
            this.calculateScores();
            return;
        }
        // programação defensiva - não deve lançar exceção por cauda do método podeJogarNovamente
        if (this.ehUltimaRodada()
            && last.getBalls().size() == 3) {
            throw new RuntimeException("jogada inválida. O jogador "
                                           + this.name + " já encerrou suas jogadas");
        }
        // programação defensiva - não deve lançar exceção por cauda do método podeJogarNovamente
        if (this.ehUltimaRodada()
            && last.getBalls().size() < 3
            && !hasStrikeLasBall(last)
            && !hasSpire(last)
        ) {
            throw new RuntimeException("jogada inválida. O jogador "
                                           + this.name + " já encerrou suas jogadas");
        }
        // 1o. e 2 BOLA da última rodada
        if (this.ehUltimaRodada()
            && last.getBalls().size() < 2) {
            add(true,jogada, last);
            this.calculateScores();
            return;
        }
        // 3o. BOLA da última rodada
        if (this.ehUltimaRodada()
            && last.getBalls().size() == 2
            && (hasSpire(last) || hasStrikeLasBall(last))) {
            add(true,jogada, last);
            this.calculateScores();
            return;
        }
        // 2o. BOLA de rodada corrente
        if (!this.ehUltimaRodada()
            && last != null
            && !hasStrikeLasBall(last)
            && last.getBalls().size() < 2) {
            add(false,jogada, last);
            this.calculateScores();
            return;
        }
        // 1o. BOLA de uma nova rodada
        if (!this.ehUltimaRodada()
            && last != null
            && (hasStrikeLasBall(last)
            || last.getBalls().size() == 2)) {
            FrameDTO frame = new FrameDTO();
            add(false,jogada, frame);
            this.getFrames().add(frame);
            this.calculateScores();
            return;
        }
    }

    private void add(final boolean ultimaJogada, final Game jogada, final FrameDTO frame) {
        frame.getBalls().add(jogada.getPins());
        final Integer totalPins = frame.getBalls().stream().reduce(0, (a, b) -> a + b);
        if (!ultimaJogada && totalPins > 10) {
            throw new RuntimeException("javaga inválida. Número total de pins inválido");
        }
    }

    private void calculateScores() {
    }

    private boolean hasSpire(final FrameDTO frame) {
        final LinkedList<Integer> balls = new LinkedList<>(frame.getBalls());
        return balls.size() >= 2 && (balls.removeLast() + balls.removeLast() == 10);
    }
}