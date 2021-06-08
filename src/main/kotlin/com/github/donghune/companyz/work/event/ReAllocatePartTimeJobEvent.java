package com.github.donghune.companyz.work.event;

import com.github.donghune.companyz.work.model.PartTimeJob;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class ReAllocatePartTimeJobEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final PartTimeJob partTimeJob;

    public ReAllocatePartTimeJobEvent(PartTimeJob partTimeJob) {
        this.partTimeJob = partTimeJob;
    }

    public PartTimeJob getPartTimeJob() {
        return partTimeJob;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
