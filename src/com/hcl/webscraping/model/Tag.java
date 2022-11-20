package com.hcl.webscraping.model;

public class Tag {

        private String name;
        private String title;
        private String url;
        private String category;
        private String thumbSource;
        private int quantity;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getUrl() {
                return url;
        }

        public void setUrl(String url) {
                this.url = url;
        }

        public String getCategory() {
                return category;
        }

        public void setCategory(String category) {
                this.category = category;
        }

        public String getThumbSource() {
                return thumbSource;
        }

        public void setThumbSource(String thumbSource) {
                this.thumbSource = thumbSource;
        }

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }

}
