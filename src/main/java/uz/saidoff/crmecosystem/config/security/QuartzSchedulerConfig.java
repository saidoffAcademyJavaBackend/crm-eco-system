package uz.saidoff.crmecosystem.config.security;

import lombok.RequiredArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.NotificationResponse;
import uz.saidoff.crmecosystem.service.ExecuteJobService;
import uz.saidoff.crmecosystem.service.QuestionService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Configuration
@RequiredArgsConstructor
public class QuartzSchedulerConfig {

    private final QuestionService questionService;

    @Bean
    public JobDetail quartzJobDetail() {
        return JobBuilder.newJob(ExecuteJobService.class)
                .withIdentity("executeJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger quartzTrigger(JobDetail jobDetail) {

        List<NotificationResponse> questionsForScheduledJob = questionService.getQuestionsForScheduledJob();

    }
}
