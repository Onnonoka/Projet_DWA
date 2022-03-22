package org.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Connexion à la base de BDD à partir d'un fichier XML de configuration pour JDBC
 * @author Eric
 */
public class SQLConnection {

   /**
    * Configuration JDBC
    */
    private static ConfigJDBC config = null;
    
    /**
     * Connexion à la base de données, pour réaliser les requêtes SQL
     */
    private static Connection connection = null;
    
    /**
     * Nom et emplacement du fichier XML de configuration de JDBC
     */
    private static String configFile = "src/dao/configJDBC.xml";
    
    private static void loadConfigFile() throws DAOException {
        try {
            JAXBContext jc;
            jc = JAXBContext.newInstance(ConfigJDBC.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            config = (ConfigJDBC)unmarshaller.unmarshal(new File(configFile));
        }
        catch(JAXBException ex) {
            throw new DAOException("Impossible de charger la configuration JDBC ("+ex.getMessage()+")");
        }
    }
    
    /**
     * Renvoie la connexion vers le SGBD.Fonctionne en mode singleton : la connexion est instanciée une seule fois. Utilise le fichier de config JDBC par défaut se trouvant dans le package sportsDAO des sources.
     * @return la connexion JDBC vers le SGBD
     * @throws org.dao.DAOException en cas de problème
     */
    public static Connection getConnection() throws DAOException {
        try {
            if (config == null)loadConfigFile();
            if (connection == null)
                connection = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
            return connection;
        } catch (SQLException ex) {
            throw new DAOException("Impossible de se connecter à la BDD ("+ex.getMessage()+")"); 
        }
    }
    
    /**
     * Renvoie la connexion vers le SGBD. Fonctionne en mode singleton : la connexion est instanciée une seule fois.
     * Utilise le fichier de config JDBC passé en paramètre.
     * @param configFile nom du fichier XML de connexion JDBC
     * @return la connexion JDBC vers le SGBD
     * @throws org.dao.DAOException en cas de problème
     */
    public static Connection getConnection(String configFile) throws DAOException {
        SQLConnection.configFile = configFile;
        return SQLConnection.getConnection();
    }
    
    /**
     * Ferme la connexion actuellement ouverte avec le SGBD
     */
    public static void closeConnection() {
        if (connection != null) try {
            connection.close();
        } catch (SQLException ex) {
            // rien à faire
        }
        connection = null;
    }
}
