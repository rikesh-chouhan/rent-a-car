package rent.a.car;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import rent.a.car.service.ServicesInitializer;

public class UtilModule extends AbstractModule {

    private static Util util;

    protected void configure() {
        util = new Util();
        bind(IdGenerator.class).annotatedWith(Names.named("IdGenerator")).toInstance(util);
        bind(DateAndTimeFormatter.class).annotatedWith(Names.named("DateAndTimeFormatter")).toInstance(util);
        bind(DateAndTimeParser.class).annotatedWith(Names.named("DateAndTimeParser")).toInstance(util);
        bind(Util.class).annotatedWith(Names.named("Util")).toInstance(util);
        bind(ServicesInitializer.class)
                .annotatedWith(Names.named("ServicesInitializer"))
                .to(ServicesInitializer.class);
    }

}
