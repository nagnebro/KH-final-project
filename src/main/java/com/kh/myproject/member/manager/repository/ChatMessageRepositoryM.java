package com.kh.myproject.member.manager.repository;

import com.kh.myproject.member.chat.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepositoryM extends JpaRepository<ChatMessage, Long> {


}
