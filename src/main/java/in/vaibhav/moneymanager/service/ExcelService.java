package in.vaibhav.moneymanager.service;

import in.vaibhav.moneymanager.dto.ExpenseDTO;
import in.vaibhav.moneymanager.dto.IncomeDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExcelService {


    public void writeIncomesToExcel(OutputStream os,
                                    List<IncomeDTO> incomes) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Incomes");

            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("S.No");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Category");
            header.createCell(3).setCellValue("Amount");
            header.createCell(4).setCellValue("Date");

            int rowNum = 1;

            for (IncomeDTO income : incomes) {

                Row row = sheet.createRow(rowNum);

                row.createCell(0).setCellValue(rowNum);
                row.createCell(1).setCellValue(income.getName());
                row.createCell(2).setCellValue(income.getCategoryName());
                row.createCell(3).setCellValue(income.getAmount().doubleValue());
                row.createCell(4).setCellValue(income.getDate().toString());

                rowNum++;
            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(os);
        }
    }



    public void writeExpensesToExcel(OutputStream os,
                                     List<ExpenseDTO> expenses) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Expenses");

            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("S.No");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Category");
            header.createCell(3).setCellValue("Amount");
            header.createCell(4).setCellValue("Date");

            int rowNum = 1;

            for (ExpenseDTO expense : expenses) {

                Row row = sheet.createRow(rowNum);

                row.createCell(0).setCellValue(rowNum);
                row.createCell(1).setCellValue(expense.getName());
                row.createCell(2).setCellValue(expense.getCategoryName());
                row.createCell(3).setCellValue(expense.getAmount().doubleValue());
                row.createCell(4).setCellValue(expense.getDate().toString());

                rowNum++;
            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(os);
        }
    }
}