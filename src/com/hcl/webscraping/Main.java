package com.hcl.webscraping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcl.webscraping.constant.Constants;
import com.hcl.webscraping.model.Category;
import com.hcl.webscraping.service.Service;
import com.hcl.webscraping.threading.CategoryThreading;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

        private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

        private static final Service service = Service.getInstance();

        public static void main(String[] args) {

                try {
                        List<Category> categories = service.getCategories();

                        // get categories
                        String categoriesData = service.generateDataToJson(categories);
                        LOGGER.log(Level.INFO, "Generating Categories data");

                        // write category data to json file
                        FileWriter categoriesFile = new FileWriter(Constants.BASE_PATH + Constants.CATEGORIES_JSON);
                        categoriesFile.write(categoriesData);
                        LOGGER.log(Level.INFO, "Writing categories.json");
                        categoriesFile.close();

                        CategoryThreading categoryThreading = new CategoryThreading(categories);
                        categoryThreading.start();

                } catch (JsonProcessingException e) {
                        LOGGER.log(Level.SEVERE, "JsonProcessingException occur", e);
                } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "IOException occur", e);
                }
        }

}
