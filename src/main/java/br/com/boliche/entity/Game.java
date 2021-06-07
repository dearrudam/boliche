package br.com.boliche.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "nome do jogador deve ser informado")
    private String name;
    @NotNull(message = "quantidade de pinos derrubados deve ser informado")
    @Min(value = 0, message = "quantidade de pinos deve ser de 0 à 10")
    @Max(value = 10, message = "quantidade de pinos deve ser de 0 à 10")
    private Integer pins;
    @NotEmpty(message = "pista deve deve ser informada")
    private String alley;
}
