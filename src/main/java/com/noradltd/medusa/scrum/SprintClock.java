package com.noradltd.medusa.scrum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SprintClock implements SprintResultListener {

	private InputStream stream;
	private Integer cycles;
	private final Set<SprintResultListener> resultListeners = new HashSet<SprintResultListener>();

	public SprintClock() {
		addResultListener(this);
	}
	
	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public void run() throws IOException {
		cycles = 0;
		String json = readEntireInputStream();
		try {
			JsonElement element = new JsonParser().parse(json);
			if (element.isJsonArray()) {
				JsonArray sprints = element.getAsJsonArray();
				for (int idx = 0; idx < sprints.size(); idx++) {
					JsonObject sprint = sprints.get(idx).getAsJsonObject();
					new SprintRunner(sprint, resultListeners).runSprint();
				}
			}
		} catch (JsonSyntaxException e) {
			System.err.println("Input was not in a readable JSON format");
		}
	}
	
	@Override
	public void sprintComplete(SprintResult result) {
		if (getCardCount(result) > 0) {
			cycles++;
		}
	}

	private int getCardCount(SprintResult result) {
		return new JsonParser().parse(result.getOriginalSprintData()).getAsJsonObject().get("cards").getAsJsonArray().size();
	}
	
	private String readEntireInputStream() throws IOException {
		BufferedReader reader = createInputStreamReader();
		StringBuilder sb = new StringBuilder();
		try {
			boolean fini = false;
			while (reader.ready() && !fini) {
				String line = reader.readLine();
				if (null == line) {
					fini = true;
				} else {
					sb.append(line);
				}
			}
		} finally {
			reader.close();
		}
		return sb.toString();
	}

	private BufferedReader createInputStreamReader() {
		return new BufferedReader((null == stream) ? new StringReader("") : new InputStreamReader(stream));
	}

	public Integer getCycles() {
		return cycles;
	}

	public void addResultListener(SprintResultListener sprintResultListener) {
		resultListeners.add(sprintResultListener);
	}

}
