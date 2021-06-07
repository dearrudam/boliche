package br.com.boliche.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

	private String name;
	private List<FrameDTO> frames;
	
	public PlayerDTO(String name) {
		this.name = name;
		this.addFrame(new FrameDTO());
	}
	
	public void addFrame(FrameDTO frame) {
		if(ObjectUtils.isEmpty(this.frames)) {
			this.frames = new ArrayList<FrameDTO>();
		}
		
		this.frames.add(frame);
	}
	
	@JsonIgnore
	public FrameDTO getCurrentFrame() {
		if(!ObjectUtils.isEmpty(this.frames)) {
			return this.frames.get(this.frames.size() - 1);
		}
		return null;
	}
	
}
