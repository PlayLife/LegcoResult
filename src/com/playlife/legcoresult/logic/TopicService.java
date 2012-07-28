package com.playlife.legcoresult.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playlife.legcoresult.persistence.daos.ITopicDAO;
import com.playlife.legcoresult.persistence.domainobjects.Topic;

@Component
public class TopicService {
	@Autowired
	ITopicDAO topicDAO;

	public Topic getById(Long id) {
		return topicDAO.get(id);
	}

	public void save(Topic topic) {
		topicDAO.save(topic);
	}
}
