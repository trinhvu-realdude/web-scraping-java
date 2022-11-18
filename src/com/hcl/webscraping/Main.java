package com.hcl.webscraping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcl.webscraping.constant.Constants;
import com.hcl.webscraping.model.Category;
import com.hcl.webscraping.service.Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

        public static void main(String[] args) {
                Service service = new Service();

                try {
                        List<Category> categories = service.getCategories();
                        
                        // get categories
                        String categoriesData = service.generateDataToJson(categories);

                        // write file
                        FileWriter categoriesFile = new FileWriter(Constants.BASE_PATH + "categories.json");
                        categoriesFile.write(categoriesData);
                        categoriesFile.close();
                        
                        // create folder for each category
                        for (Category category : categories) {
                                File categoryFolder = new File(Constants.BASE_PATH + category.getName());
                                if (!categoryFolder.exists()) {
                                        categoryFolder.mkdir();
                                }
                        }

                } catch (JsonProcessingException e) {
                        System.err.println("error: " + e);
                } catch (IOException e) {
                        System.err.println("error: " + e);
                }
        }
}
