package invalid.bt.my_budget.service;

import invalid.bt.my_budget.authentication.UserDetailsImpl;
import invalid.bt.my_budget.authentication.AuthenticationController;
import invalid.bt.my_budget.dto.TenantDTO;
import invalid.bt.my_budget.entity.Tenant;
import invalid.bt.my_budget.repository.TenantRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final TenantRepository tenantRepository;

    public UserDetailsServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    /**
     * The (hashed) password must be set in the returned userDetail.
     * <br><br>
     * This allows the authenticationManager called in {@link AuthenticationController#login(TenantDTO)}
     * to compare the stored (hashed) password with the (non hashed) password provided by the DTO.
     * <br><br>
     * This is an area where I find Spring's idioms confusing, referencing a `password` field in
     * its `UserDetails` interface rather than a hashed password (??).
     <br><br>
     * Furthermore, I find its insistance on having a `username` field really annoying as it
     * doesn't fit my use case. But I don't think I can do much about this leaky and wrong
     * abstraction, unfortunately.
     * <br><br>
     * Note to self: loading the hashed password is coherent with this method's implementation:
     * {@link org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl#loadUsersByUsername(String)}
     */
    @SuppressWarnings("JavadocReference")
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Tenant tenant = tenantRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User/tenant not found!"));

        return new UserDetailsImpl(
                tenant.getEmail(),
                tenant.getPasswordHash()
        );
    }
}
