package com.web;

import java.util.HashMap;
import java.util.Map;

public enum DispatchOperation {

	CREDITER("crediter"), DEBITER("debiter"), LIST_ETAT_COMPTE("listEtatCompte");

	private String codeOperation;

	private transient static Map<String, DispatchOperation> mapCatalogOperation;

	DispatchOperation(String codeOperation) {
		this.codeOperation = codeOperation;
	}

	/**
	 * Methode permettant de recuperer l'enumeration catalogue a partir de son code operation.<br/>
	 * 
	 * @param operation
	 * @return
	 */
	public static DispatchOperation getDispatchOperationFromCode(final String codeOperation) {

		if (mapCatalogOperation == null) {
			mapCatalogOperation = new HashMap<String, DispatchOperation>();
			for (DispatchOperation catalogOperation : DispatchOperation.values()) {
				mapCatalogOperation.put(catalogOperation.getCodeOperation(), catalogOperation);
			}
		}

		return mapCatalogOperation.get(codeOperation);
	}

	public String getCodeOperation() {
		return codeOperation;
	}

	public void setCodeOperation(String codeOperation) {
		this.codeOperation = codeOperation;
	}

}
