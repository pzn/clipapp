package ip.cl.clipapp;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static ip.cl.clipapp.ClipAppProfile.DATABASE;
import static ip.cl.clipapp.ClipAppProfile.HEROKU;
import static ip.cl.clipapp.ClipAppProfile.SIMPLE;

@ComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Profile(SIMPLE)
    @Configuration
    @EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
    public static class SimpleClipAppConfiguration {

    }

    @Profile(DATABASE)
    @Configuration
    @EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
    public static class DbClipAppConfiguration {

        @Profile(HEROKU)
        @Bean
        public DataSource dataSource() throws URISyntaxException {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            return DataSourceBuilder.create()
                            .driverClassName("org.postgresql.Driver")
                            .username(username)
                            .password(password)
                            .url(dbUrl)
                            .build();
        }
    }
}
