package com.example.FinanceTracker.Controller;

import com.example.FinanceTracker.Service.FinanceReportService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private FinanceReportService reportService;

    @GetMapping("/monthly/{userId}")
    public ResponseEntity<FinanceReportService.MonthlySummary>
    getMonthlySummary(
            @PathVariable Long userId,
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM")
            @Parameter(
                    description = "Month in format yyyy-MM (e.g., 2026-02)",
                    example = "2026-02",
                    schema = @Schema(type = "string", pattern = "yyyy-MM")
            )
            YearMonth month)
    {
        return ResponseEntity.ok(reportService.getMonthlySummary(userId, month));
    }
}