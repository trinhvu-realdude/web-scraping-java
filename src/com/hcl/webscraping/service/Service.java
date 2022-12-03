package com.hcl.webscraping.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcl.webscraping.constant.Constants;
import com.hcl.webscraping.model.Category;
import com.hcl.webscraping.model.Image;
import com.hcl.webscraping.model.Tag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.BufferedInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;

public class Service {

        private static final Logger LOGGER = Logger.getLogger(Service.class.getName());

        private static class ServiceHelper {

                private static final Service INSTANCE = new Service();
        }

        private Service() {

        }

        public static Service getInstance() {
                return ServiceHelper.INSTANCE;
        }

        public List<Image> getImagesByTag(String urlTag) {
                List<Image> list = new ArrayList<>();

                try {

                        Document document = Jsoup.connect(Constants.BASE_URL + urlTag)
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.42")
                                .get();
                        LOGGER.log(Level.WARNING, "Fetching " + Constants.BASE_URL + urlTag);

                        Elements elements = document.getElementsByClass("single_image");

                        for (Element e : elements) {
                                for (int i = 0; i < e.childrenSize(); i++) {
                                        Image data = new Image();

                                        Element imgTag = e.child(i).child(0).child(0).child(0);
                                        String id = imgTag.attr("data-id");
                                        String resolution = imgTag.attr("alt").split(" ")[0];
                                        String name = imgTag.attr("alt").replaceAll(resolution + " ", "");
                                        String tag = imgTag.attr("data-slug");
                                        String dataUrl = imgTag.attr("data-src");
                                        String downloadUrl = e.child(i).child(0).child(0).attr("href");
                                        String dataSource = Constants.BASE_URL + dataUrl;
                                        String downloadSource = Constants.BASE_URL + downloadUrl;

                                        if (!id.equals("") || !resolution.equals("") || !name.equals("")) {
                                                data.setId(id);
                                                data.setName(name);
                                                data.setResolution(resolution);
                                                data.setTag(tag);
                                                data.setDataUrl(dataUrl);
                                                data.setDownloadUrl(downloadUrl);
                                                data.setDataSource(dataSource);
                                                data.setDownloadSource(downloadSource);

                                                list.add(data);
                                        }
                                }
                        }
                } catch (IOException e) {
                        System.err.println("error: " + e);
                }

                return list;
        }

        public List<Category> getCategories() {
                List<Category> list = new ArrayList<>();

                try {

                        Document document = Jsoup.connect(Constants.BASE_URL)
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.42")
                                .get();
                        LOGGER.log(Level.WARNING, "Fetching " + Constants.BASE_URL);

                        Elements elements = document.getElementsByClass("relaxed");

                        Element col1 = elements.get(0).child(0).child(0);
                        Element col2 = elements.get(0).child(1).child(0);

                        for (int i = 0; i < col1.childrenSize(); i++) {
                                Category category = new Category();

                                String name = col1.child(i).text().split(" ")[1];
                                String icon = col1.child(i).text().split(" ")[0];
                                String url = col1.child(i).attr("href");

                                category.setName(name);
                                category.setIcon(icon);
                                category.setUrl(url);

                                list.add(category);
                        }

                        for (int i = 0; i < col2.childrenSize(); i++) {
                                Category category = new Category();

                                String name = col2.child(i).text().split(" ")[1];
                                String icon = col2.child(i).text().split(" ")[0];
                                String url = col2.child(i).attr("href");

                                category.setName(name);
                                category.setIcon(icon);
                                category.setUrl(url);

                                list.add(category);
                        }
                } catch (IOException e) {
                        System.err.println("error: " + e);
                }
                return list;
        }

        public List<Tag> getTagsByCategory(String urlCategory) {
                List<Tag> list = new ArrayList<>();

                try {

                        Document document = Jsoup.connect(Constants.BASE_URL + urlCategory)
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.42")
                                .get();
                        LOGGER.log(Level.WARNING, "Fetching " + Constants.BASE_URL + urlCategory);

                        Elements elements = document.getElementsByClass("stackable");

                        for (Element e : elements) {
                                for (int i = 0; i < e.childrenSize(); i++) {
                                        Tag tag = new Tag();

                                        String name = e.child(i).child(0).child(1).child(0).child(0).text();
                                        String title = e.child(i).child(0).child(0).attr("alt");
                                        String url = e.child(i).child(0).attr("href");
                                        String category = urlCategory.replace("/cat", "");
                                        String thumbSource = Constants.BASE_URL + e.child(i).child(0).child(0).attr("data-src");
                                        int quantity = Integer.parseInt(e.child(i).child(0).child(1).child(0).child(1).text());

                                        tag.setName(name);
                                        tag.setTitle(title);
                                        tag.setUrl(url);
                                        tag.setCategory(category);
                                        tag.setThumbSource(thumbSource);
                                        tag.setQuantity(quantity);

                                        list.add(tag);
                                }
                        }
                } catch (IOException e) {
                        System.err.println("error: " + e);
                }
                return list;
        }

        public void downloadImageByUrl(String downloadUrl, String path, Image image) throws IOException {
                Connection connection = Jsoup.connect(Constants.BASE_URL + downloadUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.42")
                        .timeout(300000)
                        .header("Cache-Control", "max-age=0")
                        .ignoreContentType(true);

                File downloadFile = null;

                if (Constants.JPG.equals(image.getDataUrl().replace(Constants.FULL + image.getId(), ""))) {
                        downloadFile = new File(path + image.getId() + Constants.JPG);
                }

                if (Constants.PNG.equals(image.getDataUrl().replace(Constants.FULL + image.getId(), ""))) {
                        downloadFile = new File(path + image.getId() + Constants.PNG);
                }

                if (!downloadFile.exists()) {
                        Connection.Response res = connection.execute();
                        BufferedInputStream body = res.bodyStream();
                        Files.copy(body, downloadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        LOGGER.log(Level.INFO, "Saved image to {0}", downloadFile.toPath());
                }

        }

        public List<Tag> searchByChar(char c) throws IOException {
                List<Tag> list = new ArrayList<>();

                Document document = Jsoup.connect(Constants.BASE_URL + "/search?q=" + c)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.42")
                        .get();

                Elements elements = document.getElementsByClass("stackable");

                for (Element e : elements) {
                        for (int i = 0; i < e.childrenSize(); i++) {
                                Tag tag = new Tag();

                                String name = e.child(i).child(0).child(1).child(0).child(0).text();
                                String title = e.child(i).child(0).child(0).attr("alt");
                                String url = e.child(i).child(0).attr("href");
                                String thumbSource = Constants.BASE_URL + e.child(i).child(0).child(0).attr("data-src");
                                int quantity = Integer.parseInt(e.child(i).child(0).child(1).child(0).child(1).text());

                                tag.setName(name);
                                tag.setTitle(title);
                                tag.setUrl(url);
                                tag.setCategory(null);
                                tag.setThumbSource(thumbSource);
                                tag.setQuantity(quantity);

                                list.add(tag);
                        }
                }
                
                return list;
        }

        public String generateDataToJson(Object data) throws JsonProcessingException {
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String result = ow.writeValueAsString(data);
                return result;
        }
}
