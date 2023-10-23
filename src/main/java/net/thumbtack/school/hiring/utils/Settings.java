package net.thumbtack.school.hiring.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilder;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class Settings {
    private DaoType daoType;
    private static Settings instance;

    public static Settings getInstance(){
        if (instance == null) {
            instance = new Settings();
            instance.setParams();
        }
        return instance;
    }

    public void setParams(){
        try {
            Parameters params = new Parameters();
            File propertiesFile = new File("database.properties");

            ConfigurationBuilder<PropertiesConfiguration> configure =
                    new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                            .configure(params.fileBased().setFile(propertiesFile));

            Configuration configuration = configure.getConfiguration();
            String type = configuration.getString("dao.type");
            if (type.equals("sql")) {
                setDaoType(DaoType.SQL);
            } else {
                setDaoType(DaoType.COLLECTIONS);
            }
        } catch (ConfigurationException e) {
            System.out.println("Error reading properties file");
        }
    }

    public DaoType getDaoType() {
        return daoType;
    }

    public void setDaoType(DaoType daoType) {
        this.daoType = daoType;
    }
}
