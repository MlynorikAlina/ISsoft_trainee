package by.issoft.domain.carriages;

import by.issoft.domain.users.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Locomotive extends Carriage {
    private static final Logger logger = LoggerFactory.getLogger(Locomotive.class);
    private Driver driver;

    public Locomotive(Driver driver) {
        logger.debug("creating Locomotive " + this.toString());
        driver.validate();
        this.driver = driver;
    }

    public void setDriver(Driver driver) {
        driver.validate();
        this.driver = driver;
    }
}
