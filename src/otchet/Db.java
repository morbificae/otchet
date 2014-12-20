package otchet;

import java.io.BufferedReader;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class Db {
//Глобальные структуры и переменные
    public List<String> AddressData = new ArrayList<>();
    public List<String> Data = new ArrayList<>();
    public List<String> Otchet = new ArrayList<>();
    public List<String> cfgFileData = new ArrayList<String>();
    public String basedir;
    Connection c = null;
    Statement stmt = null;
    
    
/////////////////////////////
//Рабочие методы
    void dbFileDel(){
        File file = new File("kip.db");
        file.delete();
    }
    void cloneParadoxAddressDataToList(){
        try {
            Class.forName("com.googlecode.paradox.Driver");
            c = DriverManager.getConnection("jdbc:paradox:"+basedir);
            c.setAutoCommit(false);
            System.out.println("Копируем объекты в память: ");
            stmt = c.createStatement();
            try (ResultSet rs = stmt.executeQuery( "SELECT * FROM B00001.db" )) {
                while (rs.next()) {
                    String nro  = new String(String.valueOf(rs.getString("Num_REG_OT")).getBytes("ISO-8859-1"), "cp1251");
                    String nrg  = new String(String.valueOf(rs.getString("Num_REG_GVS")).getBytes("ISO-8859-1"), "cp1251");
                    String ni   = new String(String.valueOf(rs.getString("Num_IVB")).getBytes("ISO-8859-1"), "cp1251");
                    String ad   = new String(String.valueOf(rs.getString("Adres_Doma")).getBytes("ISO-8859-1"), "cp1251");
                    String dm   = new String(String.valueOf(rs.getString("Num_Doma")).getBytes("ISO-8859-1"), "cp1251");
                    String kp   = new String(String.valueOf(rs.getString("Num_korp")).getBytes("ISO-8859-1"), "cp1251");
                    String ju   = new String(String.valueOf(rs.getString("Num_Jeu")).getBytes("ISO-8859-1"), "cp1251");
                    String mp   = new String(String.valueOf(rs.getString("Marka_Pribor")).getBytes("ISO-8859-1"), "cp1251");
                    String ugv   = new String(String.valueOf(rs.getString("Data_Post_Uch_GVS")).getBytes("ISO-8859-1"), "cp1251");
                    String uot   = new String(String.valueOf(rs.getString("Data_Sn_Uch_OT")).getBytes("ISO-8859-1"), "cp1251");
                    AddressData.add(nro);
                    AddressData.add(nrg);
                    AddressData.add(ni);
                    AddressData.add(ad);
                    AddressData.add(dm);
                    AddressData.add(kp);
                    AddressData.add(ju);
                    AddressData.add(mp);
                    AddressData.add(ugv);
                    AddressData.add(uot);
                }
            }
            stmt.close();
            c.close();
        } catch ( ClassNotFoundException | SQLException | UnsupportedEncodingException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Копирование объектов в память завершено.");
    }           // Копирование таблицы с описанием приборов из базы Paradox во временный массив
    void cloneParadoxDataToList(){
        try {
            Class.forName("com.googlecode.paradox.Driver");
            c = DriverManager.getConnection("jdbc:paradox:"+basedir);
            c.setAutoCommit(false);
            System.out.println("Копируем показания в память: ");
            stmt = c.createStatement();
            try (ResultSet rs = stmt.executeQuery( "SELECT * FROM B00003.db" )) {
                while (rs.next()) {
                    String ID   = new String(String.valueOf(rs.getString("ID")).getBytes("ISO-8859-1"), "cp1251");
                    String id   = new String(String.valueOf(rs.getString("Num_Pribor")).getBytes("ISO-8859-1"), "cp1251");
                    String ss   = new String(String.valueOf(rs.getString("Har_sistem")).getBytes("ISO-8859-1"), "cp1251");
                    String dp   = new String(String.valueOf(rs.getString("Data_pokaz")).getBytes("ISO-8859-1"), "cp1251");
                    String vp   = new String(String.valueOf(rs.getString("Vremya_pokaz")).getBytes("ISO-8859-1"), "cp1251");
                    String q1   = new String(String.valueOf(rs.getString("Qpod")).getBytes("ISO-8859-1"), "cp1251");
                    String q2   = new String(String.valueOf(rs.getString("Qobr")).getBytes("ISO-8859-1"), "cp1251");
                    String v1   = new String(String.valueOf(rs.getString("Vpod")).getBytes("ISO-8859-1"), "cp1251");
                    String v2   = new String(String.valueOf(rs.getString("Vobr")).getBytes("ISO-8859-1"), "cp1251");
                    String g1   = new String(String.valueOf(rs.getString("Gpod")).getBytes("ISO-8859-1"), "cp1251");
                    String g2   = new String(String.valueOf(rs.getString("Gobr")).getBytes("ISO-8859-1"), "cp1251");
                    String t1   = new String(String.valueOf(rs.getString("Tpod")).getBytes("ISO-8859-1"), "cp1251");
                    String t2   = new String(String.valueOf(rs.getString("Tobr")).getBytes("ISO-8859-1"), "cp1251");
                    String t3   = new String(String.valueOf(rs.getString("TXV")).getBytes("ISO-8859-1"), "cp1251");
                    String tw   = new String(String.valueOf(rs.getString("Traboti")).getBytes("ISO-8859-1"), "cp1251");
                    Data.add(ID);
                    Data.add(id);
                    Data.add(ss);
                    Data.add(dp);
                    Data.add(vp);
                    Data.add(q1);
                    Data.add(q2);
                    Data.add(v1);
                    Data.add(v2);
                    Data.add(g1);
                    Data.add(g2);
                    Data.add(t1);
                    Data.add(t2);
                    Data.add(t3);
                    Data.add(tw);
                }
            }
            stmt.close();
            c.close();
        } catch ( ClassNotFoundException | SQLException | UnsupportedEncodingException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Показания скопированы в память.");
    }                  // Копирование таблицы с показаниями приборов из базы Paradox во временный массив
    void cloneAddressDataToSQLiteObjects(){
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            }
            c = DriverManager.getConnection("jdbc:mysql://localhost/kip?" +
                                   "user=morbi&password=morbi");
            c.setAutoCommit(false);
            System.out.println("Вставляем объекты из памяти в SQLite БД:");
            stmt = c.createStatement();
            int y=1;
            for (int i=0; i<=AddressData.size()-2; ++i) {
                String sql = "INSERT INTO objects (id,nro,nrg,ivb,street,dom,korpus,jeu,Marka_Pribor,ugv,uot) " +
                        "VALUES("+(y-1)+", '"+AddressData.get(i)+"', '"+AddressData.get(i+1)+"', '"+AddressData.get(i+2)+"', '"+AddressData.get(i+3)+"', '"
                                         +AddressData.get(i+4)+"', '"+AddressData.get(i+5)+"', '"+AddressData.get(i+6)+"', '"+AddressData.get(i+7)+"', '"+AddressData.get(i+8)+"', '"+AddressData.get(i+9)+"' );";
                        stmt.executeUpdate(sql);
                        i=i+9;
                        y++;
            }

            stmt.close();
            c.commit();
            c.close();
        } catch ( ClassNotFoundException | SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Вставка объектов в SQLite БД завершена.");
    }         // Копирование таблицы с описанием приборов из временного массива в базу SQLite
    void cloneDataListToSQLiteObjects(){
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            }
            c = DriverManager.getConnection("jdbc:mysql://localhost/kip?" +
                                   "user=morbi&password=morbi");
            c.setAutoCommit(false);
            System.out.println("Вставляем показания из памяти в SQLite БД:");
            stmt = c.createStatement();
            int y=0;
            for (int i=0; i<=Data.size()-2; ++i) {
                String sql = "INSERT INTO data (id,ivb,system,date,time,q1,q2,v1,v2,g1,g2,t1,t2,t3,tw) " +
                        "VALUES('"+(y)+"', '"+String.valueOf(Data.get(i+1))+"', '"+Data.get(i+2)+"', '"+Data.get(i+3)+"', '"
                               +Data.get(i+4)+"', '"+Data.get(i + 5)+"', '"+Data.get(i+6)+"', '"+Data.get(i+7)+"', '"
                               +Data.get(i+8)+"', '"+Data.get(i + 9)+"', '"+Data.get(i+10)+"', '"+Data.get(i+11)+"', '"
                               +Data.get(i+12)+"', '"+Data.get(i+13)+"', '"+Data.get(i+14)+"' );";
                stmt.executeUpdate(sql);
                i=i+14;
                y++;
            }

            stmt.close();
            c.commit();
            c.close();
        } catch ( ClassNotFoundException | SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Показания записаны в SQLite БД.");
    }            // Копирование таблицы с показаниями приборов из временного массива в базу SQLite
    void createSQLiteTables() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            }
            c = DriverManager.getConnection("jdbc:mysql://localhost/kip?" +
                                   "user=morbi&password=morbi");
            System.out.println("Create началось заебись");

            stmt = c.createStatement();
            String sql = "CREATE TABLE objects " +
                    "(id INT PRIMARY KEY     NOT NULL," +
                    " nro            TEXT    NOT NULL, " +
                    " nrg            TEXT    NOT NULL, " +
                    " ivb            TEXT    NOT NULL, " +
                    " street         TEXT    NOT NULL, " +
                    " dom            TEXT     NOT NULL, " +
                    " korpus         TEXT, " +
                    " jeu            TEXT, " +                    
                    " Marka_Pribor   TEXT, " +
                    " ugv            TEXT, " +
                    " uot            TEXT)";
            stmt.executeUpdate(sql);
            String sql2 = "CREATE TABLE data " +
                    "(id INT PRIMARY KEY  NOT NULL," +
                    " ivb            TEXT NOT NULL, " +
                    " system         TEXT NOT NULL, " +
                    " date           DATE, " +
                    " time           TEXT NOT NULL, " +
                    " q1             TEXT NOT NULL, " +
                    " q2             TEXT, " +
                    " v1             TEXT NOT NULL, " +
                    " v2             TEXT, " +
                    " g1             TEXT NOT NULL, " +
                    " g2             TEXT, " +
                    " t1             TEXT NOT NULL, " +
                    " t2             TEXT, " +
                    " t3             TEXT, " +
                    " tw             TEXT)";
            stmt.executeUpdate(sql2);

            stmt.close();
            c.close();
        } catch ( ClassNotFoundException | SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Таблица создана");
    }                     // Создание таблиц Objects(Приборы) Data(Показания) в базе SQLite
    void createSQLiteOtchet(String last, String current) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:kip.db");
            System.out.println("Создание таблицы отчёта в БД SQLite");
            stmt = c.createStatement();
            String sql_1 = "CREATE TABLE otchet " +
                    "(id             TEXT," +
                    " nro            TEXT, " +
                    " nrg            TEXT, " +
                    " ivb            TEXT, " +
                    " street         TEXT, " +
                    " dom            TEXT, " +
                    " korpus         TEXT, " +
                    " jeu            TEXT, " +
                    " Marka_Pribor   TEXT, " +
                    " ugv            TEXT, " +
                    " uot            TEXT, " +
                    " system2        TEXT, " +
                    " date           DATE, " +
                    " time           TEXT, " +
                    " q1             TEXT, " +
                    " q2             TEXT, " +
                    " v1             TEXT, " +
                    " v2             TEXT, " +
                    " g1             TEXT, " +
                    " g2             TEXT, " +
                    " t1             TEXT, " +
                    " t2             TEXT, " +
                    " t3             TEXT, " +
                    " tw             TEXT)";
            System.out.println("Создание отчёта в БД SQLite");
            stmt.executeUpdate(sql_1);
            String sql_2 =("INSERT INTO otchet SELECT data.[id], objects.[nro], objects.[nrg], data.[ivb], ")+
                        ("objects.[street], objects.[dom], objects.[korpus], objects.[jeu], objects.[Marka_pribor], objects.[ugv], objects.[uot], ")+
                        ("data.[system], data.[date], data.[time], data.[q1], data.[q2], data.[v1], data.[v2], ")+
                        ("data.[g1], data.[g2], data.[t1], data.[t2], data.[t3], data.[tw] FROM data, objects ")+
                        (" WHERE data.[ivb]=objects.[ivb] AND data.[date] in ('")+last+("', '")+current+("')");
            stmt.executeUpdate(sql_2);

            stmt.close();
            c.close();
        } catch ( ClassNotFoundException | SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Таблица отчёта создана в БД SQLite");
    }                     // Создание таблицы Отчёты из выборки в базе SQLite
