package com.example.springprocessmanagementelasticbeanstalkdeploy.FileUploadController;


import java.util.HashMap;
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
            Map<String, String> keyValueMap = new HashMap<>();

            for (int i = 0; i < valuesArray.length; i++) {
                //String key = keysArray[i];
                //String value = keysArray[i];
                keyValueMap.put(keysArray[i], valuesArray[i]);
            }
            // Perform the database update using values,  databaseName, tableName, and rowId
                    // Now you have a map with keys and values
        // You can use this map to construct an update operation

                // Example: Construct an update operation using the map
                StringBuilder updateSql = new StringBuilder("UPDATE " + databaseName + "." +tableName +" SET ");
                for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
                    updateSql.append(entry.getKey()).append(" = '").append(entry.getValue()).append("', ");
                }
                updateSql.delete(updateSql.length() - 2, updateSql.length()); // Remove the trailing comma and space
                updateSql.append(" WHERE id = " +  rowId);

                System.out.println(updateSql.toString());
                try {
                    jdbcTemplate.update(updateSql.toString());
                    return "Success";
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error " + e);
                    return "Error";
                }
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
