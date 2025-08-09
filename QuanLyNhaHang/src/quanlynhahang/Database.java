
package quanlynhahang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Kết nối database mysql
public class Database {
  public static Connection connectDb(){
      try{
          Class.forName("com.mysql.cj.jdbc.Driver");

          Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Restaurant", "root","123456");
          return connect;  
      }catch(Exception e){
          e.printStackTrace();
      }
      return null;
  }

}
