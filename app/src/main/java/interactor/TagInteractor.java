package interactor;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagInteractor {

    private DataBaseConnector _dbConnector;
    private Connection _connection;
    private Boolean _isConnect;

    public TagInteractor()
    {
        _dbConnector = new DataBaseConnector();
        _connection = _dbConnector.makeConnection();
        _isConnect = _dbConnector.checkConnection(_connection);
    }

    public ResultSet getTagsFromDB(Connection connection){
        String query = "select * from Tag";
        ResultSet res = _dbConnector.runQuery(query, connection);
        return res;
    }

    public ArrayAdapter<String> getTags(Activity activity) throws SQLException {
        ResultSet res;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line);

        if (_isConnect) {
            res = getTagsFromDB(_connection);
            while (res.next()) {
                String name = res.getString("tag");
                adapter.add(name);
//                _dbConnector.setResult("Tags ");
//                _dbConnector.success(false);
            }
        }

        return adapter;
    }

}
