package invalid.bt.my_budget.service;

import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.spring.annotations.Recurring;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    record SendParams(String recipient, String subject, String body) {
    }

    @Job(name = "Send the email address verification token")
    public void send(SendParams sendParams) {
        log.info("--> recipient = " + sendParams.recipient() + ", subject = " + sendParams.subject() + ", body = " + sendParams.body());
    }

    /**
     * `@Recurring` is an alternative to Spring's @Scheduled mechanism.
     * Featuring a stored db entry + the dashboard to monitor the process out of the box.
     * For this reason, I'll favor this approach, rather than use Spring's builtin mechanism.
     */
    @Job(name = "Remove unprocessed signup token after 24h")
    @Recurring(id = "cleanup-emails", interval = "PT24H")
    public void removeUnprocessedTokens() {
        log.info("TODO");
    }
}
