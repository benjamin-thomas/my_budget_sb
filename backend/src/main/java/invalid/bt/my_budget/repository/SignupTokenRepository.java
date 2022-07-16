package invalid.bt.my_budget.repository;

import java.util.UUID;

public interface SignupTokenRepository {
    void insert(UUID token, Integer tenantID);
}
