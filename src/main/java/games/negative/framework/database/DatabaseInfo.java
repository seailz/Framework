package games.negative.framework.database;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatabaseInfo {

    private String ip;
    private int port;
    private String username;
    private String password;
    private String database;

}
