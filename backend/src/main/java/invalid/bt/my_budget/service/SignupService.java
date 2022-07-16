package invalid.bt.my_budget.service;

import invalid.bt.my_budget.dto.TenantDTO;
import invalid.bt.my_budget.repository.TenantRepository;
import invalid.bt.my_budget.repository.SignupTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SignupService {
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;
    private final SignupTokenRepository signupTokenRepository;

    public SignupService(TenantRepository tenantRepository,
                         SignupTokenRepository signupTokenRepository,
                         PasswordEncoder passwordEncoder) {
        this.tenantRepository = tenantRepository;
        this.signupTokenRepository = signupTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void onboardTenant(TenantDTO tenant) {
        int tenantID = tenantRepository.insertTenant(
                tenant.email(),
                passwordEncoder.encode(tenant.password())
        );
        signupTokenRepository.insert(UUID.randomUUID(), tenantID);
    }
}
