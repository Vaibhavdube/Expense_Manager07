package in.vaibhav.moneymanager.controller;

import in.vaibhav.moneymanager.dto.ExpenseDTO;
import in.vaibhav.moneymanager.dto.IncomeDTO;
import in.vaibhav.moneymanager.service.ExcelService;
import in.vaibhav.moneymanager.service.ExpenseService;
import in.vaibhav.moneymanager.service.IncomeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @GetMapping("/excel/download/income")
    public void downloadIncomeExcel(HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=incomes.xlsx");

        List<IncomeDTO> incomes =
                incomeService.getCurrentMonthIncomeForCurrentUser();

        excelService.writeIncomesToExcel(
                response.getOutputStream(),
                incomes
        );
    }

    @GetMapping("/excel/download/expense")
    public void downloadExpenseExcel(HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=expenses.xlsx");

        List<ExpenseDTO> expenses =
                expenseService.getCurrentMonthExpensesForCurrentUser();

        excelService.writeExpensesToExcel(
                response.getOutputStream(),
                expenses
        );
    }
}