package configs;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${env}.properties",
        "classpath:test.properties"
})


public interface TestPropertiesConfig extends org.aeonbits.owner.Config {
    @Key("baseUrl")
    String getBaseUrl();
}


