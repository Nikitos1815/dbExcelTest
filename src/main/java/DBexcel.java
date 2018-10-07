import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class DBexcel {
    public static String exec(String name, String query) {

        String[] resul = new String[1];
        StringBuilder result = new StringBuilder().append("");
        int k = 0;

        InputStream in = null;
        HSSFWorkbook wb = null;
        try {
            in = new FileInputStream(name);
            wb = new HSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            if (k == 1)
                break;
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        resul[0] = cell.getStringCellValue();
                        if (resul[0].equals(query)) {
                            result.append(cell.getStringCellValue() + " =");
                            ++k;
                        }

                        break;

                    case Cell.CELL_TYPE_NUMERIC:
                        if (k == 1)
                            result.append("[" + cell.getNumericCellValue() + "]");
                        break;

                    case Cell.CELL_TYPE_FORMULA:
                        if (k == 1)
                            result.append("[" + cell.getNumericCellValue() + "]");
                        break;
                    default:
                        result.append("|");
                        break;
                }
            }
        }
        if(result.toString().equals("")){
            return "По данному запросу ничего не найдено";
        } else return result.toString();

    }
}

