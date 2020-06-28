package org.openntf.docker.example.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.darwino.domino.napi.DominoAPI;
import com.darwino.domino.napi.DominoException;
import com.ibm.commons.util.StringUtil;

/**
 * Application Lifecycle Listener implementation class NotesRuntimeListener
 *
 */
@WebListener
public class NotesRuntimeListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String notesProgramDir = System.getenv("Notes_ExecDirectory"); //$NON-NLS-1$
		String notesIniPath = System.getenv("NotesINI"); //$NON-NLS-1$ 
		if (StringUtil.isNotEmpty(notesProgramDir)) {
			String[] initArgs = new String[] {
					notesProgramDir,
					StringUtil.isEmpty(notesIniPath) ? "" : ("=" + notesIniPath) //$NON-NLS-1$ //$NON-NLS-2$ 
			};
			
			try {
				DominoAPI.get().NotesInitExtended(initArgs);
			} catch (DominoException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new IllegalStateException("Unable to locate Notes runtime");
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DominoAPI.get().NotesTerm();
	}
}
