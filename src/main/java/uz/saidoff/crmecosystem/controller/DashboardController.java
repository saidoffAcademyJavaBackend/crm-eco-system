package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.DashboardStatsRequestDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.DashboardService;
import uz.saidoff.crmecosystem.service.EmployeeService;
import uz.saidoff.crmecosystem.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private final EmployeeService employeeService;

    @PostMapping("get-income-outcome-stats")
    public ResponseData<?> getStats(@RequestBody DashboardStatsRequestDto dashboardStatsRequestDto) {
        if (dashboardStatsRequestDto.getTo().before(dashboardStatsRequestDto.getFrom())) {
            return ResponseData.errorResponse("Bad request", "/api/dashboard/get-income-outcome-stats", 400);
        }
        return this.dashboardService.getStats(dashboardStatsRequestDto.getFrom(), dashboardStatsRequestDto.getTo(), dashboardStatsRequestDto.getDuration(), dashboardStatsRequestDto.getCurrency(), dashboardStatsRequestDto.getIsIncome());
    }

    @GetMapping("get-active-projects")
    public ResponseData<?> getActiveProjects() {
        //this method is already written in ProjectController controller class
        return null;
    }

    @GetMapping("get-employers")
    public ResponseData<?> getEmployers() {
        //this method is already written in EmployeeController controller class
        return null;
    }
}
