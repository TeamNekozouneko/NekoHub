package com.nekozouneko.nekohub.spigot.task;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public final class LoopTask extends BukkitRunnable {

    private final Consumer<Void> consumer;
    private final int original_count;
    private int count = 0;

    public LoopTask(int count, Consumer<Void> consumer) {
        this.original_count = count;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        if (original_count >= count) {
            consumer.accept(null);
            count++;
        }
        else cancel();
    }
}
