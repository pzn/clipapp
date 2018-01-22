package ip.cl.clipapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static ip.cl.clipapp.ClipAppProfile.HEROKU;
import static java.lang.System.getenv;

@SpringBootApplication
@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Profile(HEROKU)
    @Bean
    public DataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(getenv("DATABASE_URL"));

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

    @Profile(HEROKU)
    @Bean
    public JedisPool jedisPool() throws URISyntaxException {

        URI redisUri = new URI(getenv("REDIS_URL"));

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setMinIdle(1);
        poolConfig.setMaxIdle(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);

        return new JedisPool(poolConfig, redisUri);
    }
}
