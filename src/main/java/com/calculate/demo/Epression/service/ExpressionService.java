package com.calculate.demo.Epression.service;


import com.calculate.demo.Epression.entity.ExpressionEntity;
import com.calculate.demo.Epression.repository.ExpressionRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ExpressionService {
    private ExpressionRepository expressionRepository;

    public List<ExpressionEntity> getByDateTime(String date){
        List<ExpressionEntity> expressionEntities = expressionRepository.findByDate(date);
        return expressionEntities;
    }

    public void exportExcel(String date) throws IOException {
        List<ExpressionEntity> expressionEntities = expressionRepository.findByDate(date);

        Workbook workbook = WorkbookFactory.create(new FileInputStream("KetXuatExcel_20210102.xlsx"));

        Sheet sheet = workbook.getSheetAt(0);
        Row row1=sheet.createRow(0);


        Cell cell1 = row1.createCell(0);
        cell1.setCellValue("Dữ Liệu Ngày");

        cell1 =row1.createCell(1);
        cell1.setCellValue(date);

        Row row = sheet.createRow(1);
        Cell cell = row.createCell(0);
        cell.setCellValue("ID");

        cell = row.createCell(1);
        cell.setCellValue("Biểu thức");

        cell = row.createCell(2);
        cell.setCellValue("Kết quả");

        for (ExpressionEntity exe:expressionEntities
             ) {
            Row rowData = sheet.createRow(expressionEntities.indexOf(exe) + 2);
            Cell cellID = rowData.createCell(0);
            cellID.setCellValue(exe.getId());


            Cell cellName = rowData.createCell(1);
            cellName.setCellValue(exe.getKeyExpression());


            Cell cellBirthplace = rowData.createCell(2);
            cellBirthplace.setCellValue(exe.getKeyValue());
        }
        String date1= date.replace("-","");
        workbook.write(new FileOutputStream("KetXuatFile_"+date1+".xlsx"));
        workbook.close();
    }
}
