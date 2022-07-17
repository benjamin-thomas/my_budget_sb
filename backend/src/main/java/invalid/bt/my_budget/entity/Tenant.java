package invalid.bt.my_budget.entity;

public record Tenant(String email,
                     String passwordHash,
                     UserRole role,
                     Boolean isEnabled) {
}
