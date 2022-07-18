package invalid.bt.my_budget.service;

import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    record SendParams(String recipient, String subject, String body) {
    }

    @Job(name = "Send the email address verification token")
    public void send(SendParams sendParams) {
        System.out.println("--> recipient = " + sendParams.recipient() + ", subject = " + sendParams.subject() + ", body = " + sendParams.body());
    }
}
