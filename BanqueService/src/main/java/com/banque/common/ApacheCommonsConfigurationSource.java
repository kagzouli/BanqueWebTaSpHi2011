/*
 * $Id$
 * Créé le 20 oct. 2008
 * Projet EUROPAGES CORE
 * Copyright (c) 2008 EUROPAGES France - Tous droits réservés.
 */
package com.banque.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.event.ConfigurationErrorEvent;
import org.apache.commons.configuration.event.ConfigurationErrorListener;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import com.banque.common.ConfigConst.Operator;
import com.banque.common.ConfigConst.Root;
import com.banque.common.ConfigConst.TestEnv;
import com.banque.service.exception.TechnicalException;

/**
 * The Class ApacheCommonsConfigurationSource.
 */
final class ApacheCommonsConfigurationSource implements IConfigurationSource {

    /** The logger. */
    private Logger logger = Logger.getLogger(ApacheCommonsConfigurationSource.class);

    /** The config. */
    private CombinedConfiguration config;

    /** The Constant DELIMITER. */
    private static final String DELIMITER = ",";

    private static final String SEPARATOR = "=";

    /**
     * Sets the config.
     * 
     * @param config
     *            the new config
     */
    public void setConfig(CombinedConfiguration config) {
          this.config = config;
    }

    /**
     * Gets the config.
     * 
     * @return the config
     */
    public CombinedConfiguration getConfig() {
        return config;
    }

    /**
     * Instantiates a new apache commons configuration source.
     */
    protected ApacheCommonsConfigurationSource() {
    }

    /**
     * Gets the configuration as a Properties map
     * 
     * @return the properties
     */
    public Properties getAsProperties() {
        Properties properties;
        try {
             ConfigurationConverter.getProperties(getConfig());
        } catch (Exception e) {
            logger.warn("Some configuration contains invalid XPath", e);
           Iterator keys = config.getKeys();
           String key = null;
            while (keys.hasNext()) {
                try {
                    key = (String) keys.next();
                    config.getList(key);
                } catch (Exception e2) {
                    logger.warn("Removing key [" + key + "] from CombinedConfiguration");
                    config.clearProperty(key);
                }
            }
        }
        properties = ConfigurationConverter.getProperties(getConfig());
        return properties;
    }

    /**
     * Inits the ApacheCommonsConfigurationSource
     */
    protected void init() {
        //if no value find with the getters method / throw an exception NoSuchElementException
        getConfig().setThrowExceptionOnMissing(true);

        //set the XPath engine, the path is by default a XPATH query
        getConfig().setExpressionEngine(new XPathExpressionEngine());

        if (TestEnv.dev.getLabel().equals(System.getProperty(ConfigConst.ENV_PROPERTY_NAME))) {
            // FIXED This doesn't work with version 1.5
            // http://issues.apache.org/jira/browse/CONFIGURATION-348
            getConfig().setForceReloadCheck(true);
        }
        
        config.addErrorListener(new ConfigurationErrorListener() {
            public void configurationError(ConfigurationErrorEvent event) {
                 logger.error("Configuration error on : " + event.getPropertyName());
            }
        });
    }

