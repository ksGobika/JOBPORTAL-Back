package com.jobportal.controller;

import com.jobportal.model.Message;
import com.jobportal.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public List<Message> getMessages(
            @RequestParam(required = false) String senderId,
            @RequestParam(required = false) String receiverId
    ) {
        if (senderId != null && receiverId != null) {
            return messageRepository.findConversationBetween(senderId, receiverId);
        } else if (senderId != null) {
            return messageRepository.findBySenderIdOrReceiverId(senderId, senderId);
        } else if (receiverId != null) {
            return messageRepository.findBySenderIdOrReceiverId(receiverId, receiverId);
        }
        return messageRepository.findAll();
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        if (message.getId() == null || message.getId().trim().isEmpty()) {
            message.setId(UUID.randomUUID().toString());
        }
        if (message.getTimestamp() == null || message.getTimestamp().trim().isEmpty()) {
            message.setTimestamp(new Date().toString());
        }
        if (message.getStatus() == null || message.getStatus().trim().isEmpty()) {
            message.setStatus("delivered");
        }
        return messageRepository.save(message);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Message> updateMessageStatus(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        Optional<Message> msgOpt = messageRepository.findById(id);
        if (msgOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Message msg = msgOpt.get();
        if (updates.containsKey("status") && updates.get("status") != null) {
            msg.setStatus(updates.get("status").toString());
        }
        return ResponseEntity.ok(messageRepository.save(msg));
    }
}
