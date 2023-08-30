package com.example.springprocessmanagementelasticbeanstalkdeploy.FileUploadController;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("")
public class RestDatabase {
    
    private final JdbcTemplate jdbcTemplate;

    public RestDatabase (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



        @PostMapping("/edit-row")
    @ResponseBody
    public String editRow(@RequestParam String databaseName, @RequestParam String tableName, @RequestParam String rowId, @RequestParam Map<String, String> values) {
        // Create the SET clause for the UPDATE statement
        System.out.println("API is called");
        StringBuilder setClause = new StringBuilder();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            setClause.append(entry.getKey()).append(" = '").append(entry.getValue()).append("', ");
        }
        setClause.delete(setClause.length() - 2, setClause.length()); // Remove trailing comma and space
    
        // Create the WHERE clause using rowId (assuming it's a primary key)
        String whereClause = "id = " + rowId; // Replace "id" with your actual primary key column name
    
        // Construct the complete SQL update statement
        String updateSql = "UPDATE " + databaseName + "." + tableName + " SET " + setClause + " WHERE " + whereClause;
    
        try {
            jdbcTemplate.update(updateSql);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error " + e);
            return "Error";
        }
    }
    

    @PostMapping("/delete-row")
    @ResponseBody
    public String deleteRow(@RequestParam String databaseName, @RequestParam String tableName, @RequestParam String rowId) {
        String sql = "DELETE FROM " + databaseName + "." + tableName + " WHERE ..."; // Specify the condition for deletion

        try {
            jdbcTemplate.update(sql);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error " + e);
            return "Error";
        }
    }
}
