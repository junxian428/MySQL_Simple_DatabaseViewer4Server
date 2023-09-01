package com.example.springprocessmanagementelasticbeanstalkdeploy.FileUploadController;

import static org.mockito.Answers.values;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;

@RestController
@CrossOrigin("")
public class RestDatabase {
    
    private final JdbcTemplate jdbcTemplate;

    public RestDatabase (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public static boolean containsRowId(String inputString) {
        return inputString.contains("rowId");
    }

    
        @PostMapping("/edit-row")
    @ResponseBody
    public String editRow(@RequestParam String databaseName, @RequestParam String tableName, @RequestParam String rowId, @RequestParam String values,@RequestParam String keys ) {
        // Create the SET clause for the UPDATE statement
        //System.out.println("API is called");

        //System.out.println(values);
        //for (int i = 0; i < values.length; i++) {
        //    System.out.println("Element at index " + i + ": " + values[i]);
        //}
        //System.out.println(setClause);
        //setClause.delete(setClause.length() - 2, setClause.length()); // Remove trailing comma and space
    
        // Create the WHERE clause using rowId (assuming it's a primary key)
        //String whereClause = "id = " + rowId; // Replace "id" with your actual primary key column name
    
        // Construct the complete SQL update statement
        //String updateSql = "UPDATE " + databaseName + "." + tableName + " SET " + values + " WHERE " + whereClause;
        try{
            System.out.println(values.toString());
            System.out.println(keys.toString());
            if (!StringUtils.isEmpty(values)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String[] valuesArray = objectMapper.readValue(values, String[].class);
            ObjectMapper objectMapper2 = new ObjectMapper();
            String[] keysArray = objectMapper2.readValue(keys, String[].class);
            // Now you have the array of values
            //System.out.println("Array Length: " + valuesArray.length);
         
            for (int i = 0; i < valuesArray.length; i++) {
                String key = keysArray[i];
                String value = valuesArray[i];
                System.out.println("Key: " + key + ", Value: " + value);
            }
            // Perform the database update using values, databaseName, tableName, and rowId
            
            return "Success"; // Return a success response
        } else {
            return "Error: Empty jsonUpdate";
        }
        }catch(Exception e){
            System.out.println(e);
            return "Error";
        }
     
        /*  
        try {
            jdbcTemplate.update(updateSql);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error " + e);
            return "Error";
        }
        */
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
