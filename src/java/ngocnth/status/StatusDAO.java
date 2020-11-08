/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.status;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import ngocnth.util.DBHelper;

/**
 *
 * @author Ruby
 */
public class StatusDAO implements Serializable {
    
    public StatusDTO getStatus(String statusName) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT id "
                        + "FROM tblStatus "
                        + "WHERE name = ?";
                stm = con.prepareCall(sql);
                stm.setString(1, statusName);
                rs = stm.executeQuery();
                if (rs.next()) {   
                    int id = rs.getInt("id");
                    return new StatusDTO(id, sql);
                }
            } 
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }
    
}
