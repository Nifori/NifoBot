package nifori.me.datamodel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
public class RunScriptsTest {

    private static final String PATH = "classpath:testsql/scripts/*.sql";
    private static final String PATH_FOR_SYSTEM = "classpath:testsql/system/*.sql";
    private static final String INSERT_CHECKPOINT_SQL = "INSERT INTO DATAMODEL_CHECKPOINT_T01(OID, ScriptOID, ExecutionTime) VALUES (NEXT VALUE FOR DATAMODEL_CHECKPOINT_Q01, ?, CURRENT_TIMESTAMP)";
    private static final String SELECT_CHECKPOINT_SQL = "SELECT * FROM DATAMODEL_CHECKPOINT_T01 WHERE ScriptOID = ?";

    @Autowired
    private DataSource ds;
    @Autowired
    private JdbcTemplate template;

    @Test
    public void verifyScriptOrder() throws IOException, SQLException {

        String lastFilename = "";

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        for (Resource script : resolver.getResources(PATH)) {
            String currentFilename = script.getFilename();
            assertThat(currentFilename.compareTo(lastFilename)).isPositive();
            lastFilename = currentFilename;
        }

    }

    @Test
    public void runTestScripts() throws SQLException {
        assertDoesNotThrow(() -> new RunScripts(ds, PATH, PATH_FOR_SYSTEM, INSERT_CHECKPOINT_SQL, SELECT_CHECKPOINT_SQL));
       
        assertThat(JdbcTestUtils.countRowsInTable(template, "TEST")).isEqualTo(5);
        assertThat(JdbcTestUtils.countRowsInTable(template, "DATAMODEL_CHECKPOINT_T01")).isEqualTo(6);
        assertThat(JdbcTestUtils.countRowsInTableWhere(template, "DATAMODEL_CHECKPOINT_T01", "ScriptOID = 'V1.1001'")).isEqualTo(1);
    }
}
