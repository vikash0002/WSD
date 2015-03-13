package edu.smu.tspell.wordnet.impl.file;

import edu.smu.tspell.wordnet.WordNetException;

/**
 * Generated when a problem occurs retrieving data from the WordNet database.
 * 
 * @author Shubh
 */
public class RetrievalException extends WordNetException
{

	/**
	 * Accepts a message and the original exception.
	 * 
	 * @param  message Describes the nature of the problem.
	 * @param  cause Exception that was generated when retrieving data.
	 */
	public RetrievalException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Accepts a message.
	 * 
	 * @param  message Describes the nature of the problem.
	 */
	public RetrievalException(String message)
	{
		this(message, null);
	}

}