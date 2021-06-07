package br.com.boliche.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FrameDTO {

	private List<Integer> balls;
	private int score;

	public void addBall(Integer ball) {
		if(ObjectUtils.isEmpty(this.balls)) {
			this.balls = new ArrayList<Integer>();
		}
		
		this.balls.add(ball);
	}
	
	@JsonIgnore
	public boolean isStrike() {
		return ObjectUtils.isEmpty(this.balls) ? false : this.balls.get(0) == 10;
	}
	
	@JsonIgnore
	public boolean isDone() {
		return ObjectUtils.isEmpty(this.balls) ? false : this.balls.size() == 2;
	}
	
	@JsonIgnore
	public boolean isSpare() {
		return this.balls.stream().mapToInt(Integer::intValue).sum() == 10;
	}
	
}
