package utils;

public class Config {

    public static String getDataBaseURL() {
        return System.getProperty("DataBase", "jdbc:mysql://localhost:3306/exceldb");
    }

    public static String getDatabaseUser() {
        return System.getProperty("admin", "root");
    }

    public static String getDatabasePassword() {
        return System.getProperty("password", "admin");
    }

    public static String getWebsiteURL() {
        return System.getProperty("URL","https://www.boxofficemojo.com/weekend/2025W35/");
    }
}
