package invalid.bt.my_budget.repository;

import invalid.bt.my_budget.entity.Tenant;

import java.util.Optional;

public interface TenantRepository {

    Optional<Tenant> findByEmail(String email);

    int insertTenant(String email, String passwordHash);
}