    public void appendConfiguration(File configFile) throws TechnicalException {
        try {
            XMLConfiguration newConfig = new XMLConfiguration();
            newConfig.setFile(configFile);
            newConfig.setExpressionEngine(new XPathExpressionEngine());
            newConfig.load();
            getConfig().addConfiguration(newConfig);
        } catch (Exception e) {
            throw new TechnicalException("Cannot append configuration : "+e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.europages.configuration.IConfigurationSource#dump()
     */
    public String dump() {
        Iterator<String> it = getConfig().getKeys();
        StringBuffer dump = new StringBuffer();
        dump.append("Configuration dump :\n");
        while (it.hasNext()) {
            String key = it.next();
            Object value = getConfig().getProperty(key);
            dump.append(key).append(" => ").append(value).append("\n");
        }
        dump.append("Configuration dump end:\n");
        if (TestEnv.dev.equals(getEnv()))
            System.out.println(dump);
        logger.info(dump.toString());
        return dump.toString();
    }

    public Map<String, String> dumpByMap() {
        return dumpByMap((String) null);
    }

    public Map<String, String> dumpByMap(String param) {
        Map<String, String> map = new LinkedHashMap<String, String>();

        Iterator<String> it = getConfig().getKeys();
        while (it.hasNext()) {
            String key = it.next();

            if (param == null || StringUtils.containsIgnoreCase(key, param)) {
                Object value = getConfig().getProperty(key);
                map.put(key, value.toString());
            }
        }

        return map;
    }

    public Map<String, String> dumpByMap(TestEnv env) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        Iterator<String> it = getConfig().getKeys();

        while (it.hasNext()) {
            String key = it.next();

            if (StringUtils.contains(key, env.toString())) {
                Object value = getConfig().getProperty(key);
                map.put(key, value.toString());
            }
        }

        return map;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.europages.configuration.IConfigurationSource#getStringElement(java
     * .lang.String)
     */
    public String getStringElement(String path) {
        return getConfig().getString(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.europages.configuration.IConfigurationSource#getBooleanElement(java
     * .lang.String)
     */
    public Boolean getBooleanElement(String path) {
        return getConfig().getBoolean(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.europages.configuration.IConfigurationSource#getIntegerElement(java
     * .lang.String)
     */
    public Integer getIntegerElement(String path) {
        return getConfig().getInt(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.europages.configuration.IConfigurationSource#getDoubleElement(java
     * .lang.String)
     */
    public Double getDoubleElement(String path) {
        return getConfig().getDouble(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.europages.configuration.IConfigurationSource#getListElement(java.
     * lang.String)
     */
    public List<String> getListElement(String path) {
        String value = getStringWithoutException(path);

        if (null != value) {
            // permit to manage the delimiter, by default the delimiter (comma)
            // is disabled
            List<String> list = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(value, DELIMITER);

            while (st.hasMoreTokens()) {
                String str = st.nextToken();
                if (StringUtils.isNotEmpty(str)) {
                    list.add(str.trim());
                }
            }

            return list;
        } else {
            return null;
        }
    }

    public Map getMapElement(String path) {
        String value = getConfig().getString(path);

        Map map = new TreeBidiMap();
        StringTokenizer st = new StringTokenizer(value, DELIMITER);

        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            if (StringUtils.isNotEmpty(str)) {
                StringTokenizer st2 = new StringTokenizer(str, SEPARATOR);
                map.put(st2.nextToken(), st2.nextToken());
            }
        }

        return map;
    }

    public Map getMapElement(String path, Map defaultValue) {

        Map map = null;
        try {
            map = getMapElement(path);
        } catch (NoSuchElementException e) {
            map = new TreeBidiMap(defaultValue);
        }

        return map;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.europages.configuration.IConfigurationSource#getFloatElement(java
     * .lang.String)
     */
    public Float getFloatElement(String path) {
        return getConfig().getFloat(path);
    }

    public Boolean getBooleanElement(String path, Boolean defaultValue) {
        return getConfig().getBoolean(path, defaultValue);
    }

    public Double getDoubleElement(String path, Double defaultValue) {
        return getConfig().getDouble(path, defaultValue);

    }

    public Float getFloatElement(String path, Float defaultValue) {
        return getConfig().getFloat(path, defaultValue);
    }

    public Integer getIntegerElement(String path, Integer defaultValue) {
        return getConfig().getInteger(path, defaultValue);

    }

    public List getListElement(String path, List defaultValue) {
        List list = null;

        try {
            list = getListElement(path);
        } catch (NoSuchElementException e) {
            list = defaultValue;
        }

        return list;

    }

    public String getStringElement(String path, String defaultValue) {
        return getConfig().getString(path, defaultValue);
    }

    public String getStringElementByDefaultPath(String path, String defaultpath) {
        String value = getStringWithoutException(path);

        if (value == null) {
            value = getConfig().getString(defaultpath);
        }

        return value;
    }

    public String[] getStringArray(String path) {
        return getConfig().getStringArray(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.europages.configuration.IConfigurationSource#reload()
     */
    public void reload() {
        getConfig().invalidate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.europages.configuration.IConfigurationSource#getEnv()
     */
    public TestEnv getEnv() {
        TestEnv env = null;
        try {
            env = TestEnv.valueOf(getConfig().getString("test/env"));
        } catch (Exception e) {
            // If no env is set there is nothing to do...
            throw new RuntimeException("Environment unknown : " + getConfig().getString("test/env")+ ": "+e.getMessage());
        }
        return env;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.europages.configuration.IConfigurationSource#isCaptchaActivated()
     */
    public boolean isCaptchaActivated() {
        boolean activated = getConfig().getBoolean("security/captcha[@env='" + getEnv().getLabel() + "']/activated");
        return activated;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.europages.configuration.IConfigurationSource#getContextBlacklist()
     */
    public List<String> getContextBlacklist() {
        return getListElement("webapp/context-blacklist/context");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.europages.configuration.IConfigurationSource#getCountriesSpeakingLanguage
     * (java.lang.String)
     */

    public Object getTapestryCacheParameter(String parameter) {
        return getConfig().getProperty("webapp/tapestry-cache/" + getEnv().getLabel() + "/" + parameter);

    }

    @SuppressWarnings("unchecked")
    public List<String> getTapestryCacheValues(String parameter) {
        return (List<String>) getConfig().getList("webapp/tapestry-cache/" + getEnv().getLabel() + "/" + parameter + "/value");
    }

    private String getStringWithoutException(String path) {
        String value = null;
        try {
            value = getConfig().getString(path);
        } catch (NoSuchElementException e) {
            // do nothing
        }
        return value;
    }

    private String buildArgs(Map<String, String> args, Operator operator) {

        try {
            Validate.notEmpty(args);
        } catch (IllegalArgumentException e) {
            return "";
        }

        StringBuffer buffArgs = new StringBuffer("[");

        int count = 0;
        for (Entry<String, String> arg : args.entrySet()) {
            buffArgs.append(String.format("@%1s='%2s'", arg.getKey(), arg.getValue()));
            count++;

            if (args.size() > count) {
                buffArgs.append(" " + operator + " ");
            }
        }

        buffArgs.append("]");

        return buffArgs.toString();
    }

    public String get(Root root, String path, String propertyName, Map<String, String> args, Operator operator) {

        String arg = buildArgs(args, operator);
        String nodePath = StringUtils.isNotBlank(path) ? "/"+ path + arg : "";
        String node = StringUtils.isNotBlank(propertyName) ? "/" + propertyName : "";
        
        String keyBeforeEnv = root  + nodePath + "/"  + getEnv().toString() + node;
        String keyAfterEnv = root  + nodePath   + node + "/" +getEnv().toString();
        String defaultKey = root + nodePath + node;
        
        String value = null;

        try {
            value = getStringWithoutException(keyBeforeEnv);
            
            if(StringUtils.isBlank(value)){
                value = getConfig().getString(keyAfterEnv);
            }
            
        } catch (NoSuchElementException e) {
            value = getConfig().getString(defaultKey);
        }

        return value;
    }

    public String get(Root root, String propertyName, Map<String, String> args, Operator operator) {
        return get(root, null, propertyName, args, operator);
    }

    public String get(Root root, String path, String propertyName) {
        return get(root, path, propertyName, null, null);
    }

    public String get(Root root, String propertyName) {
        return get(root, "", propertyName, null, null);
    }

    public Integer getInteger(Root root, String propertyName) {
        return new Integer((String) get(root, propertyName));
    }

    public Integer getInteger(Root root, String path, String propertyName) {
        return new Integer((String) get(root, path, propertyName));
    }

    public Boolean getBoolean(Root root, String propertyName) {
        return new Boolean((String) get(root, propertyName));
    }

    public Boolean getBoolean(Root root, String path, String propertyName) {
        return new Boolean((String) get(root, path, propertyName, null, null));
    }

    public List<String> getList(Root root, String propertyName) {
        String value = (String) get(root, propertyName);

        List<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(value, DELIMITER);

        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            if (StringUtils.isNotEmpty(str)) {
                list.add(str.trim());
            }
        }

        return list;
    }
}
