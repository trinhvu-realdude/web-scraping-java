package com.hcl.webscraping.threading;

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

public class CategoryThreading implements Runnable {

        private Thread thread;
        private List<Category> categories;
        private static final Logger LOGGER = Logger.getLogger(CategoryThreading.class.getName());
        private static final Service service = Service.getInstance();

        public CategoryThreading(List<Category> categories) {
                this.categories = categories;
        }

        @Override
        public void run() {

                LOGGER.log(Level.INFO, "Category thread is starting...");

                try {

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

                                LOGGER.log(Level.INFO, "Sleeping in {0} milliseconds...", Constants.SLEEP_TIME_CATEGORY);
                                Thread.sleep(Constants.SLEEP_TIME_CATEGORY);

                                TagThreading tagThreading = new TagThreading(tags, category);
                                tagThreading.start();

                                LOGGER.log(Level.FINE, "Category {0} completed!", category.getName());
                        }
                } catch (JsonProcessingException e) {
                        LOGGER.log(Level.SEVERE, "JsonProcessingException occur", e);
                } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "IOException occur", e);
                } catch (InterruptedException e) {
                        LOGGER.log(Level.SEVERE, "InterruptedException occur", e);
                }

                LOGGER.log(Level.INFO, "Category thread ends");
        }

        public void start() {
                if (thread == null) {
                        thread = new Thread(this);
                        thread.start();
                }
        }
}
