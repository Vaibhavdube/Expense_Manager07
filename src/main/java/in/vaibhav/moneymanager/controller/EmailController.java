package in.vaibhav.moneymanager.controller;

import in.vaibhav.moneymanager.entity.ProfileEntity;
import in.vaibhav.moneymanager.service.EmailService;
import in.vaibhav.moneymanager.service.ExcelService;
import in.vaibhav.moneymanager.service.ExpenseService;
import in.vaibhav.moneymanager.service.IncomeService;
import in.vaibhav.moneymanager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final ExcelService excelService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final EmailService emailService;
    private final ProfileService profileService;

    @GetMapping("/income-excel")
    public ResponseEntity<Void> emailIncomeExcel()
            throws IOException {

        ProfileEntity profile = profileService.getCurrentProfile();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        excelService.writeIncomesToExcel(
                baos,
                incomeService.getCurrentMonthIncomeForCurrentUser()
        );

        emailService.sendEmailWithAttachment(
                profile.getEmail(),
                "Your Income Excel Report",
                "Please find attached your income report.",
                baos.toByteArray(),
                "income.xlsx"
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/expense-excel")
    public ResponseEntity<Void> emailExpenseExcel()
            throws IOException {

        ProfileEntity profile = profileService.getCurrentProfile();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        excelService.writeExpensesToExcel(
                baos,
                expenseService.getCurrentMonthExpensesForCurrentUser()
        );

        emailService.sendEmailWithAttachment(
                profile.getEmail(),
                "Your Expense Excel Report",
                "Please find attached your expense report.",
                baos.toByteArray(),
                "expense.xlsx"
        );

        return ResponseEntity.ok().build();
    }
}