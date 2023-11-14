package com.xrosstools.xstate;

/**
 * A helper class that always refuse transition. You can use it for debug
 * @author hejiehui
 *
 */
public class TransitionDenyGuard implements TransitionGuard {

    @Override
    public boolean isTransitAllowed(String sourceId, String targetId, Event event) {
        System.out.println(String.format("Transit from \"%s\" to \"%s\" on \"%s\" is denied", sourceId, targetId, event.getId()));
        return false;
    }
}
