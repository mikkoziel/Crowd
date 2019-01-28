package interactor;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Item;

public class ShopInteractor {

    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public ShopInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }

    public ArrayList<Item> getShopContent() throws Exception {
        String query = "Select * from Shop";
        ResultSet res = _dbConnector.runQuery(query);
        ArrayList<Item> items = new ArrayList<>();
        while(res.next()) {
            int itemId = res.getInt("itemID");
            String name = res.getString("name");
            Blob blobIcon = res.getBlob("icon");
            int price = res.getInt("price");
            String description = res.getString("description");

            byte[] byteIcon = blobIcon.getBytes(1, (int)blobIcon.length());
            Item item = new Item(itemId, name, byteIcon, price, description);
            items.add(item);
            setSuccess("Items set");
        }
        return items;
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
