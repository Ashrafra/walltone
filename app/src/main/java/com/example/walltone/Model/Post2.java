package com.example.walltone.Model;

import java.util.List;


public class Post2 {

    public String name;
    public Integer cat_id;
    public String thumb_image;
    public Integer total_content;
    public List<Content> content;
//
//    public Post2(String name, int cat_id, String thumb_image, int total_content, List<Content> content) {
//        this.name = name;
//        this.cat_id = cat_id;
//        this.thumb_image = thumb_image;
//        this.total_content = total_content;
//        this.content = content;
//    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public int getTotal_content() {
        return total_content;
    }

    public void setTotal_content(int total_content) {
        this.total_content = total_content;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public class Content {
        public Integer ID;
        public String jpgImage;
        public String thumb_image;
        public Integer isNew;

        public Content(int ID, String jpgImage, String thumb_image, int isNew) {
            this.ID = ID;
            this.jpgImage = jpgImage;
            this.thumb_image = thumb_image;
            this.isNew = isNew;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getJpgImage() {
            return jpgImage;
        }

        public void setJpgImage(String jpgImage) {
            this.jpgImage = jpgImage;
        }

        public String getThumb_image() {
            return thumb_image;
        }

        public void setThumb_image(String thumb_image) {
            this.thumb_image = thumb_image;
        }

        public int getIsNew() {
            return isNew;
        }

        public void setIsNew(int isNew) {
            this.isNew = isNew;
        }
    }
}


