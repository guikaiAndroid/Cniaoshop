package com.guikai.cniaoshop.bean;

public class HomeCategory extends Category {

    private int imgBig;
    private int imgSmallTop;
    private int imgSmallbottom;

    public HomeCategory(String name, int imgBig, int imgSmallTop, int imgSmallbottom) {
        super(name);
        this.imgBig = imgBig;
        this.imgSmallTop = imgSmallTop;
        this.imgSmallbottom = imgSmallbottom;
    }

    public int getImgBig() {
        return imgBig;
    }

    public void setImgBig(int imgBig) {
        this.imgBig = imgBig;
    }

    public int getImgSmallTop() {
        return imgSmallTop;
    }

    public void setImgSmallTop(int imgSmallTop) {
        this.imgSmallTop = imgSmallTop;
    }

    public int getImgSmallbottom() {
        return imgSmallbottom;
    }

    public void setImgSmallbottom(int imgSmallbottom) {
        this.imgSmallbottom = imgSmallbottom;
    }
}
