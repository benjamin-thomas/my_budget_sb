package invalid.bt.my_budget.service;

import invalid.bt.my_budget.entity.Tenant;
import invalid.bt.my_budget.repository.TenantRepository;
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return tenantRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User/tenant not found!"));
    }
}
