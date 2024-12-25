package uz.saidoff.crmecosystem.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import uz.saidoff.crmecosystem.entity.NotificationResponse;

import java.util.List;

public class ExecuteJobService implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<NotificationResponse> notificationResponses = (List<NotificationResponse>) jobExecutionContext.getMergedJobDataMap().get("notificationResponses");

    }
}