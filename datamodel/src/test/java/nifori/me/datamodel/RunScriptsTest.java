package nifori.me.datamodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;

import nifori.me.datamodel.run.RunScripts;

@DataJpaTest
@ContextConfiguration(classes = RunScripts.class)
@EnableAutoConfiguration
public class RunScriptsTest {

  private static final String PATH = "classpath:testsql/*.sql";

  @Autowired
  private RunScripts runScripts;
  @Autowired
  private JdbcTemplate template;

  @Test
  public void runTestScripts() {
    runScripts.setPATH(PATH);
    assertDoesNotThrow(() -> runScripts.run());

    assertThat(JdbcTestUtils.countRowsInTable(template, "TEST")).isEqualTo(5);
    assertThat(JdbcTestUtils.countRowsInTable(template, "DATAMODEL_CHECKPOINT_T01")).isEqualTo(6);
    assertThat(JdbcTestUtils.countRowsInTableWhere(template, "DATAMODEL_CHECKPOINT_T01", "ScriptOID = 'V1.1001'"))
        .isEqualTo(1);
  }
}
