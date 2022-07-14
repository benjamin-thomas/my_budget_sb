package invalid.bt.my_budget.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfig {

    /**
     * @return DataSource
     * @throws URISyntaxException
     *
     * Loads the datasource considering Heroku's idiom, i.e. DATABASE_URL=postgres://username:password@hostname:port/database
     * Since I use jar deployments, I do not have access to JDBC_* env vars.
     * And since database configuration + app reboot can be scheduled without notice, it's best to work only
     * from this variable being the source of truth regarding db access.
     */
    @Bean
    public DataSource getDataSource() throws URISyntaxException {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null) throw new RuntimeException("DATABASE_URL is not set!");

        URI uri = new URI(databaseUrl);

        String[] credentials = uri.getUserInfo().split(":");
        if (credentials.length != 2) {
            throw new RuntimeException("Bad credentials in: DATABASE_URL");
        }
        String username = credentials[0];
        String password = credentials[1];

        String url = "jdbc:postgresql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath();

        return DataSourceBuilder
                .create()
                .driverClassName("org.postgresql.Driver")
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}
