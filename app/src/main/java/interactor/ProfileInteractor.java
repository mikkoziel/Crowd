package interactor;

import android.util.Base64;

import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import entity.Profile;
import tools.DataBaseConnector;

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
        String query = "select * from Profile where name= '" + username + "'";
        ResultSet resultSet =_dbConnector.runQuery(query);

        if (resultSet.next())
            setFailure("Login already exist");
        else {
            String pass = encrypt(password);
            String query1 = "INSERT INTO Profile(name, password, points, " +"userLevel, " +
                    "money, avatarID) values('" + username + "', '" + pass + "', '" + "0" +
                    "', '" + "1" + "', '" + "0" + "', '" + "0" + "')";
                int result = _dbConnector.updateQuery(query1);
                if(result > 0)
                    setSuccess("Login registration successful");
                else
                    setFailure("Login registration failed");
            }
    }

    public ResultSet checkLogin(String username, String password) throws Exception {
        String query = "select * from Profile where name= '" + username + "'";
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

    public Profile createProfile(ResultSet res) throws SQLException
    {
        int id = res.getInt("profileID");
        String name = res.getString("name");
        int points = res.getInt("points");
        int level = res.getInt("userLevel");
        int money = res.getInt("money");
        int missingPoints = res.getInt("missingPoints");

        Profile profile = new Profile(id, name, points, level, money, missingPoints);
        setSuccess("Login completed successfully");
        return profile;
    }

    public int getAvatarID(Profile profile) throws SQLException
    {
        String query = "select * from Profile where profileID = " + profile.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            return res.getInt("avatarID");
        } else
            return -1;
    }

    public void modeCheckOld(Profile profile, String password, String passwordCheck, String passwordCheck2) throws Exception {
        String query = "Select * from Profile where profileID = " + profile.getID();
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
        String query = "Update Profile set password = '" + pass + "' where profileID = " + profile.getID();
        int res = _dbConnector.updateQuery(query);
        if(res > 0)
            setSuccess("Password Change successful");
        else
            setFailure("Password Change failed");
    }

    public void spendMoney(Profile profile){
        String query = "Update Profile set money = " + profile.getMoney() + "where profileID = " +profile.getID();
        int res = _dbConnector.updateQuery(query);
        if(res > 0) {
            setSuccess("Item successfully bought");
        }
        else{
            setFailure("Something went wrong. Try again.");
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
