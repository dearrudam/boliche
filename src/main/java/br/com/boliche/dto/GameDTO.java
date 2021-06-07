package br.com.boliche.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {
	
	private String alley;
	private List<PlayerDTO> players;

}