//////////////////////////////
//методы в тестовом режиме
    void configFileCreate() {
        try {
            File file = new File("config.cfg");
            file.createNewFile();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
  }
    String configFile() throws UnsupportedEncodingException, FileNotFoundException{
      BufferedReader input = null;
      String fileName = "config.cfg";
      input = new BufferedReader(new FileReader(fileName));
      try {
         String tmp;
         while ((tmp = input.readLine()) != null)
            cfgFileData.add(tmp);
      } catch (IOException e) {}
 
      String[] s = (String[])cfgFileData.toArray(new String[0]);
      basedir = s[0];
      return s[0];
      //}
   }    
    void dbExcelCreate() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, ParseException{
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:kip.db");
        System.out.println("Создаём отчёт: ");
        stmt = c.createStatement();
        String sql =("SELECT * FROM otchet"); 
        stmt.execute(sql);
        DateVariator dv = new DateVariator();
        String DCO = dv.currMonthLastDayData();


        Workbook wb = new HSSFWorkbook();
        //создание таблицы "Отчёт"
        Sheet sheet = wb.createSheet("Отчёт");  
        //создание объекта ROW(строка)
        Row row = sheet.createRow((int)0);
        sheet.createFreezePane( 0, 1, 0, 1 );
        //создание объекта CELL(ячейка)
        row.createCell(0).setCellValue("№"); row.createCell(1).setCellValue("№ ОТ");row.createCell(2).setCellValue("№ ГВ");row.createCell(3).setCellValue("ИВБ");
        row.createCell(4).setCellValue("Улица");row.createCell(5).setCellValue("Дом");row.createCell(6).setCellValue("Корп.");row.createCell(7).setCellValue("ЖЭУ");
        row.createCell(8).setCellValue("ВИД");row.createCell(9).setCellValue("КОД");row.createCell(10).setCellValue("ГВС на учёте с");row.createCell(11).setCellValue("ОТ на учёте с");
        row.createCell(12).setCellValue("Дата");row.createCell(13).setCellValue("Время");row.createCell(14).setCellValue("Qпод");row.createCell(15).setCellValue("Qобр");        
        row.createCell(16).setCellValue("Vпод");row.createCell(17).setCellValue("Vобр");row.createCell(18).setCellValue("Gпод");row.createCell(19).setCellValue("Gобр");
        row.createCell(20).setCellValue("Tпод");row.createCell(21).setCellValue("Tобр"); row.createCell(22).setCellValue("Tx"); row.createCell(23).setCellValue("Время работы");
        try (ResultSet rs = stmt.executeQuery( "SELECT * FROM otchet" )) {
            int i=1;
            while ( rs.next()) {
                        Row row1 = sheet.createRow(i);
                        row1.createCell(0).setCellValue(i);
                        row1.createCell(1).setCellValue(rs.getString("nro"));
                        row1.createCell(2).setCellValue(rs.getString("nrg"));
                        row1.createCell(3).setCellValue(rs.getString("ivb"));
                        row1.createCell(4).setCellValue(rs.getString("street"));
                        row1.createCell(5).setCellValue(rs.getString("dom"));
                        row1.createCell(6).setCellValue(rs.getString("korpus"));
                        row1.createCell(7).setCellValue(rs.getString("jeu"));
                        row1.createCell(8).setCellValue(rs.getString("Marka_Pribor"));
                        row1.createCell(9).setCellValue(rs.getString("system2"));
                        row1.createCell(10).setCellValue(rs.getString("ugv"));
                        row1.createCell(11).setCellValue(rs.getString("uot"));
                        row1.createCell(12).setCellValue(rs.getString("date"));
                        row1.createCell(13).setCellValue(rs.getString("time"));
                        row1.createCell(14).setCellValue(rs.getString("q1"));
                        row1.createCell(15).setCellValue(rs.getString("q2"));
                        row1.createCell(16).setCellValue(rs.getString("v1"));
                        row1.createCell(17).setCellValue(rs.getString("v2"));
                        row1.createCell(18).setCellValue(rs.getString("g1"));
                        row1.createCell(19).setCellValue(rs.getString("g2"));
                        row1.createCell(20).setCellValue(rs.getString("t1"));
                        row1.createCell(21).setCellValue(rs.getString("t2"));
                        row1.createCell(22).setCellValue(rs.getString("t3"));
                        row1.createCell(23).setCellValue(rs.getString("tw"));
                        i++;
                    }
        stmt.close();
        c.close();
        try (FileOutputStream fileOut = new FileOutputStream("Энергосбыт_"+DCO+".xls")) {wb.write(fileOut);}
        }
 }
        
/////////////////////////////
//опциональная хуита
    void testconn(){
    try {
        Class.forName("com.googlecode.paradox.Driver");
        c = DriverManager.getConnection("jdbc:paradox:base");
    } catch ( ClassNotFoundException | SQLException e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
    }
    System.out.println("Подключились нормально");

}                                // Проверка соединения с ParadoxDB
    void selectParadoxB00001(){
    try {
        Class.forName("com.googlecode.paradox.Driver");
        c = DriverManager.getConnection("jdbc:paradox:base");
        c.setAutoCommit(false);
        System.out.println("Открылись для выборки");

        stmt = c.createStatement();
        try (ResultSet rs = stmt.executeQuery( "SELECT * FROM B00001.db" )) {
            while ( rs.next()) {
                String nro  = new String((rs.getString("Num_REG_OT")).getBytes("ISO-8859-1"), "cp1251");
                String nrg  = new String((rs.getString("Num_REG_GVS")).getBytes("ISO-8859-1"), "cp1251");
                String id   = new String((rs.getString("Num_IVB")).getBytes("ISO-8859-1"), "cp1251");
                String ad   = new String((rs.getString("Adres_Doma")).getBytes("ISO-8859-1"), "cp1251");
                String dm   = new String((rs.getString("Num_Doma")).getBytes("ISO-8859-1"), "cp1251");
                String kp   = new String((rs.getString("Num_korp")).getBytes("ISO-8859-1"), "cp1251");
                String ju   = new String((rs.getString("Num_Jeu")).getBytes("ISO-8859-1"), "cp1251");
                String ss   = new String((rs.getString("Sistema")).getBytes("ISO-8859-1"), "cp1251");
                System.out.print( "ЭСБ-ОТ = " + nro + "; ");
                System.out.print( "ЭСБ-ГВ = " + nrg + "; ");
                System.out.print( "Номер ИВБ = " + id + "; ");
                System.out.print( "Улица = " + ad + "; ");
                System.out.print( "Дом = " + dm + "; ");
                System.out.print( "Корпус = " + kp + "; ");
                System.out.print( "ЖЭУ = " + ju + "; ");
                System.out.print( "Система = " + ss + ";\n");
                
                
            }
        }
        stmt.close();
        c.close();
    } catch ( ClassNotFoundException | SQLException | UnsupportedEncodingException e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
    }
    System.out.println("Выборка прокатила");
}                     // Просмотр таблицы с данными на приборы
    void selectParadoxB00003(){
        try {
            Class.forName("com.googlecode.paradox.Driver");
            c = DriverManager.getConnection("jdbc:paradox:base");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            try (ResultSet rs = stmt.executeQuery( "SELECT * FROM B00003.db WHERE ID > '90000'" )) {
                Object nihuya = null;
                while (rs.next()) {
                    String ID   = new String(String.valueOf(rs.getString("ID")).getBytes("ISO-8859-1"), "cp1251");
                    String id   = new String(String.valueOf(rs.getString("Num_Pribor")).getBytes("ISO-8859-1"), "cp1251");
                    String ss   = new String(String.valueOf(rs.getString("Har_sistem")).getBytes("ISO-8859-1"), "cp1251");
                    String dp   = new String(String.valueOf(rs.getString("Data_pokaz")).getBytes("ISO-8859-1"), "cp1251");
                    String vp   = new String(String.valueOf(rs.getString("Vremya_pokaz")).getBytes("ISO-8859-1"), "cp1251");
                    String q1   = new String(String.valueOf(rs.getString("Qpod")).getBytes("ISO-8859-1"), "cp1251");
                    String q2   = new String(String.valueOf(rs.getString("Qobr")).getBytes("ISO-8859-1"), "cp1251");
                    String v1   = new String(String.valueOf(rs.getString("Vpod")).getBytes("ISO-8859-1"), "cp1251");
                    String v2   = new String(String.valueOf(rs.getString("Vobr")).getBytes("ISO-8859-1"), "cp1251");
                    String g1   = new String(String.valueOf(rs.getString("Gpod")).getBytes("ISO-8859-1"), "cp1251");
                    String g2   = new String(String.valueOf(rs.getString("Gobr")).getBytes("ISO-8859-1"), "cp1251");
                    String t1   = new String(String.valueOf(rs.getString("Tpod")).getBytes("ISO-8859-1"), "cp1251");
                    String t2   = new String(String.valueOf(rs.getString("Tobr")).getBytes("ISO-8859-1"), "cp1251");
                    String t3   = new String(String.valueOf(rs.getString("TXV")).getBytes("ISO-8859-1"), "cp1251");
                    String tw   = new String(String.valueOf(rs.getString("Traboti")).getBytes("ISO-8859-1"), "cp1251");
                    
                    System.out.print( "ID: "        + ID + " ");
                    System.out.print( "Номер ИВБ: " + id + " ");
                    System.out.print( "Система: "   + ss + " ");
                    System.out.print( "Дата: "      + dp + " ");
                    System.out.print( "Время: "     + vp + " ");
                    System.out.print( "Q1: "        + q1 + " ");
                    System.out.print( "Q2: "        + q2 + " ");
                    System.out.print( "V1: "        + v1 + " ");
                    System.out.print( "V2: "        + v2 + " ");
                    System.out.print( "G1: "        + g1 + " ");
                    System.out.print( "G2: "        + g2 + " ");
                    System.out.print( "T1: "        + t1 + " ");
                    System.out.print( "T2: "        + t2 + " ");
                    System.out.print( "T3: "        + t3 + " ");
                    System.out.print( "Время Работы: " + tw + "\n");
                }
            }
            stmt.close();
            c.close();
        } catch ( ClassNotFoundException | SQLException | UnsupportedEncodingException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Выборка прокатила");
    }                     // Просмотр таблицы с данными по показаниям
    void printAddressList() {
        int i = 0;
        while (i != AddressData.size()) {
            System.out.print(AddressData.get(i) + " ");
            i++;
            System.out.print(AddressData.get(i) + " ");
            i++;
            System.out.print(AddressData.get(i) + " ");
            i++;
            System.out.print(AddressData.get(i) + " ");
            i++;
            System.out.print(AddressData.get(i) + " ");
            i++;
            System.out.print(AddressData.get(i) + " ");
            i++;
            System.out.print(AddressData.get(i) + " ");
            i++;
            System.out.print(AddressData.get(i) + " \n");
            i++;
        }

    }                       // Просмотр временного массива адресов
    void printDataList() {
        int i = 0;
        while (i != Data.size()) {
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " ");
            i++;
            System.out.print(Data.get(i) + " \n");
            i++;
        }

    }                          // Просмотр временного массива показаний
/////////////////////////////
}
