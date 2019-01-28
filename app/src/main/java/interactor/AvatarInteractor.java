package interactor;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.AppContent;
import entity.Avatar;
import entity.Profile;

public class AvatarInteractor {

    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public AvatarInteractor() {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }


//    public void setAvatar(Profile profile) throws SQLException {
//        String query = "Select * from Avatar where avatarID = " + profile.getAvatar().getID();
//        ResultSet res = _dbConnector.runQuery(query);
//        if (res.next()) {
//            Blob blobImage = res.getBlob("avatar");
//            byte[] byteImage = blobImage.getBytes(1, (int) blobImage.length());
//            //TODO soon
//            profile.setAvatar(null);
//        }
//        else{
//            setFailure("User Avatar error");
//        }
//    }


    public Avatar getAvatar(int ID, ArrayList<Avatar> avatars){
        for(Avatar x : avatars){
            if(x.getID()==ID){
                return x;
            }
        }
        return null;
    }

    public void getAllAvatars(AppContent appContent) throws SQLException {
        String query = "Select * from Avatar";
        ResultSet res = _dbConnector.runQuery(query);
        while (res.next()) {
            int id = res.getInt("avatarID");
            Blob blobImage = res.getBlob("avatar");
            Blob blobLocked = res.getBlob("locked");
            byte[] byteImage = blobImage.getBytes(1, (int) blobImage.length());
            byte[] byteLocked = null;
            if(!res.wasNull()) {
                byteLocked = blobImage.getBytes(1, (int) blobLocked.length());
            }
            Avatar avatar = new Avatar(id, byteImage, byteLocked);
            appContent.setAvatar(avatar);
        }
    }


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
