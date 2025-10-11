package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {

    private final Logger logger = LogManager.getLogger(DatabaseHelper.class);
    private final String DB_TABLE = "exceldb.myTable";

    public void saveExcelDataToDatabase(Connection connection, Sheet sheet) throws SQLException {
        String sqlCommand = String.format("INSERT INTO %s VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", DB_TABLE);
        try {
            logger.info("*** PREPARING TO ADD DATA TO DATABASE ***");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                for (int i = 0; i < 11; i++) {
                    Cell cell = row.getCell(i);
                    String ok = convertingExcelCells(cell);
                    preparedStatement.setString(i + 1, ok);
                }
                preparedStatement.addBatch();
            }
            int[] results = preparedStatement.executeBatch();
            logger.info("Added: " + results.length + " records to Database");
        } catch (SQLException e) {
            logger.error("*** COULDN`T ADD VALUE TO DATABASE ***");
            throw new SQLException("Couldn`t add value", e);
        }
    }

    private static String convertingExcelCells(Cell cell) {
       return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default ->  "";
        };
    }
}