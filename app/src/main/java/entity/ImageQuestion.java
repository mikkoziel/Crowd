package entity;

import android.media.Image;

public class ImageQuestion extends Question{

    private Image _image;

    public ImageQuestion(Image image)
    {
        this._image = image;
    }

    public void setImage(Image image)
    {
        this._image = image;
    }

    public Image getImage()
    {
        return _image;
    }
}
