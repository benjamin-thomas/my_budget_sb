package invalid.bt.my_budget.repository;

import invalid.bt.my_budget.entity.Tenant;
import org.intellij.lang.annotations.Language;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TenantRepositoryImpl implements TenantRepository {
    private final JdbcTemplate jdbcTemplate;

    public TenantRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Tenant> findByEmail(String email) {
        @Language("PostgreSQL")
        String sql = "SELECT email, password_hash FROM tenant WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> Optional.of(
                    new Tenant(
                            rs.getString("email"),
                            rs.getString("password_hash"))
            ), email);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int insertTenant(String email, String passwordHash) {
        @Language("PostgreSQL")
        String sql = """
                INSERT INTO tenant (email, password_hash) VALUES (?, ?)
                RETURNING id
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator statementCreator = conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, email);
            ps.setString(2, passwordHash);
            return ps;
        };
        jdbcTemplate.update(statementCreator, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}
