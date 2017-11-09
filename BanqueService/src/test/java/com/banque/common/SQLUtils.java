package com.banque.common;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;

public class SQLUtils {
    
    public static void executeSqlScript(final DataSource datasource,final File[] listSqlScript) throws Exception{
        
        if ((listSqlScript != null) && (listSqlScript.length > 0)){
            Connection connection = null;
            PreparedStatement pStatement = null;
            try {
                connection = datasource.getConnection();
                
                for (File fileSqlScript : listSqlScript){
                    String sqlQuery = FileUtils.readFileToString(fileSqlScript);
                    pStatement = connection.prepareStatement(sqlQuery);
                    pStatement.executeUpdate(); 
                    pStatement.close();
                }
                
                connection.commit();
                
            } catch (Exception exception) {
                exception.printStackTrace();
                connection.rollback();
            } finally {
                if (pStatement != null){
                    pStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
        }
       
        
    }

}
