package br.com.boliche.dto;

import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FrameDTO {

	private LinkedList<Integer> balls = new LinkedList<>();
	private int score;

}
