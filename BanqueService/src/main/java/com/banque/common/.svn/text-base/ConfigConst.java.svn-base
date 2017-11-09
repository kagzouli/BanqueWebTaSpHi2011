/*
 * $Id$
 * Créé le 22 oct. 2008
 * Projet EUROPAGES CORE
 * Copyright (c) 2008 EUROPAGES France - Tous droits réservés.
 */
package com.banque.common;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigConst.
 */
public class ConfigConst {

    /** The Constant ENV_PROPERTY_NAME. */
    public static final String ENV_PROPERTY_NAME = "test.env";
    
    public static final String CONFIGURATION_SOURCE_BEAN = "configurationSource";

    /**
     * The Enum EuropagesEnv.
     */
    public enum TestEnv {

	test("test"), local("local"), dev("dev"), integ("integ"),recette("recette"),prod("prod");

	protected String label;

	/**
	 * Instantiates a new europages env.
	 * 
	 * @param plabel
	 *            the plabel
	 */
	TestEnv(String plabel) {
	    this.label = plabel;
	}

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
	    return this.label;
	}
    }

    /**
     * The Enum RfiLogType.
     */
    public enum RfiLogType {

	simple("simple"), multi("multi"), error("error"), prospect("prospect"), statsTmp("stats_tmp");

	/** The type. */
	protected String type;

	/**
	 * Instantiates a new rfi log type.
	 * 
	 * @param type
	 *            the type
	 */
	RfiLogType(String type) {
	    this.type = type;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
	    return this.type;
	}

    }

    public enum Root {

	app("app"), webapp("webapp"), database("database"), common("common"), unknow("unknow");

	protected String label;

	Root(String label) {
	    this.label = label;
	}

	public String toString() {
	    return this.label;
	}

    }

    public enum Operator {

	and("and"), or("or");

	protected String label;

	Operator(String label) {
	    this.label = label;
	}
	
	public String toString(){
	    return this.label;
	}
    }
    
}
