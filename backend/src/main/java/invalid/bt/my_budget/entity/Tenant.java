package invalid.bt.my_budget.entity;

import lombok.Getter;

public class Tenant {
    @Getter
    private final String email;
    @Getter
    private final String passwordHash;
    private final UserRole userRole;

    public Tenant(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        userRole = UserRole.USER;
    }

}
