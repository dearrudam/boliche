package br.com.boliche.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

	private String name;
	private List<FrameDTO> frames;
	
	public PlayerDTO(String name) {
		this.name = name;
	}
	public PlayerDTO(String name, List<FrameDTO> frames) {
		this.name = name;
		this.frames = frames;
	}
	
}
