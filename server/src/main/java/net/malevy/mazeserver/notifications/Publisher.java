package net.malevy.mazeserver.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class Publisher {

    private long TIMEOUT = 60 * 60 * 1000;
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<SseEmitter>> mazePublishers = new ConcurrentHashMap<>();

    public SseEmitter register(String mazeId) {
        if (!StringUtils.hasText(mazeId)) return null;
        final CopyOnWriteArrayList<SseEmitter> emitters = mazePublishers.computeIfAbsent(mazeId, k -> new CopyOnWriteArrayList<>());

        final SseEmitter emitter = new SseEmitter(TIMEOUT);
        emitter.onCompletion(() -> {
            emitters.remove(emitter);
        });
        emitter.onTimeout(emitter::complete);

        emitters.add(emitter);

        return emitter;
    }

    public void unregister(String mazeId, SseEmitter emitter) {
        if (!StringUtils.hasText(mazeId)) return;
        final CopyOnWriteArrayList<SseEmitter> emitters = this.mazePublishers.get(mazeId);
        if (emitters == null) return;
        emitters.remove(emitter);
    }

    public void post(String mazeId, Object obj) {
        if (!StringUtils.hasText(mazeId)) return;
        if (obj == null) return;
        final CopyOnWriteArrayList<SseEmitter> emitters = this.mazePublishers.get(mazeId);
        if (emitters == null) return;

        List<SseEmitter> failedEmitters = new ArrayList<>();
        for(SseEmitter emitter : emitters) {
            try {
                final ObjectMapper mapper = new ObjectMapper();
                SseEmitter.SseEventBuilder event =
                        SseEmitter.event()
                                .name(obj.getClass().getName())
                                .data(mapper.writeValueAsString(obj), MediaType.APPLICATION_JSON);

                emitter.send(event);
            } catch (IOException e) {
                emitter.completeWithError(e);
                failedEmitters.add(emitter);
            } catch (RuntimeException re) {
                System.out.println(re);
            }
        }
        if (!failedEmitters.isEmpty()) emitters.removeAll(failedEmitters);
    }


}
