
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://aws-0-ap-northeast-1.pooler.supabase.com:5432/postgres";
        String user = "postgres.culbikvwzdjqpqgxsayf";
        String password = System.getenv("DB_PASSWORD");

        return DriverManager.getConnection(url, user, password);
    }
}
