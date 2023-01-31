package org.example.util;

import org.example.model.Book;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class Examine {

    public static void delayInReturningBook(List<Book> books) {
        LocalDateTime tenDaysAgo = LocalDateTime.now().minusDays(10);
        ZonedDateTime zdt = tenDaysAgo.atZone(ZoneId.systemDefault());
        Date output = Date.from(zdt.toInstant());

        for (Book book : books) {
            if (book.getTookAt().before(output)) {
                book.setExpired(true);
            }
        }
    }
}
