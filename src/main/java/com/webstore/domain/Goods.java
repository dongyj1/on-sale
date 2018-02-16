package com.webstore.domain;

public class Goods {
    private Long id;
    private String name;
    private String title;
    private String img;
    private String detail;
    private Double price;
    private Integer stockCountTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockCount() {
        return stockCountTotal;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCountTotal = stockCount;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                ", stockCount=" + stockCountTotal +
                '}';
    }
}
