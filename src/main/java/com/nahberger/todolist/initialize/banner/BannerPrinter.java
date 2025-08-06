package com.nahberger.todolist.initialize.banner;

public class BannerPrinter {

    private static final String FOOTER = "   (by Michael Nahberger)";
    private static final String BANNER = """
              _____           ____          _     _     _  \s
             |_   _|__       |  _ \\  ___   | |   (_)___| |_\s
               | |/ _ \\ _____| | | |/ _ \\  | |   | / __| __|
               | | (_) |_____| |_| | (_) | | |___| \\__ \\ |_\s
               |_|\\___/      |____/ \\___/  |_____|_|___/\\__|""";

    public static void print() {
        System.out.println();
        System.out.println(BANNER);
        System.out.println(FOOTER);
        System.out.println();
    }

}
