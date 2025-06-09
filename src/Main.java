import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){

        DataSource dataSource = createDataSource();

        try(Connection connection = createDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt1 = connection.prepareStatement("UPDATE Stavka SET CijenaPoKomadu = CijenaPoKomadu + ? WHERE IDStavka = ?");
                PreparedStatement stmt2=connection.prepareStatement("UPDATE Stavka SET CijenaPoKomadu = CijenaPoKomadu + ? WHERE IDStavka = ?")){;

                stmt1.setInt(1,10);
                stmt1.setInt(2,8);
                stmt1.executeUpdate();

                stmt2.setInt(1,-10);
                stmt2.setInt(2,9);
                stmt2.executeUpdate();


                connection.commit();
                System.out.println("Cijene su uspiješno ažurirane");
            }catch (SQLException e){
                connection.rollback();
                System.out.println("Greška kod ažuriranja cijena");
                e.printStackTrace();
            }
        }catch (SQLException e){

            System.out.println("Greska kod spajanja na bazu");
            e.printStackTrace();
        }

    }
    private static DataSource createDataSource() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(61522);
        dataSource.setDatabaseName("AdventureWorksOBP");
        dataSource.setUser("sa");
        dataSource.setPassword("SQL");
        dataSource.setEncrypt(false);
        return dataSource;
    }
}
