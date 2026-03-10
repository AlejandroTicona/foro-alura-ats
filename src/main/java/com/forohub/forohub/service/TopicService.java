package com.forohub.forohub.service;

import com.forohub.forohub.model.Topic;
import com.forohub.forohub.repository.TopicRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
  private final TopicRepository repository;

  public TopicService(TopicRepository repository) {
    this.repository = repository;
  }

  public Topic createTopic(Topic topic) {
    return repository.save(topic);
  }

  public List<Topic> getAllTopics() {
    return repository.findAll();
  }

  public Optional<Topic> getTopicById(Long id) {
    return repository.findById(id);
  }

  public Topic updateTopic(Long id, Topic updatedTopic) {
    return repository.findById(id).map(topic -> {
      topic.setTitle(updatedTopic.getTitle());
      topic.setMessage(updatedTopic.getMessage());
      topic.setStatus(updatedTopic.getStatus());
      topic.setAuthor(updatedTopic.getAuthor());
      topic.setCourse(updatedTopic.getCourse());
      return repository.save(topic);
    }).orElse(null);
  }

  public void deleteTopic(Long id) {
    repository.deleteById(id);
  }
}
