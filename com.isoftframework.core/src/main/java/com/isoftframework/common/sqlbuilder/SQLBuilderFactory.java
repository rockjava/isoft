/*
 * Created on 2005-6-17
 */
package com.isoftframework.common.sqlbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author wzq
 * 单例工厂
 *
 */
public class SQLBuilderFactory {
	
	private static SQLBuilder builder = null;

	public static SQLBuilder getSQLBuilder() {
		if(builder == null){
			builder = new OracleSQLBuilder();
		}
		return builder;
	}

}
