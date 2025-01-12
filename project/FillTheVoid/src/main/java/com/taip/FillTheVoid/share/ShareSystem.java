package com.taip.FillTheVoid.share;

public class ShareSystem {

    private String link;
    private Double linkTime;

    public ShareSystem(String link, Double linkTime) {
        this.link = link;
        this.linkTime = linkTime;
    }

    public ShareSystem() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Double getLinkTime() {
        return linkTime;
    }

    public void setLinkTime(Double linkTime) {
        this.linkTime = linkTime;
    }

    public void generateShareLink(String galleryOrPainting) {
        this.link = "https://artgallery.com/share/" + galleryOrPainting;
        this.linkTime = (double) System.currentTimeMillis();
        System.out.println("Link generated: " + link);
    }

    @Override
    public String toString() {
        return "ShareSystem [link=" + link + ", linkTime=" + linkTime + "]";
    }
}