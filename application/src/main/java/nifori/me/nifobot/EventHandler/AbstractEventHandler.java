package nifori.me.nifobot.EventHandler;

import discord4j.core.event.domain.Event;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public abstract class AbstractEventHandler {

  Class<? extends Event> eventClass;

  public AbstractEventHandler(Class<? extends Event> event) {
    this.eventClass = event;
  }

  public void handleEvent(Event event) {
    try {
      if (eventMatches(event)) {
        executeAbstract(event);
      }
    } catch (Exception e) {
      log.error(e, e);
    }
  }

  protected boolean eventMatches(Event event) {
    return event != null && event.getClass()
        .equals(this.getEventClass());
  }

  protected abstract void executeAbstract(Event event);

}
