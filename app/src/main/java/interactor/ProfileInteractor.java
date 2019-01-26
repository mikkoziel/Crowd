package interactor;

import android.util.Base64;

import java.security.Key;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import entity.Profile;

public class ProfileInteractor {
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public ProfileInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }

    public Boolean userCredentialsFilled(String username, String password)
    {
        if(username.trim().equals("")|| password.trim().equals("")) {
            setFailure("Please enter Username and Password");
            return false;
        }
        else
            return true;
    }

    public void registerLogin(String username, String password) throws Exception {
        String query = "select * from Profile where Name= '" + username + "'";
        ResultSet resultSet =_dbConnector.runQuery(query);

        if (resultSet.next())
            setFailure("Login already exist");
        else {
            String pass = encrypt(password);
            String query1 = "Insert into Profile(Name, Password) values('" + username + "', '" + pass + "')";
                int result = _dbConnector.updateQuery(query1);
                if(result > 0)
                    setSuccess("Login registration successful");
                else
                    setFailure("Login registration failed");
            }
    }

    public ResultSet checkLogin(String username, String password) throws Exception {
        String query = "select * from Profile where Name= '" + username + "'";
        ResultSet resultSet =_dbConnector.runQuery(query);
        if (resultSet.next()) {
            String pass = encrypt(password);
            if (resultSet.getString("password").equals(pass))
                setSuccess("Login Successful");
            else
                setFailure("Invalid Credentials!");
        }
        else
            setFailure("This profile doesn't exist");
        return resultSet;
    }

    public Profile setProfile(ResultSet res) throws SQLException {
        int id = res.getInt("profilID");
        String name = res.getString("name");
        int points = res.getInt("points");
        int level = res.getInt("userlevel");
        int money = res.getInt("money");
        int missingPoints = res.getInt("missingPoints");
        int avatarID = res.getInt("avatarID");
        Profile profile = new Profile(id, name, points, level, money, missingPoints, avatarID);
        setSuccess("Login successful");
        return profile;
    }

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

    public void modeCheckOld(Profile profile, String password, String passwordCheck, String passwordCheck2) throws Exception {
        String query = "Select * from Profile where profilID = " + profile.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            String name = res.getString("name");
            String oldPasswordRes = decrypt(res.getString("password"));
            if(profile.getName().equals(name) && password.equals(oldPasswordRes)){
                checkRest(passwordCheck, passwordCheck2, password);
            }
            else
                setFailure("Wrong old password");
        }
    }

    private void checkRest(String passwordCheck, String passwordCheck2, String password){
        if (!passwordCheck.equals(passwordCheck2))
            setFailure("Passwords doesn't match");
        else
            if (password.equals(passwordCheck))
                setFailure("New password is the same as old password");
            else
                setSuccess("Correct Data");
    }

    public void modeChangeToNew(String password, Profile profile) throws Exception {
        String pass = encrypt(password);
        String query = "Update Profile set password = '" + pass + "' where profilID = " + profile.getID();
        int res = _dbConnector.updateQuery(query);
        if(res > 0)
            setSuccess("Password Change successful");
        else
            setFailure("Password Change failed");
    }

    public ArrayList<Profile> getHighScore() throws Exception {
        String query = "Select Top 10 * from Profile order by points desc ";
        ResultSet res = _dbConnector.runQuery(query);
        ArrayList<Profile> high = new ArrayList<>();
        while(res.next()) {
            int id = res.getInt("profilID");
            String name = res.getString("name");
            int points = res.getInt("points");
            int level = res.getInt("userlevel");
            int money = res.getInt("money");
            int missingPoints = res.getInt("missingPoints");
            Profile profile = new Profile(id, name, points, level, money, missingPoints, 0);
            high.add(profile);
            setSuccess("HighScore set");
        }
        return high;
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

    private String encrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        return encryptedValue64.substring(0, encryptedValue64.length() - 1);

    }

    private String decrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue.substring(0, decryptedValue.length() - 1);

    }

    private static Key generateKey() throws Exception
    {
        return new SecretKeySpec(KEY.getBytes(),ALGORITHM);
    }


}
