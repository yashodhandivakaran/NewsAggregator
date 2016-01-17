package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yashodhan on 16/01/16.
 */
public class News implements Parcelable {

    private String objectId;
    private String title;
    private String description;
    private String link;
    private String newspaperUid;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNewspaperUid() {
        return newspaperUid;
    }

    public void setNewspaperUid(String newspaperUid) {
        this.newspaperUid = newspaperUid;
    }

    public News() {
    }

    public News(Parcel parcel) {
        objectId = parcel.readString();
        title = parcel.readString();
        description = parcel.readString();
        link = parcel.readString();
        newspaperUid = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeString(newspaperUid);
    }

    public static Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {

        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
