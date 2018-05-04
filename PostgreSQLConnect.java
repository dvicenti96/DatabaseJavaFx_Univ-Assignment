/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputOutput;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dvice
 */
public class PostgreSQLConnect {
    Connection connect;
    
    private static final Logger logger = Logger.getLogger(PostgreSQLConnect.class.getName());

    /**
     *
     * @param connData
     */
    public PostgreSQLConnect(ConnectionData connData)
   {
       try
       {
           Class.forName(connData.getType());
           connect = DriverManager.getConnection(connData.toString(), connData.getLogin(), connData.getPassword());
       }
       catch(Exception excep)
       {
           logger.log(Level.SEVERE, null, excep);
       }
       logger.log(Level.INFO, "Successfully Established Connection");
   }
   
    /**
     *
     * @return
     */
    public Connection getConnection()
   {
       return connect;
   }
}
