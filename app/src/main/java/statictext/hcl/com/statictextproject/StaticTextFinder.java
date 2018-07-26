package statictext.hcl.com.statictextproject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticTextFinder {

    private static final String OUTPUT_FILE_NAME = "StaticTextFinder1.xls";//output xl sheet file name
    public static List<StaticText> fileNames, fileNamesEmpty;
    public static final String DIRECTORY_PROJECT = "D:\\Shoppix new\\app-kantar\\src\\main\\java\\"; // Project location to trace hardcoded text

    public static void main(String[] args) {

        fileNames = new ArrayList<>();
        fileNamesEmpty = new ArrayList<>();
        StaticTextFinder listFilesUtil = new StaticTextFinder();

        listFilesUtil.listFilesAndFilesSubDirectories(DIRECTORY_PROJECT);
        //
        try {
            listFilesUtil.generateSheet(fileNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * List all files from a directory and its subdirectories
     *
     * @param directoryName to be listed
     */
    public void listFilesAndFilesSubDirectories(String directoryName) {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                System.out.println("File : " + file.getAbsolutePath());
                if (!file.getAbsolutePath().endsWith(".java"))
                    continue;
                //StaticText staticText =
                        readFileData(file);
//                if (staticText != null)
//                    fileNames.add(staticText);
//                else
//                    fileNamesEmpty.add(new StaticText(file.getAbsolutePath(), "", ""));
            } else if (file.isDirectory()) {
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
            }
        }
    }


    private StaticText readFileData(File file) {

        StaticText staticText = null;
        try {

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (isRegexValid(line) != null && (!line.contains("@") && !line.contains("Log.") && !line.startsWith("//"))) {
                    System.out.println("File Data : " + line);
                    staticText = new StaticText(file.getAbsolutePath().trim(), line.trim(), isRegexValid(line.trim()));
                    fileNames.add(staticText);
                }else{
                    fileNamesEmpty.add(new StaticText(file.getAbsolutePath(), "", ""));
                }

            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staticText;
    }

    private String isRegexValid(String line) {
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(line);
        while (m.find()) {
            return m.group(1).length() >0 ?m.group(1):null;
        }
        return null;
    }

    private void generateSheet(List<StaticText> fileNames) throws IOException {
        String[] columns = {"File Name", "Content", "Hard Coded"};
        // Create a Workbook
        Workbook workbook = new HSSFWorkbook(); // new XSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("StaticText"); // xl sheet 1st tab name
        Sheet sheetNA = workbook.createSheet("NoStaticText"); // xl sheet 2nd tab name

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create a Row
        Row headerRowEmpty = sheetNA.createRow(0);

        // Create cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
            //
            Cell cellEmpty = headerRowEmpty.createCell(i);
            cellEmpty.setCellValue(columns[i]);
            cellEmpty.setCellStyle(headerCellStyle);
        }


        processSheet(sheet,fileNames);
        processSheet(sheetNA,fileNamesEmpty);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(OUTPUT_FILE_NAME);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();

    }

    private void processSheet(Sheet sheet, List<StaticText> fileNames){
        int rowNum =1;
        int columns=3;
        for (StaticText fileName : fileNames) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(fileName.fileName);

            if (fileName.line == null)
                row.createCell(1)
                        .setCellValue("");
            else
                row.createCell(1)
                        .setCellValue(fileName.line.substring(0, fileName.line.length() > 100 ? 100 : fileName.line.length()));


            //Cell dateOfBirthCell = row.createCell(2);
            //dateOfBirthCell.setCellValue(employee.getDateOfBirth());
            //dateOfBirthCell.setCellStyle(dateCellStyle);

            if (fileName.staticText == null)
                row.createCell(2)
                        .setCellValue("");
            else
                row.createCell(2)
                        .setCellValue(fileName.staticText.substring(0, fileName.staticText.length() > 100 ? 100 : fileName.staticText.length()));
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns; i++) {
            sheet.autoSizeColumn(i);
        }

    }
}
