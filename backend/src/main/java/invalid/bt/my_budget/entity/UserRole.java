package invalid.bt.my_budget.entity;

/*
Keep in sync with the database, see:
```psql
\dT+ USER_ROLE
```
 */
public enum UserRole {
    ADMIN, USER
}
