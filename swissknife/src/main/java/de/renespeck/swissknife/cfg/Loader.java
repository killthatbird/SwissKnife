package de.renespeck.swissknife.cfg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Loader {

    public static final Logger  LOG           = LogManager.getLogger(Loader.class); ;
    protected static Properties properties = null;

    /**
     * Loads a given file to use as properties.
     * 
     * @param cfgFile
     *            properties file
     */
    public static boolean loadFile(String cfgFile) {
        boolean loaded = false;
        LOG.info("Loads cfg ...");

        properties = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(cfgFile);
        } catch (FileNotFoundException e) {
            LOG.error("file: " + cfgFile + " not found!");
        }
        if (in != null) {
            try {
                properties.load(in);
                loaded = true;
            } catch (IOException e) {
                LOG.error("Can't read `" + cfgFile + "` file.");
            }
            try {
                in.close();
            } catch (Exception e) {
                LOG.error("Something went wrong.\n", e);
            }
        } else {
            LOG.error("Can't read `" + cfgFile + "` file.");
        }

        return loaded;
    }

    /**
     * Gets a property.
     * 
     * @param key
     *            property key
     * @return property value
     */
    public static String get(String key) {
        if (properties == null)
            loadFile(Const.CFG_FILE);
        return properties.getProperty(key);
    }

    /**
     * Gets an object of the given class.
     * 
     * @param classPath
     *            path to class
     * @return object of a class
     * @throws LoadingNotPossibleException
     */
    public synchronized static Object getClass(String classPath) {
        LOG.info("Loading class: " + classPath);

        try {
            Class<?> clazz = null;
            clazz = Class.forName(classPath.trim());
            Constructor<?> constructor;
            constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}