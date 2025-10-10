package db;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {

    public static void saveExcelDataToDatabase(Connection connection, Sheet sheet) throws SQLException {
        String sqlCommand = "INSERT INTO exceldb.myTable VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
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
            System.out.println("Added: " + results + " to Database");
        } catch (SQLException e) {
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



























//    public static void saveExcelDataToDatabase(Connection connection, Sheet sheet) throws SQLException {
//        String sqlCommand = "INSERT INTO exceldb.myTable VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0)
//                    continue;
//                for (int i = 0; i < 11; i++) {
//                    Cell cell = row.getCell(i);
//                    String cellValue = getCellValueAsString(cell);
//                    preparedStatement.setString(i + 1, cellValue);
//                }
//                preparedStatement.addBatch(); // Dodaj do batcha dla wydajności
//            }
//
//            int[] results = preparedStatement.executeBatch(); // Wykonaj wszystkie naraz
//            System.out.println("Dodano " + results.length + " wierszy do bazy");
//        } catch (SQLException e) {
//            throw new SQLException("Error", e);
//        }
//    }
//
//    private static String getCellValueAsString(Cell cell) {
//        switch (cell.getCellType()) {
//            case STRING:
//                return cell.getStringCellValue();
//            case NUMERIC:
//                return String.valueOf(cell.getNumericCellValue());
//            case BOOLEAN:
//                return String.valueOf(cell.getBooleanCellValue());
//            case FORMULA:
//                return cell.getCellFormula();
//            default:
//                return "";
//        }
//    }
}