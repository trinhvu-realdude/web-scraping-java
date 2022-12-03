package com.hcl.webscraping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcl.webscraping.constant.Constants;
import com.hcl.webscraping.model.Category;
import com.hcl.webscraping.model.Image;
import com.hcl.webscraping.model.Tag;
import com.hcl.webscraping.service.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class DownloadImage {

//        private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
//
//        public static void main(String[] args) throws IOException {
//                Service service = new Service();
//
//                try {
//                        List<Category> categories = service.getCategories();
//
//                        // create folder for each category
//                        for (Category category : categories) {
//                                File categoryFolder = new File(Constants.DOWNLOAD_IMAGE_PATH + category.getName());
//                                if (!categoryFolder.exists()) {
//                                        categoryFolder.mkdir();
//                                        LOGGER.log(Level.INFO, "Creating {0} folder", Constants.DOWNLOAD_IMAGE_PATH + category.getName());
//                                }
//
//                                List<Tag> tags = service.getTagsByCategory(category.getUrl());
//
//                                // create folder for tag in category
//                                for (Tag tag : tags) {
//                                        File tagFolder = new File(Constants.DOWNLOAD_IMAGE_PATH + category.getName() + Constants.SLASH + tag.getTitle().replaceAll("[/:*?<>|]", ""));
//                                        if (!tagFolder.exists()) {
//                                                tagFolder.mkdir();
//                                                LOGGER.log(Level.INFO, "Creating {0} folder", Constants.DOWNLOAD_IMAGE_PATH + category.getName() + Constants.SLASH + tag.getTitle().replaceAll("[/:*?<>|]", ""));
//                                        }
//
//                                        List<Image> images = service.getImagesByTag(tag.getUrl());
//
//                                        // download image file to tag folder respectively
//                                        for (Image image : images) {
//                                                service.downloadImageByUrl(image.getDownloadUrl(), Constants.DOWNLOAD_IMAGE_PATH + category.getName() + Constants.SLASH + tag.getTitle().replaceAll("[/:*?<>|]", "") + Constants.SLASH, image);
//                                        }
//
//                                        LOGGER.log(Level.FINE, "Tag {0} completed!", tag.getTitle());
//                                }
//
//                                LOGGER.log(Level.FINE, "Category {0} completed!", category.getName());
//                        }
//
//                } catch (JsonProcessingException e) {
//                        LOGGER.log(Level.SEVERE, "JsonProcessingException occur", e);
//                } catch (IOException e) {
//                        LOGGER.log(Level.SEVERE, "IOException occur", e);
//                }
//
//                LOGGER.log(Level.INFO, "DONE!!!");
//        }
        
        public static void main(String[] args) {
                Service s = Service.getInstance();
                
                try {
                        System.out.println(s.generateDataToJson(s.searchByChar('b')));
                } catch (IOException ex) {
                        Logger.getLogger(DownloadImage.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
}
