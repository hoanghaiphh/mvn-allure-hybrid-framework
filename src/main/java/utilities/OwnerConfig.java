package utilities;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"classpath:environments/env-${environment}.properties"})
public interface OwnerConfig extends Config {

    // if property key <> method name --> add annotation @Key(propertyKey) to map value, else --> not necessary
    @Key("app.Url")
    String getAppUrl();

    @Key("app.Username")
    String getAppUsername();

    @Key("app.Password")
    String getAppPassword();

    static OwnerConfig getEnvironmentOwner() {
        String systemEnv = System.getProperty("env", "local").toLowerCase();
        ConfigFactory.setProperty("environment", systemEnv);
        return ConfigFactory.create(OwnerConfig.class);
    }

}
