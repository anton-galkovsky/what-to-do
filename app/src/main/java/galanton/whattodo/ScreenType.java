package galanton.whattodo;

enum ScreenType {
    ALL_TIME("counter"),
    DAY("today_counter");

    final String value;

    ScreenType(String value) {
        this.value = value;
    }
}
