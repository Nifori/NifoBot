package nifori.me.nifobot.feature;

import org.togglz.core.Feature;
import org.togglz.core.context.FeatureContext;

public enum NBFeature implements Feature {

  PORT_OBSERVATION;

  public boolean isActive() {
    return FeatureContext.getFeatureManager()
        .isActive(this);
  }
}
