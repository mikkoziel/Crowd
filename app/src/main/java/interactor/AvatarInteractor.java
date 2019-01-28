package interactor;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

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


    public void setAvatar(Profile profile) throws SQLException {
        String query = "Select * from Avatar where avatarID = " + profile.getAvatar().getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            Blob blobImage = res.getBlob("avatar");
            byte[] byteImage = blobImage.getBytes(1, (int) blobImage.length());
            //TODO soon
            profile.setAvatar(null);
        }
        else{
            setFailure("Wrong old password");// TODO e?
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
