package sklcc.ws.preprocess;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sklcc.ws.dao.DataDest;
import sklcc.ws.entity.Publicer;
import sklcc.ws.util.Configuration;

/**
 * Created by sukai on 12/11/15.
 * http://www.journaldev.com/2562/java-readwrite-excel-file-using-apache-poi-api
 */
public class ExcelReader {

    public DataDest dataDest;

    public ExcelReader() {
        this.dataDest = new DataDest();
    }

    public List<Publicer> readPublicers(String fileName) {
        List<Publicer> publicers = new ArrayList<Publicer>();
        try {
            FileInputStream fis = new FileInputStream(fileName);

            Workbook workbook = null;
            if (fileName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }
            int numbersOfSheets = workbook.getNumberOfSheets();

            for (int i=0; i< numbersOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    String wx_nickname = "";
                    String wx_name = "";
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                if (wx_nickname.equalsIgnoreCase("")) {
                                    wx_nickname = cell.getStringCellValue();
                                } else if (wx_name.equalsIgnoreCase("")) {
                                    wx_name = cell.getStringCellValue();
                                } else {
                                    System.out.println("Random Data" + cell.getStringCellValue());
                                }
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                System.out.println("Random Data" + cell.getStringCellValue());
                        }
                    }
                    Publicer publicer = new Publicer();
                    publicer.setWx_nickname(wx_nickname);
                    publicer.setWx_name(wx_name);
                    publicers.add(publicer);
                }
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return publicers;
    }


    public static void main(String[] args) throws IOException {
        new Configuration("project.properties");
        ExcelReader reader = new ExcelReader();
        List<Publicer> publicers = reader.readPublicers("/Users/sukai/docs/projects/wx-gsdata/媒体.xls");
        publicers.remove(0);
        System.out.println(publicers.size());
        for (int i=0;i<publicers.size(); i++) {
            System.out.println(publicers.get(i).getWx_nickname() + " " + publicers.get(i).getWx_name());
            reader.dataDest.addPublicerFromExcel(publicers.get(i));
        }
    }
}
