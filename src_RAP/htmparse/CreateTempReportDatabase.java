/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmparse;

import static DBUtils.DatabaseUtils.connection;
import static DBUtils.DatabaseUtils.statement;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author yanamalp
 */
public class CreateTempReportDatabase {

    public void createDatabaseTable(String Databasepath) throws ClassNotFoundException {

        File db_file = new File(Databasepath);
        if (db_file.exists()) {
            db_file.delete();
            System.out.println("Deleted the file");
        }
        Class.forName("org.sqlite.JDBC");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + Databasepath);

            statement = connection.createStatement();
            String Table = "CREATE TABLE TESTCASE_STATUS(Environment TEXT,"
                    + "Feature TEXT,TestCaseDesc TEXT,Status TEXT)";

            statement.execute(Table);
            statement.setQueryTimeout(30);

            statement.close();
            connection.close();

        } catch (SQLException e) {

            statement = null;
            connection = null;
            System.out.println("Exception e" + e.getMessage());

        }

    }

}
