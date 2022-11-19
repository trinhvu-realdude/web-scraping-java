package com.hcl.webscraping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcl.webscraping.constant.Constants;
import com.hcl.webscraping.model.Category;
import com.hcl.webscraping.model.Image;
import com.hcl.webscraping.model.Tag;
import com.hcl.webscraping.service.Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

        private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

        public static void main(String[] args) {
                Service service = new Service();

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

                        // create folder for each category
                        for (Category category : categories) {
                                File categoryFolder = new File(Constants.BASE_PATH + category.getName());
                                if (!categoryFolder.exists()) {
                                        categoryFolder.mkdir();
                                        LOGGER.log(Level.INFO, "Creating {0} folder", Constants.BASE_PATH + category.getName());
                                }

                                List<Tag> tags = service.getTagsByCategory(category.getUrl());

                                // get tags
                                String tagsData = service.generateDataToJson(tags);
                                LOGGER.log(Level.INFO, "Generating Tags by {0} data", category.getUrl());

                                // write tag data to json file
                                FileWriter tagsFile = new FileWriter(Constants.BASE_PATH + category.getName() + Constants.SLASH + category.getName() + Constants.HYPHEN + Constants.TAGS_JSON);
                                tagsFile.write(tagsData);
                                LOGGER.log(Level.INFO, "Writing {0}-tags.json", category.getName());
                                tagsFile.close();

                                // create folder for tag in category
                                for (Tag tag : tags) {
                                        File tagFolder = new File(Constants.BASE_PATH + category.getName() + Constants.SLASH + tag.getTitle().replaceAll("[/:*?<>|]", ""));
                                        if (!tagFolder.exists()) {
                                                tagFolder.mkdir();
                                                LOGGER.log(Level.INFO, "Creating {0} folder", Constants.BASE_PATH + category.getName() + Constants.SLASH + tag.getTitle().replaceAll("[/:*?<>|]", ""));
                                        }

                                        List<Image> images = service.getImagesByTag(tag.getUrl());

                                        // get images
                                        String imagesData = service.generateDataToJson(images);
                                        LOGGER.log(Level.INFO, "Generating Images by {0} data", tag.getUrl());

                                        // write image data to json file
                                        FileWriter imagesFile = new FileWriter(Constants.BASE_PATH + category.getName() + Constants.SLASH + tag.getTitle().replaceAll("[/:*?<>|]", "") + Constants.SLASH + tag.getUrl().substring(1) + Constants.JSON);
                                        imagesFile.write(imagesData);
                                        LOGGER.log(Level.INFO, "Writing {0}.json", tag.getUrl().substring(1));
                                        imagesFile.close();

                                        LOGGER.log(Level.FINE, "Tag {0} completed!", tag.getTitle());
                                }

                                LOGGER.log(Level.FINE, "Category {0} completed!", category.getName());
                        }

                } catch (JsonProcessingException e) {
                        LOGGER.log(Level.SEVERE, "JsonProcessingException occur", e);
                } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "IOException occur", e);
                }

                LOGGER.log(Level.INFO, "DONE!!!");
        }
}
