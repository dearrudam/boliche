package br.com.boliche.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {
	
	private String alley;
	private List<PlayerDTO> players;
	
	public GameDTO(String alley) {
		this.alley = alley;
	}
	
	public void addPlayer(PlayerDTO player) {
		if(ObjectUtils.isEmpty(this.players)) {
			this.players = new ArrayList<PlayerDTO>();
		}
		
		this.players.add(player);
	}

}
