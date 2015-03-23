
package com.loujin.task;

/**
 * @Description: 后台任务管理�? * @author ziye - ziye[at]mogujie.com
 * @date 2014-4-16 下午3:16:00
 */
public class TaskManager {

    private static TaskManager instance = new TaskManager();

    private static final int TRIGGER_DELAY_SECOND = 1000;

    private TaskTrigger trigger;

    private TimerHelper timerHelper;

    private TaskManager() {
        trigger = new TaskTrigger();
        timerHelper = new TimerHelper(TRIGGER_DELAY_SECOND, trigger);
        startTrigger();
    }

    public static TaskManager getInstance() {
        if (null == instance) {
            synchronized (instance) {
                instance = new TaskManager();
            }
        }
        return instance;
    }

    public boolean trigger(ITask task) {
        if (null != task) {
            return trigger.trigger(task);
        }
        return true;
    }

    /**
     * @Description: 停止出发�?��task
     * @return
     */
    public boolean stopTrigger() {
        if (null != timerHelper && timerHelper.isRunning()) {
            timerHelper.stopTimer();
        }

        return true;
    }

    /**
     * @Description: �?��触发
     * @return
     */
    public boolean startTrigger() {
        if (null != timerHelper && !timerHelper.isRunning()) {
            timerHelper.startTimer(true);
        }
        return true;
    }

}
