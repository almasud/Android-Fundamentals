package com.example.almasud.fundamental.map_location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The DirectionResponse model class based on the response JSON data of API.
 * The api URL is only for test and alternative of google direction api
 * because the lack of google billing account.
 */


public class DirectionResponse {
    @SerializedName("formatVersion")
    @Expose
    private String formatVersion;
    @SerializedName("routes")
    @Expose
    private List<Route> routes = null;

    public String getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public static class Leg {

        @SerializedName("summary")
        @Expose
        private Summary_ summary;
        @SerializedName("points")
        @Expose
        private List<Point> points = null;

        public Summary_ getSummary() {
            return summary;
        }

        public void setSummary(Summary_ summary) {
            this.summary = summary;
        }

        public List<Point> getPoints() {
            return points;
        }

        public void setPoints(List<Point> points) {
            this.points = points;
        }

    }

    public static class Point {

        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

    }

    public static class Route {

        @SerializedName("summary")
        @Expose
        private Summary summary;
        @SerializedName("legs")
        @Expose
        private List<Leg> legs = null;
        @SerializedName("sections")
        @Expose
        private List<Section> sections = null;

        public Summary getSummary() {
            return summary;
        }

        public void setSummary(Summary summary) {
            this.summary = summary;
        }

        public List<Leg> getLegs() {
            return legs;
        }

        public void setLegs(List<Leg> legs) {
            this.legs = legs;
        }

        public List<Section> getSections() {
            return sections;
        }

        public void setSections(List<Section> sections) {
            this.sections = sections;
        }

    }

    public static class Section {

        @SerializedName("startPointIndex")
        @Expose
        private Integer startPointIndex;
        @SerializedName("endPointIndex")
        @Expose
        private Integer endPointIndex;
        @SerializedName("sectionType")
        @Expose
        private String sectionType;
        @SerializedName("travelMode")
        @Expose
        private String travelMode;

        public Integer getStartPointIndex() {
            return startPointIndex;
        }

        public void setStartPointIndex(Integer startPointIndex) {
            this.startPointIndex = startPointIndex;
        }

        public Integer getEndPointIndex() {
            return endPointIndex;
        }

        public void setEndPointIndex(Integer endPointIndex) {
            this.endPointIndex = endPointIndex;
        }

        public String getSectionType() {
            return sectionType;
        }

        public void setSectionType(String sectionType) {
            this.sectionType = sectionType;
        }

        public String getTravelMode() {
            return travelMode;
        }

        public void setTravelMode(String travelMode) {
            this.travelMode = travelMode;
        }

    }

    public static class Summary {

        @SerializedName("lengthInMeters")
        @Expose
        private Integer lengthInMeters;
        @SerializedName("travelTimeInSeconds")
        @Expose
        private Integer travelTimeInSeconds;
        @SerializedName("trafficDelayInSeconds")
        @Expose
        private Integer trafficDelayInSeconds;
        @SerializedName("departureTime")
        @Expose
        private String departureTime;
        @SerializedName("arrivalTime")
        @Expose
        private String arrivalTime;

        public Integer getLengthInMeters() {
            return lengthInMeters;
        }

        public void setLengthInMeters(Integer lengthInMeters) {
            this.lengthInMeters = lengthInMeters;
        }

        public Integer getTravelTimeInSeconds() {
            return travelTimeInSeconds;
        }

        public void setTravelTimeInSeconds(Integer travelTimeInSeconds) {
            this.travelTimeInSeconds = travelTimeInSeconds;
        }

        public Integer getTrafficDelayInSeconds() {
            return trafficDelayInSeconds;
        }

        public void setTrafficDelayInSeconds(Integer trafficDelayInSeconds) {
            this.trafficDelayInSeconds = trafficDelayInSeconds;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

    }

    public static class Summary_ {

        @SerializedName("lengthInMeters")
        @Expose
        private Integer lengthInMeters;
        @SerializedName("travelTimeInSeconds")
        @Expose
        private Integer travelTimeInSeconds;
        @SerializedName("trafficDelayInSeconds")
        @Expose
        private Integer trafficDelayInSeconds;
        @SerializedName("departureTime")
        @Expose
        private String departureTime;
        @SerializedName("arrivalTime")
        @Expose
        private String arrivalTime;

        public Integer getLengthInMeters() {
            return lengthInMeters;
        }

        public void setLengthInMeters(Integer lengthInMeters) {
            this.lengthInMeters = lengthInMeters;
        }

        public Integer getTravelTimeInSeconds() {
            return travelTimeInSeconds;
        }

        public void setTravelTimeInSeconds(Integer travelTimeInSeconds) {
            this.travelTimeInSeconds = travelTimeInSeconds;
        }

        public Integer getTrafficDelayInSeconds() {
            return trafficDelayInSeconds;
        }

        public void setTrafficDelayInSeconds(Integer trafficDelayInSeconds) {
            this.trafficDelayInSeconds = trafficDelayInSeconds;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

    }
}
