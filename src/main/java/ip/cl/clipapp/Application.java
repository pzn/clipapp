package ip.cl.clipapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

import static ip.cl.clipapp.ClipAppProfile.HEROKU;
import static java.lang.System.getenv;

@SpringBootApplication
@EnableCaching
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Profile(HEROKU)
    public static class HerokuConfiguration {

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

        @Bean
        public RedisConnectionFactory redisConnectionFactory() throws URISyntaxException {

            URI redisUri = new URI(getenv("REDIS_URL"));

            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(5);
            poolConfig.setMinIdle(1);
            poolConfig.setMaxIdle(5);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);

            JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
            connectionFactory.setHostName(redisUri.getHost());
            connectionFactory.setPort(redisUri.getPort());
            if (redisUri.getUserInfo() == null) {
                log.warn("REDIS_URL does not provide any credentials!");
            } else {
                String[] credentials = redisUri.getUserInfo().split(":", 2);
                if (credentials.length != 2) {
                    log.warn("REDIS_URL may be missing the password!");
                } else {
                    connectionFactory.setPassword(credentials[1]);
                }
            }
            connectionFactory.setTimeout(2000);

            return connectionFactory;
        }

        @Bean
        public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) throws Exception {
            RedisTemplate redisTemplate = new RedisTemplate();
            redisTemplate.setConnectionFactory(redisConnectionFactory);
            return redisTemplate;
        }

        @Bean
        public CacheManager cacheManager(RedisTemplate redisTemplate) throws Exception {
            return new RedisCacheManager(redisTemplate);
        }
    }
}
