package otchet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


public class Otchet {
    public static void main(String[] argv) throws ParseException, IOException, FileNotFoundException, SQLException, ClassNotFoundException {
        
     Db db = new Db();
     DateVariator dv = new DateVariator();
     

     db.dbFileDel();
     db.createSQLiteTables();
     db.cloneParadoxAddressDataToList();
     db.cloneAddressDataToSQLiteObjects();
     db.cloneParadoxDataToList();
     db.cloneDataListToSQLiteObjects();
     //db.createSQLiteOtchet(dv.prevPrevMonthLastDayData(), dv.prevMonthLastDayData());
     //db.dbExcelCreate();
    }
}