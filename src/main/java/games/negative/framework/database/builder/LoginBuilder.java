package games.negative.framework.database.builder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginBuilder {

    private String ip;
    private int port;
    private String username;
    private String password;
    private String database;

}
