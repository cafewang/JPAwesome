package com.example.justpass.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ConsoleCaptor {
    public CompletableFuture<Boolean> expect(List<String> patternList, int timeoutMs) {
        PrintStream originOut = System.out;
        AtomicReference<String> stringReference = new AtomicReference<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            stringReference.set("");
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 PrintStream printStream = new PrintStream(byteArrayOutputStream)) {
                System.setOut(printStream);
                while (true) {
                    stringReference.set(byteArrayOutputStream.toString());
                    if (patternList.stream().allMatch(pattern -> stringReference.get().contains(pattern))) {
                        break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return CompletableFuture.supplyAsync(() -> {
            try {
                future.get(timeoutMs, TimeUnit.MILLISECONDS);
                return true;
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new IllegalStateException(e);
            } finally {
                System.setOut(originOut);
                System.out.print(stringReference.get());
            }
        });

    }
}
