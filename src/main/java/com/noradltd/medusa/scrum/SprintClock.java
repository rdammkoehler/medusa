package com.noradltd.medusa.scrum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SprintClock {

	private InputStream stream;
	private Integer cycles;

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
					runSprint(sprint);
				}
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	private void runSprint(JsonObject sprint) {
		JsonArray days = sprint.getAsJsonArray("days");
		if (0 < days.size()) {
			cycles++;
		}
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

}
