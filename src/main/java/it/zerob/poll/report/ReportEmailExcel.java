package it.zerob.poll.report;

import it.zerob.poll.model.Users;
import org.apache.poi.ss.usermodel.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReportEmailExcel
{

    public List<Users> importEmails(InputStream inputStream) throws Exception
    {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        int firstRowNum = sheet.getFirstRowNum(); //First Row of the file
        int lastRowNum = sheet.getLastRowNum(); //Last Row of the file

        Row headerRow = sheet.getRow(firstRowNum); //First header row

        Cell headerEmailUserCell = headerRow.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL); //Get the text of the header row
        if(headerEmailUserCell == null || !headerEmailUserCell.getStringCellValue().equalsIgnoreCase("EMAIL"))
        {
            throw new Exception("Il file non corrisponde al formato richiesto");
        }

        List<Users> emailUsers = new ArrayList<>(); //List of users with their emails

        //Looping all the data present in the excel file
        for(int i = firstRowNum + 1; i <= lastRowNum; i++)
        {
            //Get the row
            Row rowEmail = sheet.getRow(i);
            Users userEmail = new Users();

            //If row null jump
            //into the other row
            if(rowEmail == null)
            {
                continue;
            }

            //Get the value from the cell of the current row
            //and set the email and the role in db and
            //add the current data into a list
            Cell emailCell = rowEmail.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if(emailCell != null)
            {
                userEmail.setUsername(emailCell.getStringCellValue());
                userEmail.setRole("USER");
                emailUsers.add(userEmail);
            }
        }
        return emailUsers;
    }
}
