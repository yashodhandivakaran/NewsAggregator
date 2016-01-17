package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yashodhan on 16/01/16.
 */
public class Newspaper implements Parcelable {

    private String id;
    private String name;
    private String rssUrl;


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

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public Newspaper() {
    }

    public Newspaper(Parcel source) {

        id = source.readString();
        name = source.readString();
        rssUrl = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(rssUrl);
    }

    public static Parcelable.Creator<Newspaper> CREATOR = new Parcelable.Creator<Newspaper>() {

        @Override
        public Newspaper createFromParcel(Parcel source) {
            return new Newspaper(source);
        }

        @Override
        public Newspaper[] newArray(int size) {
            return new Newspaper[size];
        }
    };
}
