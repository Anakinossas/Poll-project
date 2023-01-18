package it.zerob.poll.report;

import it.zerob.poll.model.Users;
import org.apache.poi.ss.usermodel.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReportEmailExcel
{

    /**
     * Method to import the emails into the database
     * @param inputStream InputStream object to read the Excel file with the emails
     * @return List of users that have to be saved into the database
     * @throws Exception In case the file is not an Excel
     */

    public List<Users> importEmails(InputStream inputStream) throws Exception
    {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        int firstRowNum = sheet.getFirstRowNum(); //First Row of the file
        int lastRowNum = sheet.getLastRowNum(); //Last Row of the file

        Row headerRow = sheet.getRow(firstRowNum);

        Cell headerEmailUserCell = headerRow.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if(headerEmailUserCell == null || !headerEmailUserCell.getStringCellValue().equalsIgnoreCase("EMAIL"))
        {
            throw new Exception("Il file non corrisponde al formato richiesto");
        }

        List<Users> emailUsers = new ArrayList<>(); //List of users with their emails

        for(int i = firstRowNum + 1; i <= lastRowNum; i++)
        {
            Row rowEmail = sheet.getRow(i);
            Users userEmail = new Users();

            if(rowEmail == null)
            {
                continue;
            }

            Cell emailCell = rowEmail.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if(emailCell != null)
            {
                userEmail.setUsername(emailCell.getStringCellValue()); //Set the email value get from the excel
                userEmail.setRole("USER"); //Set the role of the user
                emailUsers.add(userEmail); //Adding the email into the list
            }
        }
        return emailUsers;
    }
}
