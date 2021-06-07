package br.com.boliche.util;

import java.util.List;

import org.springframework.util.ObjectUtils;

import br.com.boliche.dto.FrameDTO;
import br.com.boliche.dto.PlayerDTO;

public class ScoreUtils {

	public static void calculate(List<PlayerDTO> players) {
		for(PlayerDTO player: players) {
			FrameDTO lastFrame = null;
			for(FrameDTO frame: player.getFrames()) {
				calculate(frame);
				if(!ObjectUtils.isEmpty(lastFrame)) {
					if(lastFrame.isStrike()) {
						lastFrame.setScore(lastFrame.getScore() + frame.getScore());
					} else if(lastFrame.isSpare()) {
						lastFrame.setScore(lastFrame.getScore() + frame.getBalls().get(0));
					}
					frame.setScore(lastFrame.getScore() + frame.getScore());					
				}
				lastFrame = frame;
			}
		}
	}
	
	private static void calculate(FrameDTO frame) {
		frame.setScore(frame.getBalls().stream().mapToInt(Integer::intValue).sum());
	}
	
}
