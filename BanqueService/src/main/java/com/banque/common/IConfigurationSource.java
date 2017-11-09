/*
 * $Id$
 * Créé le 20 oct. 2008
 * Projet EUROPAGES CORE
 * Copyright (c) 2008 EUROPAGES France - Tous droits réservés.
 */
package com.banque.common;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.banque.common.ConfigConst.Operator;
import com.banque.common.ConfigConst.Root;
import com.banque.common.ConfigConst.TestEnv;
import com.banque.service.exception.TechnicalException;

/**
 * The Interface IConfigurationSource.
 */
public interface IConfigurationSource {
    // Generic methods : lifecycle management & anonymous properties
    /**
     * Reload.
     */
    public void reload();

    /**
     * Appends a configuration to the current loaded.
     *
     * @param configFile
     * @throws TechnicalExceptioh
     */
    public void appendConfiguration(File configFile) throws TechnicalException;

    /**
     * Dump the configuration to stdout if the env=DEV and also returns the
     * string
     *
     * @return the string
     */
    public String dump();

    /**
     * Gets the string element.
     *
     * @param path the path
     *
     * @return the string element
     */
    public String getStringElement(String path);

    public String getStringElement(String path, String defaultValue);
    
    public String getStringElementByDefaultPath(String path, String defaultpath);
    
    /**
     * Gets the boolean element.
     *
     * @param path the path
     *
     * @return the boolean element
     */
    public Boolean getBooleanElement(String path);

    public Boolean getBooleanElement(String path, Boolean defaultValue);

    /**
     * Gets the integer element.
     *
     * @param path the path
     *
     * @return the integer element
     */
    public Integer getIntegerElement(String path);

    public Integer getIntegerElement(String path, Integer defaultalue);

    /**
     * Gets the double element.
     *
     * @param path the path
     *
     * @return the double element
     */
    public Double getDoubleElement(String path);

    public Double getDoubleElement(String path, Double defaultValue);

    /**
     * Gets the list element.
     *
     * @param path the path
     *
     * @return the list element
     */
    public List getListElement(String path);

    public List getListElement(String path, List defaultValue);
    
    
    public Map getMapElement(String path);
    
    public Map getMapElement(String path, Map defaultValue);
    
    /**
     * Gets the float element.
     *
     * @param path the path
     *
     * @return the float element
     */
    public Float getFloatElement(String path);

    public Float getFloatElement(String path, Float defaultValue);

    public String[] getStringArray(String path);

    // Specific methods
    /**
     * Gets the env.
     *
     * @return the env
     */
    public TestEnv getEnv();

    /**
     * Checks if is captcha activated.
     *
     * @return true, if is captcha activated
     */
    public boolean isCaptchaActivated();

    /**
     * Gets the context blacklist.
     *
     * @return the context blacklist
     */
    public List<String> getContextBlacklist();

    /**
	 * Gets tapestry cache parameter.
	 *
	 * @param parameter
	 *            the parameter to retrieve.
	 *
	 * @return the parameter value.
	 */
	public Object getTapestryCacheParameter(String parameter);

	/**
	 * Gets parameters which have several values.
	 * @param parameter the parameter representing the values to retrieve.
	 * @return values the values according to the parameter.
	 */
	public List<String> getTapestryCacheValues(String parameter);   
      
    public Map<String,String> dumpByMap();
    
    public Map<String,String> dumpByMap(String param);
    
    public Map<String,String> dumpByMap(TestEnv env);
            
    /**
     * Get a string associated with the given Root configuration key and the propertyName 
     * 
     * By default is looking the path with <env>(euredit.env) if, the path doesnt exist it will look without it.
     *  
     *  XPATH: 
     *  	root/propertyName/{env} 
     *  	root/propertyName
     *  
     * @param root : root node
     * @param propertyName: node name
     * 
     * @return the value
     * 
     * @throws NoSuchElementException : if the path doesnt exist
     * 
     */
    public String get(Root root, String propertyName);
    
    
    /**
     * Get a string associated with the given Root , path and propertyName  
     * 
     * By default is looking the path with <env>(euredit.env) if, the path doesnt exist it will look without it.
     * 
     * XPATH: 
     * 		root/path/{env}/propertyName 
     * 		root/path/propertyName
     * 
     *  
     * @param root : root node
     * @param path : path node
     * @param propertyName: node name
     * 
     * @return the value
     * 
     * @throws NoSuchElementException : if the path doesnt exist
     * 
     */
    public String get(Root root, String path, String propertyName);
    
    /**
     * Get a string associated with the given Root and propertyName. It applies arguments (xpath attributes to the propertyName)
     * 
     * By default is looking the path with <env>(euredit.env) if, the path doesnt exist it will look without it.
     * 
     * XPATH: 
     * 		root/propertyName[@argKey='argValue']/{env}
     * 		root/path/propertyName[@argKey='argValue']
     * 
     *  
     * @param root : root node
     * 
     * @param propertyName: node name
     * @param args: map of xpath attributes (key = attribute name, value = attribute value)
     * 
     * @return the value
     * 
     * @throws NoSuchElementException : if the path doesnt exist
     * 
     */
    public String get(Root root, String propertyName, Map<String,String> args, Operator operator);
    
    /**
     * Get a string associated with the given Root, path and propertyName. It applies arguments (xpath attributes to the propertyName)
     * 
     * By default is looking the path with <env>(euredit.env) if, the path doesnt exist it will look without it.
     * 
     * XPATH : 
     *  	root/path/{env}/propertyName[@argKey='argValue'] 
     *  	root/path/propertyName[@argKey='argValue']
     * 
     *  
     * @param root : root node
     * @param path : path node
     * @param propertyName: node name
     * @param args: map of xpath attributes (key = attribute name, value = attribute value)
     * 
     * @return the value (string value)
     * 
     * @throws NoSuchElementException : if the path doesnt exist
     * 
     */
    public String get(Root root, String path, String propertyName, Map<String,String> args, Operator operator);
    
    /**
     * Get a integer - based on same rule of the get(...) function
     * 
     * @param root : root node
     * @param propertyName: node name
     * 
     * @return
     */
    public Integer getInteger(Root root, String propertyName);
    
    /**
     * Get a integer - based on same rule of the get(...) function
     * 
     * @param root : root node
     * @param path : path node
     * @param propertyName: node name
     * 
     * @return
     */
    public Integer getInteger(Root root, String path, String propertyName);
    
    /**
     * Get a boolean - based on same rule of the get(...) function
     * 
     * @param root : root node
     * @param propertyName: node name
     * 
     * @return
     */
    public Boolean getBoolean(Root root, String propertyName);
    
    /**
     * Get a boolean - based on same rule of the get(...) function
     * 
     * @param root : root node
     * @param path : path node
     * @param propertyName: node name
     * 
     * @return
     */
    public Boolean getBoolean(Root root, String path, String propertyName);
    
    /**
     * Get a List - based on same rule of the get(...) function
     * 
     * @param root : root node
     * @param propertyName: node name
     * 
     * @return
     */
    public List<String> getList(Root root, String propertyName);
    
    
}
