package com.noradltd.medusa.scrum;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.google.gson.GsonBuilder;

public abstract class SprintStreamifier {
	
	private SprintStreamifier() {}
	
	public static final InputStream sprintToStream(Sprint... sprints) {
		return new ByteArrayInputStream(new GsonBuilder().create().toJson(sprints).getBytes());
	}
}
