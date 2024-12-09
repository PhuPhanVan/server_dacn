package TranQuocToan.Java.DoAn.Model;

import java.util.Objects;

public class YouTubeItem {

    private String url;
    private String title;
    private String thumbnail;
    private String description;
    private String duration;  // ISO 8601 duration (e.g., PT1H30M)
    private long viewCount;  // Number of views

    // Constructor with all fields
    public YouTubeItem(String url, String title, String thumbnail, String description, String duration, long viewCount) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        this.url = url;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.duration = duration;
        this.viewCount = viewCount;
    }

    // Constructor with essential fields (without duration and viewCount)
    public YouTubeItem(String url, String title, String thumbnail, String description) {
        this(url, title, thumbnail, description, null, 0); // default values for duration and viewCount
    }

    // Getters and Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    // Override equals() and hashCode() for correct object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YouTubeItem that = (YouTubeItem) o;
        return viewCount == that.viewCount &&
                Objects.equals(url, that.url) &&
                Objects.equals(title, that.title) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(description, that.description) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title, thumbnail, description, duration, viewCount);
    }

    // Override toString() for a readable representation of the object
    @Override
    public String toString() {
        return "YouTubeItem{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", viewCount=" + viewCount +
                '}';
    }
}
