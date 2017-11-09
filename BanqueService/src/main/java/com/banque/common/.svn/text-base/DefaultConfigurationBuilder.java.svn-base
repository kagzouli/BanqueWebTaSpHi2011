package com.banque.common;

import java.io.File;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class DefaultConfigurationBuilder.
 * @author rleray
 */
public class DefaultConfigurationBuilder extends
		org.apache.commons.configuration.DefaultConfigurationBuilder {
	
	private static Logger log = LoggerFactory.getLogger(DefaultConfigurationBuilder.class);
	
	static{
		//Setting the delimiter to 0 will disable value splitting completely
		DefaultConfigurationBuilder.setDefaultListDelimiter((char)0);
	}
	
	/**
	 * Instantiates a new default configuration builder.
	 */
	public DefaultConfigurationBuilder() {
	}

	/**
	 * Instantiates a new default configuration builder.
	 * 
	 * @param file the file
	 */
	public DefaultConfigurationBuilder(File file) {
		super(file);
	}

	/**
	 * Instantiates a new default configuration builder.
	 * 
	 * @param fileName the file name
	 * 
	 * @throws ConfigurationException the configuration exception
	 */
	public DefaultConfigurationBuilder(String fileName)
			throws ConfigurationException {
		super(fileName);
	}

	/**
	 * Instantiates a new default configuration builder.
	 * 
	 * @param url the url
	 * 
	 * @throws ConfigurationException the configuration exception
	 */
	public DefaultConfigurationBuilder(URL url) throws ConfigurationException {
		super(url);
	}

}
