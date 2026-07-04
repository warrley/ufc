public class draft {
    public enum WeekDay{
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    public static void main(String args[]) {
        WeekDay today = WeekDay.FRIDAY;
        System.out.println("Today is " + today);
    }

}

