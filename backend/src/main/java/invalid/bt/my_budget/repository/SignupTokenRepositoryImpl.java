package invalid.bt.my_budget.repository;

import org.intellij.lang.annotations.Language;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SignupTokenRepositoryImpl implements SignupTokenRepository {
    private final JdbcTemplate jdbcTemplate;

    public SignupTokenRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(UUID token, Integer tenantID) {
        @Language("PostgreSQL")
        String sql = "INSERT INTO signup_token (token, tenant_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, token, tenantID);
    }
}
