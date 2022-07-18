package invalid.bt.my_budget.service;

import invalid.bt.my_budget.dto.TenantDTO;
import invalid.bt.my_budget.repository.SignupTokenRepository;
import invalid.bt.my_budget.repository.TenantRepository;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SignupService {
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JobScheduler jobScheduler;
    private final TenantRepository tenantRepository;
    private final SignupTokenRepository signupTokenRepository;

    public SignupService(TenantRepository tenantRepository,
                         SignupTokenRepository signupTokenRepository,
                         PasswordEncoder passwordEncoder,
                         EmailService emailService,
                         JobScheduler jobScheduler
    ) {
        this.tenantRepository = tenantRepository;
        this.signupTokenRepository = signupTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jobScheduler = jobScheduler;
    }


    @Transactional
    public void onboardTenant(TenantDTO tenant) {
        int tenantID = tenantRepository.insertTenant(
                tenant.email(),
                passwordEncoder.encode(tenant.password())
        );
        UUID signupToken = UUID.randomUUID();
        signupTokenRepository.insert(signupToken, tenantID);

        var sendParams = new EmailService.SendParams(
                tenant.email(),
                "You're almost signed up! Please confirm your email :)",
                "To confirm your email, please visit the following link: /todo?token=" + signupToken
        );
        jobScheduler.enqueue(() -> emailService.send(sendParams));
    }
}
