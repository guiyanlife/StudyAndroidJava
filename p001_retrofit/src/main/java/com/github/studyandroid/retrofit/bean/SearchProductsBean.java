package com.github.studyandroid.retrofit.bean;

import java.io.Serializable;
import java.util.List;

public class SearchProductsBean implements Serializable {
    private String msg;
    private String code;
    private List<DataBean> data;
    private String page;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "SearchProductsBean{" +
                "\n    msg='" + msg + '\'' +
                ",\n    code='" + code + '\'' +
                ",\n    data=" + data +
                ",\n    page='" + page + '\'' +
                "\n" + '}';
    }

    public static class DataBean {
        private int bargainPrice;
        private int itemtype;
        private int pid;
        private double price;
        private int pscid;
        private int salenum;
        private int sellerid;
        private String createtime;
        private String detailUrl;
        private String images;
        private String subhead;
        private String title;

        public int getBargainPrice() {
            return bargainPrice;
        }

        public void setBargainPrice(int bargainPrice) {
            this.bargainPrice = bargainPrice;
        }

        public int getItemtype() {
            return itemtype;
        }

        public void setItemtype(int itemtype) {
            this.itemtype = itemtype;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getPscid() {
            return pscid;
        }

        public void setPscid(int pscid) {
            this.pscid = pscid;
        }

        public int getSalenum() {
            return salenum;
        }

        public void setSalenum(int salenum) {
            this.salenum = salenum;
        }

        public int getSellerid() {
            return sellerid;
        }

        public void setSellerid(int sellerid) {
            this.sellerid = sellerid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getSubhead() {
            return subhead;
        }

        public void setSubhead(String subhead) {
            this.subhead = subhead;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "bargainPrice=" + bargainPrice +
                    ", itemtype=" + itemtype +
                    ", pid=" + pid +
                    ", price=" + price +
                    ", pscid=" + pscid +
                    ", salenum=" + salenum +
                    ", sellerid=" + sellerid +
                    ", createtime='" + createtime + '\'' +
                    ", detailUrl='" + detailUrl + '\'' +
                    ", images='" + images + '\'' +
                    ", subhead='" + subhead + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
