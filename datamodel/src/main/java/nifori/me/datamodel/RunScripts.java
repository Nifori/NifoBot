package nifori.me.datamodel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

import javax.sql.DataSource;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@Setter
public class RunScripts {

    private String INSERT_CHECKPOINT_SQL = "INSERT INTO DATAMODEL_CHECKPOINT_T01(OID, ScriptOID, ExecutionTime) VALUES (NEXTVAL(DATAMODEL_CHECKPOINT_Q01), ?, CURRENT_TIMESTAMP)";
    private String SELECT_CHECKPOINT_SQL = "SELECT * FROM DATAMODEL_CHECKPOINT_T01 WHERE ScriptOID = ?";

    private String PATH = "classpath:sql/scripts/*.sql";
    private String PATH_FOR_SYSTEM = "classpath:sql/system/*.sql";

    private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @Autowired
    public RunScripts(DataSource ds) throws IOException, SQLException {
        run(ds);
    }

    public RunScripts(DataSource ds, String PATH, String PATH_FOR_SYSTEM, String INSERT_CHECKPOINT_SQL,
                      String SELECT_CHECKPOINT_SQL) throws IOException, SQLException {
        this.PATH = PATH;
        this.PATH_FOR_SYSTEM = PATH_FOR_SYSTEM;
        this.INSERT_CHECKPOINT_SQL = INSERT_CHECKPOINT_SQL;
        this.SELECT_CHECKPOINT_SQL = SELECT_CHECKPOINT_SQL;

        run(ds);
    }

    public void run(DataSource ds) throws IOException, SQLException {
        try (Connection con = ds.getConnection()) {
            validateSystemScripts(con);
            Arrays.stream(resolver.getResources(PATH)).sorted(Comparator.comparing(Resource::getFilename)).forEach(script -> runScript(script, con));
        }
    }

    @SneakyThrows
    private void runScript(Resource script, Connection con) {
        log.info(script.getURI());

        if (!checkAlreadyExecuted(con, script)) {
            ScriptUtils.executeSqlScript(con, script);
            insertScriptIntoCheckpoint(con, script);
        }
    }

    private void insertScriptIntoCheckpoint(Connection con, Resource script) throws SQLException {
        try (PreparedStatement insertStmt = con.prepareStatement(INSERT_CHECKPOINT_SQL)) {
            insertStmt.setString(1, getScriptID(script));
            insertStmt.executeUpdate();
        }
    }

    private boolean checkAlreadyExecuted(Connection con, Resource script) throws SQLException {
        try (PreparedStatement insertStmt = con.prepareStatement(SELECT_CHECKPOINT_SQL)) {
            insertStmt.setString(1, getScriptID(script));
            ResultSet results = insertStmt.executeQuery();
            return results.next();
        }
    }

    private String getScriptID(Resource script) {
        return script.getFilename().split("__")[0];
    }

    private void validateSystemScripts(Connection con) throws IOException {
        for (Resource script : resolver.getResources(PATH_FOR_SYSTEM)) {
            log.info(script.getURI());
            ScriptUtils.executeSqlScript(con, script);
        }
    }
}
