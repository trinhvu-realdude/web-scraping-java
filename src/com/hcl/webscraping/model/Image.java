package com.hcl.webscraping.model;

public class Image {

        private String id;
        private String name;
        private String resolution;
        private String tag;
        private String dataUrl;
        private String downloadUrl;
        private String dataSource;
        private String downloadSource;

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getResolution() {
                return resolution;
        }

        public void setResolution(String resolution) {
                this.resolution = resolution;
        }

        public String getTag() {
                return tag;
        }

        public void setTag(String tag) {
                this.tag = tag;
        }

        public String getDataUrl() {
                return dataUrl;
        }

        public void setDataUrl(String dataUrl) {
                this.dataUrl = dataUrl;
        }

        public String getDownloadUrl() {
                return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
                this.downloadUrl = downloadUrl;
        }

        public String getDataSource() {
                return dataSource;
        }

        public void setDataSource(String dataSource) {
                this.dataSource = dataSource;
        }

        public String getDownloadSource() {
                return downloadSource;
        }

        public void setDownloadSource(String downloadSource) {
                this.downloadSource = downloadSource;
        }

}
