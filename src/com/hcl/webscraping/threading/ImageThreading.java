package com.hcl.webscraping.threading;

import com.hcl.webscraping.constant.Constants;
import com.hcl.webscraping.model.Category;
import com.hcl.webscraping.model.Image;
import com.hcl.webscraping.model.Tag;
import com.hcl.webscraping.service.Service;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageThreading implements Runnable {

        private Thread thread;
        private List<Image> images;
        private Category category;
        private Tag tag;
        private static final Logger LOGGER = Logger.getLogger(ImageThreading.class.getName());
        private static final Service service = Service.getInstance();

        public ImageThreading(List<Image> images, Category category, Tag tag) {
                this.images = images;
                this.category = category;
                this.tag = tag;
        }

        @Override
        public void run() {
                LOGGER.log(Level.INFO, "Image thread is starting...");

                try {
                        // download image file to tag folder respectively
                        for (Image image : images) {
                                service.downloadImageByUrl(image.getDownloadUrl(), Constants.BASE_PATH + category.getName() + Constants.SLASH + tag.getTitle().replaceAll("[/:*?<>|]", "") + Constants.SLASH, image);

                                LOGGER.log(Level.INFO, "Sleeping in {0} milliseconds...", Constants.SLEEP_TIME_IMAGE);
                                Thread.sleep(Constants.SLEEP_TIME_IMAGE);
                        }
                } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "IOException occur", e);
                } catch (InterruptedException e) {
                        LOGGER.log(Level.SEVERE, "InterruptedException occur", e);
                }
        }

        public void start() {
                if (thread == null) {
                        thread = new Thread(this);
                        thread.start();
                }
        }
}
