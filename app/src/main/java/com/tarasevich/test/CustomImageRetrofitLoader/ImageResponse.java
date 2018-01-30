package com.tarasevich.test.CustomImageRetrofitLoader;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class ImageResponse extends ArrayList<ImageResponse.Image>{


    public static class Image{
        @SerializedName("date")
        public int date;
        @SerializedName("id")
        public int imageId;
        @SerializedName("lat")
        public byte lat;
        @SerializedName("lng")
        public byte lng;
        @SerializedName("url")
        public String url;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        public byte getLat() {
            return lat;
        }

        public void setLat(byte lat) {
            this.lat = lat;
        }

        public byte getLng() {
            return lng;
        }

        public void setLng(byte lng) {
            this.lng = lng;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
