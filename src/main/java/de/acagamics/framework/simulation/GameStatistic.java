package de.acagamics.framework.simulation;

import de.acagamics.framework.interfaces.Student;

import java.util.ArrayList;
import java.util.List;

public final class GameStatistic {
	
	List<?> players;
	boolean isDraw = false;
	
	public GameStatistic(boolean isDraw, List<?> players) {
		this.isDraw = isDraw;
		this.players = players;
	}
	
	public int getPlayerAmount() {
		return players.size();
	}
	
	public boolean isDraw() {
		return isDraw;
	}
	
	public List<Object> getOrderedControllers() {
		return new ArrayList<>(players);
	}

	public static String getName(Class<?> cls){
		Student student = cls.getAnnotation(Student.class);
		if(student != null && !student.name().isEmpty()){
			return student.name();
		}
		return cls.getName();
	}
}
