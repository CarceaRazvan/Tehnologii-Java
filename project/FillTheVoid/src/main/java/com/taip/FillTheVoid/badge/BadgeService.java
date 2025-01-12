package com.taip.FillTheVoid.badge;

import com.taip.FillTheVoid.notification.BadgeDecorator;
import com.taip.FillTheVoid.notification.INotifier;
import com.taip.FillTheVoid.notification.Notifier;
import com.taip.FillTheVoid.user.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final INotifier notifier = new BadgeDecorator(
            new Notifier("user@gmail.com")
    );

    public void receiveNotificationBadge(String email) {

        notifier.send("Badge received!");

    }

    public Badge getLastBadgeByEmail(String email) {

        return badgeRepository.findLatestBadgeByUserEmail(email).get();
    }



}
