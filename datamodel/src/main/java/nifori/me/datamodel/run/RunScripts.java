package nifori.me.datamodel.run;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;

import javax.sql.DataSource;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import nifori.me.datamodel.checkpoint.Checkpoint;
import nifori.me.datamodel.checkpoint.CheckpointRepository;

@Component
@Log4j2
@RequiredArgsConstructor
public class RunScripts {

  @Setter
  private String PATH = "classpath:sql/*.sql";

  private final DataSource dataSource;
  private final CheckpointRepository repository;

  public void run() throws IOException, SQLException {
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    try (Connection con = dataSource.getConnection()) {
      Arrays.stream(resolver.getResources(PATH))
          .sorted(Comparator.comparing(Resource::getFilename))
          .forEach(script -> runScript(con, script));
      con.commit();
    }
  }

  private void runScript(Connection con, Resource script) {
    try {
      if (!checkAlreadyExecuted(script)) {
        log.info("Executing {}", script.getFilename());
        ScriptUtils.executeSqlScript(con, script);
        insertScriptIntoCheckpoint(script);
      }
    } catch (SQLException e) {
      log.error(e, e);
    }
  }

  private void insertScriptIntoCheckpoint(Resource script) {
    var checkpoint = new Checkpoint(getScriptID(script), Timestamp.from(Instant.now()));
    repository.saveAndFlush(checkpoint);
  }

  private boolean checkAlreadyExecuted(Resource script) throws SQLException {
    return repository.findByScriptOid(getScriptID(script))
        .isPresent();
  }

  private String getScriptID(Resource script) {
    return script.getFilename()
        .split("__")[0];
  }

}
