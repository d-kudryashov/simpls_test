package com.simpls.test.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class UserService {
    private Deque<Integer> deletedUsers;

    UserService() {
        deletedUsers = new ConcurrentLinkedDeque<>(Arrays.asList(0));
    }

    public void increaseDeletedUserCount() {
        if (Objects.nonNull(deletedUsers.peekLast())) {
            deletedUsers.offer(deletedUsers.peekLast() + 1);
        } else {
            deletedUsers.offer(1);
        }

    }

    public int getDeletedUsersCount() {
        if (Objects.nonNull(deletedUsers.peekLast())) {
            return deletedUsers.peekLast();
        } else {
            return 0;
        }
    }
}
