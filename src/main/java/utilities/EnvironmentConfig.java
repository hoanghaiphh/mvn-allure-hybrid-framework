package utilities;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:environments/env-${environment}.properties"})
public interface EnvironmentConfig extends Config {

    // if property key <> method name --> add annotation @Key(propertyKey) to map value, else --> not necessary

    @Key("App.Url")
    String getUrl();

    @Key("App.User")
    String getUsername();

    @Key("App.Pass")
    String getPassword();

}
