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

public class TagThreading implements Runnable {

        private Thread thread;
        private List<Tag> tags;
        private Category category;
        private static final Logger LOGGER = Logger.getLogger(TagThreading.class.getName());
        private static final Service service = Service.getInstance();

        public TagThreading(List<Tag> tags, Category category) {
                this.tags = tags;
                this.category = category;
        }

        @Override
        public void run() {

                LOGGER.log(Level.INFO, "Tag thread is starting...");

                try {
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

                                LOGGER.log(Level.INFO, "Thread " + tag.getName() + " is sleeping in " + Constants.SLEEP_TIME_TAG + " milliseconds...");
                                Thread.sleep(Constants.SLEEP_TIME_TAG);

//                                ImageThreading imageThreading = new ImageThreading(images, category, tag);
//                                imageThreading.start();
                                LOGGER.log(Level.FINE, "Tag {0} completed!", tag.getTitle());
                        }

                } catch (JsonProcessingException e) {
                        LOGGER.log(Level.SEVERE, "JsonProcessingException occur", e);
                } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "IOException occur", e);
                } catch (InterruptedException e) {
                        LOGGER.log(Level.SEVERE, "InterruptedException occur", e);
                }

                LOGGER.log(Level.INFO, "Tag thread ends");

        }

        public void start() {
                if (thread == null) {
                        thread = new Thread(this);
                        thread.start();
                }
        }

}
