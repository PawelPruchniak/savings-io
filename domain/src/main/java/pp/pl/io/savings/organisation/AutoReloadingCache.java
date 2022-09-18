package pp.pl.io.savings.organisation;


import org.slf4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public interface AutoReloadingCache {

  void checkForUpdates();

  default Timer scheduleAutoReloads(final AutoReloadingCache self, final long autoReloadIntervalInMilliseconds, final Logger log,
                                    final String taskDescription) {
    final TimerTask task = new TimerTask() {
      @Override
      public void run() {
        self.checkForUpdates();
      }
    };
    final Timer timer = new Timer();
    log.info("Scheduling {}, reload every {} ms.", taskDescription, autoReloadIntervalInMilliseconds);
    timer.scheduleAtFixedRate(task, 0, autoReloadIntervalInMilliseconds);
    return timer;
  }
}
