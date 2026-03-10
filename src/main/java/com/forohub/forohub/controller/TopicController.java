package com.forohub.forohub.controller;

import com.forohub.forohub.model.Topic;
import com.forohub.forohub.service.TopicService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
public class TopicController {

  private final TopicService service;

  public TopicController(TopicService service) {
    this.service = service;
  }

  @PostMapping
  public Topic createTopic(@RequestBody Topic topic) {
    return service.createTopic(topic);
  }

  @GetMapping
  public List<Topic> getAllTopics() {
    return service.getAllTopics();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
    return service.getTopicById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic topic) {
    Topic updated = service.updateTopic(id, topic);
    if (updated == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
    service.deleteTopic(id);
    return ResponseEntity.noContent().build();
  }
}
