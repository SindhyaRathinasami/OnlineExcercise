package main.java.logic.workingdays;

import java.time.DayOfWeek;

public class UsualWorkingDays extends WorkingDays {

    private static UsualWorkingDays instance = null;

    public static UsualWorkingDays getInstance() {
        if (instance == null) {
            instance = new UsualWorkingDays();
        }
        return instance;
    }

    private UsualWorkingDays() {
        super();
    }

    @Override
    protected void setupWorkingDays() {
        this.isWorkingDayMap.put(DayOfWeek.MONDAY, true);
        this.isWorkingDayMap.put(DayOfWeek.TUESDAY, true);
        this.isWorkingDayMap.put(DayOfWeek.WEDNESDAY, true);
        this.isWorkingDayMap.put(DayOfWeek.THURSDAY, true);
        this.isWorkingDayMap.put(DayOfWeek.FRIDAY, true);
        this.isWorkingDayMap.put(DayOfWeek.SATURDAY, false);
        this.isWorkingDayMap.put(DayOfWeek.SUNDAY, false);
    }
}
