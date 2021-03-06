package edu.warbot.scriptcore.exceptions;

public class UnrecognizedScriptLanguageException extends Exception {

	public UnrecognizedScriptLanguageException(String script, String name) 
	{
		super("The specified script language \""+script+"\" from team : \""
	+name+"\" is not currently available on this version of warbot.");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
