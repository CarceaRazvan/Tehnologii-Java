package com.taip.FillTheVoid.notification;

import com.taip.FillTheVoid.badge.BadgeRepository;
import com.taip.FillTheVoid.badge.BadgeService;
import com.taip.FillTheVoid.user.UserRepository;
import com.taip.FillTheVoid.user.UserService;

public class BadgeDecorator extends BaseNotifierDecorator{

    private final BadgeService badgeService;
    private BadgeRepository badgeRepository;

    public BadgeDecorator(INotifier wrapped) {
        super(wrapped);
        this.badgeService = new BadgeService(badgeRepository);
    }

    @Override
    public void send(String message) {

        super.send(message);
        String badgeName = badgeService.getLastBadgeByEmail(getEmail()).getName();
        String badgeType = badgeService.getLastBadgeByEmail(getEmail()).getType();
        String badgeDescription = badgeService.getLastBadgeByEmail(getEmail()).getDescription();

        System.out.println("Sending " + message + " for badge: type = " + badgeType + ", name = " + badgeName +
                ", description = " + badgeDescription);


    }
}
