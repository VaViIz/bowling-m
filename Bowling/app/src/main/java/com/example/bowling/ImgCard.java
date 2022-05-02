package com.example.bowling;

public class ImgCard {
    private String id;
    private String info;
    private int imageResource;



    public ImgCard(String info, int imageResource) {
        this.info = info;
        this.imageResource = imageResource;
    }

    public ImgCard() {
    }

    public String getInfo() {
        return info;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String _getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
}
