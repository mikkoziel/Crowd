package interactor;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Avatar;

public class AvatarInteractor {

    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public AvatarInteractor() {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }


    public ArrayList<Avatar> getAllAvatars() throws SQLException {
        ArrayList<Avatar> avatars = new ArrayList<>();
        String query = "Select * from Avatar";
        ResultSet res = _dbConnector.runQuery(query);
        while (res.next()) {
            int id = res.getInt("avatarID");
            int itemId = res.getInt("itemID");
            Blob blobImage = res.getBlob("avatar");
            Blob blobLocked = res.getBlob("locked");
            byte[] byteImage = blobImage.getBytes(1, (int) blobImage.length());
            byte[] byteLocked = null;
            if(!res.wasNull()) {
                byteLocked = blobLocked.getBytes(1, (int) blobLocked.length());
            }
            Avatar avatar = new Avatar(id, byteImage, byteLocked, itemId);
            avatars.add(avatar);
        }
        setSuccess("Avatars set");
        return avatars;
    }


    /*
    public void getAvatar(Profile profile) throws SQLException{
        String query = "Select * from Avatar where avatarID = " + profile.getAvatarID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            Blob blobImage = res.getBlob("avatar");
            byte[] byteImage = blobImage.getBytes(1, (int) blobImage.length());
            profile.setAvatar(byteImage);
        }
        else{
            setFailure("Wrong old password");
        }
    }
    */

    /*public ArrayList<byte[]> getAllAvatars() throws SQLException {
        ArrayList<byte[]> avatars = new ArrayList<>();
        String query = "Select * from Avatar";
        ResultSet res = _dbConnector.runQuery(query);
        while (res.next()) {
            Blob blobImage = res.getBlob("avatar");
            byte[] byteImage = blobImage.getBytes(1, (int) blobImage.length());
            avatars.add(byteImage);
        }
        setSuccess("Avatars downloaded");
        return avatars;
    } */

    /*
    public void changeAvatar(Profile profile, int avatar) {
        String query = "Update Profile set avatarID= " + avatar + "where profileID = " + profile.getID();
        int res = _dbConnector.updateQuery(query);
        if (res > 0) {
            setSuccess("Avatar changed");
        }else{
            setFailure("Avatar not changed");
        }
    }
    */


    private void setSuccess(String message)
    {
        _result = message;
        _isSuccess = true;
    }

    private void setFailure(String message)
    {
        _result = message;
        _isSuccess = false;
    }

    public Boolean isSuccess(){return _isSuccess;}
    public String getResult(){return _result;}

    public void endWork()
    {
        try {
            _dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
