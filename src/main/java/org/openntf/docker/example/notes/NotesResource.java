package org.openntf.docker.example.notes;

import java.io.IOException;

import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.darwino.domino.napi.DominoAPI;
import com.darwino.domino.napi.DominoException;

@Path("/")
public class NotesResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object get() throws IOException, DominoException {
		
		return Json.createObjectBuilder()
			.add("idUsername", DominoAPI.get().SECKFMGetUserName()) //$NON-NLS-1$
			.build();
	}

}
